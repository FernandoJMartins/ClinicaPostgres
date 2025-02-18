/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Prof. Fausto Maranh�o Ayres
 **********************************/

package appconsole;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
//			Autor joao, maria, jose, paulo;
			Paciente paciente1;
			Medico medico1, medico2;
			Consulta consulta1, consulta2;
			
			paciente1 = new Paciente("001", "Caioooo");
			medico1 = new Medico("Medico 1", "ABC1", "Cardiologista");
			medico2 = new Medico("Medico 2", "ABC2", "Cardiologista");
			
			consulta1 = new Consulta("11/02/2000", "001", "ABC1", "Plano");

			dataConsulta = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			
			consulta2 = new Consulta("11/02/2000", "Plano");
		
//			maria = new Autor("000.002.002-22", "maria");
//			jose = 	new Autor("000.003.003-33", "jose");
//			paulo = new Autor("000.004.004-44", "paulo");

			manager.getTransaction().begin();
			Livro java = new Livro(10101010, "java", 2016);
			java.adicionarAutor(joao);
			java.adicionarAutor(maria);
			joao.adicionarLivro(java);
			maria.adicionarLivro(java);
			manager.persist(java);
			manager.getTransaction().commit();

			
			manager.getTransaction().begin();
			Livro c = new Livro(20202020, "c",  2015);
			c.adicionarAutor(joao);
			c.adicionarAutor(jose);
			joao.adicionarLivro(c);
			jose.adicionarLivro(c);
			manager.persist(c);
			manager.getTransaction().commit();


			manager.getTransaction().begin();
			Livro php = new Livro(30303030, "php", 2015);
			php.adicionarAutor(joao);
			php.adicionarAutor(paulo);
			joao.adicionarLivro(php);
			paulo.adicionarLivro(php);
			manager.persist(php);
			manager.getTransaction().commit();


			manager.getTransaction().begin();
			Livro python = new Livro(40404040, "python",  2017);
			python.adicionarAutor(joao);
			python.adicionarAutor(maria);
			python.adicionarAutor(jose);
			python.adicionarAutor(paulo);
			joao.adicionarLivro(python);
			maria.adicionarLivro(python);
			jose.adicionarLivro(python);
			paulo.adicionarLivro(python);
			manager.persist(python);
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


