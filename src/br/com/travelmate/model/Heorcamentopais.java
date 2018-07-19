package br.com.travelmate.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "heorcamentopais")
public class Heorcamentopais implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idheorcamentopais")
    private Integer idheorcamentopais;
    @Size(max = 100)
    @Column(name = "curso")
    private String curso;
    @Size(max = 7)
    @Column(name = "datainicio")
    private String datainicio;
    @Column(name = "duracao")
    private Integer duracao;
    @Column(name = "valormoedaestrageira")
    private Float valormoedaestrageira;
    @Column(name = "valorreais")
    private Float valorreais;
    @JoinColumn(name = "heorcamento_idheorcamento", referencedColumnName = "idheorcamento")
    @ManyToOne(optional = false)
    private Heorcamento heorcamento;
    @JoinColumn(name = "cidade_idcidade", referencedColumnName = "idcidade")
    @ManyToOne(optional = false)
    private Cidade cidade;
    
	public Heorcamentopais() {
		
	}

	public Integer getIdheorcamentopais() {
		return idheorcamentopais;
	}

	public void setIdheorcamentopais(Integer idheorcamentopais) {
		this.idheorcamentopais = idheorcamentopais;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(String datainicio) {
		this.datainicio = datainicio;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}

	public Float getValormoedaestrageira() {
		return valormoedaestrageira;
	}

	public void setValormoedaestrageira(Float valormoedaestrageira) {
		this.valormoedaestrageira = valormoedaestrageira;
	}

	public Float getValorreais() {
		return valorreais;
	}

	public void setValorreais(Float valorreais) {
		this.valorreais = valorreais;
	}

	public Heorcamento getHeorcamento() {
		return heorcamento;
	}

	public void setHeorcamento(Heorcamento heorcamento) {
		this.heorcamento = heorcamento;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idheorcamentopais == null) ? 0 : idheorcamentopais.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Heorcamentopais other = (Heorcamentopais) obj;
		if (idheorcamentopais == null) {
			if (other.idheorcamentopais != null)
				return false;
		} else if (!idheorcamentopais.equals(other.idheorcamentopais))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Heorcamentopais [idheorcamentopais=" + idheorcamentopais + "]";
	}
    
}
