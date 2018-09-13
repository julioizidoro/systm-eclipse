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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "arquivoshitorico")
public class Arquivoshitorico implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idarquivoshitorico")
    private Integer idarquivoshitorico;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 10)
    @Column(name = "hora")
    private String hora;
    @Lob
    @Size(max = 16777215)
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "arquivos_idarquivos", referencedColumnName = "idarquivos")
    @ManyToOne(optional = false)
    private Arquivos arquivos;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;

	public Arquivoshitorico() {
	}

	public Integer getIdarquivoshitorico() {
		return idarquivoshitorico;
	}

	public void setIdarquivoshitorico(Integer idarquivoshitorico) {
		this.idarquivoshitorico = idarquivoshitorico;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Arquivos getArquivos() {
		return arquivos;
	}

	public void setArquivos(Arquivos arquivos) {
		this.arquivos = arquivos;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idarquivoshitorico == null) ? 0 : idarquivoshitorico.hashCode());
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
		Arquivoshitorico other = (Arquivoshitorico) obj;
		if (idarquivoshitorico == null) {
			if (other.idarquivoshitorico != null)
				return false;
		} else if (!idarquivoshitorico.equals(other.idarquivoshitorico))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Arquivoshitorico [idarquivoshitorico=" + idarquivoshitorico + "]";
	}
	
	

}
