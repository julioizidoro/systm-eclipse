package br.com.travelmate.model;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "voluntariadoprojeto")

public class Voluntariadoprojeto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvoluntariadoprojeto")
    private Integer idvoluntariadoprojeto;
    @Size(max = 200)
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 100)
    @Column(name = "transfer")
    private String transfer;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "voluntariadoprojeto")
    private List<Voluntariadoprojetoacomodacao> voluntariadoprojetoacomodacaoList;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "voluntariadoprojeto")
    private List<Voluntariadoprojetovalor> voluntariadoprojetovalorList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "voluntariadoprojeto")
    private List<Voluntariadoprojetocurso> voluntariadoprojetocursoList;
    @Column(name = "diasemanainicial")
    private String diasemanainicial;
    @Column(name = "diasemanafinal")
    private String diasemanafinal;

    public Voluntariadoprojeto() {
    }

    public Voluntariadoprojeto(Integer idvoluntariadoprojeto) {
        this.idvoluntariadoprojeto = idvoluntariadoprojeto;
    }

    public Integer getIdvoluntariadoprojeto() {
        return idvoluntariadoprojeto;
    }

    public void setIdvoluntariadoprojeto(Integer idvoluntariadoprojeto) {
        this.idvoluntariadoprojeto = idvoluntariadoprojeto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public List<Voluntariadoprojetoacomodacao> getVoluntariadoprojetoacomodacaoList() {
        return voluntariadoprojetoacomodacaoList;
    }

    public void setVoluntariadoprojetoacomodacaoList(List<Voluntariadoprojetoacomodacao> voluntariadoprojetoacomodacaoList) {
        this.voluntariadoprojetoacomodacaoList = voluntariadoprojetoacomodacaoList;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }

    public List<Voluntariadoprojetovalor> getVoluntariadoprojetovalorList() {
        return voluntariadoprojetovalorList;
    }

    public void setVoluntariadoprojetovalorList(List<Voluntariadoprojetovalor> voluntariadoprojetovalorList) {
        this.voluntariadoprojetovalorList = voluntariadoprojetovalorList;
    }

    public List<Voluntariadoprojetocurso> getVoluntariadoprojetocursoList() {
        return voluntariadoprojetocursoList;
    }

    public void setVoluntariadoprojetocursoList(List<Voluntariadoprojetocurso> voluntariadoprojetocursoList) {
        this.voluntariadoprojetocursoList = voluntariadoprojetocursoList;
    }

    public String getDiasemanainicial() {
		return diasemanainicial;
	}

	public void setDiasemanainicial(String diasemanainicial) {
		this.diasemanainicial = diasemanainicial;
	}

	public String getDiasemanafinal() {
		return diasemanafinal;
	}

	public void setDiasemanafinal(String diasemanafinal) {
		this.diasemanafinal = diasemanafinal;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idvoluntariadoprojeto != null ? idvoluntariadoprojeto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Voluntariadoprojeto)) {
            return false;
        }
        Voluntariadoprojeto other = (Voluntariadoprojeto) object;
        if ((this.idvoluntariadoprojeto == null && other.idvoluntariadoprojeto != null) || (this.idvoluntariadoprojeto != null && !this.idvoluntariadoprojeto.equals(other.idvoluntariadoprojeto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Voluntariadoprojeto[ idvoluntariadoprojeto=" + idvoluntariadoprojeto + " ]";
    }
    
}
