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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "tipoarquivo")
public class Tipoarquivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipoArquivo")
    private Integer idtipoArquivo;
    @Size(max = 50)
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 3)
    @Column(name = "unidade")
    private String unidade;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "tipoarquivo")
    private List<Arquivos> arquivosList;
    @Column(name = "enviarcliente")
    private String enviarcliente;
    @Column(name = "pertencefinanceiro")
    private boolean pertencefinanceiro;

    public Tipoarquivo() {
    }

    public Tipoarquivo(Integer idtipoArquivo) {
        this.idtipoArquivo = idtipoArquivo;
    }

    public Integer getIdtipoArquivo() {
        return idtipoArquivo;
    }

    public void setIdtipoArquivo(Integer idtipoArquivo) {
        this.idtipoArquivo = idtipoArquivo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public List<Arquivos> getArquivosList() {
        return arquivosList;
    }

    public void setArquivosList(List<Arquivos> arquivosList) {
        this.arquivosList = arquivosList;
    }
    
    

    public String getEnviarcliente() {
		return enviarcliente;
	}

	public void setEnviarcliente(String enviarcliente) {
		this.enviarcliente = enviarcliente;
	}

	public boolean isPertencefinanceiro() {
		return pertencefinanceiro;
	}

	public void setPertencefinanceiro(boolean pertencefinanceiro) {
		this.pertencefinanceiro = pertencefinanceiro;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoArquivo != null ? idtipoArquivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tipoarquivo)) {
            return false;
        }
        Tipoarquivo other = (Tipoarquivo) object;
        if ((this.idtipoArquivo == null && other.idtipoArquivo != null) || (this.idtipoArquivo != null && !this.idtipoArquivo.equals(other.idtipoArquivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescricao();
    }
    
}

