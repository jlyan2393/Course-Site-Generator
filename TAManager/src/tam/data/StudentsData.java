/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import djf.components.AppDataComponent;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tam.TAManagerApp;

/**
 *
 * @author Jason Lyan
 */
public class StudentsData implements AppDataComponent {
    TAManagerApp app;
    ObservableList<Students> students;
    
    public StudentsData(TAManagerApp initApp) {
        app = initApp;
        students = FXCollections.observableArrayList();
    }
    
    @Override
    public void resetData() {
        students.clear();
    }
    
    public ObservableList getStudentsData(){
        return students;
    }
    
    public void addStudent(String firstName, String lastName, String team, String role){
        Students student = new Students(firstName, lastName, team, role);
        students.add(student);
        Collections.sort(students);
    }
}
