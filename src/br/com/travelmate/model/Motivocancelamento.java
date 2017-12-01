package br.com.travelmate.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "motivocancelamento")

public class Motivocancelamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmotivocancelamento")
    private Integer idmotivocancelamento;
    @Size(max = 100)
    @Column(name = "motivo")
    private String motivo;
    @Column(name = "observacao")
    private boolean observacao;
   

    public Motivocancelamento() {
    }

    public Motivocancelamento(Integer idmotivocancelamento) {
        this.idmotivocancelamento = idmotivocancelamento;
    }

    public Integer getIdmotivocancelamento() {
        return idmotivocancelamento;
    }

    public void setIdmotivocancelamento(Integer idmotivocancelamento) {
        this.idmotivocancelamento = idmotivocancelamento;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    

    public boolean isObservacao() {
		return observacao;
	}

	public void setObservacao(boolean observacao) {
		this.observacao = observacao;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idmotivocancelamento != null ? idmotivocancelamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Motivocancelamento)) {
            return false;
        }
        Motivocancelamento other = (Motivocancelamento) object;
        if ((this.idmotivocancelamento == null && other.idmotivocancelamento != null) || (this.idmotivocancelamento != null && !this.idmotivocancelamento.equals(other.idmotivocancelamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Motivocancelamento[ idmotivocancelamento=" + idmotivocancelamento + " ]";
    }
    
}

