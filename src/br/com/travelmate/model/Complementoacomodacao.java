package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "complementoacomodacao")
public class Complementoacomodacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcomplementoacomodacao")
    private Integer idcomplementoacomodacao;
    @Size(max = 45)
    @Column(name = "complementoacomodacaocol")
    private String complementoacomodacaocol;
    @Size(max = 50)
    @Column(name = "tipoacomodacao")
    private String tipoacomodacao;
    @Size(max = 50)
    @Column(name = "tipoquarto")
    private String tipoquarto;
    @Size(max = 50)
    @Column(name = "tipobanheiro")
    private String tipobanheiro;
    @Size(max = 50)
    @Column(name = "tiporefeicao")
    private String tiporefeicao;
    @Size(max = 100)
    @Column(name = "complemento")
    private String complemento; 
    @Column(name = "numerosemanas")
    private int numerosemanas;
    @JoinColumn(name = "coprodutos_idcoprodutos", referencedColumnName = "idcoprodutos", updatable=true)
    @OneToOne(optional = false)
    private Coprodutos coprodutos;
    

    public Complementoacomodacao() {
    }

    public Complementoacomodacao(Integer idcomplementoacomodacao) {
        this.idcomplementoacomodacao = idcomplementoacomodacao;
    }

    public Integer getIdcomplementoacomodacao() {
        return idcomplementoacomodacao;
    }

    public void setIdcomplementoacomodacao(Integer idcomplementoacomodacao) {
        this.idcomplementoacomodacao = idcomplementoacomodacao;
    }

    public String getComplementoacomodacaocol() {
        return complementoacomodacaocol;
    }

    public void setComplementoacomodacaocol(String complementoacomodacaocol) {
        this.complementoacomodacaocol = complementoacomodacaocol;
    }

    public String getTipoacomodacao() {
        return tipoacomodacao;
    }

    public void setTipoacomodacao(String tipoacomodacao) {
        this.tipoacomodacao = tipoacomodacao;
    }

    public String getTipoquarto() {
        return tipoquarto;
    }

    public void setTipoquarto(String tipoquarto) {
        this.tipoquarto = tipoquarto;
    }

    public String getTipobanheiro() {
        return tipobanheiro;
    }

    public void setTipobanheiro(String tipobanheiro) {
        this.tipobanheiro = tipobanheiro;
    }

    public String getTiporefeicao() {
        return tiporefeicao;
    }

    public void setTiporefeicao(String tiporefeicao) {
        this.tiporefeicao = tiporefeicao;
    }

    public Coprodutos getCoprodutos() {
        return coprodutos;
    }

    public void setCoprodutos(Coprodutos coprodutos) {
        this.coprodutos = coprodutos;
    }

    public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public int getNumerosemanas() {
		return numerosemanas;
	}

	public void setNumerosemanas(int numerosemanas) {
		this.numerosemanas = numerosemanas;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomplementoacomodacao != null ? idcomplementoacomodacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Complementoacomodacao)) {
            return false;
        }
        Complementoacomodacao other = (Complementoacomodacao) object;
        if ((this.idcomplementoacomodacao == null && other.idcomplementoacomodacao != null) || (this.idcomplementoacomodacao != null && !this.idcomplementoacomodacao.equals(other.idcomplementoacomodacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Complementoacomodacao[ idcomplementoacomodacao=" + idcomplementoacomodacao + " ]";
    }
    
}

