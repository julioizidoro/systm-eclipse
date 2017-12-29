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
public class Corridaprodutoano implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idcorridaprodutoano")
	private Integer idcorridaprodutoano;
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
	
	
	public Corridaprodutoano() {
		// TODO Auto-generated constructor stub
	}


	public Integer getIdcorridaprodutoano() {
		return idcorridaprodutoano;
	}


	public void setIdcorridaprodutoano(Integer idcorridaprodutoano) {
		this.idcorridaprodutoano = idcorridaprodutoano;
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
	
	
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcorridaprodutoano != null ? idcorridaprodutoano.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Corridaprodutoano)) {
            return false;
        }
        Corridaprodutoano other = (Corridaprodutoano) object;
        if ((this.idcorridaprodutoano == null && other.idcorridaprodutoano != null) || (this.idcorridaprodutoano != null && !this.idcorridaprodutoano.equals(other.idcorridaprodutoano))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Corridaprodutoano[ idcorridaprodutoano=" + idcorridaprodutoano + " ]";
    }

}
