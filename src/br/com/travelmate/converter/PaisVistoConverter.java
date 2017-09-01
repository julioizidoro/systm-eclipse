package br.com.travelmate.converter;
  
import br.com.travelmate.model.Pais;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
/**
 *
 * @author Wolverine
 */
@FacesConverter(value="PaisVistoConverter")
public class PaisVistoConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Pais> listaPais = (List<Pais>) component.getAttributes().get("listaPais");
        if (listaPais != null) {
            for (Pais paisProduto : listaPais) {
                if (paisProduto.getNome().equalsIgnoreCase(value)) {
                    return paisProduto;
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
