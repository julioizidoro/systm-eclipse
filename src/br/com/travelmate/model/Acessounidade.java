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
    private Boolean comissaoparceiros;
    @Column(name = "emissaoconsulta")
    private Boolean emissaoconsulta;
    @Column(name = "dashboard")
    private Boolean dashboard;
    @Column(name = "consultaorcamento")
    private Boolean consultaorcamento;
    @Column(name = "crm")
    private Boolean crm;
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

    public Boolean getComissaoparceiros() {
        return comissaoparceiros;
    }

    public void setComissaoparceiros(Boolean comissaoparceiros) {
        this.comissaoparceiros = comissaoparceiros;
    }

    public Boolean getEmissaoconsulta() {
        return emissaoconsulta;
    }

    public void setEmissaoconsulta(Boolean emissaoconsulta) {
        this.emissaoconsulta = emissaoconsulta;
    }

    public Boolean getDashboard() {
        return dashboard;
    }

    public void setDashboard(Boolean dashboard) {
        this.dashboard = dashboard;
    }

    public Boolean getConsultaorcamento() {
        return consultaorcamento;
    }

    public void setConsultaorcamento(Boolean consultaorcamento) {
        this.consultaorcamento = consultaorcamento;
    }

    public Boolean getCrm() {
        return crm;
    }

    public void setCrm(Boolean crm) {
        this.crm = crm;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idacessounidade != null ? idacessounidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
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
