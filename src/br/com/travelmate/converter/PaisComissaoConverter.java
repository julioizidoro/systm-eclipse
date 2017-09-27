package br.com.travelmate.converter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Pais;

@FacesConverter(value="PaisComissaoConverter")
public class PaisComissaoConverter implements Converter{

	
	 @Override
	    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	    	try {
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	        List<Pais> listaPaisFornecedor = (List<Pais>) component.getAttributes().get("listaPaisFornecedor");
	        if (listaPaisFornecedor != null) {
	            for (Pais pais : listaPaisFornecedor) {
	                if (pais.getNome().equalsIgnoreCase(value)) {
	                    return pais;
	                }
	            }
	        } else {
	            return new Pais();
	        }
	        return null;
	    }

	    @Override
	    public String getAsString(FacesContext context, UIComponent component, Object value) {
	        if (value.toString().equalsIgnoreCase("0")){
	            return "Selecione";
	        }else {
	            if (value instanceof Pais){
	                Pais pais = (Pais) value;
	                return pais.getNome();
	            }else return "";
	        }
	    }
}
