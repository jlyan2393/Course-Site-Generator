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
public class TeamsData implements AppDataComponent {
    TAManagerApp app;
    ObservableList<Teams> teams;
    
    public TeamsData(TAManagerApp initApp) {
        app = initApp;
        teams = FXCollections.observableArrayList();
    }
    
    @Override
    public void resetData() {
        teams.clear();
    }
    
    public ObservableList getTeamsData(){
        return teams;
    }
    
    public void addTeam(String name, String color, String textColor, String link){
        Teams team = new Teams(name, color, textColor, link);
        teams.add(team);
        Collections.sort(teams);
    }
}
