/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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

import br.com.travelmate.util.Formatacao;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "logvenda")
public class Logvenda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlogvenda")
    private Integer idlogvenda;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Size(max = 10)
    @Column(name = "hora")
    private String hora;
    @Size(max = 30)
    @Column(name = "situacao")
    private String situacao;
    @Size(max = 50)
    @Column(name = "operacao")
    private String operacao;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;
        

    
    public Logvenda() {
    	setData(new Date());
    	setHora(Formatacao.foramtarHoraString());
    }
    

    public Integer getIdlogvenda() {
		return idlogvenda;
	}

	public void setIdlogvenda(Integer idlogvenda) {
		this.idlogvenda = idlogvenda;
	}


	public Date getData() {
		return data;
	}





	public void setData(Date data) {
		this.data = data;
	}





	public String getHora() {
		return hora;
	}





	public void setHora(String hora) {
		this.hora = hora;
	}





	public String getSituacao() {
		return situacao;
	}





	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}





	public String getOperacao() {
		return operacao;
	}





	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}





	public Vendas getVendas() {
		return vendas;
	}





	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}





	public Usuario getUsuario() {
		return usuario;
	}





	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}





	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idlogvenda == null) ? 0 : idlogvenda.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Logvenda other = (Logvenda) obj;
		if (idlogvenda == null) {
			if (other.idlogvenda != null)
				return false;
		} else if (!idlogvenda.equals(other.idlogvenda))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Logvenda [idlogvenda=" + idlogvenda + "]";
	}

    
}
