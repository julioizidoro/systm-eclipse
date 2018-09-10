/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.converter;

import br.com.travelmate.model.Fornecedor;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Wolverine
 */
@FacesConverter(value="FornecedorConverter")
public class FornecedorConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	List<Fornecedor> listaFornecedor = (List<Fornecedor>) component.getAttributes().get("listaFornecedor");
        if (listaFornecedor != null) {
            for (Fornecedor fornecedor : listaFornecedor) {
                if (fornecedor.getNome().equalsIgnoreCase(value)) {
                    return fornecedor;
                }
            }
        } else {
            return new Fornecedor();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Fornecedor";
        }else {
            if (value instanceof Fornecedor){
                Fornecedor fornecedorcidade = (Fornecedor) value;
                if (fornecedorcidade.getIdfornecedor()==null){
                    return "";
                }else return fornecedorcidade.getNome();
            }else return "";
        }
    }
    
}
