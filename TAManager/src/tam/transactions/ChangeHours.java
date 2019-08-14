/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transactions;

import java.util.ArrayList;
import jtps.jTPS_Transaction;
import tam.data.TAData;
import tam.file.TimeSlot;
import tam.workspace.TAController;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Jase
 */
public class ChangeHours implements jTPS_Transaction{
    private int startTime;
    private int endTime;
    private int originalEndTime;
    private int originalStartTime;
    private TAData data;
    private ArrayList<TimeSlot> timeList;
    private TAWorkspace workspace;
    private TAController controller;
    
    
    public ChangeHours(int startTime, int endTime, int originalStartTime, int originalEndTime, TAData data, ArrayList timeList, TAWorkspace workspace, TAController controller){
        this.startTime = startTime;
        this.endTime = endTime;
        this.originalStartTime = originalStartTime;
        this.originalEndTime = originalEndTime;
        this.data = data;
        this.timeList = timeList;
        this.workspace = workspace;
        this.controller = controller;
    }
    
    @Override
    public void doTransaction() {
        workspace.resetWorkspace();
        data.initHours("" + startTime, "" + endTime);
        workspace.reloadWorkspace(data);
        for(TimeSlot ts:timeList){
            int militaryAddition = 0;
            if(!ts.getTime().contains("12") && ts.getTime().contains("pm")){
                militaryAddition = 12;
            }
            
            int time = Integer.parseInt((ts.getTime().substring(0, ts.getTime().indexOf("_"))))+militaryAddition;
            if(time >= startTime && time < endTime){
                data.addOfficeHoursReservation(ts.getDay(), ts.getTime(), ts.getName());
            }
        }
    }

    @Override
    public void undoTransaction() {
        workspace.resetWorkspace();
        data.initHours("" + originalStartTime, "" + originalEndTime);
        workspace.reloadWorkspace(data);
        for(TimeSlot ts:timeList){
            int militaryAddition = 0;
            if(!ts.getTime().contains("12") && ts.getTime().contains("pm")){
                militaryAddition = 12;
            }
            
            int time = Integer.parseInt((ts.getTime().substring(0, ts.getTime().indexOf("_"))))+militaryAddition;
            if(time >= originalStartTime && time < originalEndTime){
                data.addOfficeHoursReservation(ts.getDay(), ts.getTime(), ts.getName());
            }
        }
    }
    
}
