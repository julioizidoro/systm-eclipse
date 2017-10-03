package br.com.travelmate.converter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
import br.com.travelmate.model.Tipoarquivoproduto;

@FacesConverter(value = "TipoArquivoProdutoConverter")
public class TipoArquivoProdutoConverter implements Converter{

	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	List<Tipoarquivoproduto> listaTipoArquivo = (List<Tipoarquivoproduto>) component.getAttributes().get("listaTipoArquivoProduto");
        for (Tipoarquivoproduto tipoarquivo : listaTipoArquivo) {
                if (tipoarquivo.getTipoarquivo().getDescricao().equalsIgnoreCase(value)) {
                    return tipoarquivo;
                }
            }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
        	Tipoarquivoproduto tipoarquivo = (Tipoarquivoproduto) value;
            if (tipoarquivo.getIdtipoarquivoproduto() != null) {
                return tipoarquivo.getTipoarquivo().getDescricao();
            } else {
                return "";
            }
        }
    }

}
