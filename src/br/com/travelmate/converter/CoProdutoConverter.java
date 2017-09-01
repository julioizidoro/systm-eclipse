/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package br.com.travelmate.converter;
import br.com.travelmate.model.Coprodutos;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Wolverine
 */

@FacesConverter(value = "CoProdutoConverter")
public class CoProdutoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Coprodutos> listaCoProduto = (List<Coprodutos>) component.getAttributes().get("listaCoProduto");
        if (listaCoProduto != null) {
            for (Coprodutos coprodutos : listaCoProduto) {
                if (coprodutos.getNome().equalsIgnoreCase(value)) {
                    return coprodutos;
                }
            }
        } else {
            Coprodutos coprodutos = new Coprodutos();
            return coprodutos;
        }
        Coprodutos coprodutos = new Coprodutos();
        return coprodutos;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
            Coprodutos coprodutos = (Coprodutos) value;
            if (coprodutos.getIdcoprodutos() != null) {
                return coprodutos.getNome();
            } else {
                return "";
            }
        }
    }
}
