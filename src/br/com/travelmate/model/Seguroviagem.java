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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;


/**
 *
 * @author Kamila Rodrigues
 */

@Entity
@Table(name = "seguroviagem")
public class Seguroviagem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idseguroViagem")
    private Integer idseguroViagem;
    @Size(max = 100)
    @Column(name = "seguradora")
    private String seguradora;
    @Size(max = 50)
    @Column(name = "plano")
    private String plano;
    @Column(name = "dataInicio")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Column(name = "dataTermino")
    @Temporal(TemporalType.DATE)
    private Date dataTermino;
    @Column(name = "numeroSemanas")
    private Integer numeroSemanas;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorSeguro")
    private Float valorSeguro;
    @Column(name = "descontomatriz")
    private Float descontomatriz;
    @Column(name = "descontoloja")
    private Float descontoloja;
    @Size(max = 3)
    @Column(name = "possuiSeguro")
    private String possuiSeguro;
    @Size(max = 100)
    @Column(name = "nomeContatoEmergencia")
    private String nomeContatoEmergencia;
    @Size(max = 50)
    @Column(name = "paisDestino")
    private String paisDestino;
    @Size(max = 15)
    @Column(name = "foneContatoEmergencia")
    private String foneContatoEmergencia;
    @Column(name = "idvendacurso")
    private int idvendacurso;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "obstm")
    private String obstm;
    @Size(max = 50)
    @Column(name = "controle")
    private String controle;
    @Column(name = "segurocancelamento")
    private boolean segurocancelamento;
    @JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
    @ManyToOne(optional = false)
    private Fornecedor fornecedor;
    @JoinColumn(name = "valoresseguro_idvaloresseguro", referencedColumnName = "idvaloresseguro")
    @ManyToOne(optional = false)
    private Valoresseguro valoresseguro;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "seguroviagem")
    private Controleseguro controleseguro;
    @Transient
    private Float valorMoedaEstrangeira; 
    @Transient 
    private boolean somarvalortotal;

    public Seguroviagem() {
    	this.descontoloja=0.0f;
    	this.descontomatriz=0.0f;
    	this.valorSeguro=0.0f;
    	this.valorMoedaEstrangeira=0.0f;
    	somarvalortotal = false;
    }

    public Seguroviagem(Integer idseguroViagem) {
        this.idseguroViagem = idseguroViagem;
    }

    public Integer getIdseguroViagem() {
        return idseguroViagem;
    }

    public void setIdseguroViagem(Integer idseguroViagem) {
        this.idseguroViagem = idseguroViagem;
    }

    public String getSeguradora() {
        return seguradora;
    }

    public void setSeguradora(String seguradora) {
        this.seguradora = seguradora;
    }

    public int getIdvendacurso() {
		return idvendacurso;
	}

	public void setIdvendacurso(int idvendacurso) {
		this.idvendacurso = idvendacurso;
	}

	public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Integer getNumeroSemanas() {
        return numeroSemanas;
    }

    public void setNumeroSemanas(Integer numeroSemanas) {
        this.numeroSemanas = numeroSemanas;
    }

    public Float getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(Float valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    public String getPossuiSeguro() {
        return possuiSeguro;
    }

    public void setPossuiSeguro(String possuiSeguro) {
        this.possuiSeguro = possuiSeguro;
    }

    public String getNomeContatoEmergencia() {
        return nomeContatoEmergencia;
    }

    public void setNomeContatoEmergencia(String nomeContatoEmergencia) {
        this.nomeContatoEmergencia = nomeContatoEmergencia;
    }

    public String getPaisDestino() {
        return paisDestino;
    }

    public void setPaisDestino(String paisDestino) {
        this.paisDestino = paisDestino;
    }

    public String getFoneContatoEmergencia() {
        return foneContatoEmergencia;
    }

    public void setFoneContatoEmergencia(String foneContatoEmergencia) {
        this.foneContatoEmergencia = foneContatoEmergencia;
    }

    public String getObstm() {
        return obstm;
    }

    public void setObstm(String obstm) {
        this.obstm = obstm;
    }

    public String getControle() {
        return controle;
    }

    public void setControle(String controle) {
        this.controle = controle;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Valoresseguro getValoresseguro() {
        return valoresseguro;
    }

    public void setValoresseguro(Valoresseguro valoresseguro) {
        this.valoresseguro = valoresseguro;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Float getValorMoedaEstrangeira() {
        return valorMoedaEstrangeira;
    }

    public void setValorMoedaEstrangeira(Float valorMoedaEstrangeira) {
        this.valorMoedaEstrangeira = valorMoedaEstrangeira;
    }

	public Controleseguro getControleseguro() {
		return controleseguro;
	}

	public void setControleseguro(Controleseguro controleseguro) {
		this.controleseguro = controleseguro;
	}

	public Float getDescontomatriz() {
		return descontomatriz;
	}

	public void setDescontomatriz(Float descontomatriz) {
		this.descontomatriz = descontomatriz;
	}

	public Float getDescontoloja() {
		return descontoloja;
	}

	public void setDescontoloja(Float descontoloja) {
		this.descontoloja = descontoloja;
	}

	public boolean isSomarvalortotal() {
		return somarvalortotal;
	}

	public void setSomarvalortotal(boolean somarvalortotal) {
		this.somarvalortotal = somarvalortotal;
	}

	public boolean isSegurocancelamento() {
		return segurocancelamento;
	}

	public void setSegurocancelamento(boolean segurocancelamento) {
		this.segurocancelamento = segurocancelamento;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idseguroViagem != null ? idseguroViagem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Seguroviagem)) {
            return false;
        }
        Seguroviagem other = (Seguroviagem) object;
        if ((this.idseguroViagem == null && other.idseguroViagem != null) || (this.idseguroViagem != null && !this.idseguroViagem.equals(other.idseguroViagem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Seguroviagem[ idseguroViagem=" + idseguroViagem + " ]";
    }
    
}
