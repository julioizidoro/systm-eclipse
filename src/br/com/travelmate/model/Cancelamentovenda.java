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
@Table(name = "cancelamentovenda")
public class Cancelamentovenda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcancelamentovenda")
    private Integer idcancelamentovenda;
    @JoinColumn(name = "cancelamento_idcancelamento", referencedColumnName = "idcancelamento")
    @ManyToOne(optional = false)
    private Cancelamento cancelamento;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;

    public Cancelamentovenda() {
    }

    public Cancelamentovenda(Integer idcancelamentovenda) {
        this.idcancelamentovenda = idcancelamentovenda;
    }

    public Integer getIdcancelamentovenda() {
        return idcancelamentovenda;
    }

    public void setIdcancelamentovenda(Integer idcancelamentovenda) {
        this.idcancelamentovenda = idcancelamentovenda;
    }

    public Cancelamento getCancelamento() {
        return cancelamento;
    }

    public void setCancelamento(Cancelamento cancelamento) {
        this.cancelamento = cancelamento;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcancelamentovenda != null ? idcancelamentovenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cancelamentovenda)) {
            return false;
        }
        Cancelamentovenda other = (Cancelamentovenda) object;
        if ((this.idcancelamentovenda == null && other.idcancelamentovenda != null) || (this.idcancelamentovenda != null && !this.idcancelamentovenda.equals(other.idcancelamentovenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Cancelamentovenda[ idcancelamentovenda=" + idcancelamentovenda + " ]";
    }
    
}
