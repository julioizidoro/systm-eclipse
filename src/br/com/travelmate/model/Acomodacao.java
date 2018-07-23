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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "acomodacao")
public class Acomodacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idacomodacao")
    private Integer idacomodacao;
    
    @Column(name = "tipoacomodacao")
    private String tipoacomodacao;
    @Column(name = "tipoquarto")
    private String tipoquarto;
    @Column(name = "tipobanheiro")
    private String tipobanheiro;
    @Column(name = "tiporefeicao")
    private String tiporefeicao;
    @Column(name = "datainicial")
    @Temporal(TemporalType.DATE)
    private Date datainicial;
    @Column(name = "numerosemana")
    private Integer numerosemana;
    @Column(name = "datadinal")
    @Temporal(TemporalType.DATE)
    private Date datatermino;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valormoedaestrangeira")
    private Float valormoedaestrangeira;
    @Column(name = "valormoedanacional")
    private Float valormoedanacional;
    @Column(name = "familiacomCrianca")
    private String familiacomCrianca;
    @Column(name = "familiacomAnimais")
    private String familiacomAnimais;
    @Column(name = "fumante")
    private String fumante;
    @Column(name = "vegetariano")
    private String vegetariano;
    @Column(name = "hobbies")
    private String hobbies;
    @Column(name = "possuiAlergia")
    private String possuiAlergia;
    @Column(name = "quaisAlergias")
    private String quaisAlergias;
    @Column(name = "solicitacoesEspeciais")
    private String solicitacoesEspeciais;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
    @ManyToOne(optional = false)
    private Produtos produtos;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "acomodacao")
	private Acomodacaocurso acomodacaocurso;
   

    public Acomodacao() {
    }

    

    public Integer getIdacomodacao() {
		return idacomodacao;
	}



	public void setIdacomodacao(Integer idacomodacao) {
		this.idacomodacao = idacomodacao;
	}



	public String getTipoacomodacao() {
		return tipoacomodacao;
	}



	public void setTipoacomodacao(String tipoacomodacao) {
		this.tipoacomodacao = tipoacomodacao;
	}



	public String getTipoquarto() {
		return tipoquarto;
	}



	public void setTipoquarto(String tipoquarto) {
		this.tipoquarto = tipoquarto;
	}



	public String getTipobanheiro() {
		return tipobanheiro;
	}



	public void setTipobanheiro(String tipobanheiro) {
		this.tipobanheiro = tipobanheiro;
	}



	public String getTiporefeicao() {
		return tiporefeicao;
	}



	public void setTiporefeicao(String tiporefeicao) {
		this.tiporefeicao = tiporefeicao;
	}



	public Date getDatainicial() {
		return datainicial;
	}



	public void setDatainicial(Date datainicial) {
		this.datainicial = datainicial;
	}



	public Integer getNumerosemana() {
		return numerosemana;
	}



	public void setNumerosemana(Integer numerosemana) {
		this.numerosemana = numerosemana;
	}



	public Date getDatatermino() {
		return datatermino;
	}



	public void setDatatermino(Date datatermino) {
		this.datatermino = datatermino;
	}



	public Float getValormoedaestrangeira() {
		return valormoedaestrangeira;
	}



	public void setValormoedaestrangeira(Float valormoedaestrangeira) {
		this.valormoedaestrangeira = valormoedaestrangeira;
	}



	public Float getValormoedanacional() {
		return valormoedanacional;
	}



	public void setValormoedanacional(Float valormoedanacional) {
		this.valormoedanacional = valormoedanacional;
	}



	public String getFamiliacomCrianca() {
		return familiacomCrianca;
	}



	public void setFamiliacomCrianca(String familiacomCrianca) {
		this.familiacomCrianca = familiacomCrianca;
	}



	public String getFamiliacomAnimais() {
		return familiacomAnimais;
	}



	public void setFamiliacomAnimais(String familiacomAnimais) {
		this.familiacomAnimais = familiacomAnimais;
	}



	public String getFumante() {
		return fumante;
	}



	public void setFumante(String fumante) {
		this.fumante = fumante;
	}



	public String getVegetariano() {
		return vegetariano;
	}



	public void setVegetariano(String vegetariano) {
		this.vegetariano = vegetariano;
	}



	public String getHobbies() {
		return hobbies;
	}



	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}



	public String getPossuiAlergia() {
		return possuiAlergia;
	}



	public void setPossuiAlergia(String possuiAlergia) {
		this.possuiAlergia = possuiAlergia;
	}



	public String getQuaisAlergias() {
		return quaisAlergias;
	}



	public void setQuaisAlergias(String quaisAlergias) {
		this.quaisAlergias = quaisAlergias;
	}



	public String getSolicitacoesEspeciais() {
		return solicitacoesEspeciais;
	}



	public void setSolicitacoesEspeciais(String solicitacoesEspeciais) {
		this.solicitacoesEspeciais = solicitacoesEspeciais;
	}



	public Vendas getVendas() {
		return vendas;
	}



	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}



	public Produtos getProdutos() {
		return produtos;
	}



	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}



	public Acomodacaocurso getAcomodacaocurso() {
		return acomodacaocurso;
	}



	public void setAcomodacaocurso(Acomodacaocurso acomodacaocurso) {
		this.acomodacaocurso = acomodacaocurso;
	}



	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idacomodacao != null ? idacomodacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Acomodacao)) {
            return false;
        }
        Acomodacao other = (Acomodacao) object;
        if ((this.idacomodacao == null && other.idacomodacao != null) || (this.idacomodacao != null && !this.idacomodacao.equals(other.idacomodacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Acomodacao[ idacomodacao=" + idacomodacao + " ]";
    }
    
}
