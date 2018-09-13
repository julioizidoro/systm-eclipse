package br.com.travelmate.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "heexportar")
public class Heexportar implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Basic(optional = false)
	 @Column(name = "heexportar_id")
	 private Integer heexportar_id;
	 @Column(name = "Instituion")
	 private String Instituion;
	 @Column(name = "Programs")
	 private String Programs;
	 @Column(name = "Faculty")
	 private String Faculty;
	 @Column(name = "Credential")
	 private String Credential;
	 @Column(name = "City")
	 private String City;
	 @Column(name = "Province")
	 private String Province;
	 @Column(name = "Link")
	 private String Link;
	 @Column(name = "fornecedorcidade")
	 private int fornecedorcidade;
	 
	 
	 public Heexportar() {
	}


	public Integer getHeexportar_id() {
		return heexportar_id;
	}


	public void setHeexportar_id(Integer heexportar_id) {
		this.heexportar_id = heexportar_id;
	}


	public String getInstituion() {
		return Instituion;
	}


	public void setInstituion(String instituion) {
		Instituion = instituion;
	}


	public String getPrograms() {
		return Programs;
	}


	public void setPrograms(String programs) {
		Programs = programs;
	}


	public String getFaculty() {
		return Faculty;
	}


	public void setFaculty(String faculty) {
		Faculty = faculty;
	}


	public String getCredential() {
		return Credential;
	}


	public void setCredential(String credential) {
		Credential = credential;
	}


	public String getCity() {
		return City;
	}


	public void setCity(String city) {
		City = city;
	}


	public String getProvince() {
		return Province;
	}


	public void setProvince(String province) {
		Province = province;
	}


	public String getLink() {
		return Link;
	}


	public void setLink(String link) {
		Link = link;
	}


	public int getFornecedorcidade() {
		return fornecedorcidade;
	}


	public void setFornecedorcidade(int fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}
	 
	 
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (heexportar_id != null ? heexportar_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof He)) {
            return false;
        }
        Heexportar other = (Heexportar) object;
        if ((this.heexportar_id == null && other.heexportar_id != null) || (this.heexportar_id != null && !this.heexportar_id.equals(other.heexportar_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Heexportar[ heexportar_id=" + heexportar_id + " ]";
    }
	 
}
