/**
 * 
 */
package net.aws.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Sebastian Luca
 *
 */
@WebFilter(filterName = "AdminAuthFilter", urlPatterns = { "/Pages/admin/*" })
public class AdminAuthFilter implements Filter {

	/**
	 * 
	 */
	public AdminAuthFilter() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try{
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = req.getSession(false);
			
			//reqURI contains everything after TLD and until query- strings (i.e. ?x=2;y=3)
			//originalURL removes the context path from the reqURI adn then constructs the URL so users will be redirected correctly
			//queryString returns the Strings that came as POST parameters and null if non exist.
			String reqURI = req.getRequestURI();
			String originalURL = reqURI.replace(req.getContextPath(), "");
			String queryString = req.getQueryString();
			
			//Reconstructing the original URL
			if( queryString != null ) {
				originalURL += "?" + queryString;
			}
			//redirect
			originalURL += "?faces-redirect=true";
			
			if( reqURI.indexOf("/Pages/login.xhtml") >= 0
					|| (ses != null && ses.getAttribute("username") != null)
					|| reqURI.contains("javax.faces.resource")) {
				chain.doFilter(request, response);
			} else {
				req.getSession().setAttribute("originalURL", originalURL);
				
				resp.sendRedirect(req.getContextPath() + "/Pages/login.xhtml");
			} 
		} catch (Exception e) {
			//TODO handle exception
			System.err.println("\n\tAdmin Filter Err: " + e.getMessage());
		}

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
