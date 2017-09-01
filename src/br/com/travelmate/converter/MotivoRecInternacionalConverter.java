package br.com.travelmate.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Motivorecinternacional;

@FacesConverter(value="MotivoRecInternacionalConverter")
public class MotivoRecInternacionalConverter implements Converter{

	
	 @Override
	    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	    	List<Motivorecinternacional> listaMotivoRecInternacional = (List<Motivorecinternacional>) component.getAttributes().get("listaMotivoRecInternacional");
	        for (Motivorecinternacional motivo : listaMotivoRecInternacional) {
	                if (motivo.getDescricao().equalsIgnoreCase(value)) {
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
	        	Motivorecinternacional motivo = (Motivorecinternacional) value;
	            return motivo.getDescricao();
	        }
	    }
}
