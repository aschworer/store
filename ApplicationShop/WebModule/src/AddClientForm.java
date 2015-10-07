import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @struts.dynaform name="AddClientForm"
 * type="AddClientForm"
 */

public class AddClientForm extends ValidatorForm {
    /**
     * @struts.form-field form-name="AddClientForm"
     */

    protected String lastname;
    protected String firstname;
    protected String advicer;
    protected String discription;
    protected String company;


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getAdvicer() {
        return advicer;
    }

    public void setAdvicer(String advicer) {
        this.advicer = advicer;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest request) {
        super.reset(actionMapping, request);    //To change body of overridden methods use File | Settings | File Templates.
        this.setFirstname("Firstname");
        this.setLastname("Lastname");
        //this.setAdvicer("lastname");
    }
}
