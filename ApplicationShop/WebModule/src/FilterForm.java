import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @struts.dynaform name="FilterForm"
 * type="FilterForm"
 */

public class FilterForm extends ValidatorForm {
    /**
     * @struts.form-field form-name="FilterForm"
     */

    protected String date;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
