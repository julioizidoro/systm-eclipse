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
@Table(name = "ocursoferiado")
public class Ocursoferiado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idocursoferiado")
    private Integer idocursoferiado;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Column(name = "datainicial")
    @Temporal(TemporalType.DATE)
    private Date datainicial;
    @Column(name = "datafinal")
    @Temporal(TemporalType.DATE)
    private Date datafinal;
    @Column(name = "contacurso")
    private Boolean contacurso;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;

    public Ocursoferiado() {
    }

    public Ocursoferiado(Integer idocursoferiado) {
        this.idocursoferiado = idocursoferiado;
    }

    public Integer getIdocursoferiado() {
        return idocursoferiado;
    }

    public void setIdocursoferiado(Integer idocursoferiado) {
        this.idocursoferiado = idocursoferiado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDatainicial() {
        return datainicial;
    }

    public void setDatainicial(Date datainicial) {
        this.datainicial = datainicial;
    }

    public Date getDatafinal() {
        return datafinal;
    }

    public void setDatafinal(Date datafinal) {
        this.datafinal = datafinal;
    }

    public Boolean getContacurso() {
        return contacurso;
    }

    public void setContacurso(Boolean contacurso) {
        this.contacurso = contacurso;
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
        hash += (idocursoferiado != null ? idocursoferiado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Ocursoferiado)) {
            return false;
        }
        Ocursoferiado other = (Ocursoferiado) object;
        if ((this.idocursoferiado == null && other.idocursoferiado != null) || (this.idocursoferiado != null && !this.idocursoferiado.equals(other.idocursoferiado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Ocursoferiado[ idocursoferiado=" + idocursoferiado + " ]";
    }
    
}

