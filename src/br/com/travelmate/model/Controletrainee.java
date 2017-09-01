/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "controletrainee")
public class Controletrainee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontroletrainee")
    private Integer idcontroletrainee;
    @Size(max = 3)
    @Column(name = "curiculum")
    private String curriculum;
    @Size(max = 100)
    @Column(name = "empregador")
    private String empregador;
    @Size(max = 3)
    @Column(name = "trainingplain")
    private String trainingplain;
    @Size(max = 3)
    @Column(name = "colocacao")
    private String colocacao;
    @Size(max = 3)
    @Column(name = "fichaoriginal")
    private String fichaoriginal;
    @Size(max = 3)
    @Column(name = "contratooriginal")
    private String contratooriginal;
    @Size(max = 3)
    @Column(name = "informativoprograma")
    private String informativoprograma;
    @Size(max = 3)
    @Column(name = "application")
    private String application;
    @Size(max = 3)
    @Column(name = "copiapptcolorida")
    private String copiapptcolorida;
    @Size(max = 3)
    @Column(name = "cartaapresentacao")
    private String cartaapresentacao;
    @Size(max = 3)
    @Column(name = "historicoescolar")
    private String historicoescolar;
    @Size(max = 3)
    @Column(name = "cartarecomendacao")
    private String cartarecomendacao;
    @Size(max = 3)
    @Column(name = "atestadomedico")
    private String atestadomedico;
    @Size(max = 3)
    @Column(name = "antecedentescriminais")
    private String antecedentescriminais;
    @Size(max = 3)
    @Column(name = "carteiravacinacao")
    private String carteiravacinacao;
    @Size(max = 3)
    @Column(name = "loa")
    private String loa;
    @Size(max = 3)
    @Column(name = "booking")
    private String booking;
    @Size(max = 3)
    @Column(name = "pagamento")
    private String pagamento;
    @Size(max = 3)
    @Column(name = "passagem")
    private String passagem;
    @Size(max = 3)
    @Column(name = "ds2019")
    private String ds2019;
    @Size(max = 3)
    @Column(name = "visto")
    private String visto;
    @Column(name = "dataembarque")
    @Temporal(TemporalType.DATE)
    private Date dataembarque;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @Transient
    private boolean selecionado;
    @Size(max = 50)
    @Column(name = "statusprocesso")
    private String statusprocesso;

    public Controletrainee() {
    }

    public Controletrainee(Integer idcontroletrainee) {
        this.idcontroletrainee = idcontroletrainee;
    }

    public Integer getIdcontroletrainee() {
        return idcontroletrainee;
    }

    public void setIdcontroletrainee(Integer idcontroletrainee) {
        this.idcontroletrainee = idcontroletrainee;
    }


    public String getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(String curriculum) {
		this.curriculum = curriculum;
	}

	public String getEmpregador() {
		return empregador;
	}

	public void setEmpregador(String empregador) {
		this.empregador = empregador;
	}

	public String getTrainingplain() {
		return trainingplain;
	}

	public void setTrainingplain(String trainingplain) {
		this.trainingplain = trainingplain;
	}

	public String getColocacao() {
		return colocacao;
	}

	public void setColocacao(String colocacao) {
		this.colocacao = colocacao;
	}

	public String getFichaoriginal() {
		return fichaoriginal;
	}

	public void setFichaoriginal(String fichaoriginal) {
		this.fichaoriginal = fichaoriginal;
	}

	public String getContratooriginal() {
		return contratooriginal;
	}

	public void setContratooriginal(String contratooriginal) {
		this.contratooriginal = contratooriginal;
	}

	public String getInformativoprograma() {
		return informativoprograma;
	}

	public void setInformativoprograma(String informativoprograma) {
		this.informativoprograma = informativoprograma;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getCopiapptcolorida() {
		return copiapptcolorida;
	}

	public void setCopiapptcolorida(String copiapptcolorida) {
		this.copiapptcolorida = copiapptcolorida;
	}

	public String getCartaapresentacao() {
		return cartaapresentacao;
	}

	public void setCartaapresentacao(String cartaapresentacao) {
		this.cartaapresentacao = cartaapresentacao;
	}

	public String getHistoricoescolar() {
		return historicoescolar;
	}

	public void setHistoricoescolar(String historicoescolar) {
		this.historicoescolar = historicoescolar;
	}

	public String getCartarecomendacao() {
		return cartarecomendacao;
	}

	public void setCartarecomendacao(String cartarecomendacao) {
		this.cartarecomendacao = cartarecomendacao;
	}

	public String getAtestadomedico() {
		return atestadomedico;
	}

	public void setAtestadomedico(String atestadomedico) {
		this.atestadomedico = atestadomedico;
	}

	public String getAntecedentescriminais() {
		return antecedentescriminais;
	}

	public void setAntecedentescriminais(String antecedentescriminais) {
		this.antecedentescriminais = antecedentescriminais;
	}

	public String getCarteiravacinacao() {
		return carteiravacinacao;
	}

	public void setCarteiravacinacao(String carteiravacinacao) {
		this.carteiravacinacao = carteiravacinacao;
	}

	public String getLoa() {
		return loa;
	}

	public void setLoa(String loa) {
		this.loa = loa;
	}

	public String getBooking() {
		return booking;
	}

	public void setBooking(String booking) {
		this.booking = booking;
	}

	public String getPagamento() {
		return pagamento;
	}

	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}

	public String getPassagem() {
		return passagem;
	}

	public void setPassagem(String passagem) {
		this.passagem = passagem;
	}

	public String getDs2019() {
		return ds2019;
	}

	public void setDs2019(String ds2019) {
		this.ds2019 = ds2019;
	}

	public String getVisto() {
		return visto;
	}

	public void setVisto(String visto) {
		this.visto = visto;
	}

	public Date getDataembarque() {
		return dataembarque;
	}

	public void setDataembarque(Date dataembarque) {
		this.dataembarque = dataembarque;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public String getStatusprocesso() {
		return statusprocesso;
	}

	public void setStatusprocesso(String statusprocesso) {
		this.statusprocesso = statusprocesso;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontroletrainee != null ? idcontroletrainee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Controletrainee)) {
            return false;
        }
        Controletrainee other = (Controletrainee) object;
        if ((this.idcontroletrainee == null && other.idcontroletrainee != null) || (this.idcontroletrainee != null && !this.idcontroletrainee.equals(other.idcontroletrainee))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.view.Controletrainee[ idcontroletrainee=" + idcontroletrainee + " ]";
    }
    
}
