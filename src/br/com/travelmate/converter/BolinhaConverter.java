package br.com.travelmate.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.bean.BolinhasBean;


@FacesConverter(value="BolinhaConverter")
public class BolinhaConverter implements Converter{

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<BolinhasBean> listaBolinha = (List<BolinhasBean>) component.getAttributes().get("listaBolinha");
        if (listaBolinha != null) {
            for (BolinhasBean bolinha : listaBolinha) {
                if (bolinha.getCor().equalsIgnoreCase(value)) {
                    return bolinha;
                }
            }
        } 
        return new BolinhasBean();
    }

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value.toString().equalsIgnoreCase("0")) {
			return "Selecione";
		} else {
			BolinhasBean bolinha = (BolinhasBean) value;
			return bolinha.getCor();
		}
	}

}
