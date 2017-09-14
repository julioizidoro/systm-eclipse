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
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "cartaocredito")
public class Cartaocredito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcartaocredito")
    private Integer idcartaocredito;
    @Size(max = 45)
    @Column(name = "nome")
    private String nome;
    @Size(max = 16)
    @Column(name = "numero")
    private String numero;
    @Size(max = 5)
    @Column(name = "validade")
    private String validade;
    @Size(max = 50)
    @Column(name = "bandeira")
    private String bandeira;
    @Size(max = 3)
    @Column(name = "cv")
    private String cv;
    @JoinColumn(name = "banco_idbanco", referencedColumnName = "idbanco")
    @ManyToOne(optional = false)
    private Banco banco;

    public Cartaocredito() {
    }

    public Cartaocredito(Integer idcartaocredito) {
        this.idcartaocredito = idcartaocredito;
    }

    public Integer getIdcartaocredito() {
        return idcartaocredito;
    }

    public void setIdcartaocredito(Integer idcartaocredito) {
        this.idcartaocredito = idcartaocredito;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcartaocredito != null ? idcartaocredito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cartaocredito)) {
            return false;
        }
        Cartaocredito other = (Cartaocredito) object;
        if ((this.idcartaocredito == null && other.idcartaocredito != null) || (this.idcartaocredito != null && !this.idcartaocredito.equals(other.idcartaocredito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Cartaocredito[ idcartaocredito=" + idcartaocredito + " ]";
    }
    
}

