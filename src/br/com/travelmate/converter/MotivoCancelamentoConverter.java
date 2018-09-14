package br.com.travelmate.converter;
  
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Motivocancelamento;

/**
 *
 * @author Kamila
 */
@FacesConverter(value="MotivoCancelamentoConverter")
public class MotivoCancelamentoConverter implements Converter{

    @SuppressWarnings("unchecked")
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	List<Motivocancelamento> listaMotivo = (List<Motivocancelamento>) component.getAttributes().get("listaMotivo");
        for (Motivocancelamento motivo : listaMotivo) {
                if (motivo.getMotivo().equalsIgnoreCase(value)) {
                    return motivo;
                }
            }
        return null;
    }
        
   @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
        	Motivocancelamento motivo = (Motivocancelamento) value;
            return motivo.getMotivo();
        }
    }
    
}
