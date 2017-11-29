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
@Table(name = "credito")
public class Credito implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idcredito")
	private Integer idcredito;
	@Column(name = "tipocredito")
	private String tipocredito;
	@Column(name = "valorcredito")
    private Float valorcredito;
	@Column(name = "datautilizacao")
	@Temporal(TemporalType.DATE)
	private Date datautilizacao;
	@JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
	@ManyToOne(optional = false)
	private Usuario usuario;
	@JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
	@ManyToOne(optional = false)
	private Cliente cliente;
	
	
	public Credito() {
		// TODO Auto-generated constructor stub
	}


	public Integer getIdcredito() {
		return idcredito;
	}


	public void setIdcredito(Integer idcredito) {
		this.idcredito = idcredito;
	}


	public String getTipocredito() {
		return tipocredito;
	}


	public void setTipocredito(String tipocredito) {
		this.tipocredito = tipocredito;
	}


	public Float getValorcredito() {
		return valorcredito;
	}


	public void setValorcredito(Float valorcredito) {
		this.valorcredito = valorcredito;
	}


	public Date getDatautilizacao() {
		return datautilizacao;
	}


	public void setDatautilizacao(Date datautilizacao) {
		this.datautilizacao = datautilizacao;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
	
	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcredito != null ? idcredito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Credito)) {
            return false;
        }
        Credito other = (Credito) object;
        if ((this.idcredito == null && other.idcredito != null) || (this.idcredito != null && !this.idcredito.equals(other.idcredito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Credito[ idcredito=" + idcredito + " ]";
    }

}
