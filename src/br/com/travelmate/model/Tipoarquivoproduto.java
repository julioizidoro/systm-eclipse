package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "tipoarquivoproduto")
public class Tipoarquivoproduto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipoarquivoproduto")
    private Integer idtipoarquivoproduto;
    @JoinColumn(name = "tipoarquivo_idtipoArquivo", referencedColumnName = "idtipoArquivo")
    @ManyToOne(optional = false)
    private Tipoarquivo tipoarquivo;
    @JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
    @ManyToOne(optional = false)
    private Produtos produtos;
    @Column(name = "observacao")
    private String observacao;

    public Tipoarquivoproduto() {
    }

    public Tipoarquivoproduto(Integer idtipoarquivoproduto) {
        this.idtipoarquivoproduto = idtipoarquivoproduto;
    }

    public Integer getIdtipoarquivoproduto() {
        return idtipoarquivoproduto;
    }

    public void setIdtipoarquivoproduto(Integer idtipoarquivoproduto) {
        this.idtipoarquivoproduto = idtipoarquivoproduto;
    }

    public Tipoarquivo getTipoarquivo() {
        return tipoarquivo;
    }

    public void setTipoarquivo(Tipoarquivo tipoarquivo) {
        this.tipoarquivo = tipoarquivo;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoarquivoproduto != null ? idtipoarquivoproduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoarquivoproduto)) {
            return false;
        }
        Tipoarquivoproduto other = (Tipoarquivoproduto) object;
        if ((this.idtipoarquivoproduto == null && other.idtipoarquivoproduto != null) || (this.idtipoarquivoproduto != null && !this.idtipoarquivoproduto.equals(other.idtipoarquivoproduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Tipoarquivoproduto[ idtipoarquivoproduto=" + idtipoarquivoproduto + " ]";
    }
    
}
