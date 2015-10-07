import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Date;

public class HTTPSessionListener implements HttpSessionListener {
    protected HashMap sessions;

    public HTTPSessionListener() {
        sessions = new HashMap();
    }

    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setAttribute(ApplicationActionServlet.HREFS_PARAMETER,
                ApplicationActionServlet.HREFS);
        httpSessionEvent.getSession().setAttribute(ApplicationActionServlet.LOCATIONS_PARAMETER,
                ApplicationActionServlet.LOCATIONS);
        sessions.put(httpSessionEvent.getSession().getId(), new Date(httpSessionEvent.getSession().getCreationTime()));
        if (httpSessionEvent.getSession().getServletContext().getAttribute(ApplicationActionServlet.SESSIONS_ATRIBUTE) == null) {
            httpSessionEvent.getSession().getServletContext().setAttribute(ApplicationActionServlet.SESSIONS_ATRIBUTE, sessions);
        }
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        sessions.remove(httpSessionEvent.getSession().getId());
    }
}
