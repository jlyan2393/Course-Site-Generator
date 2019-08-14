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
public class Recitations<E extends Comparable<E>> implements Comparable<E>  {
    private final StringProperty section;
    private final StringProperty instructor;
    private final StringProperty dayandtime;
    private final StringProperty location;
    private final StringProperty ta;
    private final StringProperty ta2;
    
    public Recitations(String section, String instructor, String dayandtime, String location, String ta, String ta2){
        this.section = new SimpleStringProperty(section);
        this.instructor = new SimpleStringProperty(instructor);
        this.dayandtime = new SimpleStringProperty(dayandtime);
        this.location = new SimpleStringProperty(location);
        this.ta = new SimpleStringProperty(ta);
        this.ta2 = new SimpleStringProperty(ta2);
    }

    public String getSection() {
        return section.get();
    }

    public void setSection(String section) {
        this.section.set(section);
    }

    public String getInstructor() {
        return instructor.get();
    }

    public void setInstructor(String instructor) {
        this.instructor.set(instructor);
    }

    public String getDayandtime() {
        return dayandtime.get();
    }

    public void setDayandtime(String dayandtime) {
        this.dayandtime.set(dayandtime);
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getTa() {
        return ta.get();
    }

    public void setTa(String ta) {
        this.ta.set(ta);
    }

    public String getTa2() {
        return ta2.get();
    }

    public void setTa2(String ta2) {
        this.ta2.set(ta2);
    }

    @Override
    public int compareTo(E otherRecitation) {
        return getSection().compareTo(((Recitations)otherRecitation).getSection());
    }
}
