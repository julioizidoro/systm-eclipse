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
 * @author Wolverine
 */
@Entity
@Table(name = "regravenda")
public class Regravenda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idregravenda")
    private Integer idregravenda;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorinicial")
    private Float valorinicial;
    @Column(name = "valorfinal")
    private Float valorfinal;
    @Column(name = "numerosemanasinicial")
    private Integer numerosemanasinicial;
    @Column(name = "numerosemanafinal")
    private Integer numerosemanafinal;
    @Size(max = 50)
    @Column(name = "programa")
    private String programa;
    @Column(name = "pais")
    private Integer pais;
    @Column(name = "fornecedor")
    private Integer fornecedor;
    @Column(name = "cidade")
    private Integer cidade;
    @Column(name = "situacao")
    private boolean situacao;
    @Column(name = "ponto")
    private int ponto;
    @Column(name = "escola")
    private boolean escola;
    @Column(name = "pontomais")
    private int pontomais;
    @Column(name = "pontomenos")
    private int pontomenos;
    @JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
    @ManyToOne(optional = false)
    private Produtos produtos;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "regravenda")
    private List<Pontuacaovendas> pontuacaovendasList;
    

    public Regravenda() {
    }

    public Regravenda(Integer idregravenda) {
        this.idregravenda = idregravenda;
    }

    public Integer getIdregravenda() {
        return idregravenda;
    }

    public void setIdregravenda(Integer idregravenda) {
        this.idregravenda = idregravenda;
    }

    public Float getValorinicial() {
        return valorinicial;
    }

    public void setValorinicial(Float valorinicial) {
        this.valorinicial = valorinicial;
    }

    public Float getValorfinal() {
        return valorfinal;
    }

    public void setValorfinal(Float valorfinal) {
        this.valorfinal = valorfinal;
    }

    public Integer getNumerosemanasinicial() {
        return numerosemanasinicial;
    }

    public void setNumerosemanasinicial(Integer numerosemanasinicial) {
        this.numerosemanasinicial = numerosemanasinicial;
    }

    public Integer getNumerosemanafinal() {
        return numerosemanafinal;
    }

    public void setNumerosemanafinal(Integer numerosemanafinal) {
        this.numerosemanafinal = numerosemanafinal;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public Integer getPais() {
        return pais;
    }

    public void setPais(Integer pais) {
        this.pais = pais;
    }

    public Integer getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Integer fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Integer getCidade() {
        return cidade;
    }

    public void setCidade(Integer cidade) {
        this.cidade = cidade;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    public List<Pontuacaovendas> getPontuacaovendasList() {
        return pontuacaovendasList;
    }

    public void setPontuacaovendasList(List<Pontuacaovendas> pontuacaovendasList) {
        this.pontuacaovendasList = pontuacaovendasList;
    }

    public boolean isSituacao() {
		return situacao;
	}

	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}

	public int getPonto() {
		return ponto;
	}

	public void setPonto(int ponto) {
		this.ponto = ponto;
	}

	public boolean isEscola() {
		return escola;
	}

	public void setEscola(boolean escola) {
		this.escola = escola;
	}

	public int getPontomais() {
		return pontomais;
	}

	public void setPontomais(int pontomais) {
		this.pontomais = pontomais;
	}

	public int getPontomenos() {
		return pontomenos;
	}

	public void setPontomenos(int pontomenos) {
		this.pontomenos = pontomenos;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idregravenda != null ? idregravenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Regravenda)) {
            return false;
        }
        Regravenda other = (Regravenda) object;
        if ((this.idregravenda == null && other.idregravenda != null) || (this.idregravenda != null && !this.idregravenda.equals(other.idregravenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Regravenda[ idregravenda=" + idregravenda + " ]";
    }
    
}

