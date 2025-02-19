package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Medico {
	@Id
	private String crm;
	private String nome;
	private String especialidade;
	
	@OneToMany(mappedBy = "medico", cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Consulta> consultas = new ArrayList<>();
	
	
	public void adicionarConsulta(Consulta c){
		consultas.add(c);
	}
	public void removerConsulta(Consulta c){
		consultas.remove(c);
	}
	
	public Medico() {}
	
	public Medico(String nome, String crm, String especialidade) {
		this.nome = nome;
		this.crm = crm;
		this.especialidade = especialidade;
	}
	
	public Medico(String crm) {
		this.crm = crm;
	}
	
	public String getCrm() {
		return crm;
	}
	public void setCrm(String crm) {
		this.crm = crm;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEspecialidade() {
		return especialidade;
	}
	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	@Override
	public String toString() {
		return "Medico [crm=" + crm + ", nome=" + nome + ", especialidade=" + especialidade + "]";
	}
	
	
}
