package br.com.travelmate.model;

import java.io.Serializable;


import javax.persistence.*;
import javax.validation.constraints.Size;



import java.util.Date;
import java.util.List;


/**
 * The persistent class for the avisos database table.
 * 
 * 
 */
@Entity
@Table(name="avisos")
public class Avisos implements Serializable {
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idavisos")
    private Integer idavisos;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "texto")
    private String texto;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 50)
    @Column(name = "imagem")
    private String imagem;
    @Column(name = "liberar")
    private boolean liberar;
    @Column(name = "idunidade")
    private int idunidade;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;
    @Column(name = "departamento")
    private String departamento;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "avisos")
    private List<Avisousuario> avisousuarioList;
    @Column(name = "idvenda")
    private int idvenda;

    //teste
    public Avisos() {
    	idunidade=0;
    	idvenda=0;
    	departamento="outros";
    }

    public Avisos(Integer idavisos) {
        this.idavisos = idavisos;
    }

    public Integer getIdavisos() {
        return idavisos;
    }

    public void setIdavisos(Integer idavisos) {
        this.idavisos = idavisos;
    }

    

    
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isLiberar() {
		return liberar;
	}

	public void setLiberar(boolean liberar) {
		this.liberar = liberar;
	}

	public int getIdunidade() {
		return idunidade;
	}

	public void setIdunidade(int idunidade) {
		this.idunidade = idunidade;
	}
	
	

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	
	public List<Avisousuario> getAvisousuarioList() {
		return avisousuarioList;
	}

	public void setAvisousuarioList(List<Avisousuario> avisousuarioList) {
		this.avisousuarioList = avisousuarioList;
	}

	

	public int getIdvenda() {
		return idvenda;
	}

	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idavisos != null ? idavisos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Avisos)) {
            return false;
        }
        Avisos other = (Avisos) object;
        if ((this.idavisos == null && other.idavisos != null) || (this.idavisos != null && !this.idavisos.equals(other.idavisos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.Interface.Avisos[ idavisos=" + idavisos + " ]";
    }
    
}
