package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Consulta;
import modelo.Medico;
import modelo.Paciente;

public class Listar {
	private EntityManager manager;
	
    public Listar() {
    	manager = Util.conectarBanco();
    	try {
        	TypedQuery<Medico> q1;
        	TypedQuery<Paciente> q2;
        	TypedQuery<Consulta> q3;
        	List<Medico> medicos;
        	List<Paciente> pacientes;
        	List<Consulta> consultas;

            System.out.println("\nListando todos os médicos:");
            q1 = manager.createQuery("select m from Medico m", Medico.class); 
            medicos = q1.getResultList();
            for (Medico m : medicos) {
                System.out.println(m);
            }

            System.out.println("\nListando todos os pacientes:");
            q2 = manager.createQuery("select p from Paciente p", Paciente.class);
            pacientes = q2.getResultList();
            for (Paciente p : pacientes) {
                System.out.println(p);
            }
            
            System.out.println("\nListando todas as consultas:");
            q3 = manager.createQuery("select c from Consulta c", Consulta.class);
            consultas = q3.getResultList();
            for (Consulta c : consultas) {
                System.out.println(c);
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Util.fecharBanco();
        }
    }

    //=================================================
    public static void main(String[] args) {
        new Listar();
    }
}
