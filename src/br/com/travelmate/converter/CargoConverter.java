package br.com.travelmate.converter;
    
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Cargo;
/**
 *
 * @author Kamila
 */
@FacesConverter(value="CargoConverter")
public class CargoConverter implements Converter{
    
    @SuppressWarnings("unchecked")
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Cargo> listaCargo = (List<Cargo>) component.getAttributes().get("listaCargo");
        if (listaCargo != null) {
            for (Cargo cargo : listaCargo) {
                if (cargo.getNome().equalsIgnoreCase(value)) {
                    return cargo;
                }
            }
        } else {
            return new Cargo();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            if (value instanceof Cargo){
            	if(((Cargo) value).getIdcargo()!=null) {
            		Cargo cargo = (Cargo) value;
	                return cargo.getNome();
            	}else return "";
            }else return "";
        }
    }
    
}
