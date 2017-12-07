package br.com.travelmate.managerBean.cliente;

import br.com.travelmate.model.Cliente;

public class ValidarClienteBean {
	
	private String msg;

	public ValidarClienteBean(Cliente cliente) {
		super();
		validar(cliente);
	}

	
	
	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}



	public void validar(Cliente cliente) {
		msg = " ";
		if (cliente.getCpf()==null) {
			msg = msg + "CPF inválido\r\n";
		}else if (cliente.getCpf().length()==0) {
			msg = msg + "CPF inválido\r\n";
		}
		if (cliente.getTipologradouro()==null) {
			msg = msg + "Tipo de logradouro inválido\r\n";
		}else if (cliente.getTipologradouro().length()==0) {
			msg = msg + "Tipo de logradouro inválido\r\n";
		}
		if (cliente.getLogradouro()==null) {
			msg = msg + "Logradouro inválido\r\n";
		}else if (cliente.getLogradouro().length()==0) {
			msg = msg + "Logradouro inválido\r\n";
		}
		if (cliente.getBairro()==null) {
			msg = msg + "Bairro inválido\r\n";
		}else if (cliente.getBairro().length()==0) {
			msg = msg + "Bairro inválido\r\n";
		}
		if (cliente.getCidade()==null) {
			msg = msg + "Cidade inválida\r\n";
		}else if (cliente.getCidade().length()==0) {
			msg = msg + "Cidade inválida\r\n";
		}
		if (cliente.getCep()==null) {
			msg = msg + "CEP inválido\r\n";
		}else if (cliente.getCep().length()==0) {
			msg = msg + "CEP inválido\r\n";
		}
		if (cliente.getEstado()==null) {
			msg = msg + "Estado inválido";
		}else if (cliente.getEstado().length()==0) {
			msg = msg + "Estado inválido";
		}
	}
	
	
	
	

}
