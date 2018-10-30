/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "usuario")
public class Usuario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idusuario")
    private Integer idusuario;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Size(max = 30)
    @Column(name = "login")
    private String login;
    @Size(max = 30)
    @Column(name = "senha")
    private String senha;
    @Size(max = 9)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 7)
    @Column(name = "situacao")
    private String situacao;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "usuario")
    private List<Vendascomissao> vendascomissaoList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "usuario")
    private List<Vendas> vendasList;
    @JoinColumn(name = "grupoAcesso_idgrupoAcesso", referencedColumnName = "idgrupoAcesso")
    @ManyToOne(optional = false)
    private Grupoacesso grupoacesso;
    @JoinColumn(name = "unidadeNegocio_idunidadeNegocio", referencedColumnName = "idunidadeNegocio")
    @ManyToOne(optional = false)
    private Unidadenegocio unidadenegocio;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "usuario")
    private List<Orcamentocurso> orcamentocursoList;
    @JoinColumn(name = "departamento_iddepartamento", referencedColumnName = "iddepartamento")
    @ManyToOne(optional = false)
    private Departamento departamento;
    @Column(name = "vende")
    private boolean vende;
    @Column(name = "foto")
    private boolean foto;
    @Size(max = 1)
    @Column(name = "modoexibicao")
    private String modoexibicao; 
    @Column(name = "depoimentos")
    private boolean depoimentos; 
    @Column(name = "dataaniversario")
    @Temporal(TemporalType.DATE)
    private Date dataaniversario;
    @Column(name = "datacomemorativa")
    @Temporal(TemporalType.DATE)
    private Date datacomemorativa;
    @Column(name = "dataversao")
    @Temporal(TemporalType.DATE)
    private Date dataversao;
    @Column(name = "pertencematriz")
    private boolean pertencematriz; 
    @Column(name = "recebeleadautomatica")
    private boolean recebeleadautomatica; 
    @Column(name = "emailagenda")
    private String emailagenda;
    @Transient
    private boolean selecionado;
    @Transient
    private String dashboard; 
    @Transient
    private boolean fecharaniversario; 
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "usuarioUpload")
    private List<Notificacaouploadusuario> notificacaoUploadNotificarList;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "usuario")
    private Acessounidade acessounidade;
    @JoinColumn(name = "cargo_idcargo", referencedColumnName = "idcargo")
    @ManyToOne(optional = false)
    private Cargo cargo;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "usuario")
    private Leadresponsavel responsavel;
    @Column(name = "tmturcadastro")
    private boolean tmturcadastro;
    @Column(name = "tmturativo")
    private boolean tmturativo;
    @Transient
    private String iconeTmtur;
    @Transient
    private String tituloTmtur;

    public Usuario() {
    	setDashboard("I");
    	setModoexibicao("I");
    }

    public Usuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    } 

    
    public List<Vendascomissao> getVendascomissaoList() {
        return vendascomissaoList;
    }

    public void setVendascomissaoList(List<Vendascomissao> vendascomissaoList) {
        this.vendascomissaoList = vendascomissaoList;
    }

    public List<Vendas> getVendasList() {
        return vendasList;
    }

    public void setVendasList(List<Vendas> vendasList) {
        this.vendasList = vendasList;
    }

    public Grupoacesso getGrupoacesso() {
        return grupoacesso;
    }

    public void setGrupoacesso(Grupoacesso grupoacesso) {
        this.grupoacesso = grupoacesso;
    }

    public Unidadenegocio getUnidadenegocio() {
        return unidadenegocio;
    }

    public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
        this.unidadenegocio = unidadenegocio;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }
    

    public boolean isFoto() {
		return foto;
	}

	public void setFoto(boolean foto) {
		this.foto = foto;
	}

	public String getModoexibicao() {
		return modoexibicao;
	}

	public void setModoexibicao(String modoexibicao) {
		this.modoexibicao = modoexibicao;
	}

	public String getDashboard() {
		return dashboard;
	}

	public void setDashboard(String dashboard) {
		this.dashboard = dashboard;
	}

	public Date getDataaniversario() {
		return dataaniversario;
	}

	public void setDataaniversario(Date dataaniversario) {
		this.dataaniversario = dataaniversario;
	}

	public Date getDatacomemorativa() {
		return datacomemorativa;
	}

	public void setDatacomemorativa(Date datacomemorativa) {
		this.datacomemorativa = datacomemorativa;
	}

	public boolean isPertencematriz() {
		return pertencematriz;
	}

	public void setPertencematriz(boolean pertencematriz) {
		this.pertencematriz = pertencematriz;
	}

	public boolean isFecharaniversario() {
		return fecharaniversario;
	}

	public void setFecharaniversario(boolean fecharaniversario) {
		this.fecharaniversario = fecharaniversario;
	}

	public boolean isRecebeleadautomatica() {
		return recebeleadautomatica;
	}

	public void setRecebeleadautomatica(boolean recebeleadautomatica) {
		this.recebeleadautomatica = recebeleadautomatica;
	} 

	public List<Notificacaouploadusuario> getNotificacaoUploadNotificarList() {
		return notificacaoUploadNotificarList;
	}

	public void setNotificacaoUploadNotificarList(List<Notificacaouploadusuario> notificacaoUploadNotificarList) {
		this.notificacaoUploadNotificarList = notificacaoUploadNotificarList;
	}

	public Acessounidade getAcessounidade() {
		return acessounidade;
	}

	public void setAcessounidade(Acessounidade acessounidade) {
		this.acessounidade = acessounidade;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public String getEmailagenda() {
		return emailagenda;
	}

	public void setEmailagenda(String emailagenda) {
		this.emailagenda = emailagenda;
	}

	public Leadresponsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Leadresponsavel responsavel) {
		this.responsavel = responsavel;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idusuario != null ? idusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idusuario == null && other.idusuario != null) || (this.idusuario != null && !this.idusuario.equals(other.idusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
    	return "br.com.travelmate.model.Usuario[ idusuario=" + idusuario + " ]";
    }

    
    public List<Orcamentocurso> getOrcamentocursoList() {
        return orcamentocursoList;
    }

    public void setOrcamentocursoList(List<Orcamentocurso> orcamentocursoList) {
        this.orcamentocursoList = orcamentocursoList;
    }

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public boolean isVende() {
		return vende;
	}

	public void setVende(boolean vende) {
		this.vende = vende;
	}

	public boolean isDepoimentos() {
		return depoimentos;
	}

	public void setDepoimentos(boolean depoimentos) {
		this.depoimentos = depoimentos;
	}

	public Date getDataversao() {
		return dataversao;
	}

	public void setDataversao(Date dataversao) {
		this.dataversao = dataversao;
	}

	public boolean isTmturcadastro() {
		return tmturcadastro;
	}

	public void setTmturcadastro(boolean tmturcadastro) {
		this.tmturcadastro = tmturcadastro;
	}

	public boolean isTmturativo() {
		return tmturativo;
	}

	public void setTmturativo(boolean tmturativo) {
		this.tmturativo = tmturativo;
	}

	public String getIconeTmtur() {
		return iconeTmtur;
	}

	public void setIconeTmtur(String iconeTmtur) {
		this.iconeTmtur = iconeTmtur;
	}

	public String getTituloTmtur() {
		return tituloTmtur;
	}

	public void setTituloTmtur(String tituloTmtur) {
		this.tituloTmtur = tituloTmtur;
	}
 
    
}
