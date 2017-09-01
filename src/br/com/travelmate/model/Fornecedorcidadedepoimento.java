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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author jizid
 */
@Entity
@Table(name = "fornecedorcidadedepoimento")
public class Fornecedorcidadedepoimento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedorcidadedepoimento")
    private Integer idfornecedorcidadedepoimento;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "apovado")
    private Boolean apovado;
    @Lob
    @Size(max = 16777215)
    @Column(name = "texto")
    private String texto;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @Column(name = "aceitacontato")
    private boolean aceitacontato;
    
    

    public Fornecedorcidadedepoimento() {
    }

    public Fornecedorcidadedepoimento(Integer idfornecedorcidadedepoimento) {
        this.idfornecedorcidadedepoimento = idfornecedorcidadedepoimento;
    }

    public Integer getIdfornecedorcidadedepoimento() {
        return idfornecedorcidadedepoimento;
    }

    public void setIdfornecedorcidadedepoimento(Integer idfornecedorcidadedepoimento) {
        this.idfornecedorcidadedepoimento = idfornecedorcidadedepoimento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Boolean getApovado() {
        return apovado;
    }

    public void setApovado(Boolean apovado) {
        this.apovado = apovado;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
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

	public boolean isAceitacontato() {
		return aceitacontato;
	}

	public void setAceitacontato(boolean aceitacontato) {
		this.aceitacontato = aceitacontato;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorcidadedepoimento != null ? idfornecedorcidadedepoimento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fornecedorcidadedepoimento)) {
            return false;
        }
        Fornecedorcidadedepoimento other = (Fornecedorcidadedepoimento) object;
        if ((this.idfornecedorcidadedepoimento == null && other.idfornecedorcidadedepoimento != null) || (this.idfornecedorcidadedepoimento != null && !this.idfornecedorcidadedepoimento.equals(other.idfornecedorcidadedepoimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(getIdfornecedorcidadedepoimento());
    }
    
}

