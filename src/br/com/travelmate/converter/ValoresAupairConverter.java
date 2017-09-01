package br.com.travelmate.converter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Valoresaupair;

@FacesConverter(value="ValoresAupairConverter")
public class ValoresAupairConverter  implements Converter{
	
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Valoresaupair> listaValor = (List<Valoresaupair>) component.getAttributes().get("listaValoresAupair");
        if (listaValor != null) {
            for (Valoresaupair valor : listaValor) {
                if (valor.getDescricao().equalsIgnoreCase(value)) {
                    return valor;
                }
            }
        } else {
            return new Valoresaupair();
        }
        return null;
    }

	@Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            if (value instanceof Valoresaupair){
            	Valoresaupair valores = (Valoresaupair) value;
                if (valores.getIdvaloresAupair()==null){
                    return "";
                }else return valores.getDescricao();
            }else return "";
        }
    }
    

}
