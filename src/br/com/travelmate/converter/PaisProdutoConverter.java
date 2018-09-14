package br.com.travelmate.converter;
   
import br.com.travelmate.model.Paisproduto; 
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
/**
 *
 * @author Wolverine
 */
@FacesConverter(value="PaisProdutoConverter")
public class PaisProdutoConverter implements Converter{
    
    @SuppressWarnings("unchecked")
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Paisproduto> listaPais = (List<Paisproduto>) component.getAttributes().get("listaPaisProduto");
        if (listaPais != null) {
            for (Paisproduto paisProduto : listaPais) {
                if (paisProduto.getPais().getNome().equalsIgnoreCase(value)) {
                    return paisProduto;
                }
            }
        } else {
            return new Paisproduto();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            if (value instanceof Paisproduto){ 
            		Paisproduto pais = (Paisproduto) value;
            		if(pais!=null && pais.getIdpaisproduto()!=null) {
            			return pais.getPais().getNome();
            		}else return "";
            }else return "";
        }
    }
    
}
