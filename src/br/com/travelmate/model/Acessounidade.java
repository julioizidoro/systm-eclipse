package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "acessounidade")
public class Acessounidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idacessounidade")
    private Integer idacessounidade;
    @Column(name = "comissaoparceiros")
    private boolean comissaoparceiros;
    @Column(name = "emissaoconsulta")
    private boolean emissaoconsulta;
    @Column(name = "dashboard")
    private boolean dashboard;
    @Column(name = "consultaorcamento")
    private boolean consultaorcamento;
    @Column(name = "crm")
    private boolean crm; 
    @Column(name = "margemfinanceira")
   	private boolean margemfinanceira;
    @Column(name = "posvendaunidade")
   	private boolean posvendaunidade;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @OneToOne(optional = false)
    private Usuario usuario; 

    public Acessounidade() {
    }

    public Acessounidade(Integer idacessounidade) {
        this.idacessounidade = idacessounidade;
    }

    public Integer getIdacessounidade() {
        return idacessounidade;
    }

    public void setIdacessounidade(Integer idacessounidade) {
        this.idacessounidade = idacessounidade;
    } 

    public boolean isComissaoparceiros() {
		return comissaoparceiros;
	}

	public void setComissaoparceiros(boolean comissaoparceiros) {
		this.comissaoparceiros = comissaoparceiros;
	}

	public boolean isEmissaoconsulta() {
		return emissaoconsulta;
	}

	public void setEmissaoconsulta(boolean emissaoconsulta) {
		this.emissaoconsulta = emissaoconsulta;
	}

	public boolean isDashboard() {
		return dashboard;
	}

	public void setDashboard(boolean dashboard) {
		this.dashboard = dashboard;
	}

	public boolean isConsultaorcamento() {
		return consultaorcamento;
	}

	public void setConsultaorcamento(boolean consultaorcamento) {
		this.consultaorcamento = consultaorcamento;
	}

	public boolean isCrm() {
		return crm;
	}

	public void setCrm(boolean crm) {
		this.crm = crm;
	}

	public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isMargemfinanceira() {
		return margemfinanceira;
	}

	public void setMargemfinanceira(boolean margemfinanceira) {
		this.margemfinanceira = margemfinanceira;
	}

	public boolean isPosvendaunidade() {
		return posvendaunidade;
	}

	public void setPosvendaunidade(boolean posvendaunidade) {
		this.posvendaunidade = posvendaunidade;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idacessounidade != null ? idacessounidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Acessounidade)) {
            return false;
        }
        Acessounidade other = (Acessounidade) object;
        if ((this.idacessounidade == null && other.idacessounidade != null) || (this.idacessounidade != null && !this.idacessounidade.equals(other.idacessounidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Acessounidade[ idacessounidade=" + idacessounidade + " ]";
    }
    
}
