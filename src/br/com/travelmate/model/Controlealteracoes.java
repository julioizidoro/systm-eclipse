package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "controlealteracoes")
public class Controlealteracoes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontrolealteracoes")
    private Integer idcontrolealteracoes;
    @Column(name = "dataalteracao")
    @Temporal(TemporalType.DATE)
    private Date dataalteracao;
    @Column(name = "verificado")
    private Boolean verificado;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "controlealteracoes")
    private List<Dadosalteracoes> dadosalteracoesList;
    @JoinColumn(name = "departamentoproduto_iddepartamentoproduto", referencedColumnName = "iddepartamentoproduto")
    @ManyToOne(optional = false)
    private Departamentoproduto departamentoproduto;

    public Controlealteracoes() {
    }

    public Controlealteracoes(Integer idcontrolealteracoes) {
        this.idcontrolealteracoes = idcontrolealteracoes;
    }

    public Integer getIdcontrolealteracoes() {
        return idcontrolealteracoes;
    }

    public void setIdcontrolealteracoes(Integer idcontrolealteracoes) {
        this.idcontrolealteracoes = idcontrolealteracoes;
    }

    public Date getDataalteracao() {
        return dataalteracao;
    }

    public void setDataalteracao(Date dataalteracao) {
        this.dataalteracao = dataalteracao;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Dadosalteracoes> getDadosalteracoesList() {
        return dadosalteracoesList;
    }

    public void setDadosalteracoesList(List<Dadosalteracoes> dadosalteracoesList) {
        this.dadosalteracoesList = dadosalteracoesList;
    }
    
    

    public Departamentoproduto getDepartamentoproduto() {
		return departamentoproduto;
	}

	public void setDepartamentoproduto(Departamentoproduto departamentoproduto) {
		this.departamentoproduto = departamentoproduto;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontrolealteracoes != null ? idcontrolealteracoes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Controlealteracoes)) {
            return false;
        }
        Controlealteracoes other = (Controlealteracoes) object;
        if ((this.idcontrolealteracoes == null && other.idcontrolealteracoes != null) || (this.idcontrolealteracoes != null && !this.idcontrolealteracoes.equals(other.idcontrolealteracoes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Controlealteracoes[ idcontrolealteracoes=" + idcontrolealteracoes + " ]";
    }
    
}

