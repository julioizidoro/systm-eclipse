package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "workempregador")
public class Workempregador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idworkempregador")
    private Integer idworkempregador;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Size(max = 50)
    @Column(name = "estado")
    private String estado;
    @Column(name = "oferecepickup")
    private boolean oferecepickup;
    @Size(max = 50)
    @Column(name = "aeroportosugerido")
    private String aeroportosugerido;
    @JoinColumn(name = "cidadepaisproduto_idcidadepaisproduto", referencedColumnName = "idcidadepaisproduto")
    @ManyToOne(optional = false)
    private Cidadepaisproduto cidadepaisproduto;
    @JoinColumn(name = "worksponsor_idworksponsor", referencedColumnName = "idworksponsor")
    @ManyToOne(optional = false)
    private Worksponsor worksponsor;

    public Workempregador() {
    }

    public Workempregador(Integer idworkempregador) {
        this.idworkempregador = idworkempregador;
    }

    public Integer getIdworkempregador() {
        return idworkempregador;
    }

    public void setIdworkempregador(Integer idworkempregador) {
        this.idworkempregador = idworkempregador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    } 

    public boolean isOferecepickup() {
		return oferecepickup;
	}

	public void setOferecepickup(boolean oferecepickup) {
		this.oferecepickup = oferecepickup;
	}

	public String getAeroportosugerido() {
        return aeroportosugerido;
    }

    public void setAeroportosugerido(String aeroportosugerido) {
        this.aeroportosugerido = aeroportosugerido;
    }

    public Cidadepaisproduto getCidadepaisproduto() {
        return cidadepaisproduto;
    }

    public void setCidadepaisproduto(Cidadepaisproduto cidadepaisproduto) {
        this.cidadepaisproduto = cidadepaisproduto;
    }

    public Worksponsor getWorksponsor() {
        return worksponsor;
    }

    public void setWorksponsor(Worksponsor worksponsor) {
        this.worksponsor = worksponsor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idworkempregador != null ? idworkempregador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Workempregador)) {
            return false;
        }
        Workempregador other = (Workempregador) object;
        if ((this.idworkempregador == null && other.idworkempregador != null) || (this.idworkempregador != null && !this.idworkempregador.equals(other.idworkempregador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Workempregador[ idworkempregador=" + idworkempregador + " ]";
    }
    
}

