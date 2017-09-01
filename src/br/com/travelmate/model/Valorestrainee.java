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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "valorestrainee")
public class Valorestrainee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvalorestrainee")
    private Integer idvalorestrainee;
    @Size(max = 50)
    @Column(name = "descricao")
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorgross")
    private Float valorgross;
    @Column(name = "valornet")
    private Float valornet;
    @Size(max = 30)
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "comissaopremium")
    private Float comissaopremium;
    @Column(name = "comissaoexpress")
    private Float comissaoexpress;
    @Size(max = 50)
    @Column(name = "tipotrainee")
    private String tipotrainee;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "valorestrainee")
    private List<Produtostrainee> produtostraineeList;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;
    @JoinColumn(name = "moedas_idmoedas", referencedColumnName = "idmoedas")
    @ManyToOne(optional = false)
    private Moedas moedas;
    @JoinColumn(name = "moedas_idmoedas1", referencedColumnName = "idmoedas")
    @ManyToOne(optional = false)
    private Moedas moedas1;
    @JoinColumn(name = "produtosOrcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "valorestrainee")
    private List<Trainee> traineeList;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "valorestrainee")
    private Produtostrainee produtostrainee;

    public Valorestrainee() {
    }

    public Valorestrainee(Integer idvalorestrainee) {
        this.idvalorestrainee = idvalorestrainee;
    }

    public Integer getIdvalorestrainee() {
        return idvalorestrainee;
    }

    public void setIdvalorestrainee(Integer idvalorestrainee) {
        this.idvalorestrainee = idvalorestrainee;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getValorgross() {
        return valorgross;
    }

    public void setValorgross(Float valorgross) {
        this.valorgross = valorgross;
    }

    public Float getValornet() {
        return valornet;
    }

    public void setValornet(Float valornet) {
        this.valornet = valornet;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Float getComissaopremium() {
        return comissaopremium;
    }

    public void setComissaopremium(Float comissaopremium) {
        this.comissaopremium = comissaopremium;
    }

    public Float getComissaoexpress() {
        return comissaoexpress;
    }

    public void setComissaoexpress(Float comissaoexpress) {
        this.comissaoexpress = comissaoexpress;
    }

    public String getTipotrainee() {
        return tipotrainee;
    }

    public void setTipotrainee(String tipotrainee) {
        this.tipotrainee = tipotrainee;
    }

    public List<Produtostrainee> getProdutostraineeList() {
        return produtostraineeList;
    }

    public void setProdutostraineeList(List<Produtostrainee> produtostraineeList) {
        this.produtostraineeList = produtostraineeList;
    }

    public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public Moedas getMoedas() {
		return moedas;
	}

	public void setMoedas(Moedas moedas) {
		this.moedas = moedas;
	}

	public Moedas getMoedas1() {
		return moedas1;
	}

	public void setMoedas1(Moedas moedas1) {
		this.moedas1 = moedas1;
	}

	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}

	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idvalorestrainee != null ? idvalorestrainee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Valorestrainee)) {
            return false;
        }
        Valorestrainee other = (Valorestrainee) object;
        if ((this.idvalorestrainee == null && other.idvalorestrainee != null) || (this.idvalorestrainee != null && !this.idvalorestrainee.equals(other.idvalorestrainee))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Valorestrainee[ idvalorestrainee=" + idvalorestrainee + " ]";
    }

    public List<Trainee> getTraineeList() {
        return traineeList;
    }

    public void setTraineeList(List<Trainee> traineeList) {
        this.traineeList = traineeList;
    }
    
}
