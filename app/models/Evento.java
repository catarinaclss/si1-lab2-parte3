package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.Constraints.Required;

/**
 * Classe representa um evento Hackfest
 * 
 * @author Marcus Vinicius
 * @since 2014
 * 
 */

@Entity
public class Evento implements Comparable<Evento> {
	@Id
	@GeneratedValue
	private long id;

	@Column
	@Required(message = "Nome Obrigatório")
	private String nome;

	@Column
	@Required(message = "Descrição Obrigatória")
	private String descricao;

	@Column
	@Required(message = "Data Obrigatória")
	private String data;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Column(name = "temas")
	private List<Tema> temas;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Column(name = "participantes")
	private List<Participante> participantes;


	public Evento() {
		this.participantes = new ArrayList<Participante>();

	}

	public List<Tema> getTemas() {
		return temas;
	}

	public void setTemas(List<Tema> temas) {
		this.temas = temas;
	}

	public boolean cadastrarParticipante(Participante novoParticipante) {
		for (Participante participante : participantes) {
			if (participante.equals(novoParticipante)) {
				return false;
			}
		}
		return participantes.add(novoParticipante);
	}

	public boolean removerParticipante(long id) {
		for (Participante participante : participantes) {
			if (participante.getId() == id) {
				return participantes.remove(participante);
			}
		}
		return false;
	}

	public boolean cadastrarTema(Tema novoTema) {
		
		for (Tema tema : temas) {
			if (tema.equals(novoTema)) {
				return false;
			}
		}
		return temas.add(novoTema);
	}

	public boolean removerTema(long id) {
		for (Tema tema : temas) {
			if (tema.getId() == id){
				return temas.remove(tema);
			}
		}
		return false;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getData(){
		return data;
	}
	
	public void setData(String data){
		this.data = data;
	}

	public List<Participante> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Participante> participantes) {
		this.participantes = participantes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int compareTo(Evento evento) {
		if (this.participantes.size() > evento.getParticipantes().size()) {
			return -1;
		} else if (this.participantes.size() < evento.getParticipantes().size()) {
			return 1;
		} else {
			return 0;
		}
	}
}
