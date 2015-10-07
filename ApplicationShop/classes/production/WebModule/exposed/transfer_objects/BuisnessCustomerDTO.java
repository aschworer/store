package exposed.transfer_objects;

public class BuisnessCustomerDTO extends CustomerDTO{
    private String companyName;

    public BuisnessCustomerDTO() {
//        setDiscriminator("bs");
    }

    public BuisnessCustomerDTO(String lastname, String firstname) {
        super(lastname, firstname);
    }

    public BuisnessCustomerDTO(CustomerDTO advicer, String lastname, String firstname) {
        super(advicer, lastname, firstname);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BuisnessCustomerDTO(CustomerDTO advicer, String lastname, String firstname, String discription) {
        super(advicer, lastname, firstname, discription);
    }

    public BuisnessCustomerDTO(String lastname, String firstname, String discription) {
        super(lastname, firstname, discription);
    }

}
