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
@Table(name = "fornecedorcidadeidiomaacomodacao")
public class Fornecedorcidadeidiomaacomodacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedorcidadeidiomaacomodacao")
    private Integer idfornecedorcidadeidiomaacomodacao;
    @Column(name = "diaentrada")
    private Integer diaentrada;
    @Column(name = "diasaida")
    private Integer diasaida;
    @Column(name = "tipoacomodacao")
    private String tipoacomodacao;
    @JoinColumn(name = "fornecedorcidadeidioma_idfornecedorcidadeidioma", referencedColumnName = "idfornecedorcidadeidioma")
    @ManyToOne(optional = false)
    private Fornecedorcidadeidioma fornecedorcidadeidioma;

    public Fornecedorcidadeidiomaacomodacao() {
    }

    public Fornecedorcidadeidiomaacomodacao(Integer idfornecedorcidadeidiomaacomodacao) {
        this.idfornecedorcidadeidiomaacomodacao = idfornecedorcidadeidiomaacomodacao;
    }

    public Integer getIdfornecedorcidadeidiomaacomodacao() {
        return idfornecedorcidadeidiomaacomodacao;
    }

    public void setIdfornecedorcidadeidiomaacomodacao(Integer idfornecedorcidadeidiomaacomodacao) {
        this.idfornecedorcidadeidiomaacomodacao = idfornecedorcidadeidiomaacomodacao;
    }

    public Integer getDiaentrada() {
        return diaentrada;
    }

    public void setDiaentrada(Integer diaentrada) {
        this.diaentrada = diaentrada;
    }

    public Integer getDiasaida() {
        return diasaida;
    }

    public void setDiasaida(Integer diasaida) {
        this.diasaida = diasaida;
    }

    public Fornecedorcidadeidioma getFornecedorcidadeidioma() {
        return fornecedorcidadeidioma;
    }

    public void setFornecedorcidadeidioma(Fornecedorcidadeidioma fornecedorcidadeidioma) {
        this.fornecedorcidadeidioma = fornecedorcidadeidioma;
    }

    public String getTipoacomodacao() {
		return tipoacomodacao;
	}

	public void setTipoacomodacao(String tipoacomodacao) {
		this.tipoacomodacao = tipoacomodacao;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorcidadeidiomaacomodacao != null ? idfornecedorcidadeidiomaacomodacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fornecedorcidadeidiomaacomodacao)) {
            return false;
        }
        Fornecedorcidadeidiomaacomodacao other = (Fornecedorcidadeidiomaacomodacao) object;
        if ((this.idfornecedorcidadeidiomaacomodacao == null && other.idfornecedorcidadeidiomaacomodacao != null) || (this.idfornecedorcidadeidiomaacomodacao != null && !this.idfornecedorcidadeidiomaacomodacao.equals(other.idfornecedorcidadeidiomaacomodacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fornecedorcidadeidiomaacomodacao[ idfornecedorcidadeidiomaacomodacao=" + idfornecedorcidadeidiomaacomodacao + " ]";
    }
    
}

