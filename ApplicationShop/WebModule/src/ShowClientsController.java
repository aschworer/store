import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.math.BigInteger;


public class ShowClientsController implements Controller {

    public void perform(ComponentContext componentContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ServletContext servletContext) throws ServletException, IOException {

    }

    public void execute(ComponentContext componentContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ServletContext servletContext) throws Exception {
//        componentContext.putAttribute
//                ("persons", ApplicationActionServlet.databaseBean.getPersons());

//        httpServletRequest.setAttribute(ApplicationActionServlet.PERSONS_PARAMETER,
//                ApplicationActionServlet.databaseBean.getPersonsHib());

        int step = 10;
        int pageNum = 0;
        try {
            step = Integer.parseInt(httpServletRequest.getParameter("step"));
            pageNum = Integer.parseInt(httpServletRequest.getParameter("pageNum"));
        } catch (NumberFormatException e) {
//            httpServletRequest.setAttribute(ApplicationActionServlet.PERSONS_PARAMETER,
//                    ApplicationActionServlet.databaseBean.getCustomersHib(10, 0));
        }
        httpServletRequest.setAttribute(ApplicationActionServlet.PERSONS_PARAMETER,
                    ApplicationActionServlet.databaseBean.getCustomersHib(step, pageNum));


        BigInteger count = ApplicationActionServlet.databaseBean.getCustomersCount();
        int num = count.intValue() / step + 1;
//        System.out.println(count.intValue());
//        System.out.println(step);
//        System.out.println(num);
        httpServletRequest.setAttribute(ApplicationActionServlet.PAGE_NUM, num);
        httpServletRequest.setAttribute("step", step);
//        httpServletRequest.setAttribute(ApplicationActionServlet.PERSONS_PARAMETER,
//                ApplicationActionServlet.databaseBean.getPersons());
        ApplicationActionServlet.setLocation(ApplicationActionServlet.LOCATION_SHOW_CLIENTS,
                httpServletRequest, httpServletResponse);
    }
}
