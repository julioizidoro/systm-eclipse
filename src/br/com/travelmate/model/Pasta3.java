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
@Table(name = "pasta3")
public class Pasta3 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpasta3")
    private Integer idpasta3;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta3")
    private List<Arquivo5> arquivo5List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta3")
    private List<Arquivo3> arquivo3List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta3")
    private List<Arquivo4> arquivo4List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta3")
    private List<Pasta5> pasta5List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta3")
    private List<Pasta4> pasta4List;
    @JoinColumn(name = "pasta2_idpasta2", referencedColumnName = "idpasta2")
    @ManyToOne(optional = false)
    private Pasta2 pasta2;
    @JoinColumn(name = "pasta1_idpasta1", referencedColumnName = "idpasta1")
    @ManyToOne(optional = false)
    private Pasta1 pasta1;
    @Column(name = "restrito")
    private boolean restrito;

    public Pasta3() {
    }

    public Pasta3(Integer idpasta3) {
        this.idpasta3 = idpasta3;
    }

    public Integer getIdpasta3() {
        return idpasta3;
    }

    public void setIdpasta3(Integer idpasta3) {
        this.idpasta3 = idpasta3;
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

    public List<Arquivo4> getArquivo4List() {
        return arquivo4List;
    }

    public void setArquivo4List(List<Arquivo4> arquivo4List) {
        this.arquivo4List = arquivo4List;
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

    public boolean isRestrito() {
		return restrito;
	}

	public void setRestrito(boolean restrito) {
		this.restrito = restrito;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpasta3 != null ? idpasta3.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pasta3)) {
            return false;
        }
        Pasta3 other = (Pasta3) object;
        if ((this.idpasta3 == null && other.idpasta3 != null) || (this.idpasta3 != null && !this.idpasta3.equals(other.idpasta3))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pasta3[ idpasta3=" + idpasta3 + " ]";
    }
    
}
