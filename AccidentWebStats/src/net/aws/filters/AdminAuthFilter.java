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
		// Nothing to do here
	}

	@Override
	public void destroy() {
		// Nothing to destroy

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try{
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = req.getSession(false);
			String reqURI = req.getRequestURI();
			
			if( (ses != null && ses.getAttribute("username") != null && ses.getAttribute("userRole").equals("adm"))
					|| reqURI.contains("javax.faces.resource")) {
				chain.doFilter(request, response);
			} else {				
				resp.sendRedirect(req.getContextPath() + "/Pages/user/dashboard.xhtml");
			} 
		} catch (Exception e) {
			//TODO handle exception
			System.err.println("\n\tAdmin Filter Err: " + e.getMessage());
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// nothing to do here

	}

}
