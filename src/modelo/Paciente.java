package modelo;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Paciente {
	@Id
	private String cpf;
	private String nome;
	
	
	@OneToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	private ArrayList<Consulta> consultas = new ArrayList<>();
	
	
	public void adicionarConsulta(Consulta c){
		consultas.add(c);
	}
	public void removerLivro(Consulta c){
		consultas.remove(c);
	}
	
	
	
	public Paciente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
	}
	public Paciente(String cpf) {
		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(ArrayList<Consulta> consultas) {
		this.consultas = consultas;
	}

	public void addConsulta(Consulta consulta) {
		this.consultas.add(consulta);
	}
	
	public void removeConsulta(Consulta consulta) {
		this.consultas.remove(consulta);
	}

	@Override
	public String toString() {
		String texto = " nome=" +  nome + ", cpf" + cpf;

		texto += "  consultas: ";
		for(Consulta c : consultas)
			texto += c.getId() + ",";

		return texto;
	}
	
	
}
