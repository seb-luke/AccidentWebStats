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
@WebFilter(filterName = "LogOutUserFilter", urlPatterns={"/Pages/logout.xhtml"})
public class LogoutToLoginFilter implements Filter {

	/**
	 * 
	 */
	public LogoutToLoginFilter() {
		// Nothing to destroy
	}


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// nothing to do here

	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try{
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = req.getSession(false);
			
			if( ses.getAttribute("username") == null ) {
				//user not logged in
				//let the chain flow
				chain.doFilter(request, response);
			} else {
				//user needs to be logged out first
				ses.invalidate();
				//after that, redirect to login
				resp.sendRedirect(req.getContextPath() + "/Pages/login.xhtml?faces-redirect=true");
			}
		} catch (Exception e) {
			System.err.println("\n\tLogin to Logout Filter Err: " + e.getMessage());
		}
	}


	@Override
	public void destroy() {
		// Nothing to destroy
	}

}

















