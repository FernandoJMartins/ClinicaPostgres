/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package daoJpa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Consulta;
import modelo.Paciente;

public class DAOConsulta extends DAO<Consulta> {

	public Consulta read(Object chave) {
		try {
			int id = (int) chave;
			TypedQuery<Consulta> q = manager.createQuery("select c from Consulta c where c.id = :id ",
					Consulta.class);
			q.setParameter("id", id);

			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Consulta> readAll(LocalDateTime data) {
		LocalDate dataFiltro = data.toLocalDate();
		TypedQuery<Consulta> q = manager.createQuery(
		    "SELECT c FROM Consulta c WHERE c.tipo = 'Plano' AND c.data >= :inicio AND c.data < :fim",
		    Consulta.class
		);
		q.setParameter("inicio", dataFiltro.atStartOfDay());
		q.setParameter("fim", dataFiltro.plusDays(1).atStartOfDay());
		
		return q.getResultList();
	}
	
	
	
	public List<Consulta> readAllPlanoPorData(String data) {
		TypedQuery<Consulta> q;
		q = manager.createQuery("select c from Consulta c where c.tipo = 'Plano' "
				+ "and extract(day from c.data) = :day "
		        + "and extract(month from c.data) = :month "
		        + "and extract(year from c.data) = :year", Consulta.class);
		q.setParameter("day", 11);
		q.setParameter("month", 2);
		q.setParameter("year", 2024);
		return q.getResultList();
	}
	
	public List<Consulta> consultaPacientesSeConsultaramComMedico(String crm){
		TypedQuery<Consulta> q;
		q = manager.createQuery(
			    "SELECT DISTINCT p FROM Paciente p " +
			    "JOIN p.consultas c " +   
			    "WHERE c.medico.crm = :crm", Consulta.class 
			);
			q.setParameter("crm", crm);

			return q.getResultList();
			
	}

}
