import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @struts.action
 * type="RefillWarehouseAction"
 * path="/Refill"
 */

public class RefillWarehouseAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("Refilling warehouse...");
        System.out.println("Product id=" + request.getParameter
                (ApplicationActionServlet.REFILL_ID_PARAMETER));
        System.out.println("Amount=" + request.getParameter(ApplicationActionServlet.REFILL_AMOUNT_PARAMETER));
        ApplicationActionServlet.databaseBean.refillWarehouseHib
                (Integer.parseInt(request.getParameter(ApplicationActionServlet.REFILL_ID_PARAMETER)),
                Integer.parseInt(request.getParameter(ApplicationActionServlet.REFILL_AMOUNT_PARAMETER)));
        ApplicationActionServlet.setLocation(ApplicationActionServlet.LOCATION_HELLO_PAGE, request, response);
        return mapping.findForward(ApplicationActionServlet.FORWARD_HELLO);
    }
}
