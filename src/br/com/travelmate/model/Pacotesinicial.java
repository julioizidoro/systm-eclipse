package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient; 

/**
 *
 * @author Kamila
 */ 
@Entity
@Table(name = "pacotesinicial")
public class Pacotesinicial implements Serializable {

    private static final long serialVersionUID = 1L; 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcursospacote")
    private Integer idcursospacote;
    @Column(name = "pais")
    private String pais;
    @Column(name = "idpais")
    private Integer idpais;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "curso")
    private String curso;
    @Column(name = "valoravista")
    private String valoravista;   
    @Column(name = "numerosemanacurso")
    private Integer numerosemanacurso; 
    @Column(name = "turno")
    private String turno;
    @Column(name = "datainiciocurso")
    @Temporal(TemporalType.DATE)
    private Date datainiciocurso;
    @Column(name = "dataterminocurso")
    @Temporal(TemporalType.DATE)
    private Date dataterminocurso; 
    @Column(name = "dataterminovenda")
    @Temporal(TemporalType.DATE)
    private Date dataterminovenda; 
    @Column(name = "escola")
    private String escola;
    @Column(name = "logo")
    private String logo;
    @Column(name = "numerosemanaacomodacao")
    private Float numerosemanaacomodacao; 
    @Column(name = "provincia")
    private String provincia;
    @Column(name = "duracao")
    private String duracao;
    @Column(name = "passagemaerea")
    private String passagemaerea;
    @Column(name = "modalidadework")
    private String modalidadework;
    @Column(name = "idproduto")
    private int idproduto;
    @Column(name = "descritivoacomodacao")
   	private String descritivoacomodacao; 
    @Column(name = "outrastaxas")
   	private String outrastaxas; 
    @Column(name = "idade")
   	private String idade;  
    @Column(name = "mostrarescola")
   	private boolean mostrarescola; 
    @Transient
    private boolean cursos;
    @Transient
    private boolean aupair;
    @Transient
    private boolean worktravel;
    @Transient
    private boolean highschool;
    @Transient
    private boolean turismo;
    @Transient
    private boolean voluntariado;
    @Column(name = "projetovoluntariado")
    private String projetovoluntariado;

    public Pacotesinicial() { 
    }

	public Integer getIdcursospacote() {
		return idcursospacote;
	}

	public void setIdcursospacote(Integer idcursospacote) {
		this.idcursospacote = idcursospacote;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getValoravista() {
		return valoravista;
	}

	public void setValoravista(String valoravista) {
		this.valoravista = valoravista;
	}

	public Integer getNumerosemanacurso() {
		return numerosemanacurso;
	}

	public void setNumerosemanacurso(Integer numerosemanacurso) {
		this.numerosemanacurso = numerosemanacurso;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public Date getDatainiciocurso() {
		return datainiciocurso;
	}

	public void setDatainiciocurso(Date datainiciocurso) {
		this.datainiciocurso = datainiciocurso;
	}

	public Date getDataterminocurso() {
		return dataterminocurso;
	}

	public void setDataterminocurso(Date dataterminocurso) {
		this.dataterminocurso = dataterminocurso;
	}

	public String getEscola() {
		return escola;
	}

	public void setEscola(String escola) {
		this.escola = escola;
	}

	public Float getNumerosemanaacomodacao() {
		return numerosemanaacomodacao;
	}

	public void setNumerosemanaacomodacao(Float numerosemanaacomodacao) {
		this.numerosemanaacomodacao = numerosemanaacomodacao;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getIdpais() {
		return idpais;
	}

	public void setIdpais(Integer idpais) {
		this.idpais = idpais;
	}

	public Date getDataterminovenda() {
		return dataterminovenda;
	}

	public void setDataterminovenda(Date dataterminovenda) {
		this.dataterminovenda = dataterminovenda;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public String getPassagemaerea() {
		return passagemaerea;
	}

	public void setPassagemaerea(String passagemaerea) {
		this.passagemaerea = passagemaerea;
	}

	public String getModalidadework() {
		return modalidadework;
	}

	public void setModalidadework(String modalidadework) {
		this.modalidadework = modalidadework;
	}

	public int getIdproduto() {
		return idproduto;
	}

	public void setIdproduto(int idproduto) {
		this.idproduto = idproduto;
	}

	public String getDescritivoacomodacao() {
		return descritivoacomodacao;
	}

	public void setDescritivoacomodacao(String descritivoacomodacao) {
		this.descritivoacomodacao = descritivoacomodacao;
	}

	public boolean isCursos() {
		return cursos;
	}

	public void setCursos(boolean cursos) {
		this.cursos = cursos;
	}

	public boolean isAupair() {
		return aupair;
	}

	public void setAupair(boolean aupair) {
		this.aupair = aupair;
	}

	public boolean isWorktravel() {
		return worktravel;
	}

	public void setWorktravel(boolean worktravel) {
		this.worktravel = worktravel;
	}

	public boolean isHighschool() {
		return highschool;
	}

	public void setHighschool(boolean highschool) {
		this.highschool = highschool;
	}

	public String getOutrastaxas() {
		return outrastaxas;
	}

	public void setOutrastaxas(String outrastaxas) {
		this.outrastaxas = outrastaxas;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public boolean isMostrarescola() {
		return mostrarescola;
	}

	public void setMostrarescola(boolean mostrarescola) {
		this.mostrarescola = mostrarescola;
	}

	public boolean isTurismo() {
		return turismo;
	}

	public void setTurismo(boolean turismo) {
		this.turismo = turismo;
	}

	public boolean isVoluntariado() {
		return voluntariado;
	}

	public void setVoluntariado(boolean voluntariado) {
		this.voluntariado = voluntariado;
	}

	public String getProjetovoluntariado() {
		return projetovoluntariado;
	}

	public void setProjetovoluntariado(String projetovoluntariado) {
		this.projetovoluntariado = projetovoluntariado;
	}
 
      
}
