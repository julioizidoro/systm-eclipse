/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package br.com.travelmate.converter;
import br.com.travelmate.model.Banco;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Wolverine
 */

@FacesConverter(value = "BancoConverter")
public class BancoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Banco> listaBanco = (List<Banco>) component.getAttributes().get("listaBanco");
        if (listaBanco != null) {
            for (Banco banco : listaBanco) {
                if (banco.getNome().equalsIgnoreCase(value)) {
                    return banco;
                }
            }
        } else {
            Banco banco = new Banco();
            return banco;
        }
        Banco banco = new Banco();
        return banco;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
            Banco banco = (Banco) value;
            if (banco.getIdbanco() != null) {
                return banco.getNome();
            } else {
                return "";
            }
        }
    }
}
