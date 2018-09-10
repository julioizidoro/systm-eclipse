package br.com.travelmate.converter;

import br.com.travelmate.model.Planoconta;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="PlanoContaConverter")
public class PlanoContaConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	List<Planoconta> listaPlanoConta = (List<Planoconta>) component.getAttributes().get("listaPlanoConta");
        for (Planoconta planoConta : listaPlanoConta) {
                if (planoConta.getDescricao().equalsIgnoreCase(value)) {
                    return planoConta;
                }
            }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
            Planoconta planoConta = (Planoconta) value;
            return planoConta.getDescricao();
        }
    }
}
