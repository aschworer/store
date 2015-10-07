import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class SecurityFilter implements javax.servlet.Filter {
    public static final String USERNAME_PARAMETER = "username";
    public static final String ROLE_PARAMETER = "role";
    public static final String ROLE_SALESMAN = "Salesman";
    public static final String ROLE_SUPERVISOR = "Supervisor";
    public static final String ACCESS_FORBIDDEN_JSP = "/access_forbidden.jsp";


    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            if (req.getSession().isNew()) {
                req.getSession().setAttribute(USERNAME_PARAMETER, req.getUserPrincipal().getName());
                if (req.isUserInRole(ROLE_SUPERVISOR)) {
                    req.getSession().setAttribute(ROLE_PARAMETER, ROLE_SUPERVISOR);
                } else if (req.isUserInRole(ROLE_SALESMAN)) {
                    req.getSession().setAttribute(ROLE_PARAMETER, ROLE_SALESMAN);
                }
            }
//            } else {
//                if (ROLE_SALESMAN.equals(req.getSession().getAttribute(ROLE_PARAMETER))) {
//                    if (null != req.getRequestURI()) {
//                        if ("/webmodule/shop/show_clients.jsp".equals(req.getRequestURI()) ||
//                                "/webmodule/shop/show_orders.jsp".equals(req.getRequestURI())) {
//                            HttpServletResponse res = (HttpServletResponse) response;
//                            req.getRequestDispatcher(ACCESS_FORBIDDEN_JSP).forward(req, res);
//                        }
//                    }
//                }
//            }
            chain.doFilter(request, response);

        } catch (IOException ex) {
            System.out.println("IOException on doFilterL: " + ex.getMessage());
        } catch (ServletException e) {
            System.out.println("ServletException on doFilter: " + e.getMessage());
        }
    }
}

