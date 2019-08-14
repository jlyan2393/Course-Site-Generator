/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transactions;

import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;
import jtps.jTPS_Transaction;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Jase
 */
public class Update implements jTPS_Transaction{
    private String oldName;
    private String newName;
    private String oldEmail;
    private String newEmail;
    private TAWorkspace workspace;
    private TAData data;
    private TeachingAssistant ta;
    private int index;
    
    public Update(String oldName, String newName, String oldEmail, String newEmail, TAWorkspace workspace, TAData data, TeachingAssistant ta, int index){
        this.oldName = oldName;
        this.newName = newName;
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
        this.workspace = workspace;
        this.data = data;
        this.ta = ta;
        this.index = index;
    }

    @Override
    public void doTransaction() {
        for(Pane p: workspace.getOfficeHoursGridTACellPanes().values()){
            String newCellText = "";
            String cellKey = p.getId();
            StringProperty cellProp = data.getOfficeHours().get(cellKey);
            String cellText = cellProp.getValue();
            String[] TANames = cellText.split("\n");
            for(int i = 0; i < TANames.length; i++){
                if(TANames[i].equals(oldName)){
                    TANames[i] = newName;
                }
                if(i == TANames.length - 1){
                    newCellText += TANames[i];
                    cellProp.setValue(newCellText);
                }
                else{
                    newCellText += TANames[i] + "\n";
                }
            }
        }
        ta.setName(newName);
        ta.setEmail(newEmail);
        int undeletedIndex = data.getTeachingAssistants().indexOf(ta);
        if(undeletedIndex >= 0){
            data.getTeachingAssistants().set(undeletedIndex, ta);
        }
        else{
            data.getTeachingAssistants().set(index, ta);
        }
        
        Object selectedItem = workspace.getTATable().getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            if(!ta.equals(selectedItem)){
                TeachingAssistant ta = (TeachingAssistant)selectedItem;
                workspace.getNameTextField().setText(ta.getName());
                workspace.getEmailTextField().setText(ta.getEmail());
            }
            else{
                workspace.getNameTextField().setText(newName);
                workspace.getEmailTextField().setText(newEmail);
            }
        }
    }

    @Override
    public void undoTransaction() {
        for(Pane p: workspace.getOfficeHoursGridTACellPanes().values()){
            String newCellText = "";
            String cellKey = p.getId();
            StringProperty cellProp = data.getOfficeHours().get(cellKey);
            String cellText = cellProp.getValue();
            String[] TANames = cellText.split("\n");
            for(int i = 0; i < TANames.length; i++){
                if(TANames[i].equals(newName)){
                    TANames[i] = oldName;
                }
                if(i == TANames.length - 1){
                    newCellText += TANames[i];
                    cellProp.setValue(newCellText);
                }
                else{
                    newCellText += TANames[i] + "\n";
                }
            }
        }
        ta.setName(oldName);
        ta.setEmail(oldEmail);
        int undeletedIndex = data.getTeachingAssistants().indexOf(ta);
        if(undeletedIndex >= 0){
            data.getTeachingAssistants().set(undeletedIndex, ta);
        }
        else{
            data.getTeachingAssistants().set(index, ta);
        }
            
        Object selectedItem = workspace.getTATable().getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            if(!ta.equals(selectedItem)){
                TeachingAssistant ta = (TeachingAssistant)selectedItem;
                workspace.getNameTextField().setText(ta.getName());
                workspace.getEmailTextField().setText(ta.getEmail());
            }
            else{
                workspace.getNameTextField().setText(oldName);
                workspace.getEmailTextField().setText(oldEmail);
            }
        }
    }
}
