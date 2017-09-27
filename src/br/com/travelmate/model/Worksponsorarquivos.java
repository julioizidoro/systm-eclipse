package br.com.travelmate.model;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "worksponsorarquivos")
public class Worksponsorarquivos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idworksponsorarquivos")
    private Integer idworksponsorarquivos;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Size(max = 100)
    @Column(name = "nomeftp")
    private String nomeftp;
    @JoinColumn(name = "tipoarquivo_idtipoArquivo", referencedColumnName = "idtipoArquivo")
    @ManyToOne(optional = false)
    private Tipoarquivo tipoarquivo;
    @JoinColumn(name = "worksponsor_idworksponsor", referencedColumnName = "idworksponsor")
    @ManyToOne(optional = false)
    private Worksponsor worksponsor;
    @Lob
	@Size(max = 2147483647)
	@Column(name = "descricao")
	private String descricao;

    public Worksponsorarquivos() {
    }

    public Worksponsorarquivos(Integer idworksponsorarquivos) {
        this.idworksponsorarquivos = idworksponsorarquivos;
    }

    public Integer getIdworksponsorarquivos() {
        return idworksponsorarquivos;
    }

    public void setIdworksponsorarquivos(Integer idworksponsorarquivos) {
        this.idworksponsorarquivos = idworksponsorarquivos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeftp() {
        return nomeftp;
    }

    public void setNomeftp(String nomeftp) {
        this.nomeftp = nomeftp;
    }

    public Tipoarquivo getTipoarquivo() {
        return tipoarquivo;
    }

    public void setTipoarquivo(Tipoarquivo tipoarquivo) {
        this.tipoarquivo = tipoarquivo;
    }

    public Worksponsor getWorksponsor() {
        return worksponsor;
    }

    public void setWorksponsor(Worksponsor worksponsor) {
        this.worksponsor = worksponsor;
    }

    public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idworksponsorarquivos != null ? idworksponsorarquivos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Worksponsorarquivos)) {
            return false;
        }
        Worksponsorarquivos other = (Worksponsorarquivos) object;
        if ((this.idworksponsorarquivos == null && other.idworksponsorarquivos != null) || (this.idworksponsorarquivos != null && !this.idworksponsorarquivos.equals(other.idworksponsorarquivos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Worksponsorarquivos[ idworksponsorarquivos=" + idworksponsorarquivos + " ]";
    }
    
}
