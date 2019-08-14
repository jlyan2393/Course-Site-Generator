/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jason Lyan
 */
public class Students<E extends Comparable<E>> implements Comparable<E>  {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty team;
    private final StringProperty role;
    
    public Students(String firstName, String lastName, String team, String role){
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.team = new SimpleStringProperty(team);
        this.role = new SimpleStringProperty(role);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getTeam() {
        return team.get();
    }

    public void setTeam(String team) {
        this.team.set(team);
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    @Override
    public int compareTo(E otherStudent) {
        return getFirstName().compareTo(((Students)otherStudent).getFirstName());
    }
}
