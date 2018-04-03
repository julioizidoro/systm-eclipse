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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
@Table(name = "lead")
public class Lead implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idlead")
	private Integer idlead;
	@Column(name = "dataenvio")
	@Temporal(TemporalType.DATE)
	private Date dataenvio;
	@Size(max = 12)
	@Column(name = "horaenvio")
	private String horaenvio;
	@Column(name = "jaecliente")
	private Boolean jaecliente;
	@Column(name = "dataultimocontato")
	@Temporal(TemporalType.DATE)
	private Date dataultimocontato;
	@Column(name = "situacao")
	private Integer situacao;
	@Column(name = "dataproximocontato")
	@Temporal(TemporalType.DATE)
	private Date dataproximocontato;
	@Size(max = 12)
	@Column(name = "horaproximocontato")
	private String horaproximocontato;
	@Size(max = 100)
	@Column(name = "motivocancelamento")
	private String motivocancelamento;
	@Lob
	@Size(max = 2147483647)
	@Column(name = "notas")
	private String notas;
	@JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
	@ManyToOne(optional = false)
	private Cliente cliente;
	@JoinColumn(name = "pais_idpais", referencedColumnName = "idpais")
	@ManyToOne(optional = false)
	private Pais pais;
	@JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
	@ManyToOne(optional = false)
	private Produtos produtos;
	@JoinColumn(name = "publicidade_idpublicidade", referencedColumnName = "idpublicidade")
	@ManyToOne(optional = false)
	private Publicidade publicidade;
	@JoinColumn(name = "tipocontato_idtipocontato", referencedColumnName = "idtipocontato")
	@ManyToOne(optional = false)
	private Tipocontato tipocontato;
	@JoinColumn(name = "unidadenegocio_idunidadeNegocio", referencedColumnName = "idunidadeNegocio")
	@ManyToOne(optional = false)
	private Unidadenegocio unidadenegocio;
	@JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
	@ManyToOne(optional = false)
	private Usuario usuario;
	@JoinColumn(name = "motivocancelamento_idmotivocancelamento", referencedColumnName = "idmotivocancelamento")
	@ManyToOne(optional = false)
	private Motivocancelamento motivocancelamento1;
	@Column(name = "datarecebimento")
	@Temporal(TemporalType.DATE)
	private Date datarecebimento;
	@Size(max = 12)
	@Column(name = "horarecebimento")
	private String horarecebimento;
	@Transient
	private boolean selecionado;
	@OneToOne(cascade = CascadeType.REFRESH, mappedBy = "lead")
	private Leadposvenda leadposvenda;
	@Column(name = "Urlclient")
	private String urlclient;
	@Column(name = "idcontrole")
	private Integer idcontrole;
	@Column(name = "nomeunidade")
	private String nomeunidade;
	@Transient
	private Integer numeroPublicidade;

	public Lead() {
		idcontrole = 0;
	}

	public Lead(Integer idlead) {
		this.idlead = idlead;
	}

	public Integer getIdlead() {
		return idlead;
	}

	public void setIdlead(Integer idlead) {
		this.idlead = idlead;
	}

	public Date getDataenvio() {
		return dataenvio;
	}

	public void setDataenvio(Date dataenvio) {
		this.dataenvio = dataenvio;
	}

	public String getHoraenvio() {
		return horaenvio;
	}

	public void setHoraenvio(String horaenvio) {
		this.horaenvio = horaenvio;
	}

	public Boolean getJaecliente() {
		return jaecliente;
	}

	public void setJaecliente(Boolean jaecliente) {
		this.jaecliente = jaecliente;
	}

	public Date getDataultimocontato() {
		return dataultimocontato;
	}

	public void setDataultimocontato(Date dataultimocontato) {
		this.dataultimocontato = dataultimocontato;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public Date getDataproximocontato() {
		return dataproximocontato;
	}

	public void setDataproximocontato(Date dataproximocontato) {
		this.dataproximocontato = dataproximocontato;
	}

	public String getHoraproximocontato() {
		return horaproximocontato;
	}

	public void setHoraproximocontato(String horaproximocontato) {
		this.horaproximocontato = horaproximocontato;
	}

	public String getMotivocancelamento() {
		return motivocancelamento;
	}

	public void setMotivocancelamento(String motivocancelamento) {
		this.motivocancelamento = motivocancelamento;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}

	public Publicidade getPublicidade() {
		return publicidade;
	}

	public void setPublicidade(Publicidade publicidade) {
		this.publicidade = publicidade;
	}

	public Tipocontato getTipocontato() {
		return tipocontato;
	}

	public void setTipocontato(Tipocontato tipocontato) {
		this.tipocontato = tipocontato;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Motivocancelamento getMotivocancelamento1() {
		return motivocancelamento1;
	}

	public void setMotivocancelamento1(Motivocancelamento motivocancelamento1) {
		this.motivocancelamento1 = motivocancelamento1;
	}

	public Date getDatarecebimento() {
		return datarecebimento;
	}

	public void setDatarecebimento(Date datarecebimento) {
		this.datarecebimento = datarecebimento;
	}

	public String getHorarecebimento() {
		return horarecebimento;
	}

	public void setHorarecebimento(String horarecebimento) {
		this.horarecebimento = horarecebimento;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public Leadposvenda getLeadposvenda() {
		return leadposvenda;
	}

	public void setLeadposvenda(Leadposvenda leadposvenda) {
		this.leadposvenda = leadposvenda;
	}

	public String getUrlclient() {
		return urlclient;
	}

	public void setUrlclient(String urlclient) {
		this.urlclient = urlclient;
	}

	public Integer getIdcontrole() {
		return idcontrole;
	}

	public void setIdcontrole(Integer idcontrole) {
		this.idcontrole = idcontrole;
	}

	public String getNomeunidade() {
		return nomeunidade;
	}

	public void setNomeunidade(String nomeunidade) {
		this.nomeunidade = nomeunidade;
	}

	public Integer getNumeroPublicidade() {
		return numeroPublicidade;
	}

	public void setNumeroPublicidade(Integer numeroPublicidade) {
		this.numeroPublicidade = numeroPublicidade;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idlead != null ? idlead.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof Lead)) {
			return false;
		}
		Lead other = (Lead) object;
		if ((this.idlead == null && other.idlead != null)
				|| (this.idlead != null && !this.idlead.equals(other.idlead))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Lead[ idlead=" + idlead + " ]";
	}

}