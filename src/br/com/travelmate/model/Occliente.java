package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.Date;


/**
 * The persistent class for the occliente database table.
 * 
 */
@Entity
@Table(name = "occliente")
public class Occliente implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idoccliente")
    private Integer idoccliente;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @Size(max = 20)
    @Column(name = "foneresidencial")
    private String foneresidencial;
    @Size(max = 20)
    @Column(name = "fonecelular")
    private String fonecelular;
    @Column(name = "datanascimento")
    @Temporal(TemporalType.DATE)
    private Date datanascimento;
    @Size(max = 10)
    @Column(name = "sexo")
    private String sexo;
    @Size(max = 20)
    @Column(name = "idioma")
    private String idioma;
    @Size(max = 50)
    @Column(name = "nivelidioma")
    private String nivelidioma;
    @JoinColumn(name = "publicidade_idpublicidade", referencedColumnName = "idpublicidade")
    @ManyToOne(optional = false)
    private Publicidade publicidade;
    @JoinColumn(name = "unidadeNegocio_idunidadeNegocio", referencedColumnName = "idunidadeNegocio")
    @ManyToOne(optional = false)
    private Unidadenegocio unidadenegocio;

    public Occliente() {
    }

    public Occliente(Integer idoccliente) {
        this.idoccliente = idoccliente;
    }

    public Integer getIdoccliente() {
        return idoccliente;
    }

    public void setIdoccliente(Integer idoccliente) {
        this.idoccliente = idoccliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoneresidencial() {
        return foneresidencial;
    }

    public void setFoneresidencial(String foneresidencial) {
        this.foneresidencial = foneresidencial;
    }

    public String getFonecelular() {
        return fonecelular;
    }

    public void setFonecelular(String fonecelular) {
        this.fonecelular = fonecelular;
    }

    public Date getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getNivelidioma() {
        return nivelidioma;
    }

    public void setNivelidioma(String nivelidioma) {
        this.nivelidioma = nivelidioma;
    }

    public Publicidade getPublicidade() {
        return publicidade;
    }

    public void setPublicidade(Publicidade publicidade) {
        this.publicidade = publicidade;
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
        hash += (idoccliente != null ? idoccliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Occliente)) {
            return false;
        }
        Occliente other = (Occliente) object;
        if ((this.idoccliente == null && other.idoccliente != null) || (this.idoccliente != null && !this.idoccliente.equals(other.idoccliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.Interface.Occliente[ idoccliente=" + idoccliente + " ]";
    }
    
}
