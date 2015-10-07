import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

public class NewOrderController implements Controller {


    public void perform(ComponentContext componentContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ServletContext servletContext) throws ServletException, IOException {

    }

    public void execute(ComponentContext componentContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ServletContext servletContext) throws Exception {
//        httpServletRequest.setAttribute(ApplicationActionServlet.PERSONS_PARAMETER,
//                ApplicationActionServlet.databaseBean.getPersons());

//        httpServletRequest.setAttribute(ApplicationActionServlet.PRODUCTS_PARAMETER,
//                ApplicationActionServlet.databaseBean.getProducts());

//        httpServletRequest.setAttribute(ApplicationActionServlet.PERSONS_PARAMETER,
//                ApplicationActionServlet.databaseBean.getPersonsHib());

        httpServletRequest.setAttribute(ApplicationActionServlet.PERSONS_PARAMETER,
                ApplicationActionServlet.databaseBean.getCustomersHib());

        httpServletRequest.setAttribute(ApplicationActionServlet.PRODUCTS_PARAMETER,
                        ApplicationActionServlet.databaseBean.getProductsHib());


        ApplicationActionServlet.setLocation(ApplicationActionServlet.LOCATION_ADD_ORDER,
                httpServletRequest, httpServletResponse);
    }
}



