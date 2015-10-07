import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

import exposed.transfer_objects.OrderDTO;

public class ShowOrdersController implements Controller {
    public void perform(ComponentContext componentContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ServletContext servletContext) throws ServletException, IOException {

    }

    public void execute(ComponentContext componentContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ServletContext servletContext) throws Exception {
//        httpServletRequest.setAttribute(ApplicationActionServlet.ORDERS_PARAMETER,
//                ApplicationActionServlet.databaseBean.getOrders());

        httpServletRequest.removeAttribute(ApplicationActionServlet.ORDERS_PARAMETER);

        HashMap filter = new HashMap();
        try{
            String date = (String)httpServletRequest.getAttribute("date");
            filter.put("date", date);

//            filter.put("step", 5);
//            filter.put("pageNum", 1);
//            System.out.println("CONTROLLER: ATTRIBUTE DATE "+date);
        }catch(NullPointerException e){
//            System.out.println("CONTROLLER: NO ATTRIBUTE DATE ON REQUEST");
            filter.put("date", "0");
        }


        ArrayList<OrderDTO> orders = ApplicationActionServlet.databaseBean.getOrdersHib(filter);

//        ArrayList<OrderDTO> orders = (ArrayList<OrderDTO>)ApplicationActionServlet.databaseBean.getOrders();
//        Iterator i = orders.iterator();
//        Set set = new HashSet();
//        while(i.hasNext()){
//            OrderDTO ord = (OrderDTO)i.next();
//            set.add(ord.getOrderDate());
//        }
//        System.out.println("size: "+set.size());

        httpServletRequest.setAttribute(ApplicationActionServlet.ORDERS_PARAMETER, orders);

        httpServletRequest.setAttribute(ApplicationActionServlet.DATES_PARAMETER,
                ApplicationActionServlet.databaseBean.getOrdersDates());
        httpServletRequest.setAttribute(ApplicationActionServlet.REPORT_PARAMETER,
                ApplicationActionServlet.databaseBean.getReport());
        ApplicationActionServlet.setLocation(ApplicationActionServlet.LOCATION_SHOW_ORDERS,
                httpServletRequest, httpServletResponse);
    }
}
