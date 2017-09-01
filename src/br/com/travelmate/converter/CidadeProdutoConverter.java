package br.com.travelmate.converter;
   
import br.com.travelmate.model.Cidade;
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
@FacesConverter(value="CidadeProdutoConverter")
public class CidadeProdutoConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Cidadepaisproduto> listaCidadePaisProduto = (List<Cidadepaisproduto>) component.getAttributes().get("listaCidadeProduto");
        if (listaCidadePaisProduto != null) {
            for (Cidadepaisproduto cidadepaisproduto : listaCidadePaisProduto) {
                if (cidadepaisproduto.getCidade().getNome().equalsIgnoreCase(value)) {
                    return cidadepaisproduto.getCidade();
                }
            }
        } else {
            return new Cidade();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            if (value instanceof Cidade){
            	if(((Cidade) value).getIdcidade()!=null) {
            		Cidade cidade = (Cidade) value;
	                return cidade.getNome();
            	}else return "";
            }else return "";
        }
    }
    
}
