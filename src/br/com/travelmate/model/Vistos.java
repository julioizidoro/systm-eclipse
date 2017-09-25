package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.Date;


/**
 * The persistent class for the vistos database table.
 * 
 */
@Entity
@Table(name="vistos")
public class Vistos implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvistos")
    private Integer idvistos;
    @Column(name = "dataEntregaDocumentos")
    @Temporal(TemporalType.DATE)
    private Date dataEntregaDocumentos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorEmissao")
    private Float valorEmissao;
    @Column(name = "taxaconsular")
    private Float taxaconsular;
    @Column(name = "taxaextra")
    private Float taxaextra;
    @Column(name = "taxatm")
    private Float taxatm;
    @Size(max = 50)
    @Column(name = "paisDestino")
    private String paisDestino;
    @Size(max = 30)
    @Column(name = "categoria")
    private String categoria;
    @Size(max = 50)
    @Column(name = "controle")
    private String controle;
    @Column(name = "dataembarque")
    @Temporal(TemporalType.DATE)
    private Date dataembarque;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @Column(name = "descontomatriz")
    private Float descontomatriz;
    @Column(name = "descontoloja")
    private Float descontoloja;
    @Column(name = "taxaloja")
    private Float taxaloja;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;
    @Transient
    private String imagem;
    @Transient
    private String tituloFicha;
    
    public Vistos() {
	}

	public Date getDataEntregaDocumentos() {
		return dataEntregaDocumentos;
	}

	public void setDataEntregaDocumentos(Date dataEntregaDocumentos) {
		this.dataEntregaDocumentos = dataEntregaDocumentos;
	}

	public Float getValorEmissao() {
		return valorEmissao;
	}

	public void setValorEmissao(Float valorEmissao) {
		this.valorEmissao = valorEmissao;
	}

	

	public Float getTaxaconsular() {
		return taxaconsular;
	}

	public void setTaxaconsular(Float taxaconsular) {
		this.taxaconsular = taxaconsular;
	}

	public Float getTaxaextra() {
		return taxaextra;
	}

	public void setTaxaextra(Float taxaextra) {
		this.taxaextra = taxaextra;
	}

	public Float getTaxatm() {
		return taxatm;
	}

	public void setTaxatm(Float taxatm) {
		this.taxatm = taxatm;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPaisDestino() {
		return paisDestino;
	}

	public void setPaisDestino(String paisDestino) {
		this.paisDestino = paisDestino;
	}

	public String getControle() {
		return controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
	}

	
	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	
	public void setIdvistos(Integer idvistos) {
		this.idvistos = idvistos;
	}

	public Date getDataembarque() {
		return dataembarque;
	}

	public void setDataembarque(Date dataembarque) {
		this.dataembarque = dataembarque;
	}

	public int getIdvistos() {
		return this.idvistos;
	}
	
	public Float getDescontomatriz() {
		return descontomatriz;
	}

	public void setDescontomatriz(Float descontomatriz) {
		this.descontomatriz = descontomatriz;
	}

	public Float getDescontoloja() {
		return descontoloja;
	}

	public void setDescontoloja(Float descontoloja) {
		this.descontoloja = descontoloja;
	}

	public Float getTaxaloja() {
		return taxaloja;
	}

	public void setTaxaloja(Float taxaloja) {
		this.taxaloja = taxaloja;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getTituloFicha() {
		return tituloFicha;
	}

	public void setTituloFicha(String tituloFicha) {
		this.tituloFicha = tituloFicha;
	}

	@Override
    public boolean equals(Object object) {
        if (!(object instanceof Vistos)) {
            return false;
        }
        Vistos other = (Vistos) object;
        if ((this.idvistos == null && other.idvistos != null) || (this.idvistos != null && !this.idvistos.equals(other.idvistos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Vistos[ idvistos=" + idvistos + " ]";
    }
	
	
	
}

	