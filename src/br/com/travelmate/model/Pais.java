/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;
 
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "pais")
public class Pais implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpais")
    private Integer idpais;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Size(max = 5)
    @Column(name = "sigla")
    private String sigla;
    @JoinColumn(name = "moedas_idmoedas", referencedColumnName = "idmoedas")
    @ManyToOne(optional = false)
    private Moedas moedas;
    @JoinColumn(name = "moedas_voluntariado", referencedColumnName = "idmoedas")
    @ManyToOne(optional = false)
    private Moedas moedasVolutariado;
    @Lob
    @Size(max = 16777215)
    @Column(name = "texto")
    private String texto;
    @Column(name = "documentovisto")
    private String documentovisto;
    @Transient
    private boolean selecionado;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pais") 
	private List<Paisproduto> paisprodutoList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pais")
    @OrderBy(value="nome") 
    private List<Cidade> cidadeList;
    @Column(name = "modelo")
    private int modelo;
    @Column(name = "possuifranquia")
    private boolean possuifranquia;
    @Column(name = "datacambio")
	@Temporal(TemporalType.DATE)
	private Date datacambio;
    @Column(name = "taxatm")
    private float taxatm;
    
    public Pais() {
    	taxatm = 0.0f;
    }

    public Pais(Integer idpais) {
        this.idpais = idpais;
    }

    public Integer getIdpais() {
        return idpais;
    }

    public void setIdpais(Integer idpais) {
        this.idpais = idpais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    

    public Moedas getMoedas() {
        return moedas;
    }

    public void setMoedas(Moedas moedas) {
        this.moedas = moedas;
    }

    public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public String getDocumentovisto() {
		return documentovisto;
	}

	public void setDocumentovisto(String documentovisto) {
		this.documentovisto = documentovisto;
	}

	

	public int getModelo() {
		return modelo;
	}

	public void setModelo(int modelo) {
		this.modelo = modelo;
	}

	public Moedas getMoedasVolutariado() {
		return moedasVolutariado;
	}

	public void setMoedasVolutariado(Moedas moedasVolutariado) {
		this.moedasVolutariado = moedasVolutariado;
	}

	public List<Paisproduto> getPaisprodutoList() {
		return paisprodutoList;
	}

	public void setPaisprodutoList(List<Paisproduto> paisprodutoList) {
		this.paisprodutoList = paisprodutoList;
	}

	public boolean isPossuifranquia() {
		return possuifranquia;
	}

	public void setPossuifranquia(boolean possuifranquia) {
		this.possuifranquia = possuifranquia;
	}

	public Date getDatacambio() {
		return datacambio;
	}

	public void setDatacambio(Date datacambio) {
		this.datacambio = datacambio;
	}

	public float getTaxatm() {
		return taxatm;
	}

	public void setTaxatm(float taxatm) {
		this.taxatm = taxatm;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpais != null ? idpais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if ((this.idpais == null && other.idpais != null) || (this.idpais != null && !this.idpais.equals(other.idpais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pais[ idpais=" + idpais + " ]";
    }

    public List<Cidade> getCidadeList() {
    	if (cidadeList!=null){
    		for(int i=0;i<cidadeList.size();i++){
    			if (!cidadeList.get(i).isAtiva()){
    				cidadeList.remove(i);
    			}
    		}
    	}
        return cidadeList;
    }

    public void setCidadeList(List<Cidade> cidadeList) {
        this.cidadeList = cidadeList;
    }


    
}
