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
@Table(name = "video4")
public class Video4 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvideo4")
    private Integer idvideo4;
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
    @JoinColumn(name = "videopasta3_idvideopasta3", referencedColumnName = "idvideopasta3")
    @ManyToOne(optional = false)
    private Videopasta3 videopasta3;
    @JoinColumn(name = "videopasta4_idvideopasta4", referencedColumnName = "idvideopasta4")
    @ManyToOne(optional = false)
    private Videopasta4 videopasta4;
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
    
    
    public Video4() {
	}


	public Integer getIdvideo4() {
		return idvideo4;
	}


	public void setIdvideo4(Integer idvideo4) {
		this.idvideo4 = idvideo4;
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


	public Videopasta3 getVideopasta3() {
		return videopasta3;
	}


	public void setVideopasta3(Videopasta3 videopasta3) {
		this.videopasta3 = videopasta3;
	}


	public Videopasta4 getVideopasta4() {
		return videopasta4;
	}


	public void setVideopasta4(Videopasta4 videopasta4) {
		this.videopasta4 = videopasta4;
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


	public int hashCode() {
		int hash = 0;
		hash += (idvideo4 != null ? idvideo4.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof Video4)) {
			return false;
		}
		Video4 other = (Video4) object;
		if ((this.idvideo4 == null && other.idvideo4 != null)
				|| (this.idvideo4 != null && !this.idvideo4.equals(other.idvideo4))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Video4[ idvideo4=" + idvideo4 + " ]";
	}

}
