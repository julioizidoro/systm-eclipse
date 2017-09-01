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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "tipoacomodacao")
public class Tipoacomodacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipoacomodacao")
    private Integer idtipoacomodacao;
    @Size(max = 100)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "tipoacomodacao")
    private List<Valoresacomodacao> valoresacomodacaoList;

    public Tipoacomodacao() {
    }

    public Tipoacomodacao(Integer idtipoacomodacao) {
        this.idtipoacomodacao = idtipoacomodacao;
    }

    public Integer getIdtipoacomodacao() {
        return idtipoacomodacao;
    }

    public void setIdtipoacomodacao(Integer idtipoacomodacao) {
        this.idtipoacomodacao = idtipoacomodacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Valoresacomodacao> getValoresacomodacaoList() {
        return valoresacomodacaoList;
    }

    public void setValoresacomodacaoList(List<Valoresacomodacao> valoresacomodacaoList) {
        this.valoresacomodacaoList = valoresacomodacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoacomodacao != null ? idtipoacomodacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tipoacomodacao)) {
            return false;
        }
        Tipoacomodacao other = (Tipoacomodacao) object;
        if ((this.idtipoacomodacao == null && other.idtipoacomodacao != null) || (this.idtipoacomodacao != null && !this.idtipoacomodacao.equals(other.idtipoacomodacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Tipoacomodacao[ idtipoacomodacao=" + idtipoacomodacao + " ]";
    }
    
}
