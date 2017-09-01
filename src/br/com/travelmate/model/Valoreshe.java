/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(name = "valoreshe")
public class Valoreshe implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvaloreshe")
    private Integer idvaloreshe; 
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Float valor;  
    @Column(name = "situacao")
    private boolean situacao;  
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade; 
    @JoinColumn(name = "moedas_idmoedas", referencedColumnName = "idmoedas")
    @ManyToOne(optional = false)
    private Moedas moedas; 

    public Valoreshe() {
    }
  
	public Integer getIdvaloreshe() {
		return idvaloreshe;
	}
 
	public void setIdvaloreshe(Integer idvaloreshe) {
		this.idvaloreshe = idvaloreshe;
	}
 
	public Float getValor() {
		return valor;
	} 
	
	public void setValor(Float valor) {
		this.valor = valor;
	} 
	
	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	} 
	
	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}
 
	public boolean isSituacao() {
		return situacao;
	}

	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}

	public Moedas getMoedas() {
		return moedas;
	}

	public void setMoedas(Moedas moedas) {
		this.moedas = moedas;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idvaloreshe != null ? idvaloreshe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Valoreshe)) {
            return false;
        }
        Valoreshe other = (Valoreshe) object;
        if ((this.idvaloreshe == null && other.idvaloreshe != null) || (this.idvaloreshe != null && !this.idvaloreshe.equals(other.idvaloreshe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Valoreshe[ idvaloreshe=" + idvaloreshe + " ]";
    }
    
}
