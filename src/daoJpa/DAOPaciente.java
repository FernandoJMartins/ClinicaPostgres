/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package daoJpa;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Paciente;

public class DAOPaciente extends DAO<Paciente>{

	public Paciente read (Object chave){
		try{
			String cpf = (String) chave;
			TypedQuery<Paciente> q = manager.createQuery("select p from Paciente p where p.cpf=:cpf", Paciente.class);
			q.setParameter("cpf", cpf);
			return q.getSingleResult();

		}catch(NoResultException e){
			return null;
		}
	}
	
	public List<Paciente> readAllMaiorQue(int n) {
		TypedQuery<Paciente> q;
		q = manager.createQuery("select p from Paciente p where size(p.consultas) > :n", Paciente.class);
		q.setParameter("n", n);
		return q.getResultList();
	}
	
	public List<Paciente> readAll(String cpf) {
		TypedQuery<Paciente> q = manager.createQuery("select p from Paciente p where p.cpf like :cpf", Paciente.class);
		q.setParameter("cpf", cpf + "%");
        return q.getResultList();
	}

}

