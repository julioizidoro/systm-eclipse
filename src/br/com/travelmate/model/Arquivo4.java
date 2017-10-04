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

@Entity
@Table(name="arquivo4")
public class Arquivo4 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idarquivo4")
    private Integer idarquivo4;
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
    @JoinColumn(name = "pasta4_idpasta4", referencedColumnName = "idpasta4")
    @ManyToOne(optional = false)
    private Pasta4 pasta4;
    @JoinColumn(name = "pasta1_idpasta1", referencedColumnName = "idpasta1")
    @ManyToOne(optional = false)
    private Pasta1 pasta1;
    @JoinColumn(name = "pasta2_idpasta2", referencedColumnName = "idpasta2")
    @ManyToOne(optional = false)
    private Pasta2 pasta2;
    @JoinColumn(name = "pasta3_idpasta3", referencedColumnName = "idpasta3")
    @ManyToOne(optional = false)
    private Pasta3 pasta3;
    @Column(name = "restrito")
    private boolean restrito;
    @Column(name = "copiado")
    private boolean copiado;
    @Column(name = "dataupload")
    @Temporal(TemporalType.DATE)
    private Date dataupload;
    @Column(name = "hora")
    private String hora;

    public Arquivo4() {
    	datainicio = new Date();
    }

    public Arquivo4(Integer idarquivo4) {
        this.idarquivo4 = idarquivo4;
    }

   

    public Integer getIdarquivo4() {
		return idarquivo4;
	}

	public void setIdarquivo4(Integer idarquivo4) {
		this.idarquivo4 = idarquivo4;
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

    public Pasta4 getPasta4() {
        return pasta4;
    }

    public void setPasta4(Pasta4 pasta4) {
        this.pasta4 = pasta4;
    }

    public Pasta1 getPasta1() {
        return pasta1;
    }

    public void setPasta1(Pasta1 pasta1) {
        this.pasta1 = pasta1;
    }

    public Pasta2 getPasta2() {
        return pasta2;
    }

    public void setPasta2(Pasta2 pasta2) {
        this.pasta2 = pasta2;
    }

    public Pasta3 getPasta3() {
        return pasta3;
    }

    public void setPasta3(Pasta3 pasta3) {
        this.pasta3 = pasta3;
    }
    
    

    public boolean isRestrito() {
		return restrito;
	}

	public void setRestrito(boolean restrito) {
		this.restrito = restrito;
	}

	public Date getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
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
        hash += (idarquivo4 != null ? idarquivo4.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Arquivo4)) {
            return false;
        }
        Arquivo4 other = (Arquivo4) object;
        if ((this.idarquivo4 == null && other.idarquivo4 != null) || (this.idarquivo4 != null && !this.idarquivo4.equals(other.idarquivo4))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Arquivo4[ idarquivo4=" + idarquivo4 + " ]";
    }
    
}
