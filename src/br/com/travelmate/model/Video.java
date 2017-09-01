package br.com.travelmate.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "video")
public class Video implements Serializable{
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvideo")
    private Integer idvideo;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Size(max = 200)
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 200)
    @Column(name = "url")
    private String url;
    @JoinColumn(name = "pastavideo_idpastavideo", referencedColumnName = "idpastavideo")
    @ManyToOne(optional = false)
    private Pastavideo pastavideo;
    @Transient
    private String nomePlayer;
    
    
    public Video() {
	}


	public Integer getIdvideo() {
		return idvideo;
	}


	public void setIdvideo(Integer idvideo) {
		this.idvideo = idvideo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public Pastavideo getPastavideo() {
		return pastavideo;
	}


	public void setPastavideo(Pastavideo pastavideo) {
		this.pastavideo = pastavideo;
	}
	
	

    
    
	public String getNomePlayer() {
		return nomePlayer;
	}


	public void setNomePlayer(String nomePlayer) {
		this.nomePlayer = nomePlayer;
	}


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idvideo != null ? idvideo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Video)) {
            return false;
        }
        Video other = (Video) object;
        if ((this.idvideo == null && other.idvideo != null) || (this.idvideo != null && !this.idvideo.equals(other.idvideo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Video[ idvideo=" + idvideo + " ]";
    }
}
