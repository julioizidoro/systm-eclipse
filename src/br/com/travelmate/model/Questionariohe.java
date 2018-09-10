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
import javax.validation.constraints.Size;

/**
 *
 * @author jizid
 */
@Entity
@Table(name = "questionariohe")
public class Questionariohe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idquestionariohe")
    private Integer idquestionariohe;
    @Size(max = 200)
    @Column(name = "diplomas")
    private String diplomas;
    @Size(max = 50)
    @Column(name = "ontuacaotoefl")
    private String ontuacaotoefl;
    @Size(max = 100)
    @Column(name = "resultadotesteonline")
    private String resultadotesteonline;
    @Size(max = 100)
    @Column(name = "ocupacao1")
    private String ocupacao1;
    @Size(max = 100)
    @Column(name = "ocupacao2")
    private String ocupacao2;
    @Size(max = 200)
    @Column(name = "futuroplanocarreira")
    private String futuroplanocarreira;
    @Size(max = 100)
    @Column(name = "programa")
    private String programa;
    @Size(max = 50)
    @Column(name = "nivelcetificado")
    private String nivelcetificado;
    @Size(max = 20)
    @Column(name = "orcamentomaximo")
    private String orcamentomaximo;
    @Column(name = "dataprograma")
    @Temporal(TemporalType.DATE)
    private Date dataprograma;
    @Size(max = 3)
    @Column(name = "precisatrabalahar")
    private String precisatrabalahar;
    @Size(max = 3)
    @Column(name = "interesseemimigrar")
    private String interesseemimigrar;
    @Size(max = 3)
    @Column(name = "vistotrabalho")
    private String vistotrabalho;
    @Size(max = 45)
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "dataenvio")
    @Temporal(TemporalType.DATE)
    private Date dataenvio;
    @Size(max = 50)
    @Column(name = "nivelcertificadointeresse")
    private String nivelcertificadointeresse;
    @Size(max = 50)
    @Column(name = "nivelcertificadointeresse2")
    private String nivelcertificadointeresse2;
    @Size(max = 50)
    @Column(name = "pais1")
    private String pais1;
    @Size(max = 50)
    @Column(name = "pais2")
    private String pais2;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente cliente; 
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @Lob
	@Size(max = 2147483647)
	@Column(name = "observacao")
	private String observacao;
    @Column(name = "cidade")
	private String cidade;
    @Column(name = "notalinguagem")
	private float notalinguagem;
    @Column(name = "notaciencianatureza")
	private float notaciencianatureza;
    @Column(name = "notacienciahumanas")
	private float notacienciahumanas;
    @Column(name = "notamatematica")
	private float notamatematica;
    @Column(name = "notaredacao")
	private float notaredacao;
    @Column(name = "outranacionalidade")
	private String outranacionalidade;

    public Questionariohe() {
    	setSituacao("Pendente");
    }

    public Questionariohe(Integer idquestionariohe) {
        this.idquestionariohe = idquestionariohe;
    }

    public Integer getIdquestionariohe() {
        return idquestionariohe;
    }

    public void setIdquestionariohe(Integer idquestionariohe) {
        this.idquestionariohe = idquestionariohe;
    }

    public String getDiplomas() {
        return diplomas;
    }

    public void setDiplomas(String diplomas) {
        this.diplomas = diplomas;
    }

    public String getOntuacaotoefl() {
        return ontuacaotoefl;
    }

    public void setOntuacaotoefl(String ontuacaotoefl) {
        this.ontuacaotoefl = ontuacaotoefl;
    }

    public String getResultadotesteonline() {
        return resultadotesteonline;
    }

    public void setResultadotesteonline(String resultadotesteonline) {
        this.resultadotesteonline = resultadotesteonline;
    }

    public String getOcupacao1() {
        return ocupacao1;
    }

    public void setOcupacao1(String ocupacao1) {
        this.ocupacao1 = ocupacao1;
    }

    public String getOcupacao2() {
        return ocupacao2;
    }

    public void setOcupacao2(String ocupacao2) {
        this.ocupacao2 = ocupacao2;
    }

    public String getFuturoplanocarreira() {
        return futuroplanocarreira;
    }

    public void setFuturoplanocarreira(String futuroplanocarreira) {
        this.futuroplanocarreira = futuroplanocarreira;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getNivelcetificado() {
        return nivelcetificado;
    }

    public void setNivelcetificado(String nivelcetificado) {
        this.nivelcetificado = nivelcetificado;
    }

    public String getOrcamentomaximo() {
        return orcamentomaximo;
    }

    public void setOrcamentomaximo(String orcamentomaximo) {
        this.orcamentomaximo = orcamentomaximo;
    }

    public Date getDataprograma() {
        return dataprograma;
    }

    public void setDataprograma(Date dataprograma) {
        this.dataprograma = dataprograma;
    }

    public String getPrecisatrabalahar() {
        return precisatrabalahar;
    }

    public void setPrecisatrabalahar(String precisatrabalahar) {
        this.precisatrabalahar = precisatrabalahar;
    }

    public String getInteresseemimigrar() {
        return interesseemimigrar;
    }

    public void setInteresseemimigrar(String interesseemimigrar) {
        this.interesseemimigrar = interesseemimigrar;
    }

    public String getVistotrabalho() {
        return vistotrabalho;
    }

    public void setVistotrabalho(String vistotrabalho) {
        this.vistotrabalho = vistotrabalho;
    }

    public Date getDataenvio() {
        return dataenvio;
    }

    public void setDataenvio(Date dataenvio) {
        this.dataenvio = dataenvio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    
    public String getNivelcertificadointeresse() {
		return nivelcertificadointeresse;
	}

	public void setNivelcertificadointeresse(String nivelcertificadointeresse) {
		this.nivelcertificadointeresse = nivelcertificadointeresse;
	}

	public String getNivelcertificadointeresse2() {
		return nivelcertificadointeresse2;
	}

	public void setNivelcertificadointeresse2(String nivelcertificadointeresse2) {
		this.nivelcertificadointeresse2 = nivelcertificadointeresse2;
	} 

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getPais1() {
		return pais1;
	}

	public void setPais1(String pais1) {
		this.pais1 = pais1;
	}

	public String getPais2() {
		return pais2;
	}

	public void setPais2(String pais2) {
		this.pais2 = pais2;
	}

		public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getOutranacionalidade() {
		return outranacionalidade;
	}

	public void setOutranacionalidade(String outranacionalidade) {
		this.outranacionalidade = outranacionalidade;
	}

	public float getNotalinguagem() {
		return notalinguagem;
	}

	public void setNotalinguagem(float notalinguagem) {
		this.notalinguagem = notalinguagem;
	}

	public float getNotaciencianatureza() {
		return notaciencianatureza;
	}

	public void setNotaciencianatureza(float notaciencianatureza) {
		this.notaciencianatureza = notaciencianatureza;
	}

	public float getNotacienciahumanas() {
		return notacienciahumanas;
	}

	public void setNotacienciahumanas(float notacienciahumanas) {
		this.notacienciahumanas = notacienciahumanas;
	}

	public float getNotamatematica() {
		return notamatematica;
	}

	public void setNotamatematica(float notamatematica) {
		this.notamatematica = notamatematica;
	}

	public float getNotaredacao() {
		return notaredacao;
	}

	public void setNotaredacao(float notaredacao) {
		this.notaredacao = notaredacao;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idquestionariohe != null ? idquestionariohe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Questionariohe)) {
            return false;
        }
        Questionariohe other = (Questionariohe) object;
        if ((this.idquestionariohe == null && other.idquestionariohe != null) || (this.idquestionariohe != null && !this.idquestionariohe.equals(other.idquestionariohe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Questionariohe[ idquestionariohe=" + idquestionariohe + " ]";
    }
    
}

