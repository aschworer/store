import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import exposed.transfer_objects.Status;

/**
 * @struts.action type="SaveOrderAction"
 * path="/SubmitOrder"
 * input="/tiles/add_order.jsp"
 * validate="true"
 * name="AddOrderForm"
 */

public class SaveOrderAction extends Action {


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        AddOrderForm addOrderForm = (AddOrderForm) form;
        FormProduct[] an = addOrderForm.getProducts();
        HashMap productsHashMap = new HashMap();
        for (int i = 0; i < an.length; i++) {
//            System.out.println("ord pro id=" + an[i].getId());
            String amount = an[i].getAmount();
            if (!("".equals(amount) || "0".equals(amount))) {
                productsHashMap.put(an[i].getId(), amount);
            }
            System.out.println("amount: "+productsHashMap.get(an[i].getId()));
        }

//        ApplicationActionServlet.databaseBean.addOrder(addOrderForm.getClientID(),
//                addOrderForm.getStatus(), productsHashMap);

        ApplicationActionServlet.databaseBean.addOrderHib(addOrderForm.getClientID(),
                Status.getStatus(addOrderForm.getStatus()), productsHashMap);

//        request.setAttribute(ApplicationActionServlet.ORDERS_PARAMETER,
//                ApplicationActionServlet.databaseBean.getOrders());
//        ApplicationActionServlet.setLocation(ApplicationActionServlet.LOCATION_SHOW_ORDERS,
//                request, response);
        return mapping.findForward(ApplicationActionServlet.FORWARD_SHOW_ORDERS);

    }
}
