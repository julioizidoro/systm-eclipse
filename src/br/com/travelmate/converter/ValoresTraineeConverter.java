package br.com.travelmate.converter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Valorestrainee;

@FacesConverter(value="ValoresTraineeConverter")
public class ValoresTraineeConverter  implements Converter{
	
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
		List<Valorestrainee> listaValorSeguro = (List<Valorestrainee>) component.getAttributes().get("listaValoresTrainee");
        if (listaValorSeguro != null) {
            for (Valorestrainee valor : listaValorSeguro) {
                if (valor.getDescricao().equalsIgnoreCase(value)) {
                    return valor;
                }
            }
        } else {
            return new Valorestrainee();
        }
        return null;
    }

	@Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            if (value instanceof Valorestrainee){
            	Valorestrainee valorestrainee = (Valorestrainee) value;
                if (valorestrainee.getIdvalorestrainee()==null){
                    return "";
                }else return valorestrainee.getDescricao();
            }else return "";
        }
    }
    

}
