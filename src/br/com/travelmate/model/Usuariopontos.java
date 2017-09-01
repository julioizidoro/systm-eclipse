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

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "usuariopontos")
public class Usuariopontos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idusuariopontos")
    private Integer idusuariopontos;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "pontos")
    private Integer pontos;
    @Column(name = "pontoescola")
    private Integer pontoescola;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "usuariopontos")
    private List<Pontuacaovendas> pontuacaovendasList;

    public Usuariopontos() {
    	setPontoescola(0);
    	setPontos(0);
    }

    public Usuariopontos(Integer idusuariopontos) {
        this.idusuariopontos = idusuariopontos;
    }

    public Integer getIdusuariopontos() {
        return idusuariopontos;
    }

    public void setIdusuariopontos(Integer idusuariopontos) {
        this.idusuariopontos = idusuariopontos;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Pontuacaovendas> getPontuacaovendasList() {
        return pontuacaovendasList;
    }

    public void setPontuacaovendasList(List<Pontuacaovendas> pontuacaovendasList) {
        this.pontuacaovendasList = pontuacaovendasList;
    }

    public Integer getPontoescola() {
		return pontoescola;
	}

	public void setPontoescola(Integer pontoescola) {
		this.pontoescola = pontoescola;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idusuariopontos != null ? idusuariopontos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Usuariopontos)) {
            return false;
        }
        Usuariopontos other = (Usuariopontos) object;
        if ((this.idusuariopontos == null && other.idusuariopontos != null) || (this.idusuariopontos != null && !this.idusuariopontos.equals(other.idusuariopontos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Usuariopontos[ idusuariopontos=" + idusuariopontos + " ]";
    }
    
}
