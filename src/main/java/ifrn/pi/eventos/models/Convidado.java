package ifrn.pi.eventos.models;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Convidado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String nome;
	private String rg;
	
	@ManyToOne
	private Evento evento;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	@Override
	public String toString() {
		return "Convidado [Id=" + Id + ", nome=" + nome + ", rg=" + rg + ", evento=" + evento + "]";
	}
	
	
}
