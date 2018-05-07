package br.com.travelmate.managerBean;

import br.com.travelmate.model.Vendas;

public class ContratoBean {

	private String lerContrato = "";
	private Vendas vendas;
	
	public ContratoBean(Vendas vendas) {
		this.vendas = vendas;
	}

	public String getLerContrato() {
		return lerContrato;
	}

	public void setLerContrato(String lerContrato) {
		this.lerContrato = lerContrato;
	}

	public String pegarCaminho(){
		lerContrato = "<div style=\"border: 2px solid;border-color:#9C9C9C;width:100%;\">" +
					"<p style=\"margin: 1%;\">"+
						"<b style=\"color:green;\"> PARTES :</b> <br />" + vendas.getUnidadenegocio().getRazaoSocial() + 
						", adiante apenas TRAVELMATE ou TM ,pessoa jurídica de direito privado" +
						"com sede social na "+
						vendas.getUnidadenegocio().getTipoLogradouro() + " " +
						vendas.getUnidadenegocio().getLogradouro() + ", "+
						vendas.getUnidadenegocio().getNumero() + " " + 
						vendas.getUnidadenegocio().getComplemento() + ", " +
						vendas.getUnidadenegocio().getBairro() + ", "+
						vendas.getUnidadenegocio().getCidade() + ", "+
						vendas.getUnidadenegocio().getEstado() + ", CEP: "+
						vendas.getUnidadenegocio().getCep() + ", inscrita no CNPJ/MF sob" +
						"nº " + vendas.getUnidadenegocio().getCnpj() + ", neste ato " +
						"representada pelo consultor "+ vendas.getUsuario().getNome()  +"  <br />" +
						"<br /> " + vendas.getCliente().getNome() + ", adiante apenas" +
						"PARTICIPANTE ,"+ vendas.getCliente().getPaisNascimento() + ", " +
						vendas.getCliente().getEstadoCivil() + ", "+
						vendas.getCliente().getProfissao() + " portador do CI/RG de nº" +
						vendas.getCliente().getRg() +  " e inscrito no CPF/MF sob o nº" + 
						vendas.getCliente().getCpf() + ", residente e domiciliado na" +
						vendas.getCliente().getTipologradouro() + " " +
						vendas.getCliente().getLogradouro() + ", " +
						vendas.getCliente().getNumero() + ", "+
						vendas.getCliente().getBairro() + ", " +
						vendas.getCliente().getComplemento() + ", " +
						vendas.getCliente().getCidade() + ", "+ 
						vendas.getCliente().getEstado() + ", CEP: "+
						vendas.getCliente().getCep() + ", têm entre si justos e"+
						"acertados a celebração de contrato de prestação de serviços de"+
						"intercâmbio cultural, nos termos e condições estabelecidas neste"+
						"contrato."+
					"<p><br/>"+
				"</div> <br/>";
		return lerContrato;
	}

}