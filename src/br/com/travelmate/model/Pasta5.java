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
@Table(name="pasta5")
public class Pasta5 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpasta5")
    private Integer idpasta5;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta5")
    private List<Arquivo5> arquivo5List;
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

    public Pasta5() {
    }

    public Pasta5(Integer idpasta5) {
        this.idpasta5 = idpasta5;
    }

    public Integer getIdpasta5() {
        return idpasta5;
    }

    public void setIdpasta5(Integer idpasta5) {
        this.idpasta5 = idpasta5;
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

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpasta5 != null ? idpasta5.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pasta5)) {
            return false;
        }
        Pasta5 other = (Pasta5) object;
        if ((this.idpasta5 == null && other.idpasta5 != null) || (this.idpasta5 != null && !this.idpasta5.equals(other.idpasta5))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pasta5[ idpasta5=" + idpasta5 + " ]";
    }
    
}
