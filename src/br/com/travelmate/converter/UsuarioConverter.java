package br.com.travelmate.converter;

import br.com.travelmate.model.Usuario;


import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="UsuarioConverter")
public class UsuarioConverter implements Converter{
    
    
    @SuppressWarnings("unchecked")
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Usuario> listaUsuario = (List<Usuario>) component.getAttributes().get("listaUsuario");
        if (listaUsuario != null) {
	        for (Usuario usuario : listaUsuario) {
	                if (usuario.getNome().equalsIgnoreCase(value)) {
	                    return usuario;
	                }
	            }
	    } else {
	        return new Usuario();
	    }
	    return null;
	}

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
            Usuario usuario = (Usuario) value;
            return usuario.getNome();
        }
    }
}
