import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @struts.action
 * type="SaveClientAction"
 * path="/SubmitClient"
 * input="/tiles/add_client.jsp"
 * validate="true"
 * name="AddClientForm"
 */


public class SaveClientAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AddClientForm adf = (AddClientForm) form;


//        request.setAttribute(ApplicationActionServlet.PERSONS_PARAMETER,
//                ApplicationActionServlet.databaseBean.getPersons());
//        ApplicationActionServlet.databaseBean.addClient(adf.getLastname(),
//                adf.getFirstname(), adf.getAdvicer());

//        ApplicationActionServlet.databaseBean.addClientHib(adf.getLastname(),
//                adf.getFirstname(), adf.getAdvicer());

        ApplicationActionServlet.databaseBean.addCustomerHib(adf.getLastname(),
                adf.getFirstname(), adf.getAdvicer(), adf.getDiscription(), adf.getCompany());


        ApplicationActionServlet.setLocation(ApplicationActionServlet.LOCATION_SHOW_CLIENTS,
                request, response);
        return mapping.findForward(ApplicationActionServlet.FORWARD_SHOW_CLIENTS);
    }


}
