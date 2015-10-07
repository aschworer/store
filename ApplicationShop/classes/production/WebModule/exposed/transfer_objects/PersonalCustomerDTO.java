package exposed.transfer_objects;


public class PersonalCustomerDTO extends CustomerDTO{

    public PersonalCustomerDTO() {
//        setDiscriminator("ps");
    }

    public PersonalCustomerDTO(CustomerDTO advicer, String lastname, String firstname) {
        super(advicer, lastname, firstname);
    }

    public PersonalCustomerDTO(String lastname, String firstname) {
        super(lastname, firstname);
    }


    public PersonalCustomerDTO(CustomerDTO advicer, String lastname, String firstname, String discription) {
        super(advicer, lastname, firstname, discription);
    }


    public PersonalCustomerDTO(String lastname, String firstname, String discription) {
        super(lastname, firstname, discription);
    }
}
