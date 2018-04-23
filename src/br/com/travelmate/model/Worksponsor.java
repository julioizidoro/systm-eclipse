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
 * @author wolverine
 */
@Entity
@Table(name = "worksponsor")
public class Worksponsor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idworksponsor")
    private Integer idworksponsor;
    @Size(max = 45)
    @Column(name = "estado")
    private String estado;
    @Size(max = 200)
    @Column(name = "endereco")
    private String endereco;
    @Size(max = 50)
    @Column(name = "nomecontato01")
    private String nomecontato01;
    @Size(max = 50)
    @Column(name = "emailcontato01")
    private String emailcontato01;
    @Size(max = 50)
    @Column(name = "cargocontato01")
    private String cargocontato01;
    @Size(max = 50)
    @Column(name = "nomecontato02")
    private String nomecontato02;
    @Size(max = 50)
    @Column(name = "emailcontato02")
    private String emailcontato02;
    @Size(max = 50)
    @Column(name = "cargocontato02")
    private String cargocontato02;
    @Size(max = 50)
    @Column(name = "nomecontato03")
    private String nomecontato03;
    @Size(max = 50)
    @Column(name = "emailcontato03")
    private String emailcontato03;
    @Size(max = 50)
    @Column(name = "cargocontato03")
    private String cargocontato03;
    @Size(max = 100)
    @Column(name = "recrutadora")
    private String recrutadora;
    @Size(max = 45)
    @Column(name = "telefone")
    private String telefone;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "worksponsor")
    private List<Worksponsorarquivos> worksponsorarquivosList;

    public Worksponsor() {
    }

    public Worksponsor(Integer idworksponsor) {
        this.idworksponsor = idworksponsor;
    }

    public Integer getIdworksponsor() {
        return idworksponsor;
    }

    public void setIdworksponsor(Integer idworksponsor) {
        this.idworksponsor = idworksponsor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNomecontato01() {
        return nomecontato01;
    }

    public void setNomecontato01(String nomecontato01) {
        this.nomecontato01 = nomecontato01;
    }

    public String getEmailcontato01() {
        return emailcontato01;
    }

    public void setEmailcontato01(String emailcontato01) {
        this.emailcontato01 = emailcontato01;
    }

    public String getCargocontato01() {
        return cargocontato01;
    }

    public void setCargocontato01(String cargocontato01) {
        this.cargocontato01 = cargocontato01;
    }

    public String getNomecontato02() {
        return nomecontato02;
    }

    public void setNomecontato02(String nomecontato02) {
        this.nomecontato02 = nomecontato02;
    }

    public String getEmailcontato02() {
        return emailcontato02;
    }

    public void setEmailcontato02(String emailcontato02) {
        this.emailcontato02 = emailcontato02;
    }

    public String getCargocontato02() {
        return cargocontato02;
    }

    public void setCargocontato02(String cargocontato02) {
        this.cargocontato02 = cargocontato02;
    }

    public String getNomecontato03() {
        return nomecontato03;
    }

    public void setNomecontato03(String nomecontato03) {
        this.nomecontato03 = nomecontato03;
    }

    public String getEmailcontato03() {
        return emailcontato03;
    }

    public void setEmailcontato03(String emailcontato03) {
        this.emailcontato03 = emailcontato03;
    }

    public String getCargocontato03() {
        return cargocontato03;
    }

    public void setCargocontato03(String cargocontato03) {
        this.cargocontato03 = cargocontato03;
    }

    public String getRecrutadora() {
        return recrutadora;
    }

    public void setRecrutadora(String recrutadora) {
        this.recrutadora = recrutadora;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idworksponsor != null ? idworksponsor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Worksponsor)) {
            return false;
        }
        Worksponsor other = (Worksponsor) object;
        if ((this.idworksponsor == null && other.idworksponsor != null) || (this.idworksponsor != null && !this.idworksponsor.equals(other.idworksponsor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Worksponsor[ idworksponsor=" + idworksponsor + " ]";
    }
    
}

