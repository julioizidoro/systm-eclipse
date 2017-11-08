package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.Date;

/**
 * The persistent class for the ocursoseguro database table.
 * 
 */
@Entity
@Table(name = "ocursoseguro")
public class Ocursoseguro implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idocursoseguro")
	private Integer idocursoseguro;
	@Size(max = 100)
	@Column(name = "seguradora")
	private String seguradora;
	@Column(name = "datainicial")
	@Temporal(TemporalType.DATE)
	private Date datainicial;
	@Column(name = "numerodias")
	private Integer numerodias;
	@Column(name = "datafinal")
	@Temporal(TemporalType.DATE)
	private Date datafinal;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "valor")
	private Float valor;
	@Column(name = "valormoedaestrangeira")
	private Float valormoedaestrangeira;
	@JoinColumn(name = "ocurso_idocurso", referencedColumnName = "idocurso")
	@ManyToOne(optional = false)
	private Ocurso ocurso;
	@JoinColumn(name = "valoresseguro_idvaloresseguro", referencedColumnName = "idvaloresseguro")
	@ManyToOne(optional = false)
	private Valoresseguro valoresseguro;
	@Column(name = "somarvalortotal")
	private boolean somarvalortotal;
	@Column(name = "valorseguroorcamento")
	private Float valorseguroorcamento;
	@Column(name = "segurocancelamento")
	private boolean segurocancelamento;

	public Ocursoseguro() {
	}

	public Ocursoseguro(Integer idocursoseguro) {
		this.idocursoseguro = idocursoseguro;
	}

	public Integer getIdocursoseguro() {
		return idocursoseguro;
	}

	public void setIdocursoseguro(Integer idocursoseguro) {
		this.idocursoseguro = idocursoseguro;
	}

	public String getSeguradora() {
		return seguradora;
	}

	public void setSeguradora(String seguradora) {
		this.seguradora = seguradora;
	}

	public Date getDatainicial() {
		return datainicial;
	}

	public void setDatainicial(Date datainicial) {
		this.datainicial = datainicial;
	}

	public Integer getNumerodias() {
		return numerodias;
	}

	public void setNumerodias(Integer numerodias) {
		this.numerodias = numerodias;
	}

	public Date getDatafinal() {
		return datafinal;
	}

	public void setDatafinal(Date datafinal) {
		this.datafinal = datafinal;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Valoresseguro getValoresseguro() {
		return valoresseguro;
	}

	public void setValoresseguro(Valoresseguro valoresseguro) {
		this.valoresseguro = valoresseguro;
	}

	public Ocurso getOcurso() {
		return ocurso;
	}

	public void setOcurso(Ocurso ocurso) {
		this.ocurso = ocurso;
	}

	public Float getValormoedaestrangeira() {
		return valormoedaestrangeira;
	}

	public void setValormoedaestrangeira(Float valormoedaestrangeira) {
		this.valormoedaestrangeira = valormoedaestrangeira;
	}

	public boolean isSomarvalortotal() {
		return somarvalortotal;
	}

	public void setSomarvalortotal(boolean somarvalortotal) {
		this.somarvalortotal = somarvalortotal;
	}

	public Float getValorseguroorcamento() {
		return valorseguroorcamento;
	}

	public void setValorseguroorcamento(Float valorseguroorcamento) {
		this.valorseguroorcamento = valorseguroorcamento;
	}

	public boolean isSegurocancelamento() {
		return segurocancelamento;
	}

	public void setSegurocancelamento(boolean segurocancelamento) {
		this.segurocancelamento = segurocancelamento;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idocursoseguro != null ? idocursoseguro.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof Ocursoseguro)) {
			return false;
		}
		Ocursoseguro other = (Ocursoseguro) object;
		if ((this.idocursoseguro == null && other.idocursoseguro != null)
				|| (this.idocursoseguro != null && !this.idocursoseguro.equals(other.idocursoseguro))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Ocursoseguro[ idocursoseguro=" + idocursoseguro + " ]";
	}

}