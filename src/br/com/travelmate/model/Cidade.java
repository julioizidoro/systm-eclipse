/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "cidade")
public class Cidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcidade")
    private Integer idcidade;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Column(name = "ativa")
    private boolean ativa;
    @Column(name = "acomodacaoindepentende")
    private boolean acomodacaoindepentende;
    @JoinColumn(name = "pais_idpais", referencedColumnName = "idpais")
    @ManyToOne(optional = false)
    private Pais pais; 
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "cidade") 
	private List<Cidadepaisproduto> cidadepaisList; 
    
    public Cidade() {
    		setAtiva(true);
    }

    public Cidade(Integer idcidade) {
        this.idcidade = idcidade;
    }

    public Integer getIdcidade() {
        return idcidade;
    }

    public void setIdcidade(Integer idcidade) {
        this.idcidade = idcidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
 

	public List<Cidadepaisproduto> getCidadepaisList() {
		return cidadepaisList;
	}

	public void setCidadepaisList(List<Cidadepaisproduto> cidadepaisList) {
		this.cidadepaisList = cidadepaisList;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}

	public boolean isAcomodacaoindepentende() {
		return acomodacaoindepentende;
	}

	public void setAcomodacaoindepentende(boolean acomodacaoindepentende) {
		this.acomodacaoindepentende = acomodacaoindepentende;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcidade != null ? idcidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Cidade)) {
            return false;
        }
        Cidade other = (Cidade) object;
        if ((this.idcidade == null && other.idcidade != null) || (this.idcidade != null && !this.idcidade.equals(other.idcidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Cidade[ idcidade=" + idcidade + " ]";
    }
    
}
