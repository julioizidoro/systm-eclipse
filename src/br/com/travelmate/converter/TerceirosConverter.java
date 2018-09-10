/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.converter;
import br.com.travelmate.model.Terceiros;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Wolverine
 */
@FacesConverter(value="TerceirosConverter")
public class TerceirosConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Terceiros> listaTerceiros = (List<Terceiros>) component.getAttributes().get("listaTerceiros");
        if (listaTerceiros != null) {
            for (Terceiros terceiros : listaTerceiros) {
                if (terceiros.getNome().equalsIgnoreCase(value)) {
                    return terceiros;
                }
            }
        } else {
            return new Terceiros();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Terceiros) {
            Terceiros terceiros = (Terceiros) value;
            return terceiros.getNome();
        } else {
            return "";
        }
    }
}
