package br.com.travelmate.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Valoresprogramasteens;

@FacesConverter(value="ValoresProgramasTeensConverter")
public class ValoresProgramasTeensConverter  implements Converter{

    @SuppressWarnings("unchecked")
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Valoresprogramasteens> listaValor = (List<Valoresprogramasteens>) component.getAttributes().get("listaValoresProgramasTeens");
        if (listaValor != null) {
            for (Valoresprogramasteens valor : listaValor) {
                if (valor.getInicio().equalsIgnoreCase(value)) {
                    return valor;
                }
            }
        } else {
            return new Valoresprogramasteens();
        }
        return null;
    }

	@Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            if (value instanceof Valoresprogramasteens){
            	Valoresprogramasteens valores = (Valoresprogramasteens) value;
                if (valores.getIdvaloresprogramasteens()==null){
                    return "";
                }else return valores.getInicio();
            }else return "";
        }
    }
    

}
