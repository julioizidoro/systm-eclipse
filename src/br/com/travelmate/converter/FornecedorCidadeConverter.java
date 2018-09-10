/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.converter;

import br.com.travelmate.model.Fornecedorcidade;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Wolverine
 */
@FacesConverter(value="FornecedorCidadeConverter")
public class FornecedorCidadeConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Fornecedorcidade> listaFornecedorCidade = (List<Fornecedorcidade>) component.getAttributes().get("listaFornecedorCidade");
        if (listaFornecedorCidade != null) {
            for (Fornecedorcidade fornecedorCidade : listaFornecedorCidade) {
                if (fornecedorCidade.getFornecedor().getNome().equalsIgnoreCase(value)) {
                    return fornecedorCidade;
                }
            }
        } else {
            return new Fornecedorcidade();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Fornecdor";
        }else {
            if (value instanceof Fornecedorcidade){
                Fornecedorcidade fornecedorcidade = (Fornecedorcidade) value;
                if (fornecedorcidade.getIdfornecedorcidade()==null){
                    return "";
                }else return fornecedorcidade.getFornecedor().getNome();
            }else return "";
        }
    }
    
}
