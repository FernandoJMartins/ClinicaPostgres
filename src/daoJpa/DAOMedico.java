/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

package daoJpa;

import java.util.List;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Medico;

public class DAOMedico extends DAO<Medico> {

	public Medico read(Object chave) {
		try {
			String crm = (String) chave;
			TypedQuery<Medico> q = manager.createQuery("select m from Medico m where m.crm=:crm", Medico.class);
			q.setParameter("crm", crm);
			return q.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Medico> readAll(String caracteres) {
		TypedQuery<Medico> q = manager.createQuery("select m from Medico m where m.crm like :crm", Medico.class); 
		q.setParameter("crm", caracteres + "%");
        return q.getResultList();
	}

}
