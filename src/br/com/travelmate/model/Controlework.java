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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "controlework")
public class Controlework implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontroleWork")
    private Integer idcontroleWork;
    @Column(name = "dataEmbarque")
    @Temporal(TemporalType.DATE)
    private Date dataEmbarque;
    @Column(name = "skype")
    private String skype;
    @Column(name = "sponsor")
    private String sponsor;
    @Column(name = "cidadetrabalho")
    private String cidadetrabalho;
    @Column(name = "empregador")
    private String empregador;
    @Column(name = "joboffer")
    private String joboffer;
    @Column(name = "dataIniciojoboffer")
    @Temporal(TemporalType.DATE)
    private Date datainiciojobpffer;
    @Column(name = "dataTerminoJobOffer")
    @Temporal(TemporalType.DATE)
    private Date dataterminojoboffer;
    @Column(name = "contratoorigianl")
    private String contratooriginal;
    @Column(name = "fichaoriginal")
    private String fichaoriginal;
    @Column(name = "informativooriginal")
    private String informativooriginal;
    @Column(name = "atestadomatriculaoriginal")
    private String atestadomatriculaoriginal;
    @Column(name = "copiapptcolorida")
    private String copiapptcolorida;
    @Column(name = "copiargcpf")
    private String copiargcpf;
    @Column(name = "curriculum")
    private String curriculum;
    @Column(name = "sleeptestecorrigido")
    private String sleeptestecorrigido;
    @Column(name = "cartaapresentacao")
    private String cartaoapresentacao;
    @Column(name = "cartaorecomendacao")
    private String cartaorecomendacao;
    @Column(name = "atestadosaude")
    private String atestadosaude;
    @Column(name = "antecedentescrimianis")
    private String antecedentescriminais;
    @Column(name = "dataretorno")
    @Temporal(TemporalType.DATE)
    private Date dataretorno;
    @Column(name = "statusprocesso")
    private String statusprocesso;
    @Lob
    @Column(name = "observacoes")
    private String observacoes;
    @Column(name = "modalidade")
    private String modalidade;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @Transient
    private boolean selecionado;

    public Controlework() {
    }

    public Controlework(Integer idcontroleWork) {
        this.idcontroleWork = idcontroleWork;
    }

    public Integer getIdcontroleWork() {
        return idcontroleWork;
    }

    public void setIdcontroleWork(Integer idcontroleWork) {
        this.idcontroleWork = idcontroleWork;
    }


	public Date getDataEmbarque() {
		return dataEmbarque;
	}

	public void setDataEmbarque(Date dataEmbarque) {
		this.dataEmbarque = dataEmbarque;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getEmpregador() {
		return empregador;
	}

	public void setEmpregador(String empregador) {
		this.empregador = empregador;
	}

	public String getJoboffer() {
		return joboffer;
	}

	public void setJoboffer(String joboffer) {
		this.joboffer = joboffer;
	}

	public String getCidadetrabalho() {
		return cidadetrabalho;
	}

	public void setCidadetrabalho(String cidadetrabalho) {
		this.cidadetrabalho = cidadetrabalho;
	}

	public Date getDatainiciojobpffer() {
		return datainiciojobpffer;
	}

	public void setDatainiciojobpffer(Date datainiciojobpffer) {
		this.datainiciojobpffer = datainiciojobpffer;
	}

	public Date getDataterminojoboffer() {
		return dataterminojoboffer;
	}

	public void setDataterminojoboffer(Date dataterminojoboffer) {
		this.dataterminojoboffer = dataterminojoboffer;
	}

	public String getContratooriginal() {
		return contratooriginal;
	}

	public void setContratooriginal(String contratooriginal) {
		this.contratooriginal = contratooriginal;
	}

	public String getFichaoriginal() {
		return fichaoriginal;
	}

	public void setFichaoriginal(String fichaoriginal) {
		this.fichaoriginal = fichaoriginal;
	}

	public String getInformativooriginal() {
		return informativooriginal;
	}

	public void setInformativooriginal(String informativooriginal) {
		this.informativooriginal = informativooriginal;
	}

	public String getAtestadomatriculaoriginal() {
		return atestadomatriculaoriginal;
	}

	public void setAtestadomatriculaoriginal(String atestadomatriculaoriginal) {
		this.atestadomatriculaoriginal = atestadomatriculaoriginal;
	}

	public String getCopiapptcolorida() {
		return copiapptcolorida;
	}

	public void setCopiapptcolorida(String copiapptcolorida) {
		this.copiapptcolorida = copiapptcolorida;
	}

	public String getCopiargcpf() {
		return copiargcpf;
	}

	public void setCopiargcpf(String copiargcpf) {
		this.copiargcpf = copiargcpf;
	}

	public String getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(String curriculum) {
		this.curriculum = curriculum;
	}

	public String getSleeptestecorrigido() {
		return sleeptestecorrigido;
	}

	public void setSleeptestecorrigido(String sleeptestecorrigido) {
		this.sleeptestecorrigido = sleeptestecorrigido;
	}

	public String getCartaoapresentacao() {
		return cartaoapresentacao;
	}

	public void setCartaoapresentacao(String cartaoapresentacao) {
		this.cartaoapresentacao = cartaoapresentacao;
	}

	public String getCartaorecomendacao() {
		return cartaorecomendacao;
	}

	public void setCartaorecomendacao(String cartaorecomendacao) {
		this.cartaorecomendacao = cartaorecomendacao;
	}

	public String getAtestadosaude() {
		return atestadosaude;
	}

	public void setAtestadosaude(String atestadosaude) {
		this.atestadosaude = atestadosaude;
	}

	public String getAntecedentescriminais() {
		return antecedentescriminais;
	}

	public void setAntecedentescriminais(String antecedentescriminais) {
		this.antecedentescriminais = antecedentescriminais;
	}

	public Date getDataretorno() {
		return dataretorno;
	}

	public void setDataretorno(Date dataretorno) {
		this.dataretorno = dataretorno;
	}

	public String getStatusprocesso() {
		return statusprocesso;
	}

	public void setStatusprocesso(String statusprocesso) {
		this.statusprocesso = statusprocesso;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
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

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontroleWork != null ? idcontroleWork.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Controlework)) {
            return false;
        }
        Controlework other = (Controlework) object;
        if ((this.idcontroleWork == null && other.idcontroleWork != null) || (this.idcontroleWork != null && !this.idcontroleWork.equals(other.idcontroleWork))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Controlework[ idcontroleWork=" + idcontroleWork + " ]";
    }
    
}
