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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "controlecursos")
public class Controlecurso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontroleCursos")
    private Integer idcontroleCursos;
    @Column(name = "work")
    private Integer work;
   
    @Column(name = "dataEmbarque")
    @Temporal(TemporalType.DATE)
    private Date dataEmbarque;
    
    @Column(name = "dataEnvioInscricaoEscola")
    @Temporal(TemporalType.DATE)
    private Date dataEnvioInscricaoEscola;
    @Column(name = "pagamentoAcomodacao")
    @Temporal(TemporalType.DATE)
    private Date pagamentoAcomodacao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorAcomodacao")
    private Float valorAcomodacao;
    @Column(name = "previsaoPagamentoAcomodacao")
    @Temporal(TemporalType.DATE)
    private Date previsaoPagamentoAcomodacao;
    @Column(name = "visto")
    private String visto;
    @Column(name = "kitViagem")
    private String kitViagem;
    @Column(name = "orientacaoPreEmbarque")
    private String orientacaoPreEmbarque;
    @Lob
    @Column(name = "LoasObs")
    private String loasObs;
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "possuiAcomidacao")
    private String possuiAcomidacao;
    @Column(name = "docanexado")
    private String docanexado;
    @Column(name = "docs")
    private String docs;
    @Column(name = "prioridade")
    private String prioridade;
    @JoinColumn(name = "cursos_idcursos", referencedColumnName = "idcursos")
    @OneToOne(optional = false)
    private Curso curso;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @Transient
    private boolean selecionado;
    @Column(name = "obsvisto")
    private String obsvisto;
    @Column(name = "urgenciavisto")
    private boolean urgenciavisto;
   
    @Transient
    private String tipoOrcamento;
    
    public Controlecurso() {
    	docs="VM";
    	setPrioridade("Normal");
    }

    public Controlecurso(Integer idcontroleCursos) {
        this.idcontroleCursos = idcontroleCursos;
    }

    public Integer getIdcontroleCursos() {
        return idcontroleCursos;
    }

    public void setIdcontroleCursos(Integer idcontroleCursos) {
        this.idcontroleCursos = idcontroleCursos;
    }

    public Integer getWork() {
        return work;
    }

    public String getDocanexado() {
        return docanexado;
    }

    public void setDocanexado(String docanexado) {
        this.docanexado = docanexado;
    }

    public void setWork(Integer work) {
        this.work = work;
    }

    public Date getDataEnvioInscricaoEscola() {
        return dataEnvioInscricaoEscola;
    }

    public void setDataEnvioInscricaoEscola(Date dataEnvioInscricaoEscola) {
        this.dataEnvioInscricaoEscola = dataEnvioInscricaoEscola;
    }

    public Date getPagamentoAcomodacao() {
        return pagamentoAcomodacao;
    }

    public void setPagamentoAcomodacao(Date pagamentoAcomodacao) {
        this.pagamentoAcomodacao = pagamentoAcomodacao;
    }

    public Float getValorAcomodacao() {
        return valorAcomodacao;
    }

    public void setValorAcomodacao(Float valorAcomodacao) {
        this.valorAcomodacao = valorAcomodacao;
    }

    public Date getPrevisaoPagamentoAcomodacao() {
        return previsaoPagamentoAcomodacao;
    }

    public void setPrevisaoPagamentoAcomodacao(Date previsaoPagamentoAcomodacao) {
        this.previsaoPagamentoAcomodacao = previsaoPagamentoAcomodacao;
    }

    public String getVisto() {
        return visto;
    }

    public void setVisto(String visto) {
        this.visto = visto;
    }

    public String getKitViagem() {
        return kitViagem;
    }

    public void setKitViagem(String kitViagem) {
        this.kitViagem = kitViagem;
    }

    public String getOrientacaoPreEmbarque() {
        return orientacaoPreEmbarque;
    }

    public void setOrientacaoPreEmbarque(String orientacaoPreEmbarque) {
        this.orientacaoPreEmbarque = orientacaoPreEmbarque;
    }

    public String getLoasObs() {
        return loasObs;
    }

    public void setLoasObs(String loasObs) {
        this.loasObs = loasObs;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getPossuiAcomidacao() {
        return possuiAcomidacao;
    }

    public void setPossuiAcomidacao(String possuiAcomidacao) {
        this.possuiAcomidacao = possuiAcomidacao;
    }

    public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
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

	public String getDocs() {
		return docs;
	}

	public void setDocs(String docs) {
		this.docs = docs;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}
	
	

	public Date getDataEmbarque() {
		return dataEmbarque;
	}

	public void setDataEmbarque(Date dataEmbarque) {
		this.dataEmbarque = dataEmbarque;
	}

	public String getObsvisto() {
		return obsvisto;
	}

	public void setObsvisto(String obsvisto) {
		this.obsvisto = obsvisto;
	}
	

	public boolean isUrgenciavisto() {
		return urgenciavisto;
	}

	public void setUrgenciavisto(boolean urgenciavisto) {
		this.urgenciavisto = urgenciavisto;
	} 
	
	
	public String getTipoOrcamento() {
		return tipoOrcamento;
	}

	public void setTipoOrcamento(String tipoOrcamento) {
		this.tipoOrcamento = tipoOrcamento;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontroleCursos != null ? idcontroleCursos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Controlecurso)) {
            return false;
        }
        Controlecurso other = (Controlecurso) object;
        if ((this.idcontroleCursos == null && other.idcontroleCursos != null) || (this.idcontroleCursos != null && !this.idcontroleCursos.equals(other.idcontroleCursos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Controlecursos[ idcontroleCursos=" + idcontroleCursos + " ]";
    }
    
}
