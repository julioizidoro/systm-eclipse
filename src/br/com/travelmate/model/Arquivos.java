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
@Table(name = "arquivos")
public class Arquivos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idarquivos")
    private Integer idarquivos;
    @Column(name = "dataInclusao")
    @Temporal(TemporalType.DATE)
    private Date dataInclusao;
    @Size(max = 200)
    @Column(name = "nomeArquivo")
    private String nomeArquivo;
    @Size(max = 50)
    @Column(name = "nomesalvos")
    private String nomesalvos;
    @Size(max = 100)
    @Column(name = "observacao")
    private String observacao;
    @JoinColumn(name = "tipoArquivo_idtipoArquivo", referencedColumnName = "idtipoArquivo")
    @ManyToOne(optional = false)
    private Tipoarquivo tipoarquivo;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;
    @Column(name = "se")
    private boolean se;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
	@ManyToOne(optional = false)
	private Cliente cliente;
    @Column(name = "situacao")
    private boolean sitaucao;
    @Column(name = "statusarquivo")
    private String statusarquivo;
    
    
    public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Transient
    private boolean selecionado;

    public Arquivos() {
    	setSitaucao(false);
    }
    
    

    public boolean isSitaucao() {
		return sitaucao;
	}

	public void setSitaucao(boolean sitaucao) {
		this.sitaucao = sitaucao;
	}

	public Arquivos(Integer idarquivos) {
        this.idarquivos = idarquivos;
    }

    public Integer getIdarquivos() {
        return idarquivos;
    }

    public void setIdarquivos(Integer idarquivos) {
        this.idarquivos = idarquivos;
    }

    public Date getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getNomesalvos() {
        return nomesalvos;
    }

    public void setNomesalvos(String nomesalvos) {
        this.nomesalvos = nomesalvos;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Tipoarquivo getTipoarquivo() {
        return tipoarquivo;
    }

    public void setTipoarquivo(Tipoarquivo tipoarquivo) {
        this.tipoarquivo = tipoarquivo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public boolean isSe() {
		return se;
	}

	public void setSe(boolean se) {
		this.se = se;
	}
	
	

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public String getStatusarquivo() {
		return statusarquivo;
	}

	public void setStatusarquivo(String statusarquivo) {
		this.statusarquivo = statusarquivo;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idarquivos != null ? idarquivos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Arquivos)) {
            return false;
        }
        Arquivos other = (Arquivos) object;
        if ((this.idarquivos == null && other.idarquivos != null) || (this.idarquivos != null && !this.idarquivos.equals(other.idarquivos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNomeArquivo();
    }
    
}
