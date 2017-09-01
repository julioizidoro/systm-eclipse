package br.com.travelmate.model;

import java.io.Serializable;
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

@Entity
@Table(name="departamento")
public class Departamento implements Serializable{

	

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddepartamento")
    private Integer iddepartamento;
	@Column(name = "nome")
    private String nome;
	@Column(name = "imagem")
    private String imagem;
	@Column(name = "pasta")
	private boolean pasta;
	@Column(name = "lista")
	private boolean lista;
	@JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
	@ManyToOne(optional = false, cascade = CascadeType.REFRESH)
	private Usuario usuario;
	@JoinColumn(name = "unidadenegocio_idunidadenegocio", referencedColumnName = "idunidadenegocio")
	@ManyToOne(optional = false, cascade = CascadeType.REFRESH)
	private Unidadenegocio unidadenegocio;
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "departamento")
	private List<Pasta1> pasta1List;
	 
	public Departamento() {
	}

	public Integer getIddepartamento() {
		return iddepartamento;
	}

	public void setIddepartamento(Integer iddepartamento) {
		this.iddepartamento = iddepartamento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public boolean isPasta() {
		return pasta;
	}

	public void setPasta(boolean pasta) {
		this.pasta = pasta;
	}

	
	public List<Pasta1> getPasta1List() {
		return pasta1List;
	}

	public void setPasta1List(List<Pasta1> pasta1List) {
		this.pasta1List = pasta1List;
	}
	
	
	
	
	
	public boolean isLista() {
		return lista;
	}

	public void setLista(boolean lista) {
		this.lista = lista;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (iddepartamento != null ? iddepartamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        if ((this.iddepartamento == null && other.iddepartamento != null) || (this.iddepartamento != null && !this.iddepartamento.equals(other.iddepartamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Departamento[ iddepartamento=" + iddepartamento + " ]";
    }
	
	
}
