package br.com.travelmate.converter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.bean.BolinhasBean;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Cambio;


@FacesConverter(value="BolinhaConverter")
public class BolinhaConverter implements Converter{

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
