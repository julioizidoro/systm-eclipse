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
 * @author Wolverine
 */
@Entity
@Table(name = "dadosalteracoes")
public class Dadosalteracoes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddadosalteracoes")
    private Integer iddadosalteracoes;
    @Size(max = 50)
    @Column(name = "campo")
    private String campo;
    @Size(max = 100)
    @Column(name = "dadoanterior")
    private String dadoanterior;
    @Size(max = 100)
    @Column(name = "dadoatual")
    private String dadoatual;
    @Column(name = "verificado")
    private boolean verificado;
    @JoinColumn(name = "controlealteracoes_idcontrolealteracoes", referencedColumnName = "idcontrolealteracoes")
    @ManyToOne(optional = false)
    private Controlealteracoes controlealteracoes;
    @JoinColumn(name = "departamento_iddepartamento", referencedColumnName = "iddepartamento")
    @ManyToOne(optional = false)
    private Departamento departamento;

    public Dadosalteracoes() {
    }

    public Dadosalteracoes(Integer iddadosalteracoes) {
        this.iddadosalteracoes = iddadosalteracoes;
    }

    public Integer getIddadosalteracoes() {
        return iddadosalteracoes;
    }

    public void setIddadosalteracoes(Integer iddadosalteracoes) {
        this.iddadosalteracoes = iddadosalteracoes;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getDadoanterior() {
        return dadoanterior;
    }

    public void setDadoanterior(String dadoanterior) {
        this.dadoanterior = dadoanterior;
    }

    public String getDadoatual() {
        return dadoatual;
    }

    public void setDadoatual(String dadoatual) {
        this.dadoatual = dadoatual;
    }

    

	public boolean isVerificado() {
		return verificado;
	}

	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}

	public Controlealteracoes getControlealteracoes() {
        return controlealteracoes;
    }

    public void setControlealteracoes(Controlealteracoes controlealteracoes) {
        this.controlealteracoes = controlealteracoes;
    }

    public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (iddadosalteracoes != null ? iddadosalteracoes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Dadosalteracoes)) {
            return false;
        }
        Dadosalteracoes other = (Dadosalteracoes) object;
        if ((this.iddadosalteracoes == null && other.iddadosalteracoes != null) || (this.iddadosalteracoes != null && !this.iddadosalteracoes.equals(other.iddadosalteracoes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Dadosalteracoes[ iddadosalteracoes=" + iddadosalteracoes + " ]";
    }
    
}

