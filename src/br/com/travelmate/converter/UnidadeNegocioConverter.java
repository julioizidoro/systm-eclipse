/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.converter;

import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.model.Unidadenegocio;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;




@FacesConverter(value="UnidadeNegocioConverter")
public class UnidadeNegocioConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
            List<Unidadenegocio> listaUnidadeNegocio = (List<Unidadenegocio>) component.getAttributes().get("listaUnidade");
            if (listaUnidadeNegocio==null){
            	UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
            	listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
            }
            for (Unidadenegocio unidade : listaUnidadeNegocio) {
                if (unidade.getNomeFantasia().equalsIgnoreCase(value)) {
                    return unidade;
                }
            }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
            Unidadenegocio unidade = (Unidadenegocio) value;
            return unidade.getNomeFantasia();
        }
    }

}
