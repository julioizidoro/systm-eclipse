package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;



/**
 * The persistent class for the valoresvistos database table.
 * 
 */
@Entity
@Table(name="valoresvistos")
public class Valoresvistos implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvaloresvistos")
    private Integer idvaloresvistos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valornet")
    private Float valornet;
    @Column(name = "valorgross")
    private Float valorgross;
    @Size(max = 50)
    @Column(name = "categoria")
    private String categoria;
    @Size(max = 10)
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "comissaopremium")
    private Float comissaopremium;
    @Column(name = "comissaoexpress")
    private Float comissaoexpress;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;

   

    public Integer getIdvaloresvistos() {
        return idvaloresvistos;
    }

    public void setIdvaloresvistos(Integer idvaloresvistos) {
        this.idvaloresvistos = idvaloresvistos;
    }

    public Float getValornet() {
        return valornet;
    }

    public void setValornet(Float valornet) {
        this.valornet = valornet;
    }

    public Float getValorgross() {
        return valorgross;
    }

    public void setValorgross(Float valorgross) {
        this.valorgross = valorgross;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Float getComissaopremium() {
        return comissaopremium;
    }

    public void setComissaopremium(Float comissaopremium) {
        this.comissaopremium = comissaopremium;
    }

    public Float getComissaoexpress() {
        return comissaoexpress;
    }

    public void setComissaoexpress(Float comissaoexpress) {
        this.comissaoexpress = comissaoexpress;
    }

  
    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvaloresvistos != null ? idvaloresvistos.hashCode() : 0);
        return hash;
    }

    

    @Override
    public String toString() {
        return "model.Valoresvistos[ idvaloresvistos=" + idvaloresvistos + " ]";
    }
    
}