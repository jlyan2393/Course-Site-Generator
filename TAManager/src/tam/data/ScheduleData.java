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
public class ScheduleData implements AppDataComponent {
    TAManagerApp app;
    ObservableList<Schedule> schedules;
    
    public ScheduleData(TAManagerApp initApp) {
        app = initApp;
        schedules = FXCollections.observableArrayList();
    }
    
    @Override
    public void resetData() {
        schedules.clear();
    }
    
    public ObservableList getScheduleData(){
        return schedules;
    }
    
    public void addSchedule(String type, String date, String title, String topic){
        Schedule schedule = new Schedule(type, date, title, topic);
        schedules.add(schedule);
        Collections.sort(schedules);
    }
}
