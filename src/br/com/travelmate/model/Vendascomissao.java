/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "vendascomissao")
public class Vendascomissao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvendascomissao")
    private Integer idvendascomissao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorbruto")
    private float valorbruto;
    @Column(name = "valorfornecedor")
    private float valorfornecedor;
    @Column(name = "comissaotm")
    private float comissaotm;
    @Column(name = "taxatm")
    private float taxatm;
    @Column(name = "descontotm")
    private float descontotm;
    @Column(name = "descontoloja")
    private float descontoloja;
    @Column(name = "comissaoemissor")
    private float comissaoemissor;
    @Column(name = "comissaogerente")
    private float comissaogerente;
    @Column(name = "comissaoterceiros")
    private float comissaoterceiros;
    @Column(name = "comissaofraquia")
    private float comissaofraquia;
    @Column(name = "desagio")
    private float desagio;
    @Column(name = "incentivo")
    private float incentivo;
    @Column(name = "liquidovendas")
    private float liquidovendas;
    @Column(name = "liquidofranquia")
    private float liquidofranquia;
    @Column(name = "previsaopagamento")
    @Temporal(TemporalType.DATE)
    private Date previsaopagamento;
    @Size(max = 3)
    @Column(name = "paga")
    private String paga;
    @JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
    @ManyToOne(optional = false)
    private Produtos produtos;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @OneToOne(optional = false)
    private Vendas vendas;
    @JoinColumn(name = "terceiros_idterceiros", referencedColumnName = "idterceiros")
    @ManyToOne(optional = false)
    private Terceiros terceiros;
    @Column(name = "datainicioprograma")
    @Temporal(TemporalType.DATE)
    private Date datainicioprograma;
    @Column(name = "juros")
    private Float juros;
    @Column(name = "comissaofranquiabruta")
    private float comissaofranquiabruta;
    @Column(name = "valorcomissionavel")
    private float valorcomissionavel;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "vendascomissao")
    private Faturafranquias faturaFranquias;
    @Column(name = "descontofornecedor")
    private float descontofornecedor; 
    @Column(name = "custofinanceirofranquia")
    private float custofinanceirofranquia; 
    @Column(name = "jurospagos")
    private float jurospagos; 
   
	public Vendascomissao() {
    }

    public Vendascomissao(Integer idvendascomissao) {
        this.idvendascomissao = idvendascomissao;
    }

    public Integer getIdvendascomissao() {
        return idvendascomissao;
    }

    public void setIdvendascomissao(Integer idvendascomissao) {
        this.idvendascomissao = idvendascomissao;
    }

    public Float getValorbruto() {
        return valorbruto;
    }

    public void setValorbruto(Float valorbruto) {
        this.valorbruto = valorbruto;
    }

    public Float getValorfornecedor() {
        return valorfornecedor;
    }


    public void setValorfornecedor(Float valorfornecedor) {
        this.valorfornecedor = valorfornecedor;
    }

    public float getComissaotm() {
        return comissaotm;
    }

    public Date getDatainicioprograma() {
        return datainicioprograma;
    }

    public void setDatainicioprograma(Date datainicioprograma) {
        this.datainicioprograma = datainicioprograma;
    }

    public void setComissaotm(Float comissaotm) {
        this.comissaotm = comissaotm;
    }

    public float getTaxatm() {
        return taxatm;
    }

    public void setTaxatm(Float taxatm) {
        this.taxatm = taxatm;
    }

    public float getDescontotm() {
        return descontotm;
    }

    public void setDescontotm(Float descontotm) {
        this.descontotm = descontotm;
    }

    public float getDescontoloja() {
        return descontoloja;
    }

    public void setDescontoloja(Float descontoloja) {
        this.descontoloja = descontoloja;
    }

    public float getComissaoemissor() {
        return comissaoemissor;
    }

    public void setComissaoemissor(Float comissaoemissor) {
        this.comissaoemissor = comissaoemissor;
    }

    public float getLiquidovendas() {
        return liquidovendas;
    }

    public void setLiquidovendas(Float liquidovendas) {
        this.liquidovendas = liquidovendas;
    }

    public Date getPrevisaopagamento() {
        return previsaopagamento;
    }

    public void setPrevisaopagamento(Date previsaopagamento) {
        this.previsaopagamento = previsaopagamento;
    }

    

	public Float getJuros() {
		return juros;
	}

	public void setJuros(Float juros) {
		this.juros = juros;
	}

	public float getComissaogerente() {
        return comissaogerente;
    }

    public void setComissaogerente(Float comissaogerente) {
        this.comissaogerente = comissaogerente;
    }

    public float getIncentivo() {
        return incentivo;
    }

    public void setIncentivo(Float incentivo) {
        this.incentivo = incentivo;
    }

    public float getLiquidofranquia() {
        return liquidofranquia;
    }

    public void setLiquidofranquia(Float liquidofranquia) {
        this.liquidofranquia = liquidofranquia;
    }

    public float getComissaoterceiros() {
        return comissaoterceiros;
    }

    public void setComissaoterceiros(Float comissaoterceiros) {
        this.comissaoterceiros = comissaoterceiros;
    }

    public float getComissaofraquia() {
        return comissaofraquia;
    }

    public void setComissaofraquia(Float comissaofraquia) {
        this.comissaofraquia = comissaofraquia;
    }

    public float getDesagio() {
        return desagio;
    }

    public void setDesagio(Float desagio) {
        this.desagio = desagio;
    }


    public String getPaga() {
        return paga;
    }

    public void setPaga(String paga) {
        this.paga = paga;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Terceiros getTerceiros() {
        return terceiros;
    }

    public void setTerceiros(Terceiros terceiros) {
        this.terceiros = terceiros;
    }

	public float getDescontofornecedor() {
		return descontofornecedor;
	}

	public void setDescontofornecedor(float descontofornecedor) {
		this.descontofornecedor = descontofornecedor;
	}

	public void setValorbruto(float valorbruto) {
		this.valorbruto = valorbruto;
	}

	public void setValorfornecedor(float valorfornecedor) {
		this.valorfornecedor = valorfornecedor;
	}

	public void setComissaotm(float comissaotm) {
		this.comissaotm = comissaotm;
	}

	public void setTaxatm(float taxatm) {
		this.taxatm = taxatm;
	}

	public void setDescontotm(float descontotm) {
		this.descontotm = descontotm;
	}

	public void setDescontoloja(float descontoloja) {
		this.descontoloja = descontoloja;
	}

	public void setComissaoemissor(float comissaoemissor) {
		this.comissaoemissor = comissaoemissor;
	}

	public void setComissaogerente(float comissaogerente) {
		this.comissaogerente = comissaogerente;
	}

	public void setComissaoterceiros(float comissaoterceiros) {
		this.comissaoterceiros = comissaoterceiros;
	}

	public void setComissaofraquia(float comissaofraquia) {
		this.comissaofraquia = comissaofraquia;
	}

	public void setDesagio(float desagio) {
		this.desagio = desagio;
	}

	public void setIncentivo(float incentivo) {
		this.incentivo = incentivo;
	}

	public void setLiquidovendas(float liquidovendas) {
		this.liquidovendas = liquidovendas;
	}

	public void setLiquidofranquia(float liquidofranquia) {
		this.liquidofranquia = liquidofranquia;
	}

	public void setComissaofranquiabruta(float comissaofranquiabruta) {
		this.comissaofranquiabruta = comissaofranquiabruta;
	}

	public void setValorcomissionavel(float valorcomissionavel) {
		this.valorcomissionavel = valorcomissionavel;
	}
 

	public float getCustofinanceirofranquia() {
		return custofinanceirofranquia;
	}

	public void setCustofinanceirofranquia(float custofinanceirofranquia) {
		this.custofinanceirofranquia = custofinanceirofranquia;
	}

	public float getJurospago() {
		return jurospagos;
	}

	public void setJurospago(float jurospago) {
		this.jurospagos = jurospago;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idvendascomissao != null ? idvendascomissao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Vendascomissao)) {
            return false;
        }
        Vendascomissao other = (Vendascomissao) object;
        if ((this.idvendascomissao == null && other.idvendascomissao != null) || (this.idvendascomissao != null && !this.idvendascomissao.equals(other.idvendascomissao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Vendascomissao[ idvendascomissao=" + idvendascomissao + " ]";
    }

    public Float getComissaofranquiabruta() {
        return comissaofranquiabruta;
    }

    public void setComissaofranquiabruta(Float comissaofranquiabruta) {
        this.comissaofranquiabruta = comissaofranquiabruta;
    }

    public Float getValorcomissionavel() {
        return valorcomissionavel;
    }

    public void setValorcomissionavel(Float valorcomissionavel) {
        this.valorcomissionavel = valorcomissionavel;
    }

	public Faturafranquias getFaturaFranquias() {
		return faturaFranquias;
	}

	public void setFaturaFranquias(Faturafranquias faturaFranquias) {
		this.faturaFranquias = faturaFranquias;
	}

    
    
}
