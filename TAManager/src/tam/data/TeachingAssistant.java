package tam.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class represents a Teaching Assistant for the table of TAs.
 * 
 * @author Richard McKenna
 * @co-author Jason Lyan
 */
public class TeachingAssistant<E extends Comparable<E>> implements Comparable<E>  {
    // THE TABLE WILL STORE TA NAMES AND EMAILS
    private final BooleanProperty undergrad;
    private final StringProperty name;
    private final StringProperty email;

    /**
     * Constructor initializes the TA name
     */
    public TeachingAssistant(String initName, String initEmail) {
        undergrad = new SimpleBooleanProperty(false);
        name = new SimpleStringProperty(initName);
        email = new SimpleStringProperty(initEmail);
    }

    // ACCESSORS AND MUTATORS FOR THE PROPERTIES

    public String getName() {
        return name.get();
    }

    public void setName(String initName) {
        name.set(initName);
    }

    public String getEmail() {
        return email.get();
    }
    
    public void setEmail(String initEmail) {
        email.set(initEmail);
    }
    
    @Override
    public int compareTo(E otherTA) {
        return getName().compareTo(((TeachingAssistant)otherTA).getName());
    }
    
    @Override
    public String toString() {
        return name.getValue();
    }

    public BooleanProperty getUndergrad() {
        return undergrad;
    }
    
    public boolean getIsUndergrad() {
        return undergrad.get();
    }

    public void setUndergrad(boolean undergrad) {
        this.undergrad.set(undergrad);
    }
}