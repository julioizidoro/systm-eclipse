package br.com.travelmate.converter;
 
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
import br.com.travelmate.model.Valoreswork;

@FacesConverter(value="ValoresWorkConverter")
public class ValoresWorkConverter  implements Converter{

    @SuppressWarnings("unchecked")
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
		List<Valoreswork> listaValores = (List<Valoreswork>) component.getAttributes().get("listaValoresWork");
        if (listaValores != null) {
            for (Valoreswork valor : listaValores) {
                if (valor.getDescricao().equalsIgnoreCase(value)) {
                    return valor;
                }
            }
        } else {
            return new Valoreswork();
        }
        return null;
    }

	@Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            if (value instanceof Valoreswork){
            	Valoreswork valoreswork = (Valoreswork) value;
                if (valoreswork.getIdvaloresWork()==null){
                    return "";
                }else return valoreswork.getDescricao();
            }else return "";
        }
    }
    

}
