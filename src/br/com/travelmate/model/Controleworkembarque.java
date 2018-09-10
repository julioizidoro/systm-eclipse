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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "controleworkembarque")
public class Controleworkembarque implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontroleworkembarque")
    private Integer idcontroleworkembarque;
    @Size(max = 45)
    @Column(name = "ciaaerea")
    private String ciaaerea;
    @Size(max = 100)
    @Column(name = "aeroportochegada")
    private String aeroportochegada;
    @Size(max = 15)
    @Column(name = "numerovoo")
    private String numerovoo;
    @Size(max = 8)
    @Column(name = "horachegada")
    private String horachegada;
    @Column(name = "dataembarque")
    @Temporal(TemporalType.DATE)
    private Date dataembarque;
    @Column(name = "datachegada")
    @Temporal(TemporalType.DATE)
    private Date datachegada;
    @Column(name = "dataretorno")
    @Temporal(TemporalType.DATE)
    private Date dataretorno;
    @Column(name = "dataembarqueinicial")
    @Temporal(TemporalType.DATE)
    private Date dataembarqueinicial;
    @Column(name = "dataembarquefinal")
    @Temporal(TemporalType.DATE)
    private Date dataembarquefinal;
    @Column(name = "dataretornoinicial")
    @Temporal(TemporalType.DATE)
    private Date dataretornoinicial;
    @Column(name = "dataretornofinal")
    @Temporal(TemporalType.DATE)
    private Date dataretornofinal;
    @Size(max = 15)
    @Column(name = "situacaovisto")
    private String situacaovisto;
    @Column(name = "datavisto")
    @Temporal(TemporalType.DATE)
    private Date datavisto;
    @Size(max = 45)
    @Column(name = "sevis")
    private String sevis;
    @JoinColumn(name = "controlework_idcontroleWork", referencedColumnName = "idcontroleWork")
    @OneToOne(optional = false)
    private Controlework controlework;

    public Controleworkembarque() {
    }

    public Controleworkembarque(Integer idcontroleworkembarque) {
        this.idcontroleworkembarque = idcontroleworkembarque;
    }

    public Integer getIdcontroleworkembarque() {
        return idcontroleworkembarque;
    }

    public void setIdcontroleworkembarque(Integer idcontroleworkembarque) {
        this.idcontroleworkembarque = idcontroleworkembarque;
    }

    public String getCiaaerea() {
        return ciaaerea;
    }

    public void setCiaaerea(String ciaaerea) {
        this.ciaaerea = ciaaerea;
    }

    public String getAeroportochegada() {
        return aeroportochegada;
    }

    public void setAeroportochegada(String aeroportochegada) {
        this.aeroportochegada = aeroportochegada;
    }

    public String getNumerovoo() {
        return numerovoo;
    }

    public void setNumerovoo(String numerovoo) {
        this.numerovoo = numerovoo;
    }

    public String getHorachegada() {
        return horachegada;
    }

    public void setHorachegada(String horachegada) {
        this.horachegada = horachegada;
    }

    public Date getDataembarque() {
        return dataembarque;
    }

    public void setDataembarque(Date dataembarque) {
        this.dataembarque = dataembarque;
    }

    public Date getDatachegada() {
        return datachegada;
    }

    public void setDatachegada(Date datachegada) {
        this.datachegada = datachegada;
    }

    public Date getDataretorno() {
        return dataretorno;
    }

    public void setDataretorno(Date dataretorno) {
        this.dataretorno = dataretorno;
    }

    public Date getDataembarqueinicial() {
        return dataembarqueinicial;
    }

    public void setDataembarqueinicial(Date dataembarqueinicial) {
        this.dataembarqueinicial = dataembarqueinicial;
    }

    public Date getDataembarquefinal() {
        return dataembarquefinal;
    }

    public void setDataembarquefinal(Date dataembarquefinal) {
        this.dataembarquefinal = dataembarquefinal;
    }

    public Date getDataretornoinicial() {
        return dataretornoinicial;
    }

    public void setDataretornoinicial(Date dataretornoinicial) {
        this.dataretornoinicial = dataretornoinicial;
    }

    public Date getDataretornofinal() {
        return dataretornofinal;
    }

    public void setDataretornofinal(Date dataretornofinal) {
        this.dataretornofinal = dataretornofinal;
    }

    public String getSituacaovisto() {
        return situacaovisto;
    }

    public void setSituacaovisto(String situacaovisto) {
        this.situacaovisto = situacaovisto;
    }

    public Date getDatavisto() {
        return datavisto;
    }

    public void setDatavisto(Date datavisto) {
        this.datavisto = datavisto;
    }

    public String getSevis() {
        return sevis;
    }

    public void setSevis(String sevis) {
        this.sevis = sevis;
    }

    public Controlework getControlework() {
        return controlework;
    }

    public void setControlework(Controlework controlework) {
        this.controlework = controlework;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontroleworkembarque != null ? idcontroleworkembarque.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Controleworkembarque)) {
            return false;
        }
        Controleworkembarque other = (Controleworkembarque) object;
        if ((this.idcontroleworkembarque == null && other.idcontroleworkembarque != null) || (this.idcontroleworkembarque != null && !this.idcontroleworkembarque.equals(other.idcontroleworkembarque))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Controleworkembarque[ idcontroleworkembarque=" + idcontroleworkembarque + " ]";
    }
    
}

