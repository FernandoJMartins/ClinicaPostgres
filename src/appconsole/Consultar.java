package appconsole;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import daoJpa.Util;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Consulta;
import modelo.Medico;
import modelo.Paciente;

public class Consultar {
	private EntityManager manager;
	
    public Consultar() {
    	manager = Util.conectarBanco();
        try {
        	TypedQuery<Medico> q1;
        	TypedQuery<Paciente> q2;
        	TypedQuery<Consulta> q3;
        	List<Medico> medicos;
        	List<Paciente> pacientes;
        	List<Consulta> consultas;
            
            System.out.println("\nConsultando médicos com caracteres 'Cardio':");
            q1 = manager.createQuery("select m from Medico m where m.especialidade like :e", Medico.class);
            q1.setParameter("e", "Cardio%");
            medicos = q1.getResultList();
            for (Medico m : medicos) {
            	System.out.println(m);
            }
            
            System.out.println("\nConsultando consultas que sejam do ano de '2024':");
            q3 = manager.createQuery("select c from Consulta c where extract(year from c.data) = :d", Consulta.class);
            q3.setParameter("d", "2024");
            consultas = q3.getResultList();
			for (Consulta c : consultas) {
				System.out.println(c); 
			}
			
			
			System.out.println("\nConsultando consultas de um paciente:");
			q3 = manager.createQuery("select p.consultas from Paciente p where p.cpf = :cpf", Consulta.class);
			q3.setParameter("cpf", "002");
			consultas = q3.getResultList();
			for (Consulta c: consultas) {
				System.out.println(c); 
			}
			 
			
			System.out.println("\nConsultas realizadas no plano em data específica (11/02/2024):");
			LocalDate dataFiltro = LocalDate.parse("11/02/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			q3 = manager.createQuery(
			    "SELECT c FROM Consulta c WHERE c.tipo = 'Plano' AND c.data >= :inicio AND c.data < :fim",
			    Consulta.class
			);
			q3.setParameter("inicio", dataFiltro.atStartOfDay());
			q3.setParameter("fim", dataFiltro.plusDays(1).atStartOfDay());
			
			consultas = q3.getResultList();
			for (Consulta c : consultas) {
			System.out.println(c); }																																					
            
			System.out.println("\nPacientes com mais de 1 consultas:"); 
			q2 = manager.createQuery("select p from Paciente p where size(p.consultas) > :n", Paciente.class);
			q2.setParameter("n", 1);
			pacientes = q2.getResultList();
			for (Paciente p : pacientes) {
				System.out.println(p);
			}
			
			System.out.println("\nPacientes que Se Consultaram com o Medico de crm ABC1:"); 
			q2 = manager.createQuery(
				    "SELECT DISTINCT p FROM Paciente p " +
				    "JOIN p.consultas c " +   
				    "WHERE c.medico.crm = :crm", Paciente.class 
				);
				q2.setParameter("crm", "ABC1");

				pacientes = q2.getResultList();
				for (Paciente p : pacientes) {
				    System.out.println(p);
				}
			

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        Util.fecharBanco();
        System.out.println("\nFim do programa");
    }

    //=================================================
    public static void main(String[] args) {
        new Consultar();
    }
}
