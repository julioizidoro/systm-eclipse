package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "promocaobrindecurso")
public class Promocaobrindecurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpromocaobrindecurso")
    private Integer idpromocaobrindecurso;
    @Column(name = "datavalidadeinicial")
    @Temporal(TemporalType.DATE)
    private Date datavalidadeinicial;
    @Column(name = "datavalidadefinal")
    @Temporal(TemporalType.DATE)
    private Date datavalidadefinal;
    @Column(name = "numerosemanainicial")
    private Integer numerosemanainicial;
    @Column(name = "numerosemanafinal")
    private Integer numerosemanafinal;
    @Column(name = "datamatricula")
    @Temporal(TemporalType.DATE)
    private Date datamatricula;
    @Column(name = "dataacomodacaoinicial")
    @Temporal(TemporalType.DATE)
    private Date dataacomodacaoinicial;
    @Column(name = "dataacomodacaofinal")
    @Temporal(TemporalType.DATE)
    private Date dataacomodacaofinal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorporsemana")
    private Float valorporsemana;
    @Size(max = 45)
    @Column(name = "turno")
    private String turno;
    @Column(name = "datainicio")
    @Temporal(TemporalType.DATE)
    private Date datainicio;
    @Column(name = "datafinal")
    @Temporal(TemporalType.DATE)
    private Date datafinal;
    @Column(name = "numerosemanacurso")
    private Integer numerosemanacurso;
    @Column(name = "numerosemanaacomodacao")
    private Integer numerosemanaacomodacao;
    @Column(name = "ganhasemana")
    private Integer ganhasemana;
    @Column(name = "ganhadescontosemana")
    private Integer ganhadescontosemana;
    @Column(name = "ganhadescontosemanaacomodacao")
    private Integer ganhadescontosemanaacomodacao;
    @Size(max = 254)
    @Column(name = "ganhadescricao")
    private String ganhadescricao;
    @Column(name = "cargahoraria")
    private Integer cargahoraria;
    @Size(max = 30)
    @Column(name = "tipocargahoraria")
    private String tipocargahoraria;
    @Size(max = 20)
    @Column(name = "codigo")
    private String codigo;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "promocaobrindecurso")
    private List<Promocaobrindecursocidade> promocaobrindecursocidadeList;

    public Promocaobrindecurso() {
    }

    public Promocaobrindecurso(Integer idpromocaobrindecurso) {
        this.idpromocaobrindecurso = idpromocaobrindecurso;
    }

    public Integer getIdpromocaobrindecurso() {
        return idpromocaobrindecurso;
    }

    public void setIdpromocaobrindecurso(Integer idpromocaobrindecurso) {
        this.idpromocaobrindecurso = idpromocaobrindecurso;
    }

    public Date getDatavalidadeinicial() {
        return datavalidadeinicial;
    }

    public void setDatavalidadeinicial(Date datavalidadeinicial) {
        this.datavalidadeinicial = datavalidadeinicial;
    }

    public Date getDatavalidadefinal() {
        return datavalidadefinal;
    }

    public void setDatavalidadefinal(Date datavalidadefinal) {
        this.datavalidadefinal = datavalidadefinal;
    }

    public Integer getNumerosemanainicial() {
        return numerosemanainicial;
    }

    public void setNumerosemanainicial(Integer numerosemanainicial) {
        this.numerosemanainicial = numerosemanainicial;
    }

    public Integer getNumerosemanafinal() {
        return numerosemanafinal;
    }

    public void setNumerosemanafinal(Integer numerosemanafinal) {
        this.numerosemanafinal = numerosemanafinal;
    }

    public Date getDatamatricula() {
        return datamatricula;
    }

    public void setDatamatricula(Date datamatricula) {
        this.datamatricula = datamatricula;
    }

    public Date getDataacomodacaoinicial() {
        return dataacomodacaoinicial;
    }

    public void setDataacomodacaoinicial(Date dataacomodacaoinicial) {
        this.dataacomodacaoinicial = dataacomodacaoinicial;
    }

    public Date getDataacomodacaofinal() {
        return dataacomodacaofinal;
    }

    public void setDataacomodacaofinal(Date dataacomodacaofinal) {
        this.dataacomodacaofinal = dataacomodacaofinal;
    }

    public Float getValorporsemana() {
        return valorporsemana;
    }

    public void setValorporsemana(Float valorporsemana) {
        this.valorporsemana = valorporsemana;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Date getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(Date datainicio) {
        this.datainicio = datainicio;
    }

    public Date getDatafinal() {
        return datafinal;
    }

    public void setDatafinal(Date datafinal) {
        this.datafinal = datafinal;
    }

    public Integer getNumerosemanacurso() {
        return numerosemanacurso;
    }

    public void setNumerosemanacurso(Integer numerosemanacurso) {
        this.numerosemanacurso = numerosemanacurso;
    }

    public Integer getNumerosemanaacomodacao() {
        return numerosemanaacomodacao;
    }

    public void setNumerosemanaacomodacao(Integer numerosemanaacomodacao) {
        this.numerosemanaacomodacao = numerosemanaacomodacao;
    }

    public Integer getGanhasemana() {
        return ganhasemana;
    }

    public void setGanhasemana(Integer ganhasemana) {
        this.ganhasemana = ganhasemana;
    }

    public Integer getGanhadescontosemana() {
        return ganhadescontosemana;
    }

    public void setGanhadescontosemana(Integer ganhadescontosemana) {
        this.ganhadescontosemana = ganhadescontosemana;
    }

    public Integer getGanhadescontosemanaacomodacao() {
        return ganhadescontosemanaacomodacao;
    }

    public void setGanhadescontosemanaacomodacao(Integer ganhadescontosemanaacomodacao) {
        this.ganhadescontosemanaacomodacao = ganhadescontosemanaacomodacao;
    }

    public String getGanhadescricao() {
        return ganhadescricao;
    }

    public void setGanhadescricao(String ganhadescricao) {
        this.ganhadescricao = ganhadescricao;
    }

    public List<Promocaobrindecursocidade> getPromocaobrindecursocidadeList() {
        return promocaobrindecursocidadeList;
    }

    public void setPromocaobrindecursocidadeList(List<Promocaobrindecursocidade> promocaobrindecursocidadeList) {
        this.promocaobrindecursocidadeList = promocaobrindecursocidadeList;
    }

    public Integer getCargahoraria() {
		return cargahoraria;
	}

	public void setCargahoraria(Integer cargahoraria) {
		this.cargahoraria = cargahoraria;
	}

	public String getTipocargahoraria() {
		return tipocargahoraria;
	}

	public void setTipocargahoraria(String tipocargahoraria) {
		this.tipocargahoraria = tipocargahoraria;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpromocaobrindecurso != null ? idpromocaobrindecurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Promocaobrindecurso)) {
            return false;
        }
        Promocaobrindecurso other = (Promocaobrindecurso) object;
        if ((this.idpromocaobrindecurso == null && other.idpromocaobrindecurso != null) || (this.idpromocaobrindecurso != null && !this.idpromocaobrindecurso.equals(other.idpromocaobrindecurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Promocaobrindecurso[ idpromocaobrindecurso=" + idpromocaobrindecurso + " ]";
    }
    
}
