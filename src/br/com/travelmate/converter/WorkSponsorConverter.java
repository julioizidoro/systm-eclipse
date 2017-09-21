package br.com.travelmate.converter;
    
 
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Worksponsor;
/**
 *
 * @author Kamila
 */
@FacesConverter(value="WorkSponsorConverter")
public class WorkSponsorConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Worksponsor> lista = (List<Worksponsor>) component.getAttributes().get("listaWorkSponsor");
        if (lista != null) {
            for (Worksponsor worksponsor : lista) {
                if (worksponsor.getFornecedorcidade().getFornecedor().getNome().equalsIgnoreCase(value)) {
                    return worksponsor;
                }
            }
        } else {
            return new Worksponsor();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            if (value instanceof Worksponsor){
            	if(((Worksponsor) value).getIdworksponsor()!=null) {
            		Worksponsor worksponsor = (Worksponsor) value;
	                return worksponsor.getFornecedorcidade().getFornecedor().getNome();
            	}else return "";
            }else return "";
        }
    }
    
}
