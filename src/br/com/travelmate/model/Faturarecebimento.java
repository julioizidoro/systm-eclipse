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

@Entity
@Table(name = "Faturarecebimento")
public class Faturarecebimento implements Serializable{

	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFaturarecebimento")
    private Integer idfaturarecebimento;
    @JoinColumn(name = "fatura_idfatura", referencedColumnName = "idfatura")
    @ManyToOne(optional = false)
    private Fatura fatura;
    @JoinColumn(name = "contaspagar_idcontaspagar", referencedColumnName = "idcontaspagar")
    @ManyToOne(optional = false)
    private Contaspagar contaspagar;
    
	public Faturarecebimento() {
	
	}

	public Integer getIdfaturarecebimento() {
		return idfaturarecebimento;
	}

	public void setIdfaturarecebimento(Integer idfaturarecebimento) {
		this.idfaturarecebimento = idfaturarecebimento;
	}

	public Fatura getFatura() {
		return fatura;
	}

	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
	}

	public Contaspagar getContaspagar() {
		return contaspagar;
	}

	public void setContaspagar(Contaspagar contaspagar) {
		this.contaspagar = contaspagar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idfaturarecebimento == null) ? 0 : idfaturarecebimento.hashCode());
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
		Faturarecebimento other = (Faturarecebimento) obj;
		if (idfaturarecebimento == null) {
			if (other.idfaturarecebimento != null)
				return false;
		} else if (!idfaturarecebimento.equals(other.idfaturarecebimento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Faturarecebimento [idfaturarecebimento=" + idfaturarecebimento + "]";
	}
	
	
    
    
}
