package modelo;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;;

@Entity
public class Consulta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDateTime data;
	private String tipo;
	
	public Consulta(String data, String tipo) {
		this.data = LocalDateTime.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		this.tipo = tipo;
	}
	
	public Consulta() {}
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	public Medico medico;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	public Paciente paciente;
	
	
	public void adicionarMedico(Medico m) {
		this.medico = m;
	}
	
	public void adicionarPaciente(Paciente p) {
		this.paciente = p;
	}
	
	public void removerMedico(Medico m) {
		this.medico = null;
	}
	
	public void removerPaciente(Paciente p) {
		this.paciente = null;
	}
	
	public Paciente getPaciente(){
		return paciente;
	}
	
	public Medico getMedico(){
		return medico;
	}
	
	
	
	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime  data) {
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
		return "Consulta [id=" + id + ", data=" + data + ", " + "tipo=" + tipo + ", " + paciente + ", " + medico + "]";
	}
	
}
