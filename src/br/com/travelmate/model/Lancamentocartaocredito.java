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


@Entity
@Table( name= "lancamentocartaocredito")
public class Lancamentocartaocredito implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "idlancamentocartaocredito")
	    private Integer idlancamentocartaocredito;
	    @Column(name = "datainclusao")
	    @Temporal(TemporalType.DATE)
	    private Date datainclusao;
	    @Column(name = "datalancamento")
	    @Temporal(TemporalType.DATE)
	    private Date datalancamento;
	    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
	    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
	    private Usuario usuario;
	    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
	    @ManyToOne(optional = false)
	    private Vendas vendas;
	    @Column(name = "lancado")
	    private boolean lancado;
	    @Column(name = "dataproximocontato")
	    @Temporal(TemporalType.DATE)
	    private Date dataproximocontato;
	    
	    
	    public Lancamentocartaocredito() {
		}


		public Integer getIdlancamentocartaocredito() {
			return idlancamentocartaocredito;
		}


		public void setIdlancamentocartaocredito(Integer idlancamentocartaocredito) {
			this.idlancamentocartaocredito = idlancamentocartaocredito;
		}


		public Date getDatainclusao() {
			return datainclusao;
		}


		public void setDatainclusao(Date datainclusao) {
			this.datainclusao = datainclusao;
		}


		public Date getDatalancamento() {
			return datalancamento;
		}


		public void setDatalancamento(Date datalancamento) {
			this.datalancamento = datalancamento;
		}


		public Usuario getUsuario() {
			return usuario;
		}


		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}


		public Vendas getVendas() {
			return vendas;
		}


		public void setVendas(Vendas vendas) {
			this.vendas = vendas;
		}
	    
	    
		public boolean isLancado() {
			return lancado;
		}


		public void setLancado(boolean lancado) {
			this.lancado = lancado;
		}


		public Date getDataproximocontato() {
			return dataproximocontato;
		}


		public void setDataproximocontato(Date dataproximocontato) {
			this.dataproximocontato = dataproximocontato;
		}


		@Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (idlancamentocartaocredito != null ? idlancamentocartaocredito.hashCode() : 0);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object object) {
	        if (!(object instanceof Lancamentocartaocredito)) {
	            return false;
	        }
	        Lancamentocartaocredito other = (Lancamentocartaocredito) object;
	        if ((this.idlancamentocartaocredito == null && other.idlancamentocartaocredito != null) || (this.idlancamentocartaocredito != null && !this.idlancamentocartaocredito.equals(other.idlancamentocartaocredito))) {
	            return false;
	        }
	        return true;
	    }

}
