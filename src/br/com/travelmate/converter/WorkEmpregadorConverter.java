package br.com.travelmate.converter;
    
import br.com.travelmate.model.Workempregador;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
/**
 *
 * @author Wolverine
 */
@FacesConverter(value="WorkEmpregadorConverter")
public class WorkEmpregadorConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Workempregador> listaWorkempregador = (List<Workempregador>) component.getAttributes().get("listaWorkEmpregador");
        if (listaWorkempregador != null) {
            for (Workempregador workempregador : listaWorkempregador) {
                if (workempregador.getNome().equalsIgnoreCase(value)) {
                    return workempregador;
                }
            }
        } else {
            return new Workempregador();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            if (value instanceof Workempregador){
            	if(((Workempregador) value).getIdworkempregador()!=null) {
            		Workempregador workempregador = (Workempregador) value;
	                return workempregador.getNome();
            	}else return "";
            }else return "";
        }
    }
    
}
