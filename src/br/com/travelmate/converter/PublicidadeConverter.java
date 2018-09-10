/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.converter;

import br.com.travelmate.model.Publicidade;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Wolverine
 */
@FacesConverter(value="PublicidadeConverter")
public class PublicidadeConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Publicidade> listaPublicidade = (List<Publicidade>) component.getAttributes().get("listaPublicidade");
        if (listaPublicidade != null) {
            for (Publicidade publicidade : listaPublicidade) {
                if (publicidade.getDescricao().equalsIgnoreCase(value)) {
                    return publicidade;
                }
            }
        } else {
            return new Publicidade();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            Publicidade publicidade = (Publicidade) value;
            return publicidade.getDescricao();
        }
    }
    
}
