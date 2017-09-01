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
@Table(name = "fornecedorpacotearquivopagamento")
public class Fornecedorpacotearquivopagamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedorpacotearquivopagamento")
    private Integer idfornecedorpacotearquivopagamento;
    @Size(max = 50)
    @Column(name = "nomearquivo")
    private String nomearquivo;
    @Column(name = "dataanexado")
    @Temporal(TemporalType.DATE)
    private Date dataanexado;
    @Size(max = 100)
    @Column(name = "nomeftp")
    private String nomeftp;
    @JoinColumn(name = "pacotesfornecedor_idpacotesfornecedor", referencedColumnName = "idpacotesfornecedor")
    @ManyToOne(optional = false)
    private Pacotesfornecedor pacotesfornecedor;

    public Fornecedorpacotearquivopagamento() {
    } 

    public Integer getIdfornecedorpacotearquivopagamento() {
		return idfornecedorpacotearquivopagamento;
	} 

	public void setIdfornecedorpacotearquivopagamento(Integer idfornecedorpacotearquivopagamento) {
		this.idfornecedorpacotearquivopagamento = idfornecedorpacotearquivopagamento;
	} 

	public String getNomearquivo() {
        return nomearquivo;
    }

    public void setNomearquivo(String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }

    public Date getDataanexado() {
        return dataanexado;
    }

    public void setDataanexado(Date dataanexado) {
        this.dataanexado = dataanexado;
    }

    public String getNomeftp() {
        return nomeftp;
    }

    public void setNomeftp(String nomeftp) {
        this.nomeftp = nomeftp;
    }

    public Pacotesfornecedor getPacotesfornecedor() {
        return pacotesfornecedor;
    }

    public void setPacotesfornecedor(Pacotesfornecedor pacotesfornecedor) {
        this.pacotesfornecedor = pacotesfornecedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorpacotearquivopagamento != null ? idfornecedorpacotearquivopagamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fornecedorpacotearquivopagamento)) {
            return false;
        }
        Fornecedorpacotearquivopagamento other = (Fornecedorpacotearquivopagamento) object;
        if ((this.idfornecedorpacotearquivopagamento == null && other.idfornecedorpacotearquivopagamento != null) || (this.idfornecedorpacotearquivopagamento != null && !this.idfornecedorpacotearquivopagamento.equals(other.idfornecedorpacotearquivopagamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fornecedorpacotearquivopagamento[ idfornecedorpacotearquivopagamento=" + idfornecedorpacotearquivopagamento + " ]";
    }
    
}
