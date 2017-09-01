package br.com.travelmate.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "midias")
public class Midias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmidias")
    private Integer idmidias;
    @Column(name = "titulo")
    private String titulo;
    @Lob
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "link")
    private String link;
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "tipo")
    private String tipo;
	
    
    public Integer getIdmidias() {
		return idmidias;
	}
	
	
	public void setIdmidias(Integer idmidias) {
		this.idmidias = idmidias;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
    
	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idmidias!= null ? idmidias.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Midias)) {
            return false;
        }
        Midias other = (Midias) object;
        if ((this.idmidias == null && other.idmidias!= null) || (this.idmidias!= null && !this.idmidias.equals(other.idmidias))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Midias[ idmidias=" + idmidias + " ]";
    }
    
    
}
