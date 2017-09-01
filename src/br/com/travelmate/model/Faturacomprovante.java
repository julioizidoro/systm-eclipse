package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "faturacomprovante")
public class Faturacomprovante implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfaturacomprovante")
    private Integer idfaturacomprovante;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "nome")
    private String nome;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "fatura_idfatura", referencedColumnName = "idfatura")
    @ManyToOne(optional = false)
    private Fatura fatura;
    
    
    public Faturacomprovante() {
	}


	public Integer getIdfaturacomprovante() {
		return idfaturacomprovante;
	}


	public void setIdfaturacomprovante(Integer idfaturacomprovante) {
		this.idfaturacomprovante = idfaturacomprovante;
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Fatura getFatura() {
		return fatura;
	}


	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
	}
    
    
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idfaturacomprovante != null ? idfaturacomprovante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Faturacomprovante)) {
            return false;
        }
        Faturacomprovante other = (Faturacomprovante) object;
        if ((this.idfaturacomprovante == null && other.idfaturacomprovante != null) || (this.idfaturacomprovante != null && !this.idfaturacomprovante.equals(other.idfaturacomprovante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Faturacomprovante[ idfaturacomprovante=" + idfaturacomprovante + " ]";
    }
    
    
    

}
