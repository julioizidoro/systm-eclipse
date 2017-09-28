/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;


@Entity
@Table(name = "coprodutos")
public class Coprodutos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcoprodutos")
    private Integer idcoprodutos;
    @Size(max = 20)
    @Column(name = "tipo")
    private String tipo;
    @Lob
    @Size(max = 16777215)
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "produtosOrcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;
    @JoinColumn(name = "fornecedorcidadeidioma_idfornecedorcidadeidioma", referencedColumnName = "idfornecedorcidadeidioma")
    @ManyToOne(optional = false)
    private Fornecedorcidadeidioma fornecedorcidadeidioma;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Size(max = 50)
    @Column(name = "turno")
    private String turno;
    @Column(name = "pacote")
    private boolean pacote;
    @Column(name = "acomodacao")
    private boolean acomodacao;
    @Column(name = "transfer")
    private boolean transfer;
    @Column(name = "situacao")
    private boolean situacao;
    @Column(name = "suplementomenoridade")
    private boolean suplementemenoridade;
    @Column(name = "apenaspacote")
    private boolean apenaspacote;
    @Column(name = "idade")
    private int idade;
    @Lob
   	@Size(max = 2147483647)
   	@Column(name = "advertencia")
   	private String advertencia; 
    @Lob
   	@Size(max = 2147483647)
   	@Column(name = "advertenciaseguro")
   	private String advertenciaseguro;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "coprodutos")
    private Complementoacomodacao complementoacomodacao;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "coprodutos")
    private Complementocurso complementocurso;
    @Transient
    private boolean selecione; 
    
    public Coprodutos() {
    	setPacote(false);
    	setTransfer(false);
    	setAcomodacao(false);
    	setSituacao(false);
    	setSelecione(false);
    }

    public Coprodutos(Integer idcoprodutos) {
        this.idcoprodutos = idcoprodutos;
    }

    public Integer getIdcoprodutos() {
        return idcoprodutos;
    }

    public void setIdcoprodutos(Integer idcoprodutos) {
        this.idcoprodutos = idcoprodutos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Produtosorcamento getProdutosorcamento() {
        return produtosorcamento;
    }

    public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
        this.produtosorcamento = produtosorcamento;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
       this.fornecedorcidade = fornecedorcidade;
    }
    
    public Fornecedorcidadeidioma getFornecedorcidadeidioma() {
		return fornecedorcidadeidioma;
	}

	public void setFornecedorcidadeidioma(Fornecedorcidadeidioma fornecedorcidadeidioma) {
		this.fornecedorcidadeidioma = fornecedorcidadeidioma;
	}
	public String getTurno() {
		return turno;
	}
	
	public void setTurno(String turno) {
		this.turno = turno;
	}

    public boolean isSuplementemenoridade() {
		return suplementemenoridade;
	}

	public void setSuplementemenoridade(boolean suplementemenoridade) {
		this.suplementemenoridade = suplementemenoridade;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Complementoacomodacao getComplementoacomodacao() {
		return complementoacomodacao;
	}

	public void setComplementoacomodacao(Complementoacomodacao complementoacomodacao) {
		this.complementoacomodacao = complementoacomodacao;
	}

	public Complementocurso getComplementocurso() {
		return complementocurso;
	}

	public void setComplementocurso(Complementocurso complementocurso) {
		this.complementocurso = complementocurso;
	}

	public boolean isPacote() {
		return pacote;
	}

	public void setPacote(boolean pacote) {
		this.pacote = pacote;
	}

	public boolean isAcomodacao() {
		return acomodacao;
	}

	public void setAcomodacao(boolean acomodacao) {
		this.acomodacao = acomodacao;
	}

	public boolean isTransfer() {
		return transfer;
	}

	public void setTransfer(boolean transfer) {
		this.transfer = transfer;
	}

	public boolean isSituacao() {
		return situacao;
	}

	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}
 
	public boolean isSelecione() {
		return selecione;
	}

	public void setSelecione(boolean selecione) {
		this.selecione = selecione;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public boolean isApenaspacote() {
		return apenaspacote;
	}

	public void setApenaspacote(boolean apenaspacote) {
		this.apenaspacote = apenaspacote;
	}

	public String getAdvertencia() {
		return advertencia;
	}

	public void setAdvertencia(String advertencia) {
		this.advertencia = advertencia;
	}

	public String getAdvertenciaseguro() {
		return advertenciaseguro;
	}

	public void setAdvertenciaseguro(String advertenciaseguro) {
		this.advertenciaseguro = advertenciaseguro;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcoprodutos != null ? idcoprodutos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Coprodutos)) {
            return false;
        }
        Coprodutos other = (Coprodutos) object;
        if ((this.idcoprodutos == null && other.idcoprodutos != null) || (this.idcoprodutos != null && !this.idcoprodutos.equals(other.idcoprodutos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Coprodutos[ idcoprodutos=" + idcoprodutos + " ]";
    }

}
