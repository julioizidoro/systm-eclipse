/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.converter;

import br.com.travelmate.model.Produtos;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Wolverine
 */
@FacesConverter(value="ProdutoConverter")
public class ProdutoConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Produtos> listaProdutos = (List<Produtos>) component.getAttributes().get("listaProdutos");
        if (listaProdutos != null) {
            for (Produtos produto : listaProdutos) {
                if (produto.getDescricao().equalsIgnoreCase(value)) {
                    return produto;
                }
            }
        } else {
            return new Produtos();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            Produtos produto = (Produtos) value;
            return produto.getDescricao();
        }
    }
    
}
