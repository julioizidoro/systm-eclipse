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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "fornecedorfinanceiro")
public class Fornecedorfinanceiro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedorfinanceiro")
    private Integer idfornecedorfinanceiro;
    @Size(max = 50)
    @Column(name = "nomebanco")
    private String nomebanco;
    @Size(max = 5)
    @Column(name = "numerobanco")
    private String numerobanco;
    @Size(max = 10)
    @Column(name = "agencia")
    private String agencia;
    @Size(max = 20)
    @Column(name = "conta")
    private String conta;
    @Size(max = 18)
    @Column(name = "cnpj")
    private String cnpj;
    @Size(max = 100)
    @Column(name = "emailfinanceiro")
    private String emailfinanceiro;
    @Size(max = 150)
    @Column(name = "endereco")
    private String endereco;
    @Size(max = 45)
    @Column(name = "tipoconta")
    private String tipoconta;
    @Size(max = 30)
    @Column(name = "swiftcode")   
    private String swiftcode;    
    @Size(max = 30)
    @Column(name = "produto")
    private String produto;
    @JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor", updatable=true)
    @OneToOne(optional = false)
    private Fornecedor fornecedor;
    @JoinColumn(name = "cidade_idcidade", referencedColumnName = "idcidade", updatable=true)
    @OneToOne(optional = false)
    private Cidade cidade;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "fornecedorfinanceiro")
    private List<Fornecedorfinanceiroproduto> fornecedorfinanceiroprodutoList;

    public Fornecedorfinanceiro() {
    }

    public Fornecedorfinanceiro(Integer idfornecedorfinanceiro) {
        this.idfornecedorfinanceiro = idfornecedorfinanceiro;
    }

    public Integer getIdfornecedorfinanceiro() {
        return idfornecedorfinanceiro;
    }

    public void setIdfornecedorfinanceiro(Integer idfornecedorfinanceiro) {
        this.idfornecedorfinanceiro = idfornecedorfinanceiro;
    }

    public String getNomebanco() {
        return nomebanco;
    }

    public void setNomebanco(String nomebanco) {
        this.nomebanco = nomebanco;
    }

    public String getNumerobanco() {
        return numerobanco;
    }

    public void setNumerobanco(String numerobanco) {
        this.numerobanco = numerobanco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmailfinanceiro() {
        return emailfinanceiro;
    }

    public void setEmailfinanceiro(String emailfinanceiro) {
        this.emailfinanceiro = emailfinanceiro;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTipoconta() {
		return tipoconta;
	}

	public void setTipoconta(String tipoconta) {
		this.tipoconta = tipoconta;
	}

	public String getSwiftcode() {
		return swiftcode;
	}

	public void setSwiftcode(String swiftcode) {
		this.swiftcode = swiftcode;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
 

	public List<Fornecedorfinanceiroproduto> getFornecedorfinanceiroprodutoList() {
		return fornecedorfinanceiroprodutoList;
	}

	public void setFornecedorfinanceiroprodutoList(List<Fornecedorfinanceiroproduto> fornecedorfinanceiroprodutoList) {
		this.fornecedorfinanceiroprodutoList = fornecedorfinanceiroprodutoList;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorfinanceiro != null ? idfornecedorfinanceiro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) { 
        if (!(object instanceof Fornecedorfinanceiro)) {
            return false;
        }
        Fornecedorfinanceiro other = (Fornecedorfinanceiro) object;
        if ((this.idfornecedorfinanceiro == null && other.idfornecedorfinanceiro != null) || (this.idfornecedorfinanceiro != null && !this.idfornecedorfinanceiro.equals(other.idfornecedorfinanceiro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fornecedorfinanceiro[ idfornecedorfinanceiro=" + idfornecedorfinanceiro + " ]";
    }
    
}