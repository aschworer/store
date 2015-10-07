import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

public class CustomAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("custom action");
        ApplicationActionServlet.setLocation(ApplicationActionServlet.LOCATION_HELLO_PAGE,
                request, response);
        return mapping.findForward(ApplicationActionServlet.FORWARD_HELLO);

        //GET LOCATION FROM COOKIES

//        String loc = detectLocation(request);
//        ApplicationActionServlet.setLocation(loc, request, response);
//        if (loc.equals(ApplicationActionServlet.LOCATION_ADD_CLIENT)) {
//            return mapping.findForward(ApplicationActionServlet.FORWARD_ADD_CLIENT);
//        } else if (loc.equals(ApplicationActionServlet.LOCATION_ADD_ORDER)) {
//            return mapping.findForward(ApplicationActionServlet.FORWARD_ADD_ORDER);
//        } else if (loc.equals(ApplicationActionServlet.LOCATION_SHOW_CLIENTS)) {
//            return mapping.findForward(ApplicationActionServlet.FORWARD_SHOW_CLIENTS);
//        } else if (loc.equals(ApplicationActionServlet.LOCATION_SHOW_ORDERS)) {
//            return mapping.findForward(ApplicationActionServlet.FORWARD_SHOW_ORDERS);
//        } else {
//            return mapping.findForward(ApplicationActionServlet.FORWARD_HELLO);
//        }
    }

//    private String detectLocation(HttpServletRequest request) {
//        if (request.getCookies() != null) {
//            Cookie[] c = request.getCookies();
//            for (int i = 0; i < c.length; i++) {
//                if (ApplicationActionServlet.LOCATION_COOKIE.equals(c[i].getName())) {
//                    return c[i].getValue();
//                }
//            }
//        }
//        return ApplicationActionServlet.LOCATION_HELLO_PAGE;
//    }

}
