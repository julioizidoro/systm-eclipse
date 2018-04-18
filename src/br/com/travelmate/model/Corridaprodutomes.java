package br.com.travelmate.model;

import java.io.Serializable;

import javax.inject.Named;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Named
@Entity
public class Corridaprodutomes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idcorridaprodutomes")
	private Integer idcorridaprodutomes;
	@Column(name = "mes")
	private Integer mes;
	@Column(name = "ano")
	private Integer ano;
	@Column(name = "pontos")
	private Integer pontos;
	@JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
	@ManyToOne(optional = false)
	private Usuario usuario;
	@JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
	@ManyToOne(optional = false)
	private Produtos produtos;
	@Column(name = "pontosnegativo")
	private Integer pontosnegativo;
	
	
	
	public Corridaprodutomes() {
	
	}


	public Integer getIdcorridaprodutomes() {
		return idcorridaprodutomes;
	}


	public void setIdcorridaprodutomes(Integer idcorridaprodutomes) {
		this.idcorridaprodutomes = idcorridaprodutomes;
	}


	public Integer getMes() {
		return mes;
	}


	public void setMes(Integer mes) {
		this.mes = mes;
	}


	public Integer getAno() {
		return ano;
	}


	public void setAno(Integer ano) {
		this.ano = ano;
	}


	public Integer getPontos() {
		return pontos;
	}


	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Produtos getProdutos() {
		return produtos;
	}


	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}
	
	
	
	
	public Integer getPontosnegativo() {
		return pontosnegativo;
	}


	public void setPontosnegativo(Integer pontosnegativo) {
		this.pontosnegativo = pontosnegativo;
	}


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcorridaprodutomes != null ? idcorridaprodutomes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Corridaprodutomes)) {
            return false;
        }
        Corridaprodutomes other = (Corridaprodutomes) object;
        if ((this.idcorridaprodutomes == null && other.idcorridaprodutomes != null) || (this.idcorridaprodutomes != null && !this.idcorridaprodutomes.equals(other.idcorridaprodutomes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Corridaprodutomes[ idcorridaprodutomes=" + idcorridaprodutomes + " ]";
    }

}
