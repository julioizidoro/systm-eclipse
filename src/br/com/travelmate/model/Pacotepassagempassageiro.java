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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "pacotepassagempassageiro")
@NamedQueries({
    @NamedQuery(name = "Pacotepassagempassageiro.findAll", query = "SELECT p FROM Pacotepassagempassageiro p")})
public class Pacotepassagempassageiro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpacotepassagempassageiro")
    private Integer idpacotepassagempassageiro;
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
    @JoinColumn(name = "pacotepassagem_idpacotepassagem", referencedColumnName = "idpacotepassagem")
    @ManyToOne(optional = false)
    private Pacotepassagem pacotepassagem;
    

    public Pacotepassagempassageiro() {
    }

    public Pacotepassagempassageiro(Integer idpacotepassagempassageiro) {
        this.idpacotepassagempassageiro = idpacotepassagempassageiro;
    }

    public Integer getIdpacotepassagempassageiro() {
        return idpacotepassagempassageiro;
    }

    public void setIdpacotepassagempassageiro(Integer idpacotepassagempassageiro) {
        this.idpacotepassagempassageiro = idpacotepassagempassageiro;
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

    public Pacotepassagem getPacotepassagem() {
        return pacotepassagem;
    }

    public void setPacotepassagem(Pacotepassagem pacotepassagem) {
        this.pacotepassagem = pacotepassagem;
    }

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpacotepassagempassageiro != null ? idpacotepassagempassageiro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pacotepassagempassageiro)) {
            return false;
        }
        Pacotepassagempassageiro other = (Pacotepassagempassageiro) object;
        if ((this.idpacotepassagempassageiro == null && other.idpacotepassagempassageiro != null) || (this.idpacotepassagempassageiro != null && !this.idpacotepassagempassageiro.equals(other.idpacotepassagempassageiro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pacotepassagempassageiro[ idpacotepassagempassageiro=" + idpacotepassagempassageiro + " ]";
    }
    
}
