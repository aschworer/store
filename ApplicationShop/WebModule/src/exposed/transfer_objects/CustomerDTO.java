package exposed.transfer_objects;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class CustomerDTO {
    private CustomerDTO advicer;
    private int personID;
    private String lastname;
    private String firstname;
    private String type;
    private String discription;
//    private String discriminator;

    CustomerDTO() {
    }


    public CustomerDTO(CustomerDTO advicer, String lastname, String firstname, String discription) {
        this.advicer = advicer;
        this.lastname = lastname;
        this.firstname = firstname;
        this.discription = discription;
    }

    public CustomerDTO(String lastname, String firstname, String discription) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.discription = discription;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public CustomerDTO(int personID) {
        this.personID = personID;
    }


    public CustomerDTO(CustomerDTO advicer, int personID, String lastname, String firstname, String type) {
        this.advicer = advicer;
        this.personID = personID;
        this.lastname = lastname;
        this.firstname = firstname;
        this.type = type;
    }

    public CustomerDTO(CustomerDTO advicer, String lastname, String firstname) {
        this.advicer = advicer;
        this.lastname = lastname;
        this.firstname = firstname;
//        this.discriminator = discriminator;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CustomerDTO(String lastname, String firstname) {
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public CustomerDTO getAdvicer() {
        return advicer;
    }

    public void setAdvicer(CustomerDTO advicer) {
        this.advicer = advicer;
    }


    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
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

//    public String getDiscriminator() {
//        return discriminator;
//    }
//
//    public void setDiscriminator(String discriminator) {
//        this.discriminator = discriminator;
//    }


    public String toString() {
        return lastname + " " + firstname;
    }

    public static String hash(String str) {

        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();

            byte[] hash = algorithm.digest(str.getBytes());
            hash = algorithm.digest(hash);

            StringBuffer buf = new StringBuffer();
            for (byte aHash : hash) {
                buf.append(Byte.toString(aHash));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            return str;
        }
    }

//     public int hashCode() {
//        int result;
//        result = personID;
//        result = 29 * result + Integer.parseInt(
//                this.hash(advicer.toString()));
//        return result;
//    }
}