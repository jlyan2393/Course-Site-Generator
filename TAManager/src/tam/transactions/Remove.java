/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transactions;

import java.util.ArrayList;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import jtps.jTPS_Transaction;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.file.TimeSlot;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Jase
 */
public class Remove implements jTPS_Transaction{
    private String name;
    private String email;
    private TAData data;
    private ArrayList<TimeSlot> timeList;
    private TAWorkspace workspace;
    
    public Remove(String name, String email, TAData data, ArrayList<TimeSlot> timeList, TAWorkspace workspace){
        this.name = name;
        this.email = email;
        this.data = data;
        this.timeList = timeList;
        this.workspace = workspace;
    }

    @Override
    public void doTransaction() {
        data.removeTA(name);
        for(Pane p: workspace.getOfficeHoursGridTACellPanes().values()){
            String cellKey = p.getId();
            StringProperty cellProp = data.getOfficeHours().get(cellKey);
            String cellText = cellProp.getValue();
            String[] TANames = cellText.split("\n");
            for(int i = 0; i < TANames.length; i++){
                if(TANames[i].equals(name)){
                    data.removeTAFromCell(cellProp, name);
                }
            }
        }
        
        TableView taTable = workspace.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            return;
        }
        TextField nameText = workspace.getNameTextField();
        TextField emailText = workspace.getEmailTextField();
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        String taName = ta.getName();
        String taEmail = ta.getEmail();
        selectedItem = taTable.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            nameText.setText("");
            emailText.setText("");
            return;
        }
        ta = (TeachingAssistant)selectedItem;
        taName = ta.getName();
        taEmail = ta.getEmail();
        nameText.setText(taName);
        emailText.setText(taEmail);
    }

    @Override
    public void undoTransaction() {
        data.addTA(name, email);
        for(TimeSlot ts: timeList){
            String day = ts.getDay();
            String time = ts.getTime();
            String taName = ts.getName();
            if(name.equals(taName)){
                data.addOfficeHoursReservation(day, time, taName);
            }
        }
        TableView taTable = workspace.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            return;
        }
        TextField nameText = workspace.getNameTextField();
        TextField emailText = workspace.getEmailTextField();
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        String taName = ta.getName();
        String taEmail = ta.getEmail();
        selectedItem = taTable.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            nameText.setText("");
            emailText.setText("");
            return;
        }
        ta = (TeachingAssistant)selectedItem;
        taName = ta.getName();
        taEmail = ta.getEmail();
        nameText.setText(taName);
        emailText.setText(taEmail);
    }
    
}
