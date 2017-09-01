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

@Entity
@Table(name = "pastavideo")
public class Pastavideo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idpastavideo")
	private Integer idpastavideo;
	@Size(max = 100)
	@Column(name = "nome")
	private String nome;
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pastavideo")
    private List<Video> videoList;
	
	
	public Pastavideo() {
	}


	public Integer getIdpastavideo() {
		return idpastavideo;
	}


	public void setIdpastavideo(Integer idpastavideo) {
		this.idpastavideo = idpastavideo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public List<Video> getVideoList() {
		return videoList;
	}


	public void setVideoList(List<Video> videoList) {
		this.videoList = videoList;
	}
	
	
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpastavideo != null ? idpastavideo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pastavideo)) {
            return false;
        }
        Pastavideo other = (Pastavideo) object;
        if ((this.idpastavideo == null && other.idpastavideo != null) || (this.idpastavideo != null && !this.idpastavideo.equals(other.idpastavideo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pastavideo[ idpastavideo=" + idpastavideo + " ]";
    }
}
