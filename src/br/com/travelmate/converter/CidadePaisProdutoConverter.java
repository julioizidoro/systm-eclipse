package br.com.travelmate.converter;
   
import br.com.travelmate.model.Cidadepaisproduto; 
 
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
/**
 *
 * @author Wolverine
 */
@FacesConverter(value="CidadePaisProdutoConverter")
public class CidadePaisProdutoConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Cidadepaisproduto> listaCidadePaisProduto = (List<Cidadepaisproduto>) component.getAttributes().get("listaCidadePaisProduto");
        if (listaCidadePaisProduto != null) {
            for (Cidadepaisproduto cidadepaisproduto : listaCidadePaisProduto) {
                if (cidadepaisproduto.getCidade().getNome().equalsIgnoreCase(value)) {
                    return cidadepaisproduto;
                }
            }
        } else {
            return new Cidadepaisproduto();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            if (value instanceof Cidadepaisproduto){
            	if(((Cidadepaisproduto) value).getIdcidadepaisproduto()!=null) {
	            	Cidadepaisproduto cidade = (Cidadepaisproduto) value;
	                return cidade.getCidade().getNome();
            	}else return "";
            }else return "";
        }
    }
    
}
