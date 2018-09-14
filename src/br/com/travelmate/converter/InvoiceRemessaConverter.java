/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.converter;
 
 
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Invoiceremessa;
import br.com.travelmate.util.Formatacao;

/**
 *
 * @author Wolverine
 */
@FacesConverter(value="InvoiceRemessaConverter")
public class InvoiceRemessaConverter implements Converter{
    
	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		List<Invoiceremessa> lista = (List<Invoiceremessa>) component.getAttributes().get("listaInvoiceRemessa");
		if (lista != null) {
			for (Invoiceremessa invoiceremessa : lista) {
				String nome = Formatacao.ConvercaoDataPadrao(invoiceremessa.getData()) + " - "
						+ invoiceremessa.getHora();
				if (nome.equalsIgnoreCase(value)) {
					return invoiceremessa;
				}
			}
		} else {
			return new Invoiceremessa();
		}
		return null;
	}

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Invoice Remessa";
        }else {
            if (value instanceof Invoiceremessa){
            	Invoiceremessa invoiceremessa = (Invoiceremessa) value;
                if (invoiceremessa.getIdinvoiceremessa()==null){
                    return "";
                }else return Formatacao.ConvercaoDataPadrao(invoiceremessa.getData())+" - "+invoiceremessa.getHora();
            }else return "";
        }
    }
    
}
