import org.apache.struts.action.ActionServlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApplicationActionServlet extends ActionServlet {
    public static final String PERSONS_PARAMETER = "persons";
    public static final String ORDERS_PARAMETER = "orders";
    public static final String AMOUNTS_PARAMETER = "amounts";
    public static final String PRODUCTS_PARAMETER = "products";
    public static final String LOCATION_ADD_CLIENT = "Add Client";
    public static final String LOCATION_ADD_ORDER = "Add Order";
    public static final String LOCATION_SHOW_ORDERS = "Show Orders";
    public static final String LOCATION_HELLO_PAGE = "Hello";
    public static final String LOCATION_SHOW_CLIENTS = "Show Clients";
    public static final String[] LOCATIONS = {LOCATION_HELLO_PAGE, LOCATION_SHOW_ORDERS, LOCATION_SHOW_CLIENTS,
            LOCATION_ADD_ORDER, LOCATION_ADD_CLIENT};
    public static final String CURRENT_LOCATION = "currentLocation";
//    public static final String WAREHOUSE_EMAIL = "Aigul.Zainullina@school.starsoftlabs.com";
    public static final String LOCATION_COOKIE = "locationCookie";
    public static final String LOCATIONS_PARAMETER = "locations";
    public static final String HREFS_PARAMETER = "hrefs";
    public static final String REFILL_ID_PARAMETER = "id";
    public static final String REFILL_AMOUNT_PARAMETER = "amount";
    public static final String SESSIONS_ATRIBUTE = "sessions";
    public static final String REPORT_PARAMETER = "report";
    public static final String DATES_PARAMETER = "dates";
    public static final String[] HREFS = {
            "/webmodule/Hello.do",
            "/webmodule/ShowOrders.do",
            "/webmodule/ShowClients.do",
            "/webmodule/NewOrder.do",
            "/webmodule/NewClient.do"
    };
    public static final String SHOPBUISNESSFACADE_JNDI = "ShopBeanJNDILocal";
//    public static final int PAGE_NUM = ;
    public static ShopBuisnessFacadeLocal databaseBean;
//    public static final String WAREHOUSE_EMAIL_PAR = "warehouseEmail";
    public static final String FORWARD_HELLO = "Hello";
    public static final String FORWARD_SHOW_CLIENTS = "ShowClients";
    public static final String FORWARD_SHOW_ORDERS = "ShowOrders";
    public static final String FORWARD_ADD_CLIENT = "NewClientForm";
    public static final String FORWARD_ADD_ORDER = "NewOrderForm";
    public static final String PAGE_NUM = "pagesNum";

//    public static final String FORWARD_SHOW_CLIENTS_REDIRECT = "ShowClientsRedirect";
//    public static final String FORWARD_SHOW_ORDERS_REDIRECT = "ShowOrdersRedirect";

    public ApplicationActionServlet() {
        super();
        System.out.println("\n\n\n-----------------------------INIT SERVLET----------------------------");
        try {
            Context ctx = new InitialContext();
            ShopBuisnessFacadeLocalHome localHome =
                    (ShopBuisnessFacadeLocalHome) ctx.lookup(SHOPBUISNESSFACADE_JNDI);
            databaseBean = localHome.create();
//            databaseBean.setWarehouseEmail(WAREHOUSE_EMAIL);
//            System.out.println("................Servlet initialized");
        } catch (NamingException e) {
        } catch (CreateException e) {
        }
    }
    public static void setLocation(String newLocation, HttpServletRequest request,
                                   HttpServletResponse response) {
        request.setAttribute(CURRENT_LOCATION, newLocation);
//        Cookie co = new Cookie(LOCATION_COOKIE, newLocation);
//        co.setMaxAge(400);
//        response.addCookie(co);
    }

    /*public static void ifNewUser(HttpServletRequest request, HttpServletResponse response) {
        if (!request.getParameterNames().hasMoreElements()) {
            String loc = detectLocation(request);
            setLocation(loc, request, response);
            if (loc.equals(LOCATION_ADD_CLIENT)) {
                option = OPTION_NEW_CLIENT;
            } else if (loc.equals(LOCATION_ADD_ORDER)) {
                option = OPTION_NEW_ORDER;
            } else if (loc.equals(LOCATION_SHOW_CLIENTS)) {
                option = OPTION_SHOW_CLIENTS;
            } else if (loc.equals(LOCATION_SHOW_ORDERS)) {
                option = OPTION_SHOW_ORDERS;
            } else {
                option = "default";
            }
        }
    }

    private static String detectLocation(HttpServletRequest request) {

        if (request.getCookies() != null) {
            Cookie[] c = request.getCookies();
            for (int i = 0; i < c.length; i++) {
                if (LOCATION_COOKIE.equals(c[i].getName())) {
                    return c[i].getValue();
                }
            }
        }
        return LOCATION_HELLO_PAGE;
    }*/


    public void destroy() {
        System.out.println("\n\n\n-------------------SERVLET .destroy() invoked-------------------");
        super.destroy();
    }
}
