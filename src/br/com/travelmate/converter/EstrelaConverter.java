package br.com.travelmate.converter;
 
import java.util.List;
 
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter; 
import javax.faces.convert.FacesConverter;
 
import br.com.travelmate.bean.EstrelasBean;  

@FacesConverter(value="EstrelaConverter")
public class EstrelaConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<EstrelasBean> listaEstrela = (List<EstrelasBean>) component.getAttributes().get("listaEstrela");
        if (listaEstrela != null) {
            for (EstrelasBean estrela : listaEstrela) {
                if (estrela.getCaminho().equalsIgnoreCase(value)) {
                    return estrela;
                }
            }
        } 
        return new EstrelasBean();
    }

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value.toString().equalsIgnoreCase("0")) {
			return "Selecione";
		} else {
			EstrelasBean bolinha = (EstrelasBean) value;
			return bolinha.getCaminho();
		}
	}

}
