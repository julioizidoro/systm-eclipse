package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the controleprogramasteens database table.
 * 
 */
@Entity
@Table(name="controleprogramasteens")
@NamedQuery(name="Controleprogramasteen.findAll", query="SELECT c FROM Controleprogramasteen c")
public class Controleprogramasteen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontroleProgramasTeens")
    private Integer idcontroleProgramasTeens;
    @Column(name = "dataEmbarque")
    @Temporal(TemporalType.DATE)
    private Date dataEmbarque;
    @Column(name = "cidadeDestino")
    private String cidadeDestino;
    @Column(name = "dataEnvioApp")
    @Temporal(TemporalType.DATE)
    private Date dataEnvioApp;
    @Column(name = "bonus")
    private String bonus;
    @Column(name = "dataRetorno")
    @Temporal(TemporalType.DATE)
    private Date dataRetorno;
    @Column(name = "dataAplicouVisto")
    @Temporal(TemporalType.DATE)
    private Date dataAplicouVisto;
    @Lob
    @Column(name = "observacoes")
    private String observacoes;
    @Column(name = "visto")
    private String visto;
    @Column(name = "contrato")
    private String contrato;
    @Column(name = "documentacao")
    private String documentacao;
    @Column(name = "familia")
    private String familia;
    @Lob
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "finalizado")
    private String finalizado;
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "docanexado")
    private String docanexado;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
	@ManyToOne(optional = false)
	private Vendas vendas;

    public Controleprogramasteen() {
    }

    public Controleprogramasteen(Integer idcontroleProgramasTeens) {
        this.idcontroleProgramasTeens = idcontroleProgramasTeens;
    }

    public Integer getIdcontroleProgramasTeens() {
        return idcontroleProgramasTeens;
    }

    public void setIdcontroleProgramasTeens(Integer idcontroleProgramasTeens) {
        this.idcontroleProgramasTeens = idcontroleProgramasTeens;
    }

    public Date getDataEmbarque() {
        return dataEmbarque;
    }

    public String getDocanexado() {
        return docanexado;
    }

    public void setDocanexado(String docanexado) {
        this.docanexado = docanexado;
    }

    public void setDataEmbarque(Date dataEmbarque) {
        this.dataEmbarque = dataEmbarque;
    }

    public String getCidadeDestino() {
        return cidadeDestino;
    }

    public void setCidadeDestino(String cidadeDestino) {
        this.cidadeDestino = cidadeDestino;
    }

    public Date getDataEnvioApp() {
        return dataEnvioApp;
    }

    public void setDataEnvioApp(Date dataEnvioApp) {
        this.dataEnvioApp = dataEnvioApp;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public Date getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(Date dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public Date getDataAplicouVisto() {
        return dataAplicouVisto;
    }

    public void setDataAplicouVisto(Date dataAplicouVisto) {
        this.dataAplicouVisto = dataAplicouVisto;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getVisto() {
        return visto;
    }

    public void setVisto(String visto) {
        this.visto = visto;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getDocumentacao() {
        return documentacao;
    }

    public void setDocumentacao(String documentacao) {
        this.documentacao = documentacao;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(String finalizado) {
        this.finalizado = finalizado;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }


    public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontroleProgramasTeens != null ? idcontroleProgramasTeens.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Controleprogramasteen)) {
            return false;
        }
        Controleprogramasteen other = (Controleprogramasteen) object;
        if ((this.idcontroleProgramasTeens == null && other.idcontroleProgramasTeens != null) || (this.idcontroleProgramasTeens != null && !this.idcontroleProgramasTeens.equals(other.idcontroleProgramasTeens))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Controleprogramasteens[ idcontroleProgramasTeens=" + idcontroleProgramasTeens + " ]";
    }
}