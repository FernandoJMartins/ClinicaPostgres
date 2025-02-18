package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

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
	
	@OneToOne(mappedBy = "consulta", cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	public List<Medico> medicos = new ArrayList<>();
	
	@OneToOne(mappedBy = "consulta", cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	public List<Paciente> alunos = new ArrayList<>();
	
	
	
	
	
	
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

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
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
