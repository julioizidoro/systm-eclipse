/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.converter;

import br.com.travelmate.model.Valoresseguro;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


/**
 *
 * @author Wolverine
 */
@FacesConverter(value="ValorSeguroConverter")
public class ValorSeguroConverter  implements Converter{

    @SuppressWarnings("unchecked")
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Valoresseguro> listaValorSeguro = (List<Valoresseguro>) component.getAttributes().get("listaValorSeguro");
        if (listaValorSeguro != null) {
            for (Valoresseguro valor : listaValorSeguro) {
                if (valor.getTarifario().equalsIgnoreCase(value)) {
                    return valor;
                }
            }
        } else {
            return new Valoresseguro();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            if (value instanceof Valoresseguro){
                Valoresseguro valor = (Valoresseguro) value;
            return valor.getTarifario();
            }else return "";
        }
    }
    
}
