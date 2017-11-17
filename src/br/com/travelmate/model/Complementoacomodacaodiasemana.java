package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table; 

/**
 *
 * @author Kamila
 */
@Entity
@Table(name = "complementoacomodacaodiasemana")
public class Complementoacomodacaodiasemana implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idcomplementoacomodacaodiasemana")
	private Integer idcomplementoacomodacaodiasemana;
	@Column(name = "diasemanainicial1")
	private Integer diasemanainicial1;
	@Column(name = "diasemanainicial2")
	private Integer diasemanainicial2;
	@Column(name = "diasemanafinal1")
	private Integer diasemanafinal1;
	@Column(name = "diasemanafinal2")
	private Integer diasemanafinal2;
	@JoinColumn(name = "coprodutos_idcoprodutos", referencedColumnName = "idcoprodutos", updatable = true)
	@OneToOne(optional = false)
	private Coprodutos coprodutos;

	public Complementoacomodacaodiasemana() {
	}

	public Integer getIdcomplementoacomodacaodiasemana() {
		return idcomplementoacomodacaodiasemana;
	}

	public void setIdcomplementoacomodacaodiasemana(Integer idcomplementoacomodacaodiasemana) {
		this.idcomplementoacomodacaodiasemana = idcomplementoacomodacaodiasemana;
	} 
	
	public Integer getDiasemanainicial1() {
		return diasemanainicial1;
	}

	public void setDiasemanainicial1(Integer diasemanainicial1) {
		this.diasemanainicial1 = diasemanainicial1;
	}

	public Integer getDiasemanainicial2() {
		return diasemanainicial2;
	}

	public void setDiasemanainicial2(Integer diasemanainicial2) {
		this.diasemanainicial2 = diasemanainicial2;
	}

	public Integer getDiasemanafinal1() {
		return diasemanafinal1;
	}

	public void setDiasemanafinal1(Integer diasemanafinal1) {
		this.diasemanafinal1 = diasemanafinal1;
	}

	public Integer getDiasemanafinal2() {
		return diasemanafinal2;
	}

	public void setDiasemanafinal2(Integer diasemanafinal2) {
		this.diasemanafinal2 = diasemanafinal2;
	}

	public Coprodutos getCoprodutos() {
		return coprodutos;
	}

	public void setCoprodutos(Coprodutos coprodutos) {
		this.coprodutos = coprodutos;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idcomplementoacomodacaodiasemana != null ? idcomplementoacomodacaodiasemana.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Complementoacomodacaodiasemana)) {
			return false;
		}
		Complementoacomodacaodiasemana other = (Complementoacomodacaodiasemana) object;
		if ((this.idcomplementoacomodacaodiasemana == null && other.idcomplementoacomodacaodiasemana != null)
				|| (this.idcomplementoacomodacaodiasemana != null
						&& !this.idcomplementoacomodacaodiasemana.equals(other.idcomplementoacomodacaodiasemana))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Complementoacomodacaodiasemana[ idcomplementoacomodacaodiasemana=" + idcomplementoacomodacaodiasemana
				+ " ]";
	}

}
