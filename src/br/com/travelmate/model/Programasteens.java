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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Kamila
 */
@Entity
@Table(name = "programasteens")
@NamedQueries({
    @NamedQuery(name = "Programasteens.findAll", query = "SELECT p FROM Programasteens p")})
public class Programasteens implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprogramasTeens")
    private Integer idprogramasTeens;
    @Size(max = 100)
    @Column(name = "tipoPrograma")
    private String tipoPrograma;
    @Size(max = 100)
    @Column(name = "nomeContatoEmergencia")
    private String nomeContatoEmergencia;
    @Size(max = 14)
    @Column(name = "foneContatoEmergencia")
    private String foneContatoEmergencia;
    @Size(max = 100)
    @Column(name = "emailContatoEmergencia")
    private String emailContatoEmergencia;
    @Size(max = 100)
    @Column(name = "relacaoContatoEmergencia")
    private String relacaoContatoEmergencia;
    @Size(max = 100)
    @Column(name = "nomeCurso")
    private String nomeCurso;
    @Size(max = 100)
    @Column(name = "nomeEscola")
    private String nomeEscola;
    @Size(max = 50)
    @Column(name = "cidadeCurso")
    private String cidadeCurso;
    @Size(max = 50)
    @Column(name = "paisCurso")
    private String paisCurso;
    @Size(max = 50)
    @Column(name = "aulasSemana")
    private String aulasSemana;
    @Column(name = "numeroSemanas")
    private Integer numeroSemanas;
    @Column(name = "dataInicioCurso")
    @Temporal(TemporalType.DATE)
    private Date dataInicioCurso;
    @Column(name = "dataTerminoCurso")
    @Temporal(TemporalType.DATE)
    private Date dataTerminoCurso;
    @Size(max = 50)
    @Column(name = "tipoAcomodacao")
    private String tipoAcomodacao;
    @Column(name = "numeroSemanasAcomodacao")
    private Integer numeroSemanasAcomodacao;
    @Column(name = "dataChegadaAcomodacao")
    @Temporal(TemporalType.DATE)
    private Date dataChegadaAcomodacao;
    @Column(name = "dataPartidaAcomodacao")
    @Temporal(TemporalType.DATE)
    private Date dataPartidaAcomodacao;
    @Size(max = 50)
    @Column(name = "tipoQuarto")
    private String tipoQuarto;
    @Size(max = 50)
    @Column(name = "refeicao")
    private String refeicao;
    @Size(max = 50)
    @Column(name = "tipoRefeicao")
    private String tipoRefeicao;
    @Size(max = 50)
    @Column(name = "solicitarVisto")
    private String solicitarVisto;
    @Size(max = 50)
    @Column(name = "tipoSolicitacao")
    private String tipoSolicitacao;
    @Column(name = "dataEntragaDocumentosVistos")
    @Temporal(TemporalType.DATE)
    private Date dataEntragaDocumentosVistos;
    @Size(max = 200)
    @Column(name = "observacaoVistos")
    private String observacaoVistos;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "obstm")
    private String obstm;
    @Size(max = 50)
    @Column(name = "controle")
    private String controle;
    @Size(max = 14)
    @Column(name = "cpfpai")
    private String cpfpai;
    @Size(max = 30)
    @Column(name = "rgpai")
    private String rgpai;
    @Column(name = "datanascimentopai")
    @Temporal(TemporalType.DATE)
    private Date datanascimentopai;
    @Size(max = 14)
    @Column(name = "cpfmae")
    private String cpfmae;
    @Size(max = 30)
    @Column(name = "rgmae")
    private String rgmae;
    @Column(name = "datanascimentomae")
    @Temporal(TemporalType.DATE)
    private Date datanascimentomae;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @JoinColumn(name = "valoresprogramasteens_idvaloresprogramasteens", referencedColumnName = "idvaloresprogramasteens")
    @ManyToOne(optional = false)
    private Valoresprogramasteens valoresprogramasteens;
    @JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
    @ManyToOne(optional = false)
    private Fornecedor fornecedor;
    @Size(max = 15)
    @Column(name = "tamanhocamisa")
    private String tamanhocamisa;
    @Size(max = 3)
    @Column(name = "vegetariano")
    private String vegetariano;
    @Size(max = 3)
    @Column(name = "alergia")
    private String alergia;
    @Size(max = 55)
    @Column(name = "alergiaobs")
    private String alergiaobs;
    @Size(max = 3)
    @Column(name = "medicacao")
    private String medicacao;
    @Size(max = 55)
    @Column(name = "medicacaoobs")
    private String medicacaoobs;
    @Size(max = 3)
    @Column(name = "problemapsicologico")
    private String problemapsicologico;
    @Size(max = 55)
    @Column(name = "problemapsicologicoobs")
    private String problemapsicologicoobs;
    @Transient 
    private boolean habilitarImagemGerencial;
    @Transient 
    private boolean habilitarImagemFranquia;
    @Transient 
    private String imagem;

    public Programasteens() {
    }

    public Programasteens(Integer idprogramasTeens) {
        this.idprogramasTeens = idprogramasTeens;
    }

    public Integer getIdprogramasTeens() {
        return idprogramasTeens;
    }

    public void setIdprogramasTeens(Integer idprogramasTeens) {
        this.idprogramasTeens = idprogramasTeens;
    }

    public String getTipoPrograma() {
        return tipoPrograma;
    }

    public void setTipoPrograma(String tipoPrograma) {
        this.tipoPrograma = tipoPrograma;
    }

    public String getNomeContatoEmergencia() {
        return nomeContatoEmergencia;
    }

    public void setNomeContatoEmergencia(String nomeContatoEmergencia) {
        this.nomeContatoEmergencia = nomeContatoEmergencia;
    }

    public String getFoneContatoEmergencia() {
        return foneContatoEmergencia;
    }

    public void setFoneContatoEmergencia(String foneContatoEmergencia) {
        this.foneContatoEmergencia = foneContatoEmergencia;
    }

    public String getEmailContatoEmergencia() {
        return emailContatoEmergencia;
    }

    public void setEmailContatoEmergencia(String emailContatoEmergencia) {
        this.emailContatoEmergencia = emailContatoEmergencia;
    }

    public String getRelacaoContatoEmergencia() {
        return relacaoContatoEmergencia;
    }

    public void setRelacaoContatoEmergencia(String relacaoContatoEmergencia) {
        this.relacaoContatoEmergencia = relacaoContatoEmergencia;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getNomeEscola() {
        return nomeEscola;
    }

    public void setNomeEscola(String nomeEscola) {
        this.nomeEscola = nomeEscola;
    }

    public String getCidadeCurso() {
        return cidadeCurso;
    }

    public void setCidadeCurso(String cidadeCurso) {
        this.cidadeCurso = cidadeCurso;
    }

    public String getPaisCurso() {
        return paisCurso;
    }

    public void setPaisCurso(String paisCurso) {
        this.paisCurso = paisCurso;
    }

    public String getAulasSemana() {
        return aulasSemana;
    }

    public void setAulasSemana(String aulasSemana) {
        this.aulasSemana = aulasSemana;
    }

    public Integer getNumeroSemanas() {
        return numeroSemanas;
    }

    public void setNumeroSemanas(Integer numeroSemanas) {
        this.numeroSemanas = numeroSemanas;
    }

    public Date getDataInicioCurso() {
        return dataInicioCurso;
    }

    public void setDataInicioCurso(Date dataInicioCurso) {
        this.dataInicioCurso = dataInicioCurso;
    }

    public Date getDataTerminoCurso() {
        return dataTerminoCurso;
    }

    public void setDataTerminoCurso(Date dataTerminoCurso) {
        this.dataTerminoCurso = dataTerminoCurso;
    }

    public String getTipoAcomodacao() {
        return tipoAcomodacao;
    }

    public void setTipoAcomodacao(String tipoAcomodacao) {
        this.tipoAcomodacao = tipoAcomodacao;
    }

    public Integer getNumeroSemanasAcomodacao() {
        return numeroSemanasAcomodacao;
    }

    public void setNumeroSemanasAcomodacao(Integer numeroSemanasAcomodacao) {
        this.numeroSemanasAcomodacao = numeroSemanasAcomodacao;
    }

    public Date getDataChegadaAcomodacao() {
        return dataChegadaAcomodacao;
    }

    public void setDataChegadaAcomodacao(Date dataChegadaAcomodacao) {
        this.dataChegadaAcomodacao = dataChegadaAcomodacao;
    }

    public Date getDataPartidaAcomodacao() {
        return dataPartidaAcomodacao;
    }

    public void setDataPartidaAcomodacao(Date dataPartidaAcomodacao) {
        this.dataPartidaAcomodacao = dataPartidaAcomodacao;
    }

    public String getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(String tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }

    public String getRefeicao() {
        return refeicao;
    }

    public void setRefeicao(String refeicao) {
        this.refeicao = refeicao;
    }

    public String getTipoRefeicao() {
        return tipoRefeicao;
    }

    public void setTipoRefeicao(String tipoRefeicao) {
        this.tipoRefeicao = tipoRefeicao;
    }

    public String getSolicitarVisto() {
        return solicitarVisto;
    }

    public void setSolicitarVisto(String solicitarVisto) {
        this.solicitarVisto = solicitarVisto;
    }

    public String getTipoSolicitacao() {
        return tipoSolicitacao;
    }

    public void setTipoSolicitacao(String tipoSolicitacao) {
        this.tipoSolicitacao = tipoSolicitacao;
    }

    public Date getDataEntragaDocumentosVistos() {
        return dataEntragaDocumentosVistos;
    }

    public void setDataEntragaDocumentosVistos(Date dataEntragaDocumentosVistos) {
        this.dataEntragaDocumentosVistos = dataEntragaDocumentosVistos;
    }

    public String getObservacaoVistos() {
        return observacaoVistos;
    }

    public void setObservacaoVistos(String observacaoVistos) {
        this.observacaoVistos = observacaoVistos;
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

    public String getCpfpai() {
        return cpfpai;
    }

    public void setCpfpai(String cpfpai) {
        this.cpfpai = cpfpai;
    }

    public String getRgpai() {
        return rgpai;
    }

    public void setRgpai(String rgpai) {
        this.rgpai = rgpai;
    }

    public Date getDatanascimentopai() {
        return datanascimentopai;
    }

    public void setDatanascimentopai(Date datanascimentopai) {
        this.datanascimentopai = datanascimentopai;
    }

    public String getCpfmae() {
        return cpfmae;
    }

    public void setCpfmae(String cpfmae) {
        this.cpfmae = cpfmae;
    }

    public String getRgmae() {
        return rgmae;
    }

    public void setRgmae(String rgmae) {
        this.rgmae = rgmae;
    }

    public Date getDatanascimentomae() {
        return datanascimentomae;
    }

    public void setDatanascimentomae(Date datanascimentomae) {
        this.datanascimentomae = datanascimentomae;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Valoresprogramasteens getValoresprogramasteens() {
        return valoresprogramasteens;
    }

    public void setValoresprogramasteens(Valoresprogramasteens valoresprogramasteens) {
        this.valoresprogramasteens = valoresprogramasteens;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public boolean isHabilitarImagemGerencial() {
		return habilitarImagemGerencial;
	}

	public void setHabilitarImagemGerencial(boolean habilitarImagemGerencial) {
		this.habilitarImagemGerencial = habilitarImagemGerencial;
	}

	public boolean isHabilitarImagemFranquia() {
		return habilitarImagemFranquia;
	}

	public void setHabilitarImagemFranquia(boolean habilitarImagemFranquia) {
		this.habilitarImagemFranquia = habilitarImagemFranquia;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getTamanhocamisa() {
		return tamanhocamisa;
	}

	public void setTamanhocamisa(String tamanhocamisa) {
		this.tamanhocamisa = tamanhocamisa;
	}

	public String getVegetariano() {
		return vegetariano;
	}

	public void setVegetariano(String vegetariano) {
		this.vegetariano = vegetariano;
	}

	public String getAlergia() {
		return alergia;
	}

	public void setAlergia(String alergia) {
		this.alergia = alergia;
	}

	public String getAlergiaobs() {
		return alergiaobs;
	}

	public void setAlergiaobs(String alergiaobs) {
		this.alergiaobs = alergiaobs;
	}

	public String getMedicacao() {
		return medicacao;
	}

	public void setMedicacao(String medicacao) {
		this.medicacao = medicacao;
	}

	public String getMedicacaoobs() {
		return medicacaoobs;
	}

	public void setMedicacaoobs(String medicacaoobs) {
		this.medicacaoobs = medicacaoobs;
	}

	public String getProblemapsicologico() {
		return problemapsicologico;
	}

	public void setProblemapsicologico(String problemapsicologico) {
		this.problemapsicologico = problemapsicologico;
	}

	public String getProblemapsicologicoobs() {
		return problemapsicologicoobs;
	}

	public void setProblemapsicologicoobs(String problemapsicologicoobs) {
		this.problemapsicologicoobs = problemapsicologicoobs;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idprogramasTeens != null ? idprogramasTeens.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Programasteens)) {
            return false;
        }
        Programasteens other = (Programasteens) object;
        if ((this.idprogramasTeens == null && other.idprogramasTeens != null) || (this.idprogramasTeens != null && !this.idprogramasTeens.equals(other.idprogramasTeens))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Programasteens[ idprogramasTeens=" + idprogramasTeens + " ]";
    }
    
}
