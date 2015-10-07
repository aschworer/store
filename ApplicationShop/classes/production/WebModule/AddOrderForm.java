import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

/**
 * @struts.dynaform name="AddOrderForm"
 * type="AddOrderForm"
 */

public class AddOrderForm extends ValidatorForm {
    protected String clientID;
    protected String status;
    private LinkedList<FormProduct> productList = new LinkedList<FormProduct>();

    //
    public FormProduct[] getProducts() {
        FormProduct[] e = new FormProduct[productList.size()];
        return productList.toArray(e);
    }

    public FormProduct getProducts(int index) {
        if (productList.size() <= index) {
            setProducts(index, new FormProduct("0", "0"));
        }
        return productList.get(index);
    }

    public void setProducts(FormProduct[] goods) {
        this.productList.addAll(Arrays.asList(goods));
    }

    public void setProducts(int index, FormProduct goods) {
        if (productList.size() > index) {
            this.productList.set(index, goods);
        } else {
            for (int i = productList.size(); i < index; i++) {
                this.productList.addLast(new FormProduct("0", "0"));
            }
            this.productList.addLast(goods);
        }
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest request) {
        productList = new LinkedList<FormProduct>();
    }


    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest request) {
//        System.out.println("----------ordered: " + getProducts().length);
        ActionErrors errors = super.validate(actionMapping, request);
        if (errors == null) {
            errors = new ActionErrors();
        }
        if (errors.isEmpty()) {
            FormProduct[] products = getProducts();
//            ArrayList<FormProduct> newArray = new ArrayList<FormProduct>();
            int u = 0;
            int count = 0;
            for (int i = 0; i < products.length; i++) {
                try {
                    if (Integer.parseInt(products[i].getAmount()) > 0) {
                        count++;
//                    System.out.println("1");
//                    newArray.add(products[i]);
//                    u++;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
//            try {
            if (count <= 0) {
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionMessage("errors.order.validation"));
            }
//            else {
//                Object[] objArray = newArray.toArray();
//                FormProduct[] array = new FormProduct[objArray.length];
//                for (int t = 0; t < objArray.length; t++) {
//                    array[t] = (FormProduct) objArray[t];
//                }
//                setProducts(array);
//            }
//            } catch (NullPointerException e) {
//                errors.add(ActionErrors.GLOBAL_MESSAGE,
//                        new ActionMessage("errors.order.validation"));
//            }
//        }

//        if (getProducts().length <= 0) {
//            errors.add(ActionErrors.GLOBAL_MESSAGE,
//                    new ActionMessage("errors.order.validation"));
        }
        return errors;
    }
}
