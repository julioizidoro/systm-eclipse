package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.*;



@Entity
@Table(name = "grupoobrigatorio")
@NamedQueries({
    @NamedQuery(name = "Grupoobrigatorio.findAll", query = "SELECT g FROM Grupoobrigatorio g")})
	public class Grupoobrigatorio implements Serializable {
	    private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "idgrupoobrigatorio")
	    private Integer idgrupoobrigatorio;
	    @Column(name = "menorobrigatorio")
	    private boolean menorobrigatorio;
	    @JoinColumn(name = "coprodutos_idcoprodutos", referencedColumnName = "idcoprodutos")
	    @ManyToOne(optional = false)
	    private Coprodutos coprodutos;
	    @JoinColumn(name = "coprodutos_produto", referencedColumnName = "idcoprodutos")
	    @ManyToOne(optional = false)
	    private Coprodutos produto;
	    

	    public Grupoobrigatorio() {
	    }
	
	    public Grupoobrigatorio(Integer idgrupoobrigatorio) {
	        this.idgrupoobrigatorio = idgrupoobrigatorio;
	    }
	
	    public Integer getIdgrupoobrigatorio() {
	        return idgrupoobrigatorio;
	    }
	
	    public void setIdgrupoobrigatorio(Integer idgrupoobrigatorio) {
	        this.idgrupoobrigatorio = idgrupoobrigatorio;
	    }
	
	    public Coprodutos getCoprodutos() {
	        return coprodutos;
	    }
	
	    public void setCoprodutos(Coprodutos coprodutos) {
	        this.coprodutos = coprodutos;
	    }
	
	    public Coprodutos getProduto() {
			return produto;
		}

		public void setProduto(Coprodutos produto) {
			this.produto = produto;
		}

		public boolean isMenorobrigatorio() {
			return menorobrigatorio;
		}

		public void setMenorobrigatorio(boolean menorobrigatorio) {
			this.menorobrigatorio = menorobrigatorio;
		}

		@Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (idgrupoobrigatorio != null ? idgrupoobrigatorio.hashCode() : 0);
	        return hash;
	    }
	
	    @Override
	    public boolean equals(Object object) {
	        if (!(object instanceof Grupoobrigatorio)) {
	            return false;
	        }
	        Grupoobrigatorio other = (Grupoobrigatorio) object;
	        if ((this.idgrupoobrigatorio == null && other.idgrupoobrigatorio != null) || (this.idgrupoobrigatorio != null && !this.idgrupoobrigatorio.equals(other.idgrupoobrigatorio))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "br.com.travelmate.model.Grupoobrigatorio[ idgrupoobrigatorio=" + idgrupoobrigatorio + " ]";
	    }
    
}