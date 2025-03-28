package Helper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "User")  // This must match the XML root element name
@XmlAccessorType(XmlAccessType.FIELD)  // Use fields directly for marshalling/unmarshalling
public class User {

    @XmlElement  // This annotation tells JAXB to include this field when marshalling/unmarshalling
    private String username;

    @XmlElement
    private String password;

    // Static field - don't annotate static fields with @XmlElement
    private static User currentUser;

    // No-argument constructor is required for JAXB marshalling/unmarshalling
    public User() {}

    // Constructor for creating a User object with username and password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters (since we're using @XmlAccessorType(XmlAccessType.FIELD), JAXB will automatically use fields, but you can keep these methods for internal use if needed)
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    // Static methods should not be annotated with JAXB annotations
    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
