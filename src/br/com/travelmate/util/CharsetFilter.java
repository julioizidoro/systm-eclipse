package br.com.travelmate.util;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.travelmate.managerBean.UsuarioLogadoMB;

public class CharsetFilter implements Filter {

	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
    private String encoding;

    public void init(FilterConfig config) throws ServletException {
        this.encoding = config.getInitParameter("requestEncoding");

        if (this.encoding == null) {
            this.encoding = "UTF-8";
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	String contexto = ((HttpServletRequest) request).getContextPath();
		if(!usuarioLogadoMB.isLogar()){
			((HttpServletResponse) response).sendRedirect(contexto+"/");
		}  
    	if (null == request.getCharacterEncoding()) {  
            request.setCharacterEncoding(this.encoding);
        }

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        chain.doFilter(request, response); 
    }

	@Override
	public void destroy() {
		
	}

}