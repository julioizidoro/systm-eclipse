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
import javax.persistence.ManyToOne;  
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;


@Entity
@Table(name = "valoreshighschool")
public class Valoreshighschool implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvaloresHighSchool")
    private Integer idvaloresHighSchool;
    @Size(max = 100)
    @Column(name = "duracao")
    private String duracao;
    @Size(max = 100)
    @Column(name = "inicio")
    private String inicio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorgross")
    private Float valorgross;
    @Column(name = "valornet")
    private Float valornet;
    @Size(max = 10)
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "comissaopremium")
    private Float comissaopremium;
    @Column(name = "comissaoexpress")
    private Float comissaoexpress;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "valoreshighschool")
    private List<Highschool> highschoolList;
    @JoinColumn(name = "produtosOrcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento;
    @JoinColumn(name = "moedas_idmoedas1", referencedColumnName = "idmoedas")
    @ManyToOne(optional = false)
    private Moedas moedas;
    @JoinColumn(name = "moedas_idmoedas", referencedColumnName = "idmoedas")
    @ManyToOne(optional = false)
    private Moedas moedas1;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;
    @JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
    @ManyToOne(optional = false)
    private Fornecedor fornecedor;
    @Column(name = "datainicio")
    @Temporal(TemporalType.DATE)
    private Date datainicio;
    @Column(name = "datavalidade")
    @Temporal(TemporalType.DATE)
    private Date datavalidade;
    @Column(name = "anoinicio") 
    private String anoinicio;

    public Valoreshighschool() {
    }

    public Valoreshighschool(Integer idvaloresHighSchool) {
        this.idvaloresHighSchool = idvaloresHighSchool;
    }

    public Integer getIdvaloresHighSchool() {
        return idvaloresHighSchool;
    }

    public void setIdvaloresHighSchool(Integer idvaloresHighSchool) {
        this.idvaloresHighSchool = idvaloresHighSchool;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public Float getValorgross() {
        return valorgross;
    }

    public void setValorgross(Float valorgross) {
        this.valorgross = valorgross;
    }

    public Float getValornet() {
        return valornet;
    }

    public void setValornet(Float valornet) {
        this.valornet = valornet;
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

    public List<Highschool> getHighschoolList() {
        return highschoolList;
    }

    public void setHighschoolList(List<Highschool> highschoolList) {
        this.highschoolList = highschoolList;
    }

    public Produtosorcamento getProdutosorcamento() {
        return produtosorcamento;
    }

    public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
        this.produtosorcamento = produtosorcamento;
    }

    public Moedas getMoedas() {
        return moedas;
    }

    public void setMoedas(Moedas moedas) {
        this.moedas = moedas;
    }

    public Moedas getMoedas1() {
        return moedas1;
    }

    public void setMoedas1(Moedas moedas1) {
        this.moedas1 = moedas1;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
    

    public Date getDatavalidade() {
		return datavalidade;
	}

	public void setDatavalidade(Date datavalidade) {
		this.datavalidade = datavalidade;
	}

	public String getAnoinicio() {
		return anoinicio;
	}

	public void setAnoinicio(String anoinicio) {
		this.anoinicio = anoinicio;
	}

	public Date getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idvaloresHighSchool != null ? idvaloresHighSchool.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Valoreshighschool)) {
            return false;
        }
        Valoreshighschool other = (Valoreshighschool) object;
        if ((this.idvaloresHighSchool == null && other.idvaloresHighSchool != null) || (this.idvaloresHighSchool != null && !this.idvaloresHighSchool.equals(other.idvaloresHighSchool))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Valoreshighschool[ idvaloresHighSchool=" + idvaloresHighSchool + " ]";
    }
    
}
