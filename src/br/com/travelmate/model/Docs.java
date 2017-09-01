package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "docs")
public class Docs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddocs")
    private Integer iddocs;
    @Size(max = 3)
    @Column(name = "lao")
    private String lao;
    @Size(max = 3)
    @Column(name = "booking")
    private String booking;
    @Size(max = 3)
    @Column(name = "coe")
    private String coe;
    @Size(max = 3)
    @Column(name = "cor")
    private String cor;
    @Size(max = 3)
    @Column(name = "i20")
    private String i20;
    @Size(max = 3)
    @Column(name = "caq")
    private String caq;
    @Size(max = 3)
    @Column(name = "familia")
    private String familia;
    @Size(max = 3)
    @Column(name = "transferin")
    private String transferin;
    @Size(max = 3)
    @Column(name = "tansferout")
    private String tansferout;
    @Size(max = 3)
    @Column(name = "segurogovernamental")
    private String segurogovernamental;
    @Size(max = 3)
    @Column(name = "passagem")
    private String passagem;
    @Size(max = 3)
    @Column(name = "visto")
    private String visto;
    @Size(max = 3)
    @Column(name = "cartacustodia")
    private String cartacustodia;
    @Size(max = 3)
    @Column(name = "pagamento")
    private String pagamento;
    @Size(max = 3)
    @Column(name = "passaporte")
    private String passaporte;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @OneToOne(optional = false)
    private Vendas vendas;

    public Docs() {
    }

    public Docs(Integer iddocs) {
        this.iddocs = iddocs;
    }

    public Integer getIddocs() {
        return iddocs;
    }

    public void setIddocs(Integer iddocs) {
        this.iddocs = iddocs;
    }

    public String getLao() {
        return lao;
    }

    public void setLao(String lao) {
        this.lao = lao;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public String getCoe() {
        return coe;
    }

    public void setCoe(String coe) {
        this.coe = coe;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getI20() {
        return i20;
    }

    public void setI20(String i20) {
        this.i20 = i20;
    }

    public String getCaq() {
        return caq;
    }

    public void setCaq(String caq) {
        this.caq = caq;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getTransferin() {
        return transferin;
    }

    public void setTransferin(String transferin) {
        this.transferin = transferin;
    }

    public String getTansferout() {
        return tansferout;
    }

    public void setTansferout(String tansferout) {
        this.tansferout = tansferout;
    }

    public String getSegurogovernamental() {
        return segurogovernamental;
    }

    public void setSegurogovernamental(String segurogovernamental) {
        this.segurogovernamental = segurogovernamental;
    }

    public String getPassagem() {
        return passagem;
    }

    public void setPassagem(String passagem) {
        this.passagem = passagem;
    }

    public String getVisto() {
        return visto;
    }

    public void setVisto(String visto) {
        this.visto = visto;
    }

    public String getCartacustodia() {
        return cartacustodia;
    }

    public void setCartacustodia(String cartacustodia) {
        this.cartacustodia = cartacustodia;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public String getPassaporte() {
		return passaporte;
	}

	public void setPassaporte(String passaporte) {
		this.passaporte = passaporte;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (iddocs != null ? iddocs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Docs)) {
            return false;
        }
        Docs other = (Docs) object;
        if ((this.iddocs == null && other.iddocs != null) || (this.iddocs != null && !this.iddocs.equals(other.iddocs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Docs[ iddocs=" + iddocs + " ]";
    }
    
}

