package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;;

@Entity
public class Consulta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String data;
	private Paciente paciente;
	private Medico medico;
	private String tipo;
	
	public Consulta(String data, String tipo) {
		this.data = data;
		this.tipo = tipo;
	}
	
	@ManyToMany(mappedBy = "consultas", cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	public List<Medico> medicos = new ArrayList<>();
	
	@ManyToMany(mappedBy = "consultas", cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	public List<Paciente> pacientes = new ArrayList<>();
	
	
	public void adicionarMedico(Medico m) {
		medicos.add(m);
	}
	
	public void adicionarPaciente(Paciente p) {
		pacientes.add(p);
	}
	
	public void removerMedico(Medico m) {
		medicos.remove(m);
	}
	
	public void removerPaciente(Paciente p) {
		pacientes.remove(p);
	}
	
	public List<Paciente> getPaciente(){
		return pacientes;
	}
	
	public List<Medico> getMedico(){
		return medicos;
	}
	
	
	
	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	
	
	
	
	public Consulta() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}



	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Consulta [id=" + id + ", data=" + data + ", paciente=" + paciente + ", medico=" + medico + ", tipo="
				+ tipo + "]";
	}
	
}
