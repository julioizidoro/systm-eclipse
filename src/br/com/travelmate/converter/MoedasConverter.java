package br.com.travelmate.converter;

import br.com.travelmate.model.Moedas;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Wolverine
 */
@FacesConverter(value="MoedasConverter")
public class MoedasConverter implements Converter{

    @SuppressWarnings("unchecked")
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	List<Moedas> listaMoedas = (List<Moedas>) component.getAttributes().get("listaMoedas");
        for (Moedas Moedas : listaMoedas) {
                if (Moedas.getSigla().equalsIgnoreCase(value)) {
                    return Moedas;
                }
            }
        return null;
    }
        
   @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
            Moedas Moedas = (Moedas) value;
            return Moedas.getSigla();
        }
    }
    
}
