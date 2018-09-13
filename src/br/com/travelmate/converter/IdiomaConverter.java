/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.converter;


import br.com.travelmate.model.Idioma;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Kamila Rodrigues
 */
@FacesConverter(value="IdiomaConverter")
public class IdiomaConverter implements Converter{

    @SuppressWarnings("unchecked")
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Idioma> listaIdioma = (List<Idioma>) component.getAttributes().get("listaIdiomas");
        if (listaIdioma != null) {
            for (Idioma idioma : listaIdioma) {
                if (idioma.getDescricao().equalsIgnoreCase(value)) {
                    return idioma;
                }
            }
        } else {
            return new Idioma();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            Idioma idioma = (Idioma) value;
            return idioma.getDescricao();
        }
    }
    
}
