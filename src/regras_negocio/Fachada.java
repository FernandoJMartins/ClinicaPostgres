package regras_negocio;
/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import daoJpa.DAO;
import daoJpa.DAOConsulta;
import daoJpa.DAOMedico;
import daoJpa.DAOPaciente;
import modelo.Consulta;
import modelo.Medico;
import modelo.Paciente;


public class Fachada {
	private Fachada() {}

	private static DAOMedico daoMedico = new DAOMedico();
	private static DAOPaciente daoPaciente = new DAOPaciente();
	private static DAOConsulta daoConsulta = new DAOConsulta();

	public static void inicializar() {
		DAO.open();
	}

	public static void finalizar() {
		DAO.close();
	}

	public static Medico localizarMedico(String crm) throws Exception {
		Medico p = daoMedico.read(crm);
		if (p == null) {
			throw new Exception("pessoa inexistente:" + crm);
		}
		return p;
	}
	
	public static Paciente localizarPaciente(String cpf) throws Exception {
		Paciente a = daoPaciente.read(cpf);
		if (a == null) {
			throw new Exception("paciente inexistente:" + cpf);
		}
		return a;
	}
	
	public static Consulta localizarConsulta (int id) throws Exception {
		Consulta a = daoConsulta.read(id);
		if (a == null) {
			throw new Exception("consulta inexistente:" + id);
		}
		return a;
	}

	public static void criarConsulta(LocalDateTime dataConsulta, String paciente, String medico, String tipo ) throws Exception {
		//PRINCIPAL REGRA DE NEGÓCIO 
		DAO.begin();
		Paciente pacienteLocalizado;
		try {
			pacienteLocalizado = localizarPaciente(paciente);
		} catch (DateTimeParseException e) {
			DAO.rollback();
			throw new Exception("formato data invalido:" + dataConsulta);
		}
		  catch(Exception e){
			throw new Exception("Paciente não localizado:"+ paciente);
		}
		
		
		if (tipo.equals("plano")) {
			
			LocalDateTime dataMin = dataConsulta.minusDays(8);
			LocalDateTime dataMax = dataConsulta.plusDays(8);
			for (Consulta c : pacienteLocalizado.getConsultas()) {
				
				if (c.getData().isAfter(dataMin) || c.getData().isBefore(dataMax))  {
					if (c.getMedico().getCrm().equals(medico) && c.getTipo().equals("plano"))   {
						throw new Exception("Não pode haver duas consultas de um paciente pelo plano num intervalo de 7 dias com o mesmo médico");
					}
				}
			}
		}
		
		Paciente pacientelocalizado = localizarPaciente(paciente);
		Consulta consulta = new Consulta();
		consulta.setData(dataConsulta);
		consulta.setPaciente(pacientelocalizado);
		consulta.setMedico(localizarMedico(medico));
		consulta.setTipo(tipo);
		pacientelocalizado.addConsulta(consulta);
		
		daoConsulta.create(consulta);
		daoPaciente.update(pacientelocalizado);
		DAO.commit();
	}

	public static void criarPaciente(String cpf, String nome) throws Exception {
		DAO.begin();
		Paciente p = daoPaciente.read(cpf);
		if (p != null) {
			DAO.rollback();
			throw new Exception("criar paciente - nome ja existe:" + nome);
		}
		Paciente a = new Paciente(cpf);
		a.setNome(nome);
		
		daoPaciente.create(a);
		DAO.commit();
	}
	
	public static void criarMedico(String nome, String crm, String especialidade) throws Exception {
		DAO.begin();
		Medico m = daoMedico.read(crm);
		if (m != null) {
			DAO.rollback();
			throw new Exception("criar Medico - Medico ja existe:" + nome);
		}
		Medico medico = new Medico(crm);
		medico.setNome(nome);
		medico.setEspecialidade(especialidade);
		daoMedico.create(medico);
		DAO.commit();
	}

	//public static void alterarPaciente(String cpf, String nome, ArrayList<Consulta> consultas) throws Exception {
	public static void alterarPaciente(String cpf, String nome) throws Exception {
	
		// permite alterar data, foto e apelidos
		DAO.begin();
		Paciente p = daoPaciente.read(cpf);
		if (p == null) {
			DAO.rollback();
			throw new Exception("alterar pessoa - pessoa inexistente:" + nome);
		}
		
		//p.setCpf(cpf);
		p.setNome(nome);
		//p.setConsultas(consultas);

		daoPaciente.update(p);
		DAO.commit();
	}

	public static void alterarMedico(String nome, String crm, String especialidade) throws Exception {
		// permite alterar data, foto e apelidos
		DAO.begin();
		Medico m = daoMedico.read(crm);
		if (m == null) {
			DAO.rollback();
			throw new Exception("alterar medico - crm inexistente:" + crm);
		}

		m.setNome(nome);
		//m.setCrm(crm);
		m.setEspecialidade(especialidade);
		
		daoMedico.update(m);
		DAO.commit();
	}

	public static void alterarData(int id, String data) throws Exception {
		DAO.begin();
		Consulta c = daoConsulta.read(id);
		
		if (c == null) {
			DAO.rollback();
			throw new Exception("alterar data - consulta inexistente:" + id);
		}
		
		if (data != null) {
			try {
				LocalDateTime.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			} catch (DateTimeParseException e) {
				DAO.rollback();;
				throw new Exception("formato data invalido:" + data);
			}
		}
		c.setData(LocalDateTime.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
		daoConsulta.update(c);
		DAO.commit();
	}

	public static void excluirMedico(String crm) throws Exception {
		DAO.begin();
		Medico m = daoMedico.read(crm);
		if (m == null) {
			DAO.rollback();
			throw new Exception("excluir Medico - CRM inexistente:" + crm);
		}
		
		List<Consulta> consultas = new ArrayList<>(m.getConsultas());
		for(Consulta c : consultas) {
			m.getConsultas().remove(c);
			
	        Paciente paciente = c.getPaciente();
	        if (paciente != null) {
	            paciente.getConsultas().remove(c);
	        }
	        daoConsulta.delete(c);
		}
		
		daoMedico.delete(m);
		DAO.commit();
	
	}
	
	public static void excluirPaciente(String cpf) throws Exception {
		DAO.begin();
		Paciente p = daoPaciente.read(cpf);
		if (p == null) {
			DAO.rollback();
			throw new Exception("excluir Paciente - cpf inexistente:" + cpf);
		}
		
		List<Consulta> consultas = new ArrayList<>(p.getConsultas());
		for(Consulta c : consultas) {
			p.getConsultas().remove(c);
			
	        Medico medico = c.getMedico();
	        if (medico != null) {
	            medico.getConsultas().remove(c);
	        }
	        daoConsulta.delete(c);
		}
		 
		daoPaciente.delete(p);
		DAO.commit();
	}
	

	public static void excluirConsulta(int numero) throws Exception {
		DAO.begin();
		Consulta c = daoConsulta.read(numero);
		if (c == null) {
			DAO.rollback();
			throw new Exception("excluir consulta - numero inexistente:" + numero);
		}
		
		Paciente p = c.getPaciente();
		if (!(p == null)) {
			p.removeConsulta(c);
		}
		
		
		c.setPaciente(null);
		c.setMedico(null);
		
		daoPaciente.update(p);
		daoConsulta.delete(c);
		DAO.commit();
	}

	public static void alterarTipo(int id, String novoTipo) throws Exception {
		DAO.begin();
		Consulta c = daoConsulta.read(id);
		if (c == null) {
			DAO.rollback();
			throw new Exception("alterar tipo - Consulta inexistente:" + id);
		}
		//Consulta c2 = daoConsulta.read(id);
//		if (c2 != null) {
//			DAO.rollback();
//			throw new Exception("alterar numero - novo numero ja existe:" + id);
//		}
		if (novoTipo.isEmpty()) {
			DAO.rollback();
			throw new Exception("alterar tipo - novo tipo vazio:");
		}

		c.setTipo(novoTipo); // substituir
		daoConsulta.update(c);
		DAO.commit();
	}

	public static List<Medico> listarMedicos() {
		List<Medico> result = daoMedico.readAll();
		return result;
	}

	public static List<Paciente> listarPacientes() {
		List<Paciente> result = daoPaciente.readAll();
		return result;
	}

	public static List<Consulta> listarConsultas() {
		List<Consulta> result = daoConsulta.readAll();
		return result;
	}

	/**********************************************************
	 * 
	 * CONSULTAS IMPLEMENTADAS NOS DAO
	 * 
	 **********************************************************/
	public static List<Medico> consultarMedicos(String caracteres) {
		List<Medico> result;
		if (caracteres.isEmpty())
			result = daoMedico.readAll();
		else
			result = daoMedico.readAll(caracteres);
		return result;
	}


	public static List<Consulta> consultarConsultas(String digitos) {
		LocalDateTime data = LocalDateTime.parse(digitos + " 00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		List<Consulta> result;
		if (digitos.isEmpty())
			result = daoConsulta.readAll();
		else
			result = daoConsulta.readAll(data);
		return result;
	}

	public static List<Paciente> consultarPacientes(String digitos) {
		List<Paciente> result;
		if (digitos.isEmpty())
			result = daoPaciente.readAll();
		else
			result = daoPaciente.readAll(digitos);
		return result;
	}
	
	public static List<Consulta> consultasDoPlanoNaData(LocalDate data) throws Exception{
	    return daoConsulta.readAllPlanoPorData(data);
	}
	
	public static List<Paciente> consultaPacientesSeConsultaramComMedico(String crm){
		List<Paciente> pacientesComConsulta;
		List<Paciente> result = new ArrayList<>();
		pacientesComConsulta = daoPaciente.readAllMaiorQue(0);

		for (Paciente p : pacientesComConsulta) {
			for (Consulta c : p.getConsultas()) {
				if (c.getMedico().getCrm().equals(crm)) {
					if(!result.contains(p)) {
						result.add(p);
					}
				}
			}
		}
		
		return result;
	}
	
	public static List<Paciente> consultaNumeroConsultasMaiorQue(int n){
		List<Paciente> result;
		result = daoPaciente.readAllMaiorQue(n);
		return result;
	}
	
}
