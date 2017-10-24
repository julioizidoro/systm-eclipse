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

@Entity
@Table(name = "cartaocreditolancamentocontas")
public class Cartaocreditolancamentocontas implements Serializable{
	
	 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "idcartaocreditolancamentocontas")
	    private Integer idcartaocreditolancamentocontas;
	 	@JoinColumn(name = "contaspagar_idcontaspagar", referencedColumnName = "idcontaspagar")
	    @ManyToOne(optional = false)
	    private Contaspagar contaspagar;
	 	@JoinColumn(name = "cartaocreditolancamento_idcartaocreditolancamento", referencedColumnName = "idcartaocreditolancamento")
	    @ManyToOne(optional = false)
	    private Cartaocreditolancamento cartaocreditolancamento;
	 	
	 	
	 	public Cartaocreditolancamentocontas() {
			// TODO Auto-generated constructor stub
		}


		


		public Integer getIdcartaocreditolancamentocontas() {
			return idcartaocreditolancamentocontas;
		}





		public void setIdcartaocreditolancamentocontas(Integer idcartaocreditolancamentocontas) {
			this.idcartaocreditolancamentocontas = idcartaocreditolancamentocontas;
		}





		public Contaspagar getContaspagar() {
			return contaspagar;
		}


		public void setContaspagar(Contaspagar contaspagar) {
			this.contaspagar = contaspagar;
		}


		public Cartaocreditolancamento getCartaocreditolancamento() {
			return cartaocreditolancamento;
		}


		public void setCartaocreditolancamento(Cartaocreditolancamento cartaocreditolancamento) {
			this.cartaocreditolancamento = cartaocreditolancamento;
		}
	 	
	 	
	 	

}
