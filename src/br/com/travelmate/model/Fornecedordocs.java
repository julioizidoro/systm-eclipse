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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "fornecedordocs")
public class Fornecedordocs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedordocs")
    private Integer idfornecedordocs;
    @Column(name = "nome")
    private String nome;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "nomeftp")
    private String nomeftp;
    @Column(name = "datavalidade")
    @Temporal(TemporalType.DATE)
    private Date datavalidade;
    @Column(name = "datainicio")
    @Temporal(TemporalType.DATE)
    private Date datainicio;
    @Column(name = "restrito")
    private Boolean restrito;
    @Lob
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
    @ManyToOne(optional = false)
    private Fornecedor fornecedor;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "fornecedordocs")
    private List<Fornecedorcidadedocs> fornecedorcidadedocsList;
    @Column(name = "dataupload")
    @Temporal(TemporalType.DATE)
    private Date dataupload;
    @Column(name = "hora")
    private String hora;

    public Fornecedordocs() {
    }

    public Fornecedordocs(Integer idfornecedordocs) {
        this.idfornecedordocs = idfornecedordocs;
    }

    public Integer getIdfornecedordocs() {
        return idfornecedordocs;
    }

    public void setIdfornecedordocs(Integer idfornecedordocs) {
        this.idfornecedordocs = idfornecedordocs;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomeftp() {
        return nomeftp;
    }

    public void setNomeftp(String nomeftp) {
        this.nomeftp = nomeftp;
    }

    public Date getDatavalidade() {
        return datavalidade;
    }

    public void setDatavalidade(Date datavalidade) {
        this.datavalidade = datavalidade;
    }

    public Date getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(Date datainicio) {
        this.datainicio = datainicio;
    }

    public Boolean getRestrito() {
        return restrito;
    }

    public void setRestrito(Boolean restrito) {
        this.restrito = restrito;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<Fornecedorcidadedocs> getFornecedorcidadedocsList() {
        return fornecedorcidadedocsList;
    }

    public void setFornecedorcidadedocsList(List<Fornecedorcidadedocs> fornecedorcidadedocsList) {
        this.fornecedorcidadedocsList = fornecedorcidadedocsList;
    }

    public Date getDataupload() {
		return dataupload;
	}

	public void setDataupload(Date dataupload) {
		this.dataupload = dataupload;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedordocs != null ? idfornecedordocs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fornecedordocs)) {
            return false;
        }
        Fornecedordocs other = (Fornecedordocs) object;
        if ((this.idfornecedordocs == null && other.idfornecedordocs != null) || (this.idfornecedordocs != null && !this.idfornecedordocs.equals(other.idfornecedordocs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fornecedordocs[ idfornecedordocs=" + idfornecedordocs + " ]";
    }
    
}
