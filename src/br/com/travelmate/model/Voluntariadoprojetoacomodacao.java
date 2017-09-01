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
@Table(name = "voluntariadoprojetoacomodacao")

public class Voluntariadoprojetoacomodacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvoluntariadoprojetoacomodacao")
    private Integer idvoluntariadoprojetoacomodacao;
    @Size(max = 45)
    @Column(name = "tipoacomodacao")
    private String tipoacomodacao;
    @Size(max = 50)
    @Column(name = "tipoquarto")
    private String tipoquarto;
    @Size(max = 50)
    @Column(name = "tipobanheiro")
    private String tipobanheiro;
    @Size(max = 50)
    @Column(name = "tiporefeicao")
    private String tiporefeicao;
    @Size(max = 100)
    @Column(name = "complemento")
    private String complemento;
    @Column(name = "numerosemanas")
    private Integer numerosemanas;
    @JoinColumn(name = "voluntariadoprojeto_idvoluntariadoprojeto", referencedColumnName = "idvoluntariadoprojeto")
    @ManyToOne(optional = false)
    private Voluntariadoprojeto voluntariadoprojeto;

    public Voluntariadoprojetoacomodacao() {
    }

    public Voluntariadoprojetoacomodacao(Integer idvoluntariadoprojetoacomodacao) {
        this.idvoluntariadoprojetoacomodacao = idvoluntariadoprojetoacomodacao;
    }

    public Integer getIdvoluntariadoprojetoacomodacao() {
        return idvoluntariadoprojetoacomodacao;
    }

    public void setIdvoluntariadoprojetoacomodacao(Integer idvoluntariadoprojetoacomodacao) {
        this.idvoluntariadoprojetoacomodacao = idvoluntariadoprojetoacomodacao;
    }

    public String getTipoacomodacao() {
        return tipoacomodacao;
    }

    public void setTipoacomodacao(String tipoacomodacao) {
        this.tipoacomodacao = tipoacomodacao;
    }

    public String getTipoquarto() {
        return tipoquarto;
    }

    public void setTipoquarto(String tipoquarto) {
        this.tipoquarto = tipoquarto;
    }

    public String getTipobanheiro() {
        return tipobanheiro;
    }

    public void setTipobanheiro(String tipobanheiro) {
        this.tipobanheiro = tipobanheiro;
    }

    public String getTiporefeicao() {
        return tiporefeicao;
    }

    public void setTiporefeicao(String tiporefeicao) {
        this.tiporefeicao = tiporefeicao;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Integer getNumerosemanas() {
        return numerosemanas;
    }

    public void setNumerosemanas(Integer numerosemanas) {
        this.numerosemanas = numerosemanas;
    }

    public Voluntariadoprojeto getVoluntariadoprojeto() {
        return voluntariadoprojeto;
    }

    public void setVoluntariadoprojeto(Voluntariadoprojeto voluntariadoprojeto) {
        this.voluntariadoprojeto = voluntariadoprojeto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvoluntariadoprojetoacomodacao != null ? idvoluntariadoprojetoacomodacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Voluntariadoprojetoacomodacao)) {
            return false;
        }
        Voluntariadoprojetoacomodacao other = (Voluntariadoprojetoacomodacao) object;
        if ((this.idvoluntariadoprojetoacomodacao == null && other.idvoluntariadoprojetoacomodacao != null) || (this.idvoluntariadoprojetoacomodacao != null && !this.idvoluntariadoprojetoacomodacao.equals(other.idvoluntariadoprojetoacomodacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Voluntariadoprojetoacomodacao[ idvoluntariadoprojetoacomodacao=" + idvoluntariadoprojetoacomodacao + " ]";
    }
    
}

