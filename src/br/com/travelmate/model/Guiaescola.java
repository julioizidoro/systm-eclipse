package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "guiaescola")
public class Guiaescola implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idguiaescola")
    private Integer idguiaescola;
    @Size(max = 100)
    @Column(name = "site")
    private String site;
    @Column(name = "idade")
    private Integer idade;
    @Size(max = 20)
    @Column(name = "inicio")
    private String inicio;
    @Lob
    @Size(max = 65535)
    @Column(name = "teste")
    private String teste;
    @Lob
    @Size(max = 65535)
    @Column(name = "duracao")
    private String duracao;
    @Lob
    @Size(max = 65535)
    @Column(name = "material")
    private String material;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "courier")
    private Float courier;
    @Column(name = "examefinal")
    private Float examefinal;
    @Lob
    @Size(max = 65535)
    @Column(name = "pagaexamefinal")
    private String pagaexamefinal;
    @Size(max = 20)
    @Column(name = "homestayentrada")
    private String homestayentrada;
    @Size(max = 20)
    @Column(name = "homestaysaida")
    private String homestaysaida;
    @Size(max = 3)
    @Column(name = "homestaynoiteextra")
    private String homestaynoiteextra;
    @Column(name = "homestaynumeronoiteextra")
    private Integer homestaynumeronoiteextra;
    @Size(max = 3)
    @Column(name = "homestaycompartilhado")
    private String homestaycompartilhado;
    @Size(max = 100)
    @Column(name = "homestayobservacao")
    private String homestayobservacao;
    @Size(max = 20)
    @Column(name = "apartmententrada")
    private String apartmententrada;
    @Size(max = 20)
    @Column(name = "apartmentsaida")
    private String apartmentsaida;
    @Size(max = 3)
    @Column(name = "apartmentnoiteextra")
    private String apartmentnoiteextra;
    @Column(name = "apartmentnumeronoiteextra")
    private Integer apartmentnumeronoiteextra;
    @Size(max = 3)
    @Column(name = "apartmentcompartilhado")
    private String apartmentcompartilhado;
    @Size(max = 100)
    @Column(name = "apartmentobservacao")
    private String apartmentobservacao;
    @Size(max = 20)
    @Column(name = "studioentrada")
    private String studioentrada;
    @Size(max = 20)
    @Column(name = "studiosaida")
    private String studiosaida;
    @Size(max = 3)
    @Column(name = "studionoiteextra")
    private String studionoiteextra;
    @Column(name = "studionumeronoiteextra")
    private Integer studionumeronoiteextra;
    @Size(max = 3)
    @Column(name = "studiocompartilhado")
    private String studiocompartilhado;
    @Size(max = 100)
    @Column(name = "studioobservacao")
    private String studioobservacao;
    @Size(max = 20)
    @Column(name = "hostelentrada")
    private String hostelentrada;
    @Size(max = 20)
    @Column(name = "hostelsaida")
    private String hostelsaida;
    @Size(max = 3)
    @Column(name = "hostelnoiteextra")
    private String hostelnoiteextra;
    @Column(name = "hostelnumeronoiteextra")
    private Integer hostelnumeronoiteextra;
    @Size(max = 3)
    @Column(name = "hostelcompartilhado")
    private String hostelcompartilhado;
    @Size(max = 100)
    @Column(name = "hostelobservacao")
    private String hostelobservacao;
    @Size(max = 20)
    @Column(name = "residenceentrada")
    private String residenceentrada;
    @Size(max = 20)
    @Column(name = "residencesaida")
    private String residencesaida;
    @Size(max = 3)
    @Column(name = "residencenoiteextra")
    private String residencenoiteextra;
    @Column(name = "residencenumeronoiteextra")
    private Integer residencenumeronoiteextra;
    @Size(max = 3)
    @Column(name = "residencecompartilhado")
    private String residencecompartilhado;
    @Size(max = 100)
    @Column(name = "residenceobservacao")
    private String residenceobservacao;
    @Size(max = 20)
    @Column(name = "studentresidenceentrada")
    private String studentresidenceentrada;
    @Size(max = 20)
    @Column(name = "studentresidencesaida")
    private String studentresidencesaida;
    @Size(max = 3)
    @Column(name = "studentresidencenoiteextra")
    private String studentresidencenoiteextra;
    @Column(name = "studentresidencenumeronoiteextra")
    private Integer studentresidencenumeronoiteextra;
    @Size(max = 3)
    @Column(name = "studentresidencecompartilhado")
    private String studentresidencecompartilhado;
    @Size(max = 100)
    @Column(name = "studentresidenceobservacao")
    private String studentresidenceobservacao;
    @Column(name = "datafechamento")
    @Temporal(TemporalType.DATE)
    private Date datafechamento;
    @Column(name = "dataabertura")
    @Temporal(TemporalType.DATE)
    private Date dataabertura;
    @Size(max = 30)
    @Column(name = "mesnovotarifario")
    private String mesnovotarifario;
    @Lob
    @Size(max = 65535)
    @Column(name = "feriados")
    private String feriados;
    @Lob
    @Size(max = 65535)
    @Column(name = "observacoesgerais")
    private String observacoesgerais;
    @Size(max = 45)
    @Column(name = "datainiciocurso")
    private String datainiciocurso;
     @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "guiaescola")
    private List<Fornecedorcidadeguia> fornecedorcidadeguiaList;

    public Guiaescola() {
    }

    public Guiaescola(Integer idguiaescola) {
        this.idguiaescola = idguiaescola;
    }

    public Integer getIdguiaescola() {
        return idguiaescola;
    }

    public void setIdguiaescola(Integer idguiaescola) {
        this.idguiaescola = idguiaescola;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getTeste() {
        return teste;
    }

    public void setTeste(String teste) {
        this.teste = teste;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Float getCourier() {
        return courier;
    }

    public void setCourier(Float courier) {
        this.courier = courier;
    }

    public Float getExamefinal() {
        return examefinal;
    }

    public void setExamefinal(Float examefinal) {
        this.examefinal = examefinal;
    }

    public String getPagaexamefinal() {
        return pagaexamefinal;
    }

    public void setPagaexamefinal(String pagaexamefinal) {
        this.pagaexamefinal = pagaexamefinal;
    }

    public String getHomestayentrada() {
        return homestayentrada;
    }

    public void setHomestayentrada(String homestayentrada) {
        this.homestayentrada = homestayentrada;
    }

    public String getHomestaysaida() {
        return homestaysaida;
    }

    public void setHomestaysaida(String homestaysaida) {
        this.homestaysaida = homestaysaida;
    }

    public String getHomestaynoiteextra() {
        return homestaynoiteextra;
    }

    public void setHomestaynoiteextra(String homestaynoiteextra) {
        this.homestaynoiteextra = homestaynoiteextra;
    }

    public Integer getHomestaynumeronoiteextra() {
        return homestaynumeronoiteextra;
    }

    public void setHomestaynumeronoiteextra(Integer homestaynumeronoiteextra) {
        this.homestaynumeronoiteextra = homestaynumeronoiteextra;
    }

    public String getHomestaycompartilhado() {
        return homestaycompartilhado;
    }

    public void setHomestaycompartilhado(String homestaycompartilhado) {
        this.homestaycompartilhado = homestaycompartilhado;
    }

    public String getHomestayobservacao() {
        return homestayobservacao;
    }

    public void setHomestayobservacao(String homestayobservacao) {
        this.homestayobservacao = homestayobservacao;
    }

    public String getApartmententrada() {
        return apartmententrada;
    }

    public void setApartmententrada(String apartmententrada) {
        this.apartmententrada = apartmententrada;
    }

    public String getApartmentsaida() {
        return apartmentsaida;
    }

    public void setApartmentsaida(String apartmentsaida) {
        this.apartmentsaida = apartmentsaida;
    }

    public String getApartmentnoiteextra() {
        return apartmentnoiteextra;
    }

    public void setApartmentnoiteextra(String apartmentnoiteextra) {
        this.apartmentnoiteextra = apartmentnoiteextra;
    }

    public Integer getApartmentnumeronoiteextra() {
        return apartmentnumeronoiteextra;
    }

    public void setApartmentnumeronoiteextra(Integer apartmentnumeronoiteextra) {
        this.apartmentnumeronoiteextra = apartmentnumeronoiteextra;
    }

    public String getApartmentcompartilhado() {
        return apartmentcompartilhado;
    }

    public void setApartmentcompartilhado(String apartmentcompartilhado) {
        this.apartmentcompartilhado = apartmentcompartilhado;
    }

    public String getApartmentobservacao() {
        return apartmentobservacao;
    }

    public void setApartmentobservacao(String apartmentobservacao) {
        this.apartmentobservacao = apartmentobservacao;
    }

    public String getStudioentrada() {
        return studioentrada;
    }

    public void setStudioentrada(String studioentrada) {
        this.studioentrada = studioentrada;
    }

    public String getStudiosaida() {
        return studiosaida;
    }

    public void setStudiosaida(String studiosaida) {
        this.studiosaida = studiosaida;
    }

    public String getStudionoiteextra() {
        return studionoiteextra;
    }

    public void setStudionoiteextra(String studionoiteextra) {
        this.studionoiteextra = studionoiteextra;
    }

    public Integer getStudionumeronoiteextra() {
        return studionumeronoiteextra;
    }

    public void setStudionumeronoiteextra(Integer studionumeronoiteextra) {
        this.studionumeronoiteextra = studionumeronoiteextra;
    }

    public String getStudiocompartilhado() {
        return studiocompartilhado;
    }

    public void setStudiocompartilhado(String studiocompartilhado) {
        this.studiocompartilhado = studiocompartilhado;
    }

    public String getStudioobservacao() {
        return studioobservacao;
    }

    public void setStudioobservacao(String studioobservacao) {
        this.studioobservacao = studioobservacao;
    }

    public String getHostelentrada() {
        return hostelentrada;
    }

    public void setHostelentrada(String hostelentrada) {
        this.hostelentrada = hostelentrada;
    }

    public String getHostelsaida() {
        return hostelsaida;
    }

    public void setHostelsaida(String hostelsaida) {
        this.hostelsaida = hostelsaida;
    }

    public String getHostelnoiteextra() {
        return hostelnoiteextra;
    }

    public void setHostelnoiteextra(String hostelnoiteextra) {
        this.hostelnoiteextra = hostelnoiteextra;
    }

    public Integer getHostelnumeronoiteextra() {
        return hostelnumeronoiteextra;
    }

    public void setHostelnumeronoiteextra(Integer hostelnumeronoiteextra) {
        this.hostelnumeronoiteextra = hostelnumeronoiteextra;
    }

    public String getHostelcompartilhado() {
        return hostelcompartilhado;
    }

    public void setHostelcompartilhado(String hostelcompartilhado) {
        this.hostelcompartilhado = hostelcompartilhado;
    }

    public String getHostelobservacao() {
        return hostelobservacao;
    }

    public void setHostelobservacao(String hostelobservacao) {
        this.hostelobservacao = hostelobservacao;
    }

    public String getResidenceentrada() {
        return residenceentrada;
    }

    public void setResidenceentrada(String residenceentrada) {
        this.residenceentrada = residenceentrada;
    }

    public String getResidencesaida() {
        return residencesaida;
    }

    public void setResidencesaida(String residencesaida) {
        this.residencesaida = residencesaida;
    }

    public String getResidencenoiteextra() {
        return residencenoiteextra;
    }

    public void setResidencenoiteextra(String residencenoiteextra) {
        this.residencenoiteextra = residencenoiteextra;
    }

    public Integer getResidencenumeronoiteextra() {
        return residencenumeronoiteextra;
    }

    public void setResidencenumeronoiteextra(Integer residencenumeronoiteextra) {
        this.residencenumeronoiteextra = residencenumeronoiteextra;
    }

    public String getResidencecompartilhado() {
        return residencecompartilhado;
    }

    public void setResidencecompartilhado(String residencecompartilhado) {
        this.residencecompartilhado = residencecompartilhado;
    }

    public String getResidenceobservacao() {
        return residenceobservacao;
    }

    public void setResidenceobservacao(String residenceobservacao) {
        this.residenceobservacao = residenceobservacao;
    }

    public String getStudentresidenceentrada() {
        return studentresidenceentrada;
    }

    public void setStudentresidenceentrada(String studentresidenceentrada) {
        this.studentresidenceentrada = studentresidenceentrada;
    }

    public String getStudentresidencesaida() {
        return studentresidencesaida;
    }

    public void setStudentresidencesaida(String studentresidencesaida) {
        this.studentresidencesaida = studentresidencesaida;
    }

    public String getStudentresidencenoiteextra() {
        return studentresidencenoiteextra;
    }

    public void setStudentresidencenoiteextra(String studentresidencenoiteextra) {
        this.studentresidencenoiteextra = studentresidencenoiteextra;
    }

    public Integer getStudentresidencenumeronoiteextra() {
        return studentresidencenumeronoiteextra;
    }

    public void setStudentresidencenumeronoiteextra(Integer studentresidencenumeronoiteextra) {
        this.studentresidencenumeronoiteextra = studentresidencenumeronoiteextra;
    }

    public String getStudentresidencecompartilhado() {
        return studentresidencecompartilhado;
    }

    public void setStudentresidencecompartilhado(String studentresidencecompartilhado) {
        this.studentresidencecompartilhado = studentresidencecompartilhado;
    }

    public String getStudentresidenceobservacao() {
        return studentresidenceobservacao;
    }

    public void setStudentresidenceobservacao(String studentresidenceobservacao) {
        this.studentresidenceobservacao = studentresidenceobservacao;
    }

    public Date getDatafechamento() {
        return datafechamento;
    }

    public void setDatafechamento(Date datafechamento) {
        this.datafechamento = datafechamento;
    }

    public Date getDataabertura() {
        return dataabertura;
    }

    public void setDataabertura(Date dataabertura) {
        this.dataabertura = dataabertura;
    }

    public String getMesnovotarifario() {
        return mesnovotarifario;
    }

    public void setMesnovotarifario(String mesnovotarifario) {
        this.mesnovotarifario = mesnovotarifario;
    }

    public String getFeriados() {
        return feriados;
    }

    public void setFeriados(String feriados) {
        this.feriados = feriados;
    }

    public String getObservacoesgerais() {
        return observacoesgerais;
    }

    public void setObservacoesgerais(String observacoesgerais) {
        this.observacoesgerais = observacoesgerais;
    }

    public List<Fornecedorcidadeguia> getFornecedorcidadeguiaList() {
        return fornecedorcidadeguiaList;
    }

    public void setFornecedorcidadeguiaList(List<Fornecedorcidadeguia> fornecedorcidadeguiaList) {
        this.fornecedorcidadeguiaList = fornecedorcidadeguiaList;
    }

    public String getDatainiciocurso() {
        return datainiciocurso;
    }

    public void setDatainiciocurso(String datainiciocurso) {
        this.datainiciocurso = datainiciocurso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idguiaescola != null ? idguiaescola.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Guiaescola)) {
            return false;
        }
        Guiaescola other = (Guiaescola) object;
        if ((this.idguiaescola == null && other.idguiaescola != null) || (this.idguiaescola != null && !this.idguiaescola.equals(other.idguiaescola))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Guiaescola[ idguiaescola=" + idguiaescola + " ]";
    }
    
}

