package br.com.travelmate.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Valoreshighschool;

@FacesConverter(value="ValoresHighSchoolConverter")
public class ValoresHighSchoolConverter  implements Converter{

    @SuppressWarnings("unchecked")
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Valoreshighschool> listaValor = (List<Valoreshighschool>) component.getAttributes().get("listaValoresHighSchool");
        if (listaValor != null) {
            for (Valoreshighschool valor : listaValor) {
            	String comparacao = valor.getInicio() + " " + valor.getAnoinicio() + " - " + valor.getDuracao();
                if (comparacao.equalsIgnoreCase(value)) {
                    return valor;
                }
            }
        } else {
            return new Valoreshighschool();
        }
        return null;
    }

	@Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            if (value instanceof Valoreshighschool){
            	Valoreshighschool valores = (Valoreshighschool) value;
                if (valores.getIdvaloresHighSchool()==null){
                    return "";
                }else return valores.getInicio() + " " + valores.getAnoinicio() + " - " + valores.getDuracao();
            }else return "";
        }
    }
    

}
