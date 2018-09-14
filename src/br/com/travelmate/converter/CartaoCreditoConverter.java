package br.com.travelmate.converter;
  
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Cartaocredito;

@FacesConverter(value="CartaoCreditoConverter")
public class CartaoCreditoConverter implements Converter{
    
    @SuppressWarnings("unchecked")
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	List<Cartaocredito> listaCartaocredito = (List<Cartaocredito>) component.getAttributes().get("listaCartaoCredito");
        for (Cartaocredito cartaocredito : listaCartaocredito) {
                if (cartaocredito.getNome().equalsIgnoreCase(value)) {
                    return cartaocredito;
                }
            }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
        	Cartaocredito cartaocredito = (Cartaocredito) value;
            return cartaocredito.getNome();
        }
    }
}
