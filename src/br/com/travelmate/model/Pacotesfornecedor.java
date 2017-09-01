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
 * @author wolverine
 */
@Entity
@Table(name = "pacotesfornecedor")
public class Pacotesfornecedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpacotesfornecedor")
    private Integer idpacotesfornecedor;
    @Column(name = "datapagamento")
    @Temporal(TemporalType.DATE)
    private Date datapagamento;
    @Column(name = "idprodutotrecho")
    private Integer idprodutotrecho;
    @Size(max = 45)
    @Column(name = "tipoprodutotrecho")
    private String tipoprodutotrecho;
    @Column(name = "comprovante")
    private boolean comprovante;
    @Column(name = "emailenviado")
    private boolean emailenviado;
    @Column(name = "valor")
    private float valor;
    @Column(name = "numeroreserva")
    private String numeroreserva; 
    @JoinColumn(name = "pacotes_idpacotes", referencedColumnName = "idpacotes")
    @ManyToOne(optional = false)
    private Pacotes pacotes;
    @JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
    @ManyToOne(optional = false)
    private Fornecedor fornecedor;

    public Pacotesfornecedor() {
    }

    public Pacotesfornecedor(Integer idpacotesfornecedor) {
        this.idpacotesfornecedor = idpacotesfornecedor;
    }

    public Integer getIdpacotesfornecedor() {
        return idpacotesfornecedor;
    }

    public void setIdpacotesfornecedor(Integer idpacotesfornecedor) {
        this.idpacotesfornecedor = idpacotesfornecedor;
    }

    public Date getDatapagamento() {
        return datapagamento;
    }

    public void setDatapagamento(Date datapagamento) {
        this.datapagamento = datapagamento;
    }

    public Integer getIdprodutotrecho() {
        return idprodutotrecho;
    }

    public void setIdprodutotrecho(Integer idprodutotrecho) {
        this.idprodutotrecho = idprodutotrecho;
    }

    public String getTipoprodutotrecho() {
        return tipoprodutotrecho;
    }

    public void setTipoprodutotrecho(String tipoprodutotrecho) {
        this.tipoprodutotrecho = tipoprodutotrecho;
    }

    public Pacotes getPacotes() {
        return pacotes;
    }

    public void setPacotes(Pacotes pacotes) {
        this.pacotes = pacotes;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public boolean isComprovante() {
		return comprovante;
	}

	public void setComprovante(boolean comprovante) {
		this.comprovante = comprovante;
	}

	public boolean isEmailenviado() {
		return emailenviado;
	}

	public void setEmailenviado(boolean emailenviado) {
		this.emailenviado = emailenviado;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public String getNumeroreserva() {
		return numeroreserva;
	}

	public void setNumeroreserva(String numeroreserva) {
		this.numeroreserva = numeroreserva;
	} 

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpacotesfornecedor != null ? idpacotesfornecedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pacotesfornecedor)) {
            return false;
        }
        Pacotesfornecedor other = (Pacotesfornecedor) object;
        if ((this.idpacotesfornecedor == null && other.idpacotesfornecedor != null) || (this.idpacotesfornecedor != null && !this.idpacotesfornecedor.equals(other.idpacotesfornecedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pacotesfornecedor[ idpacotesfornecedor=" + idpacotesfornecedor + " ]";
    }
    
}

