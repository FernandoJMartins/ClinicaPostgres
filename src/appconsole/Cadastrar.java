/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Prof. Fausto Maranh�o Ayres
 **********************************/

package appconsole;

import jakarta.persistence.EntityManager;
import modelo.Consulta;
import modelo.Medico;
import modelo.Paciente;

public class Cadastrar {
	private EntityManager manager;

	public Cadastrar() {
		try {
			manager = Util.conectarBanco();

			System.out.println("Cadastrando...");

			Paciente paciente1, paciente2, paciente3, paciente4, paciente5;
			Medico medico1, medico2, medico3, medico4, medico5;
			Consulta consulta1, consulta2, consulta3, consulta4, consulta5;

            paciente1 = new Paciente("001", "Caioooo");
            paciente2 = new Paciente("002", "Ana");
            paciente3 = new Paciente("003", "Bruno");
            paciente4 = new Paciente("004", "Carla");
            paciente5 = new Paciente("005", "Diego");
            
			
            medico1 = new Medico("Medico 1", "ABC1", "Cardiologista");
            medico2 = new Medico("Medico 2", "ABC2", "Ortopedista");
            medico3 = new Medico("Medico 3", "ABC3", "Pediatra");
            medico4 = new Medico("Medico 4", "ABC4", "Dermatologista");
            medico5 = new Medico("Medico 5", "ABC5", "Neurologista");
			

            manager.getTransaction().begin();
            consulta1 = new Consulta("11/02/2024 08:30", "Plano");
            consulta1.adicionarMedico(medico1);
            consulta1.adicionarPaciente(paciente1);
            manager.persist(consulta1);
            manager.getTransaction().commit();

            
            manager.getTransaction().begin();
            consulta2 = new Consulta("12/02/2025 13:00", "Particular");
            consulta2.adicionarMedico(medico2);
            consulta2.adicionarPaciente(paciente2);
            manager.persist(consulta2);
            manager.getTransaction().commit();
            
            
            manager.getTransaction().begin();
            consulta3 = new Consulta("13/02/2024 15:00", "Plano");
            consulta3.adicionarMedico(medico3);
            consulta3.adicionarPaciente(paciente3);
            manager.persist(consulta3);
            manager.getTransaction().commit();
            
            
            manager.getTransaction().begin();
            consulta4 = new Consulta("14/02/2025 09:00", "Particular");
            consulta4.adicionarMedico(medico4);
            consulta4.adicionarPaciente(paciente4);
            manager.persist(consulta4);
            manager.getTransaction().commit();

            
            manager.getTransaction().begin();
            consulta5 = new Consulta("15/02/2025 10:00", "Plano");
            consulta5.adicionarMedico(medico5);
            consulta5.adicionarPaciente(paciente5);
            manager.persist(consulta5);
            manager.getTransaction().commit();

			

		} catch (Exception e) {
			System.out.println("excecao=" + e.getMessage());
		}
		Util.fecharBanco();
		System.out.println("fim da aplica��o");

	}

	// =================================================
	public static void main(String[] args) {
		new Cadastrar();
	}
	// =================================================

}


