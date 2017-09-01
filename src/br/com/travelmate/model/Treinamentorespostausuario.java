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

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "treinamentorespostausuario")
public class Treinamentorespostausuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtreinamentorespostausuario")
    private Integer idtreinamentorespostausuario;
    @JoinColumn(name = "treinamentorespostas_idtreinamentorespostas", referencedColumnName = "idtreinamentorespostas")
    @ManyToOne(optional = false)
    private Treinamentorespostas treinamentorespostas;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @Column(name = "respusuario1")
    private String respusuario1;
    @Column(name = "respusuario2")
    private String respusuario2;
    @Column(name = "respusuario3")
    private String respusuario3;
    @Column(name = "respusuario4")
    private String respusuario4;
    @Column(name = "respusuario5")
    private String respusuario5;
    @Column(name = "respusuario6")
    private String respusuario6;


    public Treinamentorespostausuario() {
    }

    public Treinamentorespostausuario(Integer idtreinamentorespostausuario) {
        this.idtreinamentorespostausuario = idtreinamentorespostausuario;
    }

    public Integer getIdtreinamentorespostausuario() {
        return idtreinamentorespostausuario;
    }

    public void setIdtreinamentorespostausuario(Integer idtreinamentorespostausuario) {
        this.idtreinamentorespostausuario = idtreinamentorespostausuario;
    }

    public Treinamentorespostas getTreinamentorespostas() {
        return treinamentorespostas;
    }

    public void setTreinamentorespostas(Treinamentorespostas treinamentorespostas) {
        this.treinamentorespostas = treinamentorespostas;
    }

    public String getRespusuario1() {
		return respusuario1;
	}

	public void setRespusuario1(String respusuario1) {
		this.respusuario1 = respusuario1;
	}

	public String getRespusuario2() {
		return respusuario2;
	}

	public void setRespusuario2(String respusuario2) {
		this.respusuario2 = respusuario2;
	} 
	 
	public String getRespusuario3() {
		return respusuario3;
	}

	public void setRespusuario3(String respusuario3) {
		this.respusuario3 = respusuario3;
	}

	public String getRespusuario4() {
		return respusuario4;
	}

	public void setRespusuario4(String respusuario4) {
		this.respusuario4 = respusuario4;
	}

	public String getRespusuario5() {
		return respusuario5;
	}

	public void setRespusuario5(String respusuario5) {
		this.respusuario5 = respusuario5;
	}

	public String getRespusuario6() {
		return respusuario6;
	}

	public void setRespusuario6(String respusuario6) {
		this.respusuario6 = respusuario6;
	}

	public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtreinamentorespostausuario != null ? idtreinamentorespostausuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Treinamentorespostausuario)) {
            return false;
        }
        Treinamentorespostausuario other = (Treinamentorespostausuario) object;
        if ((this.idtreinamentorespostausuario == null && other.idtreinamentorespostausuario != null) || (this.idtreinamentorespostausuario != null && !this.idtreinamentorespostausuario.equals(other.idtreinamentorespostausuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Treinamentorespostausuario[ idtreinamentorespostausuario=" + idtreinamentorespostausuario + " ]";
    }
    
}

