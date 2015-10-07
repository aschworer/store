package new_tag_lib;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class PrintTableTagSupport extends SimpleTagSupport {
    private ArrayList sourse;
    private String fields;
    private String headers = "";

    public void doTag() throws JspException, IOException {
        String tableView = " border='5' cellspacing='5' cellpadding='5'";
        String headColor = "grey";

        StringBuffer out = new StringBuffer();
        out.append("<table" + tableView + " width='100%'>");
        if (!"".equals(headers)) {
            out.append("<tr bgcolor ='" + headColor + "'>");
            StringTokenizer tokenizer = new StringTokenizer(headers, ",");
            while (tokenizer.hasMoreTokens()) {
                out.append("<td><b>").
                        append(tokenizer.nextToken())
                        .append("</b></td >");

            }
            out.append("</tr>");

        }
        Iterator cbIterator = sourse.iterator();
        while (cbIterator.hasNext()) {
            out.append("<tr>");
            Object o = cbIterator.next();

            Method[] methods = o.getClass().getMethods();
//            System.out.println("methods: " + methods.length);
            StringTokenizer tokenizer = new StringTokenizer(fields, ",");
            while (tokenizer.hasMoreTokens()) {
                String field = tokenizer.nextToken();
                for (int i = 0; i < methods.length; i++) {
//                    System.out.println("Method: " + methods[i].getName());
                    if (methods[i].getName().equals
                            ("get" + Character.toUpperCase(field.charAt(0)) + field.substring(1, field.length()))) {
//                        System.out.println("same to: " + "get" + Character.toUpperCase(field.charAt(0)) + field.substring(1, field.length()));
                        try {
                            out.append("<td>").append(methods[i].invoke(o)).append("</td>");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            out.append("</tr>");
        }
        out.append("</table>");
        getJspContext().getOut().write(out.toString());
    }

    public ArrayList getSourse() {
        return sourse;
    }

    public void setSourse(ArrayList sourse) {
        this.sourse = sourse;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }


    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }
}
