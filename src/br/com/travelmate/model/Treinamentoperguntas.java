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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "treinamentoperguntas")
public class Treinamentoperguntas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtreinamentoperguntas")
    private Integer idtreinamentoperguntas;
    @Column(name = "pergunta1")
    private String pergunta1;
    @Column(name = "pergunta2")
    private String pergunta2;
    @Column(name = "pergunta3")
    private String pergunta3;
    @Column(name = "pergunta4")
    private String pergunta4;
    @Column(name = "pergunta5")
    private String pergunta5;
    @Column(name = "pergunta6")
    private String pergunta6;
    @JoinColumn(name = "mtp_idmtp", referencedColumnName = "idmtp")
    @ManyToOne(optional = false)
    private Mtp mtp;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "treinamentoperguntas")
    private List<Treinamentorespostas> treinamentorespostasList;

    public Treinamentoperguntas() {
    }

    public Treinamentoperguntas(Integer idtreinamentoperguntas) {
        this.idtreinamentoperguntas = idtreinamentoperguntas;
    }

    public Integer getIdtreinamentoperguntas() {
        return idtreinamentoperguntas;
    }

    public void setIdtreinamentoperguntas(Integer idtreinamentoperguntas) {
        this.idtreinamentoperguntas = idtreinamentoperguntas;
    }

    public String getPergunta1() {
        return pergunta1;
    }

    public void setPergunta1(String pergunta1) {
        this.pergunta1 = pergunta1;
    }

    public String getPergunta2() {
        return pergunta2;
    }

    public void setPergunta2(String pergunta2) {
        this.pergunta2 = pergunta2;
    }

    public String getPergunta3() {
        return pergunta3;
    }

    public void setPergunta3(String pergunta3) {
        this.pergunta3 = pergunta3;
    }

    public String getPergunta4() {
        return pergunta4;
    }

    public void setPergunta4(String pergunta4) {
        this.pergunta4 = pergunta4;
    }

    public String getPergunta5() {
        return pergunta5;
    }

    public void setPergunta5(String pergunta5) {
        this.pergunta5 = pergunta5;
    }

    public String getPergunta6() {
        return pergunta6;
    }

    public void setPergunta6(String pergunta6) {
        this.pergunta6 = pergunta6;
    }

    public Mtp getMtp() {
        return mtp;
    }

    public void setMtp(Mtp mtp) {
        this.mtp = mtp;
    }

    public List<Treinamentorespostas> getTreinamentorespostasList() {
        return treinamentorespostasList;
    }

    public void setTreinamentorespostasList(List<Treinamentorespostas> treinamentorespostasList) {
        this.treinamentorespostasList = treinamentorespostasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtreinamentoperguntas != null ? idtreinamentoperguntas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Treinamentoperguntas)) {
            return false;
        }
        Treinamentoperguntas other = (Treinamentoperguntas) object;
        if ((this.idtreinamentoperguntas == null && other.idtreinamentoperguntas != null) || (this.idtreinamentoperguntas != null && !this.idtreinamentoperguntas.equals(other.idtreinamentoperguntas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Treinamentoperguntas[ idtreinamentoperguntas=" + idtreinamentoperguntas + " ]";
    }
    
}

