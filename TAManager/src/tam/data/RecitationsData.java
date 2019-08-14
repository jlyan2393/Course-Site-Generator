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
public class RecitationsData implements AppDataComponent {
    TAManagerApp app;
    ObservableList<Recitations> recitations;
    
    public RecitationsData(TAManagerApp initApp) {
        app = initApp;
        recitations = FXCollections.observableArrayList();
    }

    @Override
    public void resetData() {
        recitations.clear();
    }
    
    public ObservableList getRecitationsData(){
        return recitations;
    }
    
    public void addRecitation(String section, String instructor, String dayandtime, String location, String ta, String ta2){
        Recitations recitation = new Recitations(section, instructor, dayandtime, location, ta, ta2);
        recitations.add(recitation);
        Collections.sort(recitations);
    }
}
