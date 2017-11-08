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

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "orcamentomanualseguro")
public class Orcamentomanualseguro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorcamentomanualseguro")
    private Integer idorcamentomanualseguro;
    @Column(name = "datainicio")
    @Temporal(TemporalType.DATE)
    private Date datainicio;
    @Column(name = "numerodias")
    private Integer numerodias;
    @Column(name = "datatermino")
    @Temporal(TemporalType.DATE)
    private Date datatermino;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Float valor;
    @JoinColumn(name = "orcamentocurso_idorcamentoCurso", referencedColumnName = "idorcamentoCurso")
    @OneToOne(optional = false)
    private Orcamentocurso orcamentocurso;
    @JoinColumn(name = "valoresseguro_idvaloresseguro", referencedColumnName = "idvaloresseguro")
    @OneToOne(optional = false)
    private Valoresseguro valoresseguro;
    @Column(name = "somarvalortotal")
    private boolean somarvalortotal;
    @Column(name = "segurocancelamento")
    private boolean segurocancelamento;
    

    public Orcamentomanualseguro() {
    }

    public Orcamentomanualseguro(Integer idorcamentomanualseguro) {
        this.idorcamentomanualseguro = idorcamentomanualseguro;
    }

    public Integer getIdorcamentomanualseguro() {
        return idorcamentomanualseguro;
    }

    public void setIdorcamentomanualseguro(Integer idorcamentomanualseguro) {
        this.idorcamentomanualseguro = idorcamentomanualseguro;
    }

    public Date getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(Date datainicio) {
        this.datainicio = datainicio;
    }

    public Integer getNumerodias() {
        return numerodias;
    }

    public void setNumerodias(Integer numerodias) {
        this.numerodias = numerodias;
    }

    public Date getDatatermino() {
        return datatermino;
    }

    public void setDatatermino(Date datatermino) {
        this.datatermino = datatermino;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Orcamentocurso getOrcamentocurso() {
        return orcamentocurso;
    }

    public void setOrcamentocurso(Orcamentocurso orcamentocurso) {
        this.orcamentocurso = orcamentocurso;
    }

    public Valoresseguro getValoresseguro() {
        return valoresseguro;
    }

    public void setValoresseguro(Valoresseguro valoresseguro) {
        this.valoresseguro = valoresseguro;
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
        hash += (idorcamentomanualseguro != null ? idorcamentomanualseguro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Orcamentomanualseguro)) {
            return false;
        }
        Orcamentomanualseguro other = (Orcamentomanualseguro) object;
        if ((this.idorcamentomanualseguro == null && other.idorcamentomanualseguro != null) || (this.idorcamentomanualseguro != null && !this.idorcamentomanualseguro.equals(other.idorcamentomanualseguro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Orcamentomanualseguro[ idorcamentomanualseguro=" + idorcamentomanualseguro + " ]";
    }
    
}

