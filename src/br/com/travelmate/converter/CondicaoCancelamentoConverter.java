package br.com.travelmate.converter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Condicaocancelamento;


@FacesConverter(value = "CondicaoCancelamentoConverter")
public class CondicaoCancelamentoConverter implements Converter {

	 @Override
	    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	        List<Condicaocancelamento> listaCondicao = (List<Condicaocancelamento>) component.getAttributes().get("listaCondicao");
	        if (listaCondicao != null) {
	            for (Condicaocancelamento condicaocancelamento : listaCondicao) {
	                if (condicaocancelamento.getDescricao().equalsIgnoreCase(value)) {
	                    return condicaocancelamento;
	                }
	            }
	        } else {
	        	Condicaocancelamento condicaocancelamento = new Condicaocancelamento();
	            return condicaocancelamento;
	        }
	        Condicaocancelamento condicaocancelamento = new Condicaocancelamento();
	        return condicaocancelamento;
	    }

	    @Override
	    public String getAsString(FacesContext context, UIComponent component, Object value) {
	        if (value.toString().equalsIgnoreCase("0")) {
	            return "Selecione";
	        } else {
	        	Condicaocancelamento condicaocancelamento = (Condicaocancelamento) value;
	            if (condicaocancelamento.getIdcondicaocancelamento() != null) {
	                return condicaocancelamento.getDescricao();
	            } else {
	                return "";
	            }
	        }
	    }
}
