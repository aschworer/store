import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @struts.action type="FilterOrdersAction"
 * path="/FilterOrders"
 * input="/tiles/show_orders.jsp"
 * validate="false"
 * name="FilterForm"
 */


public class FilterOrdersAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FilterForm f = (FilterForm) form;

        try {
            f.getDate();
//            System.out.println("FILTER DATE "+f.getDate());
            request.setAttribute("date", f.getDate());
//            System.out.println("Attribute set "+ f.getDate());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapping.findForward(ApplicationActionServlet.FORWARD_SHOW_ORDERS);
    }


}
