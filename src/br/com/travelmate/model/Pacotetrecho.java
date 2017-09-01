/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "pacotetrecho")
@NamedQueries({
    @NamedQuery(name = "Pacotetrecho.findREFRESH", query = "SELECT p FROM Pacotetrecho p")})
public class Pacotetrecho implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpacotetrecho")
    private Integer idpacotetrecho;
    @Lob
    @Size(max = 16777215)
    @Column(name = "descritivo")
    private String descritivo;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pacotetrecho")
    private List<Pacotepasseio> pacotepasseioList;
    @JoinColumn(name = "pacotes_idpacotes", referencedColumnName = "idpacotes")
    @ManyToOne(optional = false)
    private Pacotes pacotes;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pacotetrecho")
    private List<Pacotecarro> pacotecarroList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pacotetrecho")
    private List<Pacotetransfer> pacotetransferList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pacotetrecho")
    private List<Pacotetrem> pacotetremList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pacotetrecho")
    private List<Pacotehotel> pacotehotelList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pacotetrecho")
    private List<Pacotecruzeiro> pacotecruzeiroList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pacotetrecho")
    private List<Pacoteingresso> pacoteingressoList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pacotetrecho")
    private List<Pacotepassagem> pacotepassagemList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pacotetrecho")
    private List<Pacotecircuito> pacotecircuitoList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "pacotetrecho")
    private List<Pacoteservico> pacoteservicoList;
    @Transient
    private String imagemCarro;
    @Transient
    private String imagemtransfer;
    @Transient
    private String imagemtrem;
    @Transient
    private String imagemhotel;
    @Transient
    private String imagemcruzeiro;
    @Transient
    private String imagemingresso;
    @Transient
    private String imagempassagem;
    @Transient
    private String imagemcircuito;
    @Transient
    private String imagemservico;

    public Pacotetrecho() {
    }

    public Pacotetrecho(Integer idpacotetrecho) {
        this.idpacotetrecho = idpacotetrecho;
    }

    public Integer getIdpacotetrecho() {
        return idpacotetrecho;
    }

    public void setIdpacotetrecho(Integer idpacotetrecho) {
        this.idpacotetrecho = idpacotetrecho;
    }

    public String getDescritivo() {
        return descritivo;
    }

    public void setDescritivo(String descritivo) {
        this.descritivo = descritivo;
    }

    public List<Pacotepasseio> getPacotepasseioList() {
        return pacotepasseioList;
    }

    public void setPacotepasseioList(List<Pacotepasseio> pacotepasseioList) {
        this.pacotepasseioList = pacotepasseioList;
    }

    public Pacotes getPacotes() {
        return pacotes;
    }

    public void setPacotes(Pacotes pacotes) {
        this.pacotes = pacotes;
    }

    public List<Pacotecarro> getPacotecarroList() {
        return pacotecarroList;
    }

    public void setPacotecarroList(List<Pacotecarro> pacotecarroList) {
        this.pacotecarroList = pacotecarroList;
    }

    public List<Pacotetransfer> getPacotetransferList() {
        return pacotetransferList;
    }

    public void setPacotetransferList(List<Pacotetransfer> pacotetransferList) {
        this.pacotetransferList = pacotetransferList;
    }

    public List<Pacotetrem> getPacotetremList() {
        return pacotetremList;
    }

    public void setPacotetremList(List<Pacotetrem> pacotetremList) {
        this.pacotetremList = pacotetremList;
    }

    public List<Pacotehotel> getPacotehotelList() {
        return pacotehotelList;
    }

    public void setPacotehotelList(List<Pacotehotel> pacotehotelList) {
        this.pacotehotelList = pacotehotelList;
    }

    public List<Pacotecruzeiro> getPacotecruzeiroList() {
        return pacotecruzeiroList;
    }

    public void setPacotecruzeiroList(List<Pacotecruzeiro> pacotecruzeiroList) {
        this.pacotecruzeiroList = pacotecruzeiroList;
    }

    public List<Pacoteingresso> getPacoteingressoList() {
        return pacoteingressoList;
    }

    public void setPacoteingressoList(List<Pacoteingresso> pacoteingressoList) {
        this.pacoteingressoList = pacoteingressoList;
    }

    public List<Pacotepassagem> getPacotepassagemList() {
        return pacotepassagemList;
    }

    public void setPacotepassagemList(List<Pacotepassagem> pacotepassagemList) {
        this.pacotepassagemList = pacotepassagemList;
    }


	public String getImagemCarro() {
		return imagemCarro;
	}

	public void setImagemCarro(String imagemCarro) {
		this.imagemCarro = imagemCarro;
	}

	public String getImagemtransfer() {
		return imagemtransfer;
	}

	public void setImagemtransfer(String imagemtransfer) {
		this.imagemtransfer = imagemtransfer;
	}

	public String getImagemtrem() {
		return imagemtrem;
	}

	public void setImagemtrem(String imagemtrem) {
		this.imagemtrem = imagemtrem;
	}

	public String getImagemhotel() {
		return imagemhotel;
	}

	public void setImagemhotel(String imagemhotel) {
		this.imagemhotel = imagemhotel;
	}

	public String getImagemcruzeiro() {
		return imagemcruzeiro;
	}

	public void setImagemcruzeiro(String imagemcruzeiro) {
		this.imagemcruzeiro = imagemcruzeiro;
	}

	public String getImagemingresso() {
		return imagemingresso;
	}

	public void setImagemingresso(String imagemingresso) {
		this.imagemingresso = imagemingresso;
	}

	public String getImagempassagem() {
		return imagempassagem;
	}

	public void setImagempassagem(String imagempassagem) {
		this.imagempassagem = imagempassagem;
	}

	public List<Pacotecircuito> getPacotecircuitoList() {
		return pacotecircuitoList;
	}

	public void setPacotecircuitoList(List<Pacotecircuito> pacotecircuitoList) {
		this.pacotecircuitoList = pacotecircuitoList;
	}

	public String getImagemcircuito() {
		return imagemcircuito;
	}

	public void setImagemcircuito(String imagemcircuito) {
		this.imagemcircuito = imagemcircuito;
	}

	public String getImagemservico() {
		return imagemservico;
	}

	public void setImagemservico(String imagemservico) {
		this.imagemservico = imagemservico;
	}

	public List<Pacoteservico> getPacoteservicoList() {
		return pacoteservicoList;
	}

	public void setPacoteservicoList(List<Pacoteservico> pacoteservicoList) {
		this.pacoteservicoList = pacoteservicoList;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpacotetrecho != null ? idpacotetrecho.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pacotetrecho)) {
            return false;
        }
        Pacotetrecho other = (Pacotetrecho) object;
        if ((this.idpacotetrecho == null && other.idpacotetrecho != null) || (this.idpacotetrecho != null && !this.idpacotetrecho.equals(other.idpacotetrecho))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pacotetrecho[ idpacotetrecho=" + idpacotetrecho + " ]";
    }
    
}
