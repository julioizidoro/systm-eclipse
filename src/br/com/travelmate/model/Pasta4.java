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

@Entity
@Table(name="pasta4")
public class Pasta4 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpasta4")
    private Integer idpasta4;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta4")
    private List<Arquivo5> arquivo5List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta4")
    private List<Arquivo4> arquivo4List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta4")
    private List<Pasta5> pasta5List;
    @JoinColumn(name = "pasta3_idpasta3", referencedColumnName = "idpasta3")
    @ManyToOne(optional = false)
    private Pasta3 pasta3;
    @JoinColumn(name = "pasta1_idpasta1", referencedColumnName = "idpasta1")
    @ManyToOne(optional = false)
    private Pasta1 pasta1;
    @JoinColumn(name = "pasta2_idpasta2", referencedColumnName = "idpasta2")
    @ManyToOne(optional = false)
    private Pasta2 pasta2;
    @Column(name = "restrito")
    private boolean restrito;

    public Pasta4() {
    }

    public Pasta4(Integer idpasta4) {
        this.idpasta4 = idpasta4;
    }

    public Integer getIdpasta4() {
        return idpasta4;
    }

    public void setIdpasta4(Integer idpasta4) {
        this.idpasta4 = idpasta4;
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

    public Pasta3 getPasta3() {
        return pasta3;
    }

    public void setPasta3(Pasta3 pasta3) {
        this.pasta3 = pasta3;
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
    
    

    public boolean isRestrito() {
		return restrito;
	}

	public void setRestrito(boolean restrito) {
		this.restrito = restrito;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpasta4 != null ? idpasta4.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pasta4)) {
            return false;
        }
        Pasta4 other = (Pasta4) object;
        if ((this.idpasta4 == null && other.idpasta4 != null) || (this.idpasta4 != null && !this.idpasta4.equals(other.idpasta4))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pasta4[ idpasta4=" + idpasta4 + " ]";
    }
    
}
