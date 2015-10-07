import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.io.*;

public class LoggerFilter implements javax.servlet.Filter {
    public static final String LOG_FILE = "ApplicationLogs.txt";
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            FileOutputStream streamOUT = new FileOutputStream(LOG_FILE, true);
            BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(streamOUT, "Cp1251"));
            Date date = new Date(System.currentTimeMillis());
            bf.write(date + "| ");
            bf.write(request.getRemoteAddr() + "; request uri: " + ((HttpServletRequest)request).getRequestURI());
//            if (null != request.getParameter(ApplicationServlet.ACT)) {
//                bf.write(request.getParameter(ApplicationServlet.ACT));
//            }
            bf.newLine();
            bf.close();
            streamOUT.close();
            request.setCharacterEncoding("cp1251");
            chain.doFilter(request, response);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (UnsupportedEncodingException r) {
            System.err.println("Unsupported encoding: " + r.getMessage());
        } catch (IOException ex) {
            System.out.println("Exception on doFilter" + ex.getMessage());
        } catch (ServletException e) {
            System.out.println("Exception on doFilter" + e.getMessage());
        }
    }


    public void destroy() {

    }
}