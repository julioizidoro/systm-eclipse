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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "tisolicitacoes")
public class Tisolicitacoes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtisolicitacoes")
    private Integer idtisolicitacoes;
    @Column(name = "datasolicitacao")
    @Temporal(TemporalType.DATE)
    private Date datasolicitacao;
    @Column(name = "dataconclusao")
    @Temporal(TemporalType.DATE)
    private Date dataconclusao;
    @Lob
    @Size(max = 16777215)
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "concluido")
    private Boolean concluido;
    @Column(name = "dataprevisao")
    @Temporal(TemporalType.DATE)
    private Date dataprevisao;
    @Size(max = 30)
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "liberar")
    private Boolean liberar;
    @JoinColumn(name = "departamento_iddepartamento", referencedColumnName = "iddepartamento")
    @ManyToOne(optional = false)
    private Departamento departamento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tisolicitacoes")
    private List<Tisolicitacoeshistorico> tisolicitacoeshistoricoList;

    public Tisolicitacoes() {
    }

    public Tisolicitacoes(Integer idtisolicitacoes) {
        this.idtisolicitacoes = idtisolicitacoes;
    }

    public Integer getIdtisolicitacoes() {
        return idtisolicitacoes;
    }

    public void setIdtisolicitacoes(Integer idtisolicitacoes) {
        this.idtisolicitacoes = idtisolicitacoes;
    }

    public Date getDatasolicitacao() {
        return datasolicitacao;
    }

    public void setDatasolicitacao(Date datasolicitacao) {
        this.datasolicitacao = datasolicitacao;
    }

    public Date getDataconclusao() {
        return dataconclusao;
    }

    public void setDataconclusao(Date dataconclusao) {
        this.dataconclusao = dataconclusao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getConcluido() {
        return concluido;
    }

    public void setConcluido(Boolean concluido) {
        this.concluido = concluido;
    }

    public Date getDataprevisao() {
        return dataprevisao;
    }

    public void setDataprevisao(Date dataprevisao) {
        this.dataprevisao = dataprevisao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Boolean getLiberar() {
        return liberar;
    }

    public void setLiberar(Boolean liberar) {
        this.liberar = liberar;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Tisolicitacoeshistorico> getTisolicitacoeshistoricoList() {
        return tisolicitacoeshistoricoList;
    }

    public void setTisolicitacoeshistoricoList(List<Tisolicitacoeshistorico> tisolicitacoeshistoricoList) {
        this.tisolicitacoeshistoricoList = tisolicitacoeshistoricoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtisolicitacoes != null ? idtisolicitacoes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tisolicitacoes)) {
            return false;
        }
        Tisolicitacoes other = (Tisolicitacoes) object;
        if ((this.idtisolicitacoes == null && other.idtisolicitacoes != null) || (this.idtisolicitacoes != null && !this.idtisolicitacoes.equals(other.idtisolicitacoes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Tisolicitacoes[ idtisolicitacoes=" + idtisolicitacoes + " ]";
    }
    
}
