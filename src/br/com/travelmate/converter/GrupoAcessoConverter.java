/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package br.com.travelmate.converter;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Grupoacesso;



/**
 *
 * @author Wolverine
 */
@FacesConverter(value="GrupoAcessoConverter")
public class GrupoAcessoConverter  implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Grupoacesso> listaGrupoacesso = (List<Grupoacesso>) component.getAttributes().get("listaGrupoAcesso");
        if (listaGrupoacesso != null) {
            for (Grupoacesso grupoacesso : listaGrupoacesso) {
                if (grupoacesso.getDescricao().equalsIgnoreCase(value)) {
                    return grupoacesso;
                }
            }
        } else {
            return new Grupoacesso();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            if (value instanceof Grupoacesso){
            	Grupoacesso grupoacesso = (Grupoacesso) value;
                 return grupoacesso.getDescricao();
            }else return ""; 
        }
    }
    
}
