package br.com.travelmate.model;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "video1")
public class Video1 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvideo1")
    private Integer idvideo1;
    @Size(max = 100)
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 200)
    @Column(name = "host")
    private String host;
    @Column(name = "ativo")
    private boolean ativo;
    @JoinColumn(name = "videopasta1_idvideopasta1", referencedColumnName = "idvideopasta1")
    @ManyToOne(optional = false)
    private Videopasta1 videopasta1;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Column(name = "copiado")
    private boolean copiado;
    @Column(name = "dataupload")
    @Temporal(TemporalType.DATE)
    private Date dataupload;
    @Column(name = "hora")
    private String hora;
    
    
    public Video1() {
	}


	public Integer getIdvideo1() {
		return idvideo1;
	}


	public void setIdvideo1(Integer idvideo1) {
		this.idvideo1 = idvideo1;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public boolean isAtivo() {
		return ativo;
	}


	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


	public Videopasta1 getVideopasta1() {
		return videopasta1;
	}


	public void setVideopasta1(Videopasta1 videopasta1) {
		this.videopasta1 = videopasta1;
	}
	
	
    
    
    
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public boolean isCopiado() {
		return copiado;
	}


	public void setCopiado(boolean copiado) {
		this.copiado = copiado;
	}


	public Date getDataupload() {
		return dataupload;
	}


	public void setDataupload(Date dataupload) {
		this.dataupload = dataupload;
	}


	public String getHora() {
		return hora;
	}


	public void setHora(String hora) {
		this.hora = hora;
	}


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idvideo1 != null ? idvideo1.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Video1)) {
            return false;
        }
        Video1 other = (Video1) object;
        if ((this.idvideo1 == null && other.idvideo1 != null) || (this.idvideo1 != null && !this.idvideo1.equals(other.idvideo1))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Video1[ idvideo1=" + idvideo1 + " ]";
    }
    
    

}
