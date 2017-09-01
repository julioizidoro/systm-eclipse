/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "ocrusoprodutos")
@NamedQueries({ @NamedQuery(name = "Ocrusoprodutos.findAll", query = "SELECT o FROM Ocrusoprodutos o") })
public class Ocrusoprodutos implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idocrusoprodutos")
	private Integer idocrusoprodutos;
	@Column(name = "nome")
	private String nome;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "tipo")
	private int tipo;
	@Column(name = "nomegrupo")
	private String nomegrupo;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "numerosemanas")
	private Double numerosemanas;
	@Column(name = "valorpromocional")
	private Float valorpromocional;
	@Column(name = "valororiginal")
	private Float valororiginal;
	@Column(name = "possuipromocao")
	private boolean possuipromocao;
	@Size(max = 20)
	@Column(name = "codigo")
	private String codigo;
	@JoinColumn(name = "ocurso_idocurso", referencedColumnName = "idocurso")
	@ManyToOne(optional = false)
	private Ocurso ocurso;
	@JoinColumn(name = "valorcoprodutos_idvalorcoprodutos", referencedColumnName = "idvalorcoprodutos")
	@ManyToOne(optional = false)
	private Valorcoprodutos valorcoprodutos;
	@Column(name = "somavalortotal")
	private boolean somavalortotal;

	// tipo Produtos
	// 1-Obrigatorios
	// 2-Opcional
	// 3-Acomodacao
	// 4-Opcional Acomodacao
	// 5-Transfer
	// 6-Adicional

	public Ocrusoprodutos() {
	}

	public Ocrusoprodutos(Integer idocrusoprodutos) {
		this.idocrusoprodutos = idocrusoprodutos;
	}

	public Integer getIdocrusoprodutos() {
		return idocrusoprodutos;
	}

	public void setIdocrusoprodutos(Integer idocrusoprodutos) {
		this.idocrusoprodutos = idocrusoprodutos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getNumerosemanas() {
		return numerosemanas;
	}

	public void setNumerosemanas(Double numerosemanas) {
		this.numerosemanas = numerosemanas;
	}

	public Float getValorpromocional() {
		return valorpromocional;
	}

	public void setValorpromocional(Float valorpromocional) {
		this.valorpromocional = valorpromocional;
	}

	public Float getValororiginal() {
		return valororiginal;
	}

	public void setValororiginal(Float valororiginal) {
		this.valororiginal = valororiginal;
	}

	public Ocurso getOcurso() {
		return ocurso;
	}

	public void setOcurso(Ocurso ocurso) {
		this.ocurso = ocurso;
	}

	public Valorcoprodutos getValorcoprodutos() {
		return valorcoprodutos;
	}

	public void setValorcoprodutos(Valorcoprodutos valorcoprodutos) {
		this.valorcoprodutos = valorcoprodutos;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getNomegrupo() {
		return nomegrupo;
	}

	public void setNomegrupo(String nomegrupo) {
		this.nomegrupo = nomegrupo;
	}

	public boolean isPossuipromocao() {
		return possuipromocao;
	}

	public void setPossuipromocao(boolean possuipromocao) {
		this.possuipromocao = possuipromocao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public boolean isSomavalortotal() {
		return somavalortotal;
	}

	public void setSomavalortotal(boolean somavalortotal) {
		this.somavalortotal = somavalortotal;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idocrusoprodutos != null ? idocrusoprodutos.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Ocrusoprodutos)) {
			return false;
		}
		Ocrusoprodutos other = (Ocrusoprodutos) object;
		if ((this.idocrusoprodutos == null && other.idocrusoprodutos != null)
				|| (this.idocrusoprodutos != null && !this.idocrusoprodutos.equals(other.idocrusoprodutos))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Ocrusoprodutos[ idocrusoprodutos=" + idocrusoprodutos + " ]";
	}

}
