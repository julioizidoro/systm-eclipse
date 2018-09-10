/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.converter;

import br.com.travelmate.model.Fornecedorcidadeidioma;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Greici
 */
@FacesConverter(value="FornecedorCidadeIdiomaConverter")
public class FornecedorCidadeIdiomaConverter implements Converter{
     @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Fornecedorcidadeidioma> listaFornecedorcidadeidiomas = (List<Fornecedorcidadeidioma>) component.getAttributes().get("listaFornecedorCidadeIdioma");
        if (listaFornecedorcidadeidiomas != null) {
            for (Fornecedorcidadeidioma fornecedorcidadeidioma : listaFornecedorcidadeidiomas) {
                if (fornecedorcidadeidioma.getFornecedorcidade().getFornecedor().getNome().equalsIgnoreCase(value)) {
                    return fornecedorcidadeidioma;
                }
            }
        } else {
            return new Fornecedorcidadeidioma();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Fornecdor";
        }else {
            if (value instanceof Fornecedorcidadeidioma){
                Fornecedorcidadeidioma fornecedorcidadeidioma= (Fornecedorcidadeidioma) value;
                if (fornecedorcidadeidioma.getIdfornecedorcidadeidioma()==null){
                    return "";
                }else return fornecedorcidadeidioma.getFornecedorcidade().getFornecedor().getNome();
            }else return "";
        }
    }
    
}
    
