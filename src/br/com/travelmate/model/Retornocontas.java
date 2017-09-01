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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "retornocontas")
public class Retornocontas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idretornocontas")
    private Integer idretornocontas;
    @Size(max = 3)
    @Column(name = "codigoocorrencia")
    private String codigoocorrencia;
    @JoinColumn(name = "retornoarquivo_idretornoarquivo", referencedColumnName = "idretornoarquivo")
    @ManyToOne(optional = false)
    private Retornoarquivo retornoarquivo;
    @JoinColumn(name = "contasreceber_idcontasreceber", referencedColumnName = "idcontasreceber")
    @ManyToOne(optional = false)
    private Contasreceber contasreceber;
    @Transient
    private boolean selecionado;

    public Retornocontas() {
    }

    public Retornocontas(Integer idretornocontas) {
        this.idretornocontas = idretornocontas;
    }

    public Integer getIdretornocontas() {
        return idretornocontas;
    }

    public void setIdretornocontas(Integer idretornocontas) {
        this.idretornocontas = idretornocontas;
    }

    

    public String getCodigoocorrencia() {
		return codigoocorrencia;
	}

	public void setCodigoocorrencia(String codigoocorrencia) {
		this.codigoocorrencia = codigoocorrencia;
	}

	public Retornoarquivo getRetornoarquivo() {
        return retornoarquivo;
    }

    public void setRetornoarquivo(Retornoarquivo retornoarquivo) {
        this.retornoarquivo = retornoarquivo;
    }

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idretornocontas != null ? idretornocontas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Retornocontas)) {
            return false;
        }
        Retornocontas other = (Retornocontas) object;
        if ((this.idretornocontas == null && other.idretornocontas != null) || (this.idretornocontas != null && !this.idretornocontas.equals(other.idretornocontas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.Interface.Retornocontas[ idretornocontas=" + idretornocontas + " ]";
    }
    
}

