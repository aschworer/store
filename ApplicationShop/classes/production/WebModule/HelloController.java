import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

public class HelloController implements Controller {

    public void perform(ComponentContext componentContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ServletContext servletContext) throws ServletException, IOException {
    }

    public void execute(ComponentContext componentContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ServletContext servletContext) throws Exception {
        ApplicationActionServlet.setLocation(ApplicationActionServlet.LOCATION_HELLO_PAGE,
                httpServletRequest, httpServletResponse);
        componentContext.putAttribute("sessions", httpServletRequest.getSession().getServletContext().getAttribute("sessions"));


//        Iterator i = ApplicationActionServlet.databaseBean.getReport().iterator();
//        while(i.hasNext()){
//            ReportItemDTO item = (ReportItemDTO)i.next();
//            System.out.println(item.getCustomerType()+
//            item.getDate()+
//            item.getNumberOfOrders()+
//            item.getOrdersSum());
//            System.out.println("-----------------\n");
//
//        }

//        System.out.println("sessions size: " + ((HashMap) httpServletRequest.getSession().getServletContext().getAttribute("sessions")).size());
//        System.out.println("controller invoked");
//        if ("en".equals(componentContext.getAttribute("lang"))) {
//            componentContext.putAttribute("menu", (List)componentContext.getAttribute("menu"));
//        } else {
//            componentContext.putAttribute("menuRUS", componentContext.getAttribute("menuRUS"));
//        }
//        List value = (List)componentContext.getAttribute("menu");
//        System.out.println("size (3):"+value.size());
//        ArrayList menu = new ArrayList();
//        menu.add("1");
//        menu.add("1");
//        componentContext.putAttribute("menu", menu);
    }
}

