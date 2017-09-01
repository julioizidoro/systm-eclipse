package br.com.travelmate.model;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "video2")
public class Video2 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvideo2")
    private Integer idvideo2;
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
    @JoinColumn(name = "videopasta2_idvideopasta2", referencedColumnName = "idvideopasta2")
    @ManyToOne(optional = false)
    private Videopasta2 videopasta2;
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
    
    
    public Video2() {
	}


	public Integer getIdvideo2() {
		return idvideo2;
	}


	public void setIdvideo2(Integer idvideo2) {
		this.idvideo2 = idvideo2;
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


	public Videopasta2 getVideopasta2() {
		return videopasta2;
	}


	public void setVideopasta2(Videopasta2 videopasta2) {
		this.videopasta2 = videopasta2;
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
        hash += (idvideo2 != null ? idvideo2.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Video2)) {
            return false;
        }
        Video2 other = (Video2) object;
        if ((this.idvideo2 == null && other.idvideo2 != null) || (this.idvideo2 != null && !this.idvideo2.equals(other.idvideo2))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Video2[ idvideo2=" + idvideo2 + " ]";
    }
    

}
