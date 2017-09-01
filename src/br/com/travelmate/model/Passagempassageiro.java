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
@Table(name = "passagempassageiro")
public class Passagempassageiro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpassagempassageiro")
    private Integer idpassagempassageiro;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Column(name = "datanascimento")
    @Temporal(TemporalType.DATE)
    private Date datanascimento;
    @Size(max = 50)
    @Column(name = "rg")
    private String rg;
    @Size(max = 5)
    @Column(name = "categoria")
    private String categoria;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Float valor;
    @JoinColumn(name = "passagemaerea_idpassagemAerea", referencedColumnName = "idpassagemAerea")
    @ManyToOne(optional = false)
    private Passagemaerea passagemaerea;

    public Passagempassageiro() {
    }

    public Passagempassageiro(Integer idpassagempassageiro) {
        this.idpassagempassageiro = idpassagempassageiro;
    }

    public Integer getIdpassagempassageiro() {
        return idpassagempassageiro;
    }

    public void setIdpassagempassageiro(Integer idpassagempassageiro) {
        this.idpassagempassageiro = idpassagempassageiro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Passagemaerea getPassagemaerea() {
        return passagemaerea;
    }

    public void setPassagemaerea(Passagemaerea passagemaerea) {
        this.passagemaerea = passagemaerea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpassagempassageiro != null ? idpassagempassageiro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Passagempassageiro)) {
            return false;
        }
        Passagempassageiro other = (Passagempassageiro) object;
        if ((this.idpassagempassageiro == null && other.idpassagempassageiro != null) || (this.idpassagempassageiro != null && !this.idpassagempassageiro.equals(other.idpassagempassageiro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Passagempassageiro[ idpassagempassageiro=" + idpassagempassageiro + " ]";
    }
    
}
