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
@Table(name = "controlevoluntariado")
public class Controlevoluntariado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontrolevoluntariado")
    private Integer idcontrolevoluntariado;
    @Size(max = 3)
    @Column(name = "processoenviado")
    private String processoenviado;
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
    @Column(name = "visto")
    private String visto;
    @Size(max = 3)
    @Column(name = "vacina")
    private String vacina;
    @Size(max = 3)
    @Column(name = "fichaoriginal")
    private String fichaoriginal;
    @Size(max = 3)
    @Column(name = "contratooriginal")
    private String contratooriginal;
    @Size(max = 3)
    @Column(name = "informativooriginal")
    private String informativooriginal;
    @Size(max = 3)
    @Column(name = "copiapptcolorida")
    private String copiapttcolorida;
    @Size(max = 3)
    @Column(name = "cartamotivacao")
    private String cartamotivacao;
    @Size(max = 3)
    @Column(name = "curriculum")
    private String curriculum;
    @Size(max = 3)
    @Column(name = "antecedentescriminais")
    private String antecedentescriminais;
    @Size(max = 50)
    @Column(name = "statusprocesso")
    private String statusprocesso;
    @Column(name = "dataembarque")
    @Temporal(TemporalType.DATE)
    private Date dataembarque;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @Transient
    private boolean selecionado;

    public Controlevoluntariado() {
    }

    public Controlevoluntariado(Integer idcontrolevoluntariado) {
        this.idcontrolevoluntariado = idcontrolevoluntariado;
    }

    public Integer getIdcontrolevoluntariado() {
        return idcontrolevoluntariado;
    }

    public void setIdcontrolevoluntariado(Integer idcontrolevoluntariado) {
        this.idcontrolevoluntariado = idcontrolevoluntariado;
    }

    public String getProcessoenviado() {
        return processoenviado;
    }

    public void setProcessoenviado(String processoenviado) {
        this.processoenviado = processoenviado;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Date getDataembarque() {
        return dataembarque;
    }

    public void setDataembarque(Date dataembarque) {
        this.dataembarque = dataembarque;
    }

    public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
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

	public String getVisto() {
		return visto;
	}

	public void setVisto(String visto) {
		this.visto = visto;
	}

	public String getVacina() {
		return vacina;
	}

	public void setVacina(String vacina) {
		this.vacina = vacina;
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

	public String getInformativooriginal() {
		return informativooriginal;
	}

	public void setInformativooriginal(String informativooriginal) {
		this.informativooriginal = informativooriginal;
	}

	public String getCopiapttcolorida() {
		return copiapttcolorida;
	}

	public void setCopiapttcolorida(String copiapttcolorida) {
		this.copiapttcolorida = copiapttcolorida;
	}

	public String getCartamotivacao() {
		return cartamotivacao;
	}

	public void setCartamotivacao(String cartamotivacao) {
		this.cartamotivacao = cartamotivacao;
	}

	public String getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(String curriculum) {
		this.curriculum = curriculum;
	}

	public String getAntecedentescriminais() {
		return antecedentescriminais;
	}

	public void setAntecedentescriminais(String antecedentescriminais) {
		this.antecedentescriminais = antecedentescriminais;
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
        hash += (idcontrolevoluntariado != null ? idcontrolevoluntariado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Controlevoluntariado)) {
            return false;
        }
        Controlevoluntariado other = (Controlevoluntariado) object;
        if ((this.idcontrolevoluntariado == null && other.idcontrolevoluntariado != null) || (this.idcontrolevoluntariado != null && !this.idcontrolevoluntariado.equals(other.idcontrolevoluntariado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.view.Controlevoluntariado[ idcontrolevoluntariado=" + idcontrolevoluntariado + " ]";
    }
    
}
