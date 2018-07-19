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
import javax.validation.constraints.Size;

@Entity
@Table(name = "heorcamento")
public class Heorcamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idheorcamento")
    private Integer idheorcamento;
    @Column(name = "dataemissao")
    @Temporal(TemporalType.DATE)
    private Date dataemissao; 
    @Size(max = 10)
    @Column(name = "horaemissao")
    private String horaemissao;
    @Lob
    @Size(max = 16777215)
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "valorassessoria")
    private Float valorassessoria;
    @Column(name = "historicomedio")
    private boolean historicomedio;
    @Column(name = "passaporte")
    private boolean passaporte;
    @Column(name = "historicosuperior")
    private boolean historicosuperior;
    @Column(name = "score")
    private boolean score;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "heorcamento")
    private List<Heorcamentopais> heorcamentopaisList;
    
	public Heorcamento() {
		
	}

	public Integer getIdheorcamento() {
		return idheorcamento;
	}

	public void setIdheorcamento(Integer idheorcamento) {
		this.idheorcamento = idheorcamento;
	}

	public Date getDataemissao() {
		return dataemissao;
	}

	public void setDataemissao(Date dataemissao) {
		this.dataemissao = dataemissao;
	}

	public String getHoraemissao() {
		return horaemissao;
	}

	public void setHoraemissao(String horaemissao) {
		this.horaemissao = horaemissao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Float getValorassessoria() {
		return valorassessoria;
	}

	public void setValorassessoria(Float valorassessoria) {
		this.valorassessoria = valorassessoria;
	}

	public boolean isHistoricomedio() {
		return historicomedio;
	}

	public void setHistoricomedio(boolean historicomedio) {
		this.historicomedio = historicomedio;
	}

	public boolean isPassaporte() {
		return passaporte;
	}

	public void setPassaporte(boolean passaporte) {
		this.passaporte = passaporte;
	}

	public boolean isHistoricosuperior() {
		return historicosuperior;
	}

	public void setHistoricosuperior(boolean historicosuperior) {
		this.historicosuperior = historicosuperior;
	}

	public boolean isScore() {
		return score;
	}

	public void setScore(boolean score) {
		this.score = score;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Heorcamentopais> getHeorcamentopaisList() {
		return heorcamentopaisList;
	}

	public void setHeorcamentopaisList(List<Heorcamentopais> heorcamentopaisList) {
		this.heorcamentopaisList = heorcamentopaisList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idheorcamento == null) ? 0 : idheorcamento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Heorcamento other = (Heorcamento) obj;
		if (idheorcamento == null) {
			if (other.idheorcamento != null)
				return false;
		} else if (!idheorcamento.equals(other.idheorcamento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Heorcamento [idheorcamento=" + idheorcamento + "]";
	}
	
	
	
    
    




}
