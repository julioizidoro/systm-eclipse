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
 * @author Wolverine
 */
@Entity
@Table(name = "aupair")
@NamedQueries({
    @NamedQuery(name = "Aupair.findAll", query = "SELECT a FROM Aupair a")})
public class Aupair implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idaupair")
    private Integer idaupair;
    @Column(name = "dataInscricao")
    @Temporal(TemporalType.DATE)
    private Date dataInscricao;
    @Size(max = 3)
    @Column(name = "possuiAmigosPais")
    private String possuiAmigosPais;
    @Size(max = 100)
    @Column(name = "nomeAmigo")
    private String nomeAmigo;
    @Size(max = 13)
    @Column(name = "foneAmigo")
    private String foneAmigo;
    @Size(max = 200)
    @Column(name = "endercoAmigo")
    private String endercoAmigo;
    @Size(max = 50)
    @Column(name = "relacaoAmigo")
    private String relacaoAmigo;
    @Size(max = 50)
    @Column(name = "nivelEstudo")
    private String nivelEstudo;
    @Size(max = 50)
    @Column(name = "ocupacao")
    private String ocupacao;
    @Size(max = 50)
    @Column(name = "inituicaoEstuda")
    private String inituicaoEstuda;
    @Size(max = 50)
    @Column(name = "curso")
    private String curso;
    @Size(max = 50)
    @Column(name = "duracaoCurso")
    private String duracaoCurso;
    @Size(max = 50)
    @Column(name = "cursandoPeriodo")
    private String cursandoPeriodo;
    @Size(max = 45)
    @Column(name = "idioma01")
    private String idioma01;
    @Size(max = 50)
    @Column(name = "nivelIdioma01")
    private String nivelIdioma01;
    @Size(max = 45)
    @Column(name = "idioma02")
    private String idioma02;
    @Size(max = 45)
    @Column(name = "nivelIdioma02")
    private String nivelIdioma02;
    @Size(max = 45)
    @Column(name = "idioma03")
    private String idioma03;
    @Size(max = 45)
    @Column(name = "nivelIdioma03")
    private String nivelIdioma03;
    @Column(name = "dataInicioPretendida01")
    @Temporal(TemporalType.DATE)
    private Date dataInicioPretendida01;
    @Column(name = "dataInicioPretendida02")
    @Temporal(TemporalType.DATE)
    private Date dataInicioPretendida02;
    @Column(name = "dataTerminoPretendida01")
    @Temporal(TemporalType.DATE)
    private Date dataTerminoPretendida01;
    @Column(name = "dataTerminoPretendida02")
    @Temporal(TemporalType.DATE)
    private Date dataTerminoPretendida02;
    @Size(max = 50)
    @Column(name = "tipoPassagem")
    private String tipoPassagem;
    @Size(max = 100)
    @Column(name = "observacaoPassagem")
    private String observacaoPassagem;
    @Size(max = 50)
    @Column(name = "tipoSolicitacaoVisto")
    private String tipoSolicitacaoVisto;
    @Size(max = 3)
    @Column(name = "cartaoVTM")
    private String cartaoVTM;
    @Size(max = 45)
    @Column(name = "numeroCartao")
    private String numeroCartao;
    @Size(max = 45)
    @Column(name = "moedaCartao")
    private String moedaCartao;
    @Size(max = 100)
    @Column(name = "nomeContatoEmergencia")
    private String nomeContatoEmergencia;
    @Size(max = 14)
    @Column(name = "foneContatoEmergencia")
    private String foneContatoEmergencia;
    @Size(max = 100)
    @Column(name = "emailContatoEmergencia")
    private String emailContatoEmergencia;
    @Size(max = 45)
    @Column(name = "relacaoContatoEmergencia")
    private String relacaoContatoEmergencia;
    @Size(max = 3)
    @Column(name = "eua")
    private String eua;
    @Size(max = 3)
    @Column(name = "alemanha")
    private String alemanha;
    @Size(max = 3)
    @Column(name = "franca")
    private String franca;
    @Size(max = 3)
    @Column(name = "holanda")
    private String holanda;
    @Size(max = 3)
    @Column(name = "demi")
    private String demi;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "obstm")
    private String obstm;
    @Size(max = 50)
    @Column(name = "controle")
    private String controle;
    @Size(max = 50)
    @Column(name = "paisinteresse")
    private String paisinteresse;
    @Column(name = "dataEntregaDocumentosVistos")
    @Temporal(TemporalType.DATE)
    private Date dataEntregaDocumentosVistos;
    @Size(max = 100)
    @Column(name = "observacaoVisto")
    private String observacaoVisto;
    @Size(max = 3)
    @Column(name = "possuicnh")
    private String possuicnh;
    @Size(max = 25)
    @Column(name = "tempocnh")
    private String tempocnh;
    @JoinColumn(name = "valoresAupair_idvaloresAupair", referencedColumnName = "idvaloresAupair")
    @ManyToOne(optional = false)
    private Valoresaupair valoresAupair;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    
    @Transient 
    private boolean habilitarImagemGerencial;
    @Transient 
    private boolean habilitarImagemFranquia;
    @Transient 
    private String imagem;

    public Aupair() {
    }

    public Aupair(Integer idaupair) {
        this.idaupair = idaupair;
    }

    public Integer getIdaupair() {
        return idaupair;
    }

    public void setIdaupair(Integer idaupair) {
        this.idaupair = idaupair;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public String getPossuiAmigosPais() {
        return possuiAmigosPais;
    }

    public void setPossuiAmigosPais(String possuiAmigosPais) {
        this.possuiAmigosPais = possuiAmigosPais;
    }

    public String getNomeAmigo() {
        return nomeAmigo;
    }

    public void setNomeAmigo(String nomeAmigo) {
        this.nomeAmigo = nomeAmigo;
    }

    public String getFoneAmigo() {
        return foneAmigo;
    }

    public void setFoneAmigo(String foneAmigo) {
        this.foneAmigo = foneAmigo;
    }

    public String getEndercoAmigo() {
        return endercoAmigo;
    }

    public void setEndercoAmigo(String endercoAmigo) {
        this.endercoAmigo = endercoAmigo;
    }

    public String getRelacaoAmigo() {
        return relacaoAmigo;
    }

    public void setRelacaoAmigo(String relacaoAmigo) {
        this.relacaoAmigo = relacaoAmigo;
    }

    public String getNivelEstudo() {
        return nivelEstudo;
    }

    public void setNivelEstudo(String nivelEstudo) {
        this.nivelEstudo = nivelEstudo;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getInituicaoEstuda() {
        return inituicaoEstuda;
    }

    public void setInituicaoEstuda(String inituicaoEstuda) {
        this.inituicaoEstuda = inituicaoEstuda;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDuracaoCurso() {
        return duracaoCurso;
    }

    public void setDuracaoCurso(String duracaoCurso) {
        this.duracaoCurso = duracaoCurso;
    }

    public String getCursandoPeriodo() {
        return cursandoPeriodo;
    }

    public void setCursandoPeriodo(String cursandoPeriodo) {
        this.cursandoPeriodo = cursandoPeriodo;
    }

    public String getIdioma01() {
        return idioma01;
    }

    public void setIdioma01(String idioma01) {
        this.idioma01 = idioma01;
    }

    public String getNivelIdioma01() {
        return nivelIdioma01;
    }

    public void setNivelIdioma01(String nivelIdioma01) {
        this.nivelIdioma01 = nivelIdioma01;
    }

    public String getIdioma02() {
        return idioma02;
    }

    public void setIdioma02(String idioma02) {
        this.idioma02 = idioma02;
    }

    public String getNivelIdioma02() {
        return nivelIdioma02;
    }

    public void setNivelIdioma02(String nivelIdioma02) {
        this.nivelIdioma02 = nivelIdioma02;
    }

    public String getIdioma03() {
        return idioma03;
    }

    public void setIdioma03(String idioma03) {
        this.idioma03 = idioma03;
    }

    public String getNivelIdioma03() {
        return nivelIdioma03;
    }

    public void setNivelIdioma03(String nivelIdioma03) {
        this.nivelIdioma03 = nivelIdioma03;
    }

    public Date getDataInicioPretendida01() {
        return dataInicioPretendida01;
    }

    public void setDataInicioPretendida01(Date dataInicioPretendida01) {
        this.dataInicioPretendida01 = dataInicioPretendida01;
    }

    public Date getDataInicioPretendida02() {
        return dataInicioPretendida02;
    }

    public void setDataInicioPretendida02(Date dataInicioPretendida02) {
        this.dataInicioPretendida02 = dataInicioPretendida02;
    }

    public Date getDataTerminoPretendida01() {
        return dataTerminoPretendida01;
    }

    public void setDataTerminoPretendida01(Date dataTerminoPretendida01) {
        this.dataTerminoPretendida01 = dataTerminoPretendida01;
    }

    public Date getDataTerminoPretendida02() {
        return dataTerminoPretendida02;
    }

    public void setDataTerminoPretendida02(Date dataTerminoPretendida02) {
        this.dataTerminoPretendida02 = dataTerminoPretendida02;
    }

    public String getTipoPassagem() {
        return tipoPassagem;
    }

    public void setTipoPassagem(String tipoPassagem) {
        this.tipoPassagem = tipoPassagem;
    }

    public String getObservacaoPassagem() {
        return observacaoPassagem;
    }

    public void setObservacaoPassagem(String observacaoPassagem) {
        this.observacaoPassagem = observacaoPassagem;
    }

    public String getTipoSolicitacaoVisto() {
        return tipoSolicitacaoVisto;
    }

    public void setTipoSolicitacaoVisto(String tipoSolicitacaoVisto) {
        this.tipoSolicitacaoVisto = tipoSolicitacaoVisto;
    }

    public String getCartaoVTM() {
        return cartaoVTM;
    }

    public void setCartaoVTM(String cartaoVTM) {
        this.cartaoVTM = cartaoVTM;
    }

    public String getPossuicnh() {
		return possuicnh;
	}

	public void setPossuicnh(String possuicnh) {
		this.possuicnh = possuicnh;
	}

	public String getTempocnh() {
		return tempocnh;
	}

	public void setTempocnh(String tempocnh) {
		this.tempocnh = tempocnh;
	}

	public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getMoedaCartao() {
        return moedaCartao;
    }

    public void setMoedaCartao(String moedaCartao) {
        this.moedaCartao = moedaCartao;
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

    public String getEua() {
        return eua;
    }

    public void setEua(String eua) {
        this.eua = eua;
    }

    public String getAlemanha() {
        return alemanha;
    }

    public void setAlemanha(String alemanha) {
        this.alemanha = alemanha;
    }

    public String getFranca() {
        return franca;
    }

    public void setFranca(String franca) {
        this.franca = franca;
    }

    public String getHolanda() {
        return holanda;
    }

    public void setHolanda(String holanda) {
        this.holanda = holanda;
    }

    public String getDemi() {
        return demi;
    }

    public void setDemi(String demi) {
        this.demi = demi;
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

    public String getPaisinteresse() {
        return paisinteresse;
    }

    public void setPaisinteresse(String paisinteresse) {
        this.paisinteresse = paisinteresse;
    }

	public Valoresaupair getValoresAupair() {
		return valoresAupair;
	}

	public void setValoresAupair(Valoresaupair valoresAupair) {
		this.valoresAupair = valoresAupair;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public Date getDataEntregaDocumentosVistos() {
		return dataEntregaDocumentosVistos;
	}

	public void setDataEntregaDocumentosVistos(Date dataEntregaDocumentosVistos) {
		this.dataEntregaDocumentosVistos = dataEntregaDocumentosVistos;
	}

	public String getObservacaoVisto() {
		return observacaoVisto;
	}

	public void setObservacaoVisto(String observacaoVisto) {
		this.observacaoVisto = observacaoVisto;
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

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idaupair != null ? idaupair.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Aupair)) {
            return false;
        }
        Aupair other = (Aupair) object;
        if ((this.idaupair == null && other.idaupair != null) || (this.idaupair != null && !this.idaupair.equals(other.idaupair))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Aupair[ idaupair=" + idaupair + " ]";
    }
    
}
