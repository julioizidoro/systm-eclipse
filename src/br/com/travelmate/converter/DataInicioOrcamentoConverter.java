package br.com.travelmate.converter;
 
import java.util.List;
 
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter; 
import javax.faces.convert.FacesConverter;
 
import br.com.travelmate.managerBean.OrcamentoCurso.DatasBean;  

@FacesConverter(value="DataInicioOrcamentoConverter")
public class DataInicioOrcamentoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<DatasBean> listaData = (List<DatasBean>) component.getAttributes().get("listaData");
        if (listaData != null) {
            for (DatasBean data : listaData) {
                if (data.getDescricao().equalsIgnoreCase(value)) {
                    return data;
                }
            }
        } 
        return new DatasBean();
    }

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value.toString().equalsIgnoreCase("0")) {
			return "Selecione";
		} else {
			DatasBean data = (DatasBean) value;
			return data.getDescricao();
		}
	}

}
