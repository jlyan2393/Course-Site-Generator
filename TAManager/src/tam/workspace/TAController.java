package tam.workspace;

import static tam.TAManagerProp.*;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import jtps.jTPS;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.file.TimeSlot;
import tam.style.TAStyle;
import tam.transactions.Add;
import tam.transactions.ChangeHours;
import tam.transactions.Remove;
import tam.transactions.Toggle;
import tam.transactions.Update;

/**
 * This class provides responses to all workspace interactions, meaning
 * interactions with the application controls not including the file
 * toolbar.
 * 
 * @author Richard McKenna
 * @co-author Jason Lyan
 * @version 1.0
 */
public class TAController {
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    TAManagerApp app;
    private Pattern pattern;
    private Matcher matcher;
    public jTPS jTPS;
    private static final String EMAIL_PATTERN =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Constructor, note that the app must already be constructed.
     */
    public TAController(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        jTPS = new jTPS();
    }
    
    /**
     * This method responds to when the user requests to add
     * a new TA via the UI. Note that it must first do some
     * validation to make sure a unique name and email address
     * has been provided.
     */
    public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = (TAData)app.getDataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        else if (email.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));  
        }
        // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        else if (data.containsTAEmail(email)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));
        }
        else if (!matcher.matches()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_EMAIL_NOT_PROPER_TITLE), props.getProperty(TA_EMAIL_NOT_PROPER_MESSAGE));
        }
        // EVERYTHING IS FINE, ADD A NEW TA
        else {
            Add add = new Add(name, email, data);
            jTPS.addTransaction(add);
            // ADD THE NEW TA TO THE DATA
            data.addTA(name, email);
            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");
            app.getGUI().getAppFileController().markAsEdited(app.getGUI());
            
            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();
        }
    }
    
    public void handleUpdateTA(TextField name, TextField email) {
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        TAData data = (TAData)app.getDataComponent();
        String updatedName = name.getText();
        String updatedEmail = email.getText();
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(updatedEmail);
        String defaultTAName = ta.getName();
        String defaultTAEmail = ta.getEmail();
        //Set TA name/email to blank in order to check other TAs for duplicates
        ta.setName("");
        ta.setEmail("");
        if (updatedName.isEmpty()) {
            ta.setName(defaultTAName);
            ta.setEmail(defaultTAEmail);
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        else if (updatedEmail.isEmpty()) {
            ta.setName(defaultTAName);
            ta.setEmail(defaultTAEmail);
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));  
        }
        else if (data.containsTA(updatedName)) {
            ta.setName(defaultTAName);
            ta.setEmail(defaultTAEmail);
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        else if (data.containsTAEmail(updatedEmail)) {
            ta.setName(defaultTAName);
            ta.setEmail(defaultTAEmail);
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));
        }
        else if (!matcher.matches()){
            ta.setName(defaultTAName);
            ta.setEmail(defaultTAEmail);
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_EMAIL_NOT_PROPER_TITLE), props.getProperty(TA_EMAIL_NOT_PROPER_MESSAGE));
        }
        else{
            ta.setName(defaultTAName);
            ta.setEmail(defaultTAEmail);
            updateTAName(defaultTAName, updatedName);
            ta.setName(updatedName);
            ta.setEmail(updatedEmail);
            int index = data.getTeachingAssistants().indexOf(ta);
            data.getTeachingAssistants().set(index, ta);
            app.getGUI().getAppFileController().markAsEdited(app.getGUI());
        }
    }
    
    public void updateTAName(String oldName, String newName) {
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        String oldEmail = ta.getEmail();
        String newEmail = workspace.getEmailTextField().getText();
        if(selectedItem == null){
            return;
        }
        TAData data = (TAData)app.getDataComponent();
        int index = data.getTeachingAssistants().indexOf(ta);
        Update update = new Update(oldName, newName, oldEmail, newEmail, workspace, data, ta, index);
        jTPS.addTransaction(update);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }
    
    public int handleMilitaryConversion(ComboBox c){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        int time = 0;
        String convert = c.valueProperty().getValue().toString();
        String[] convertToInt = convert.split(":");
        if(convertToInt[0].equals("12") && convertToInt[1].equals("00AM")){
            time = 0;
        }
        else if(convertToInt[0].equals("12") && convertToInt[1].equals("00PM")){
            time = 12;
        }
        else if(convertToInt[1].equals("00PM")){
            time = 12 + parseInt(convertToInt[0]);
        }
        else{
            time = parseInt(convertToInt[0]);
        }
        
        return time;
    }
    
    public boolean handleOfficeHoursInput(){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        int startTime = handleMilitaryConversion(workspace.getComboBox1());
        int endTime = handleMilitaryConversion(workspace.getComboBox2());
        
        if(startTime > endTime){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(OFFICE_HOURS_INVALID_TITLE),props.getProperty(OFFICE_HOURS_INVALID_MESSAGE));
            return true;
        }
        
        return false;
    }
    
    public void fixComboBoxes(ComboBox c1, ComboBox c2){
        TAData data = (TAData)app.getDataComponent();
        int start = data.getStartHour();
        int end = data.getEndHour();
        String startTime = "";
        String endTime = "";
        
        if(start == 0){
            startTime = "12:00AM";
        }
        else if(start > 12){
            startTime = start - 12 + ":00PM";
        }
        else if(start == 12){
            startTime = start + ":00PM";
        }
        else{
            startTime = start + ":00AM";
        }
        
        if(end == 0){
            endTime = "12:00AM";
        }
        else if(end > 12){
            endTime = end - 12 + ":00PM";
        }
        else if(end == 12){
            endTime = end + ":00PM";
        }
        else{
            endTime = end + ":00AM";
        }
        
        Object[] c1List = c1.getItems().toArray();
        Object[] c2List = c2.getItems().toArray();
        
        for(int i=0; i < c1List.length; i++){
            if(startTime.equals((String)c1List[i])){
                c1.setValue(c1List[i]);
            }
            if(endTime.equals((String)c2List[i])){
                c2.setValue(c2List[i]);
            }
        }
    }
    
    public void handleHourChanges() {
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TAData data = (TAData)app.getDataComponent();
        int startTime = handleMilitaryConversion(workspace.getComboBox1());
        int endTime = handleMilitaryConversion(workspace.getComboBox2());
        ArrayList<TimeSlot> timeList = TimeSlot.buildOfficeHoursList(data);
        for(TimeSlot ts:timeList){
            int militaryAddition = 0;
            if(!ts.getTime().contains("12") && ts.getTime().contains("pm")){
                militaryAddition = 12;
            }
            int time = parseInt((ts.getTime().substring(0, ts.getTime().indexOf("_"))))+militaryAddition;
            if(time < startTime || time >= endTime){
                if(handleUserHoursChangeValidation()){
                    break;
                }
                else{
                    return;
                }
            }
        }
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
        ChangeHours changeHours = new ChangeHours(startTime, endTime, data.getStartHour(), data.getEndHour(), data, timeList, workspace, this);
        jTPS.addTransaction(changeHours);
    }
    
    public void handleDeleteTA() {
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            return;
        }
        boolean doDelete = app.getGUI().getAppFileController().promptToDelete();
        if(doDelete){
            TeachingAssistant ta = (TeachingAssistant)selectedItem;
            String taName = ta.getName();
            String taEmail = ta.getEmail();
            TAData data = (TAData)app.getDataComponent();
            ArrayList<TimeSlot> timeList = TimeSlot.buildOfficeHoursList(data);
            Remove remove = new Remove(taName, taEmail, data, timeList, workspace);
            jTPS.addTransaction(remove);
            app.getGUI().getAppFileController().markAsEdited(app.getGUI());
        }
    }
    
    public void handleGridHighlight(Pane p) {
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        String currentPaneKey = p.getId();
        String[] paneCoordinates = currentPaneKey.split("_");
        int colNum = parseInt(paneCoordinates[0]);
        int rowNum = parseInt(paneCoordinates[1]);
        int colNumCheck;
        int rowNumCheck;
        p.getStyleClass().set(0, TAStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_SELECTED);
        for(Pane pCheck: workspace.getOfficeHoursGridTACellPanes().values()){
            String cellKey = pCheck.getId();
            String[] checkedPane = cellKey.split("_");
            colNumCheck = parseInt(checkedPane[0]);
            rowNumCheck = parseInt(checkedPane[1]); 
            if(colNum == colNumCheck && rowNumCheck < rowNum ||
              rowNum == rowNumCheck && colNumCheck < colNum){
                pCheck.getStyleClass().set(0, TAStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_HIGHLIGHT);
            }      
        }   
    }
    
    public void handleGridHighlightFix(Pane p) {
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        String currentPaneKey = p.getId();
        String[] paneCoordinates = currentPaneKey.split("_");
        int colNum = parseInt(paneCoordinates[0]);
        int rowNum = parseInt(paneCoordinates[1]);
        int colNumCheck;
        int rowNumCheck;
        p.getStyleClass().set(0, TAStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        for(Pane pCheck: workspace.getOfficeHoursGridTACellPanes().values()){
            String cellKey = pCheck.getId();
            String[] checkedPane = cellKey.split("_");
            colNumCheck = parseInt(checkedPane[0]);
            rowNumCheck = parseInt(checkedPane[1]); 
            if(colNum == colNumCheck && rowNumCheck < rowNum ||
              rowNum == rowNumCheck && colNumCheck < colNum){
                pCheck.getStyleClass().set(0,TAStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
            }      
        }  
    }
        
    
    /**
     * This function provides a response for when the user clicks
     * on the office hours grid to add or remove a TA to a time slot.
     * 
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            return;
        }
        
        // GET THE TA
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        String taName = ta.getName();
        TAData data = (TAData)app.getDataComponent();
        String cellKey = pane.getId();
        
        // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
        //data.toggleTAOfficeHours(cellKey, taName);
        Toggle toggle = new Toggle(cellKey, taName, data);
        jTPS.addTransaction(toggle);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }

    public void resetjTPS(){
        if(app.getGUI().getAppFileController().isSaved()){
            jTPS = new jTPS();
        }
    }
    
    public boolean handleUserHoursChangeValidation() {
        AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        yesNoDialog.show(props.getProperty(OFFICE_HOURS_CHANGE_CONFIRMATION_TITLE),props.getProperty(OFFICE_HOURS_CHANGE_CONFIRMATION_MESSAGE));
        String selection = yesNoDialog.getSelection();
        
        if(selection == null || selection.equals(AppYesNoCancelDialogSingleton.CANCEL) || selection.equals(AppYesNoCancelDialogSingleton.NO)){
            return false;
        }
        return true;
    }
}