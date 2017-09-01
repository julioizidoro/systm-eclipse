package br.com.travelmate.model;

import javax.persistence.Table;
import javax.validation.constraints.Size;

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

@Entity
@Table(name="pasta1")
public class Pasta1 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpasta1")
    private Integer idpasta1;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Column(name = "posicao")
    private Integer posicao;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta1")
    private List<Arquivo5> arquivo5List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta1")
    private List<Arquivo3> arquivo3List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta1")
    private List<Pasta2> pasta2List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta1")
    private List<Arquivo4> arquivo4List;
    @JoinColumn(name = "departamento_iddepartamento", referencedColumnName = "iddepartamento")
    @ManyToOne(optional = false)
    private Departamento departamento;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta1")
    private List<Arquivo1> arquivo1List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta1")
    private List<Arquivo2> arquivo2List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta1")
    private List<Pasta5> pasta5List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta1")
    private List<Pasta4> pasta4List;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pasta1")
    private List<Pasta3> pasta3List;
    @Column(name = "restrito")
    private boolean restrito;

    public Pasta1() {
    }

    public Pasta1(Integer idpasta1) {
        this.idpasta1 = idpasta1;
    }

    public Integer getIdpasta1() {
        return idpasta1;
    }

    public void setIdpasta1(Integer idpasta1) {
        this.idpasta1 = idpasta1;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
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

    public List<Pasta2> getPasta2List() {
        return pasta2List;
    }

    public void setPasta2List(List<Pasta2> pasta2List) {
        this.pasta2List = pasta2List;
    }

    public List<Arquivo4> getArquivo4List() {
        return arquivo4List;
    }

    public void setArquivo4List(List<Arquivo4> arquivo4List) {
        this.arquivo4List = arquivo4List;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Arquivo1> getArquivo1List() {
        return arquivo1List;
    }

    public void setArquivo1List(List<Arquivo1> arquivo1List) {
        this.arquivo1List = arquivo1List;
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
        hash += (idpasta1 != null ? idpasta1.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pasta1)) {
            return false;
        }
        Pasta1 other = (Pasta1) object;
        if ((this.idpasta1 == null && other.idpasta1 != null) || (this.idpasta1 != null && !this.idpasta1.equals(other.idpasta1))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pasta1[ idpasta1=" + idpasta1 + " ]";
    }
    
}
