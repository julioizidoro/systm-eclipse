package br.com.travelmate.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "pasta2")
public class Pasta2 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpasta2")
    private Integer idpasta2;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta2")
    private List<Arquivo5> arquivo5List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta2")
    private List<Arquivo3> arquivo3List;
    @JoinColumn(name = "pasta1_idpasta1", referencedColumnName = "idpasta1")
    @ManyToOne(optional = false)
    private Pasta1 pasta1;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta2")
    private List<Arquivo4> arquivo4List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta2")
    private List<Arquivo2> arquivo2List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta2")
    private List<Pasta5> pasta5List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta2")
    private List<Pasta4> pasta4List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta2")
    private List<Pasta3> pasta3List;
    @Column(name = "restrito")
    private boolean restrito;

    public Pasta2() {
    }

    public Pasta2(Integer idpasta2) {
        this.idpasta2 = idpasta2;
    }

    public Integer getIdpasta2() {
        return idpasta2;
    }

    public void setIdpasta2(Integer idpasta2) {
        this.idpasta2 = idpasta2;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Arquivo5> getArquivo5List() {
        return arquivo5List;
    }

    public void setArquivo5List(List<Arquivo5> arquivo5List) {
        this.arquivo5List = arquivo5List;
    }

    public List<Arquivo3> getArquivo3List() {
        return arquivo3List;
    }

    public void setArquivo3List(List<Arquivo3> arquivo3List) {
        this.arquivo3List = arquivo3List;
    }

    public Pasta1 getPasta1() {
        return pasta1;
    }

    public void setPasta1(Pasta1 pasta1) {
        this.pasta1 = pasta1;
    }

    public List<Arquivo4> getArquivo4List() {
        return arquivo4List;
    }

    public void setArquivo4List(List<Arquivo4> arquivo4List) {
        this.arquivo4List = arquivo4List;
    }

    public List<Arquivo2> getArquivo2List() {
        return arquivo2List;
    }

    public void setArquivo2List(List<Arquivo2> arquivo2List) {
        this.arquivo2List = arquivo2List;
    }

    public List<Pasta5> getPasta5List() {
        return pasta5List;
    }

    public void setPasta5List(List<Pasta5> pasta5List) {
        this.pasta5List = pasta5List;
    }

    public List<Pasta4> getPasta4List() {
        return pasta4List;
    }

    public void setPasta4List(List<Pasta4> pasta4List) {
        this.pasta4List = pasta4List;
    }

    public List<Pasta3> getPasta3List() {
        return pasta3List;
    }

    public void setPasta3List(List<Pasta3> pasta3List) {
        this.pasta3List = pasta3List;
    }

    public boolean isRestrito() {
		return restrito;
	}

	public void setRestrito(boolean restrito) {
		this.restrito = restrito;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpasta2 != null ? idpasta2.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pasta2)) {
            return false;
        }
        Pasta2 other = (Pasta2) object;
        if ((this.idpasta2 == null && other.idpasta2 != null) || (this.idpasta2 != null && !this.idpasta2.equals(other.idpasta2))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pasta2[ idpasta2=" + idpasta2 + " ]";
    }
    
}
