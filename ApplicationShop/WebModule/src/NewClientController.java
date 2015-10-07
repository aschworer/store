import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;


public class NewClientController implements Controller {

    public void perform(ComponentContext componentContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ServletContext servletContext) throws ServletException, IOException {

    }

    public void execute(ComponentContext componentContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ServletContext servletContext) throws Exception {
//        ArrayList per = ApplicationActionServlet.databaseBean.getPersonsHyb();
//        System.out.println("persons get. size " + per.size());
//        httpServletRequest.setAttribute(ApplicationActionServlet.PERSONS_PARAMETER,
//                ApplicationActionServlet.databaseBean.getPersonsHib());
        
        httpServletRequest.setAttribute(ApplicationActionServlet.PERSONS_PARAMETER,
                ApplicationActionServlet.databaseBean.getCustomersHib());

//        httpServletRequest.setAttribute(ApplicationActionServlet.PERSONS_PARAMETER,
//                ApplicationActionServlet.databaseBean.getPersons());
        ApplicationActionServlet.setLocation(ApplicationActionServlet.LOCATION_ADD_CLIENT,
                httpServletRequest, httpServletResponse);
    }
}

