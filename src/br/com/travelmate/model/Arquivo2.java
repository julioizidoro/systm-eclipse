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
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "arquivo2")
public class Arquivo2 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idarquivo2")
    private Integer idarquivo2;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Size(max = 30)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 100)
    @Column(name = "nomeftp")
    private String nomeftp;
    @Column(name = "datavalidade")
    @Temporal(TemporalType.DATE)
    private Date datavalidade;
    @Column(name = "datainicio")
    @Temporal(TemporalType.DATE)
    private Date datainicio;
    @JoinColumn(name = "pasta2_idpasta2", referencedColumnName = "idpasta2")
    @ManyToOne(optional = false)
    private Pasta2 pasta2;
    @JoinColumn(name = "pasta1_idpasta1", referencedColumnName = "idpasta1")
    @ManyToOne(optional = false)
    private Pasta1 pasta1;
    @Column(name = "restrito")
    private boolean restrito;
    @Column(name = "copiado")
    private boolean copiado;
    @Column(name = "dataupload")
    @Temporal(TemporalType.DATE)
    private Date dataupload;
    @Column(name = "hora")
    private String hora;

    public Arquivo2() {
    	datainicio = new Date();
    }

    public Arquivo2(Integer idarquivo2) {
        this.idarquivo2 = idarquivo2;
    }

    public Integer getIdarquivo2() {
        return idarquivo2;
    }

    public void setIdarquivo2(Integer idarquivo2) {
        this.idarquivo2 = idarquivo2;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomeftp() {
        return nomeftp;
    }

    public void setNomeftp(String nomeftp) {
        this.nomeftp = nomeftp;
    }

    public Date getDatavalidade() {
        return datavalidade;
    }

    public void setDatavalidade(Date datavalidade) {
        this.datavalidade = datavalidade;
    }

    public Pasta2 getPasta2() {
        return pasta2;
    }

    public void setPasta2(Pasta2 pasta2) {
        this.pasta2 = pasta2;
    }

    public Pasta1 getPasta1() {
        return pasta1;
    }

    public void setPasta1(Pasta1 pasta1) {
        this.pasta1 = pasta1;
    }
    
    

    public Date getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}

	public boolean isRestrito() {
		return restrito;
	}

	public void setRestrito(boolean restrito) {
		this.restrito = restrito;
	}

	public boolean isCopiado() {
		return copiado;
	}

	public void setCopiado(boolean copiado) {
		this.copiado = copiado;
	}

	public Date getDataupload() {
		return dataupload;
	}

	public void setDataupload(Date dataupload) {
		this.dataupload = dataupload;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idarquivo2 != null ? idarquivo2.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Arquivo2)) {
            return false;
        }
        Arquivo2 other = (Arquivo2) object;
        if ((this.idarquivo2 == null && other.idarquivo2 != null) || (this.idarquivo2 != null && !this.idarquivo2.equals(other.idarquivo2))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Arquivo2[ idarquivo2=" + idarquivo2 + " ]";
    }
    
}
