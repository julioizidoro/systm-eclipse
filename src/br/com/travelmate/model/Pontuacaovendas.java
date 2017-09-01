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

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "pontuacaovendas")
public class Pontuacaovendas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpontuacaovendas")
    private Integer idpontuacaovendas;
    @JoinColumn(name = "regravenda_idregravenda", referencedColumnName = "idregravenda")
    @ManyToOne(optional = false)
    private Regravenda regravenda;
    @JoinColumn(name = "usuariopontos_idusuariopontos", referencedColumnName = "idusuariopontos")
    @ManyToOne(optional = false)
    private Usuariopontos usuariopontos;
   


    public Pontuacaovendas() {
    }

    public Pontuacaovendas(Integer idpontuacaovendas) {
        this.idpontuacaovendas = idpontuacaovendas;
    }

    public Integer getIdpontuacaovendas() {
        return idpontuacaovendas;
    }

    public void setIdpontuacaovendas(Integer idpontuacaovendas) {
        this.idpontuacaovendas = idpontuacaovendas;
    }

    public Regravenda getRegravenda() {
        return regravenda;
    }

    public void setRegravenda(Regravenda regravenda) {
        this.regravenda = regravenda;
    }

    public Usuariopontos getUsuariopontos() {
        return usuariopontos;
    }

    public void setUsuariopontos(Usuariopontos usuariopontos) {
        this.usuariopontos = usuariopontos;
    }

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpontuacaovendas != null ? idpontuacaovendas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pontuacaovendas)) {
            return false;
        }
        Pontuacaovendas other = (Pontuacaovendas) object;
        if ((this.idpontuacaovendas == null && other.idpontuacaovendas != null) || (this.idpontuacaovendas != null && !this.idpontuacaovendas.equals(other.idpontuacaovendas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pontuacaovendas[ idpontuacaovendas=" + idpontuacaovendas + " ]";
    }
    
}
