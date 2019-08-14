package tam.workspace;

import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import tam.TAManagerApp;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import properties_manager.PropertiesManager;
import tam.TAManagerProp;
import tam.data.CourseData;
import tam.data.Recitations;
import tam.data.RecitationsData;
import tam.data.Schedule;
import tam.data.ScheduleData;
import tam.data.SitePages;
import tam.data.Students;
import tam.data.StudentsData;
import tam.style.TAStyle;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.data.Teams;
import tam.data.TeamsData;

/**
 * This class serves as the workspace component for the TA Manager
 * application. It provides all the user interface controls in 
 * the workspace area.
 * 
 * @author Richard McKenna
 * @co-author Jason Lyan
 */
public class TAWorkspace extends AppWorkspaceComponent {
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    TAManagerApp app;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    TAController controller;

    // PANES AND PROPERTIES FOR COURSE DETAILS
    VBox courseDetailsPane;
    VBox courseOrderingPane;
    VBox courseOrderingPane2;
    VBox courseOrderingPane3;
    HBox courseInfoHeaderPane;
    GridPane courseInfoPane;
    VBox siteTemplatePane;
    HBox siteTemplateHeaderPane;
    HBox pageStyleHeaderPane;
    GridPane pageStylePane;
    
    Label cdExportDirectory;
    Label cdTemplateDirectory;
    
    ImageView cdBannerSchoolImage;
    ImageView cdLeftFooterImage;
    ImageView cdRightFooterImage;
    
    TextField cdTitleTextField;
    TextField cdInstructorNameTextField;
    TextField cdInstructorHomeTextField;
    
    ComboBox cdSubjectComboBox;
    ComboBox cdNumberComboBox;
    ComboBox cdSemesterComboBox;
    ComboBox cdYearComboBox;
    ComboBox cdStylesheetComboBox;
    
    Button cdExportDirButton;
    Button cdTemplateDirectoryButton;
    Button cdBannerSchoolImageButton;
    Button cdLeftFooterImageButton;
    Button cdRightFooterImageButton;
    
    TableView<SitePages> cdTable;
    TableColumn cdUseColumn;
    TableColumn<SitePages, String> cdNavbarTitleColumn;
    TableColumn<SitePages, String> cdFileNameColumn;
    TableColumn<SitePages, String> cdScriptColumn;
    
    // PANES AND PROPERTIES FOR RECITATION DATA
    VBox recitationDataPane;
    VBox recitationOrderingPane;
    HBox recitationsPane;
    VBox recitationTablePane;
    GridPane recitationAddOrEditPane;
    
    ComboBox recSupervisingTAComboBox;
    ComboBox recSupervisingTA2ComboBox;
    
    Button deleteRecitationButton;
    Button recAddOrUpdateButton;
    Button recClearButton;
    
    TableView<Recitations> recTable;
    TableColumn<Recitations, String> recSectionColumn;
    TableColumn<Recitations, String> recInstructorColumn;
    TableColumn<Recitations, String> recDayOrTimeColumn;
    TableColumn<Recitations, String> recLocationColumn;
    TableColumn<Recitations, String> recTAColumn;
    TableColumn<Recitations, String> recTA2Column;
    
    TextField recSectionTextField;
    TextField recInstructorTextField;
    TextField recDayOrTimeTextField;
    TextField recLocationTextField;
    
    // PANES AND PROPERTIES FOR SCHEDULE DATA
    VBox scheduleDataPane;
    VBox scheduleOrderingPane;
    HBox scheduleHeaderPane;
    GridPane calendarBoundariesPane;
    HBox scheduleItemsPane;
    VBox scheduleItemsTablePane;
    GridPane scheduleAddOrEditPane;
    
    TableView<Schedule> schedTable;
    TableColumn<Schedule, String> schedTypeColumn;
    TableColumn<Schedule, String> schedDateColumn;
    TableColumn<Schedule, String> schedTitleColumn;
    TableColumn<Schedule, String> schedTopicColumn;
    
    ComboBox schedTypeComboBox;
    
    DatePicker startingDate;
    DatePicker endingDate;
    DatePicker scheduleDate;
    
    TextField schedTimeTextField;
    TextField schedTitleTextField;
    TextField schedTopicTextField;
    TextField schedLinkTextField;
    TextField schedCriteriaTextField;
    
    Button deleteScheduleButton;
    Button schedAddOrUpdateButton;
    Button schedClearButton;
    
    // PANES AND PROPERTIES FOR PROJECT DATA
    VBox projectDataPane;
    VBox projectOrderingPane;
    VBox projectOrderingPane2;
    HBox projDataHeaderPane;
    HBox projTeamsPane;
    VBox projTeamsTablePane;
    GridPane projTeamsAddOrEditPane;
    HBox projStudentsPane;
    VBox projStudentsTablePane;
    GridPane projStudentsAddOrEditPane;
    
    TableView<Teams> teamsTable;
    TableColumn<Teams, String> projTeamNameColumn;
    TableColumn<Teams, String> projColorColumn;
    TableColumn<Teams, String> projTextColorColumn;
    TableColumn<Teams, String> projLinkColumn;
    
    TableView<Students> studentsTable;
    TableColumn<Students, String> projFirstNameColumn;
    TableColumn<Students, String> projLastNameColumn;
    TableColumn<Students, String> projTeamColumn;
    TableColumn<Students, String> projRoleColumn;
    
    ColorPicker projTeamColor;
    ColorPicker projTeamTextColor;
    
    ComboBox projTeamComboBox;
    
    TextField projTeamNameTextField;
    TextField projLinkTextField;
    TextField projFirstNameTextField;
    TextField projLastNameTextField;
    TextField projRoleTextField;
    
    Button deleteTeamsButton;
    Button deleteStudentsButton;
    Button projAddOrEditTeamsButton;
    Button projClearTeamsButton;
    Button projAddOrEditStudentsButton;
    Button projClearStudentsButton;
    
    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    
    // FOR THE HEADER ON THE LEFT
    HBox tasHeaderBox;
    Label tasHeaderLabel;
    
    // FOR THE TA TABLE
    TableView<TeachingAssistant> taTable;
    TableColumn undergradColumn;
    TableColumn<TeachingAssistant, String> nameColumn;
    TableColumn<TeachingAssistant, String> emailColumn;

    // THE TA INPUT
    HBox addBox;
    TextField nameTextField;
    TextField emailTextField;
    Button addButton;
    Button clearButton;

    // THE HEADER ON THE RIGHT
    HBox officeHoursHeaderBox;
    Label officeHoursHeaderLabel;
    
    Button officeHoursButton;
    ComboBox comboBox;
    ComboBox comboBox2;
    
    // THE OFFICE HOURS GRID
    GridPane officeHoursGridPane;
    HashMap<String, Pane> officeHoursGridTimeHeaderPanes;
    HashMap<String, Label> officeHoursGridTimeHeaderLabels;
    HashMap<String, Pane> officeHoursGridDayHeaderPanes;
    HashMap<String, Label> officeHoursGridDayHeaderLabels;
    HashMap<String, Pane> officeHoursGridTimeCellPanes;
    HashMap<String, Label> officeHoursGridTimeCellLabels;
    HashMap<String, Pane> officeHoursGridTACellPanes;
    HashMap<String, Label> officeHoursGridTACellLabels;

    VBox comboBoxesPane;
    
    /**
     * The contstructor initializes the user interface, except for
     * the full office hours grid, since it doesn't yet know what
     * the hours will be until a file is loaded or a new one is created.
     */
    public TAWorkspace(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // *******THIS IS THE INITIALIZATION OF ALL COURSE DETAILS COMPONENTS*******
        courseDetailsPane = new VBox();
        
        courseInfoHeaderPane = new HBox();
        courseInfoHeaderPane.getChildren().add(new Label(props.getProperty(TAManagerProp.COURSE_INFO_TEXT.toString())));
        
        courseInfoPane = new GridPane();
        courseInfoPane.setVgap(10);
        courseInfoPane.setHgap(50);
        courseInfoPane.add(new Label(props.getProperty(TAManagerProp.CD_SUBJECT_TEXT.toString())), 0, 0);
        ObservableList<String> subjectList = 
        FXCollections.observableArrayList(
            "CSE"
        );
        cdSubjectComboBox = new ComboBox(subjectList);
        cdSubjectComboBox.setValue("CSE");
        courseInfoPane.add(cdSubjectComboBox, 1, 0);
        courseInfoPane.add(new Label(props.getProperty(TAManagerProp.CD_NUMBER_TEXT.toString())), 2, 0);
        ObservableList<String> numberList = 
        FXCollections.observableArrayList(
            "219"
        );
        cdNumberComboBox = new ComboBox(numberList);
        cdNumberComboBox.setValue("219");
        courseInfoPane.add(cdNumberComboBox, 3, 0);
        courseInfoPane.add(new Label(props.getProperty(TAManagerProp.CD_SEMESTER_TEXT.toString())), 0, 1);
        ObservableList<String> semesterList = 
        FXCollections.observableArrayList(
            "Fall"
        );
        cdSemesterComboBox = new ComboBox(semesterList);
        cdSemesterComboBox.setValue("Fall");
        courseInfoPane.add(cdSemesterComboBox, 1, 1);
        courseInfoPane.add(new Label(props.getProperty(TAManagerProp.CD_YEAR_TEXT.toString())), 2, 1);
        ObservableList<String> yearList = 
        FXCollections.observableArrayList(
            "2017"
        );
        cdYearComboBox = new ComboBox(yearList);
        cdYearComboBox.setValue("2017");
        courseInfoPane.add(cdYearComboBox, 3, 1);
        courseInfoPane.add(new Label(props.getProperty(TAManagerProp.CD_TITLE_TEXT.toString())), 0, 2);
        cdTitleTextField = new TextField();
        cdTitleTextField.setPromptText("Computer Science III");
        courseInfoPane.add(cdTitleTextField, 1, 2, 3, 1);
        courseInfoPane.add(new Label(props.getProperty(TAManagerProp.CD_INSTRUCTOR_NAME_TEXT.toString())), 0, 3);
        cdInstructorNameTextField = new TextField();
        cdInstructorNameTextField.setPromptText("Richard McKenna");
        courseInfoPane.add(cdInstructorNameTextField, 1, 3, 3, 1);
        courseInfoPane.add(new Label(props.getProperty(TAManagerProp.CD_INSTRUCTOR_HOME_TEXT.toString())), 0, 4);
        cdInstructorHomeTextField = new TextField();
        cdInstructorHomeTextField.setPromptText("http://www.example.com");
        courseInfoPane.add(cdInstructorHomeTextField, 1, 4, 3, 1);
        courseInfoPane.add(new Label(props.getProperty(TAManagerProp.CD_EXPORT_DIR_TEXT.toString())), 0, 5);
        cdExportDirectory = new Label("..\\..\\..\\Courses\\CSE219\\Summer2017\\public");
        courseInfoPane.add(cdExportDirectory, 1, 5, 2, 1);
        cdExportDirButton = new Button(props.getProperty(TAManagerProp.CD_CHANGE_BUTTON_TEXT.toString()));
        courseInfoPane.add(cdExportDirButton, 3, 5);
        
        siteTemplateHeaderPane = new HBox();
        siteTemplateHeaderPane.getChildren().add(new Label(props.getProperty(TAManagerProp.CD_SITE_TEMPLATE_TEXT.toString())));
        
        siteTemplatePane = new VBox();
        siteTemplatePane.getChildren().add(new Label(props.getProperty(TAManagerProp.CD_SITE_TEMPLATE_DESCRIPTION_TEXT.toString())));
        cdTemplateDirectory = new Label(".\\templates\\CSE219");
        siteTemplatePane.getChildren().add(cdTemplateDirectory);
        cdTemplateDirectoryButton = new Button(props.getProperty(TAManagerProp.CD_TEMPLATE_DIRECTORY_BUTTON_TEXT.toString()));
        siteTemplatePane.getChildren().add(cdTemplateDirectoryButton);
        siteTemplatePane.getChildren().add(new Label(props.getProperty(TAManagerProp.CD_SITE_PAGES_TEXT.toString())));
        cdTable = new TableView();
        cdTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        CourseData cdata = (CourseData) app.getCourseDataComponent();
        ObservableList<SitePages> cdTableData = cdata.getCourseData();
        cdTable.setItems(cdTableData);
        cdUseColumn = new TableColumn(props.getProperty(TAManagerProp.CD_USE_COLUMN_TEXT.toString()));
        cdNavbarTitleColumn = new TableColumn(props.getProperty(TAManagerProp.CD_NAVBAR_TITLE_COLUMN_TEXT.toString()));
        cdFileNameColumn = new TableColumn(props.getProperty(TAManagerProp.CD_FILE_NAME_COLUMN_TEXT.toString()));
        cdScriptColumn = new TableColumn(props.getProperty(TAManagerProp.CD_SCRIPT_COLUMN_TEXT.toString()));
        cdUseColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<SitePages, Boolean>, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<SitePages, Boolean> p) {
                       return p.getValue().getUsePage();
                    }           
                }
        );
        cdUseColumn.setCellFactory(column -> new CheckBoxTableCell());
        cdUseColumn.setEditable(true);
        cdNavbarTitleColumn.setCellValueFactory(
                new PropertyValueFactory<SitePages, String>("navbarTitle")
        );
        cdFileNameColumn.setCellValueFactory(
                new PropertyValueFactory<SitePages, String>("fileName")
        );
        cdScriptColumn.setCellValueFactory(
                new PropertyValueFactory<SitePages, String>("script")
        );
        cdTable.getColumns().add(cdUseColumn);
        cdTable.getColumns().add(cdNavbarTitleColumn);
        cdTable.getColumns().add(cdFileNameColumn);
        cdTable.getColumns().add(cdScriptColumn);
        cdTable.setEditable(true);
        siteTemplatePane.getChildren().add(cdTable);
        
        pageStyleHeaderPane = new HBox();
        pageStyleHeaderPane.getChildren().add(new Label(props.getProperty(TAManagerProp.CD_PAGE_STYLE_TEXT.toString())));
        
        pageStylePane = new GridPane();
        pageStylePane.setVgap(10);
        pageStylePane.setHgap(50);
        pageStylePane.add(new Label(props.getProperty(TAManagerProp.CD_BANNER_SCHOOL_IMAGE_TEXT.toString())), 0, 0);
        cdBannerSchoolImage = new ImageView();
        pageStylePane.add(cdBannerSchoolImage, 1, 0);
        cdBannerSchoolImageButton = new Button(props.getProperty(TAManagerProp.CD_CHANGE_BUTTON_TEXT.toString()));
        pageStylePane.add(cdBannerSchoolImageButton, 2, 0);
        pageStylePane.add(new Label(props.getProperty(TAManagerProp.CD_LEFT_FOOTER_IMAGE_TEXT.toString())), 0, 1);
        cdLeftFooterImage = new ImageView();
        pageStylePane.add(cdLeftFooterImage, 1, 1);
        cdLeftFooterImageButton = new Button(props.getProperty(TAManagerProp.CD_CHANGE_BUTTON_TEXT.toString()));
        pageStylePane.add(cdLeftFooterImageButton, 2, 1);
        pageStylePane.add(new Label(props.getProperty(TAManagerProp.CD_RIGHT_FOOTER_IMAGE_TEXT.toString())), 0, 2);
        cdRightFooterImage = new ImageView();
        pageStylePane.add(cdRightFooterImage, 1, 2);
        cdRightFooterImageButton = new Button(props.getProperty(TAManagerProp.CD_CHANGE_BUTTON_TEXT.toString()));
        pageStylePane.add(cdRightFooterImageButton, 2, 2);
        pageStylePane.add(new Label(props.getProperty(TAManagerProp.CD_STYLESHEET_TEXT.toString())), 0, 3);
        ObservableList<String> stylesheetList = 
        FXCollections.observableArrayList(
            "sea_wolf.css"
        );
        cdStylesheetComboBox = new ComboBox(stylesheetList);
        cdStylesheetComboBox.setValue("sea_wolf.css");
        pageStylePane.add(cdStylesheetComboBox, 1, 3);
        pageStylePane.add(new Label(props.getProperty(TAManagerProp.CD_PAGE_STYLE_NOTE_TEXT.toString())), 0, 4);
        
        courseOrderingPane = new VBox();
        courseOrderingPane.getChildren().add(courseInfoHeaderPane);
        courseOrderingPane.getChildren().add(courseInfoPane);
        courseOrderingPane2 = new VBox();
        courseOrderingPane2.getChildren().add(siteTemplateHeaderPane);
        courseOrderingPane2.getChildren().add(siteTemplatePane);
        courseOrderingPane3 = new VBox();
        courseOrderingPane3.getChildren().add(pageStyleHeaderPane);
        courseOrderingPane3.getChildren().add(pageStylePane);
        
        
        courseDetailsPane.getChildren().add(courseOrderingPane);
        courseDetailsPane.getChildren().add(courseOrderingPane2);
        courseDetailsPane.getChildren().add(courseOrderingPane3);
        
        // *******THIS IS THE INITIALIZATION OF ALL RECITATION DATA COMPONENTS*******
        recitationDataPane = new VBox();
        recitationsPane = new HBox();
        recitationsPane.getChildren().add(new Label(props.getProperty(TAManagerProp.RECITATIONS_TEXT.toString())));
        deleteRecitationButton = new Button(props.getProperty(TAManagerProp.RECITATIONS_BUTTON_TEXT.toString()));
        recitationsPane.getChildren().add(deleteRecitationButton);
        
        recitationTablePane = new VBox();
        recTable = new TableView();
        recTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        RecitationsData rdata = (RecitationsData) app.getRecitationDataComponent();
        ObservableList<Recitations> recitationData = rdata.getRecitationsData();
        recTable.setItems(recitationData);
        recSectionColumn = new TableColumn(props.getProperty(TAManagerProp.REC_SECTION_COLUMN_TEXT.toString()));
        recInstructorColumn = new TableColumn(props.getProperty(TAManagerProp.REC_INSTRUCTOR_COLUMN_TEXT.toString()));
        recDayOrTimeColumn = new TableColumn(props.getProperty(TAManagerProp.REC_DAY_OR_TIME_COLUMN_TEXT.toString()));
        recLocationColumn = new TableColumn(props.getProperty(TAManagerProp.REC_LOCATION_COLUMN_TEXT.toString()));
        recTAColumn = new TableColumn(props.getProperty(TAManagerProp.REC_TA_COLUMN_TEXT.toString()));
        recTA2Column = new TableColumn(props.getProperty(TAManagerProp.REC_TA_COLUMN_TEXT.toString()));
        recSectionColumn.setCellValueFactory(
                new PropertyValueFactory<Recitations, String>("section")
        );
        recInstructorColumn.setCellValueFactory(
                new PropertyValueFactory<Recitations, String>("instructor")
        );
        recDayOrTimeColumn.setCellValueFactory(
                new PropertyValueFactory<Recitations, String>("dayandtime")
        );
        recLocationColumn.setCellValueFactory(
                new PropertyValueFactory<Recitations, String>("location")
        );
        recTAColumn.setCellValueFactory(
                new PropertyValueFactory<Recitations, String>("ta")
        );
        recTA2Column.setCellValueFactory(
                new PropertyValueFactory<Recitations, String>("ta2")
        );
        recTable.getColumns().add(recSectionColumn);
        recTable.getColumns().add(recInstructorColumn);
        recTable.getColumns().add(recDayOrTimeColumn);
        recTable.getColumns().add(recLocationColumn);
        recTable.getColumns().add(recTAColumn);
        recTable.getColumns().add(recTA2Column);
        recitationTablePane.getChildren().add(recTable);
        
        recitationAddOrEditPane = new GridPane();
        recitationAddOrEditPane.setVgap(10);
        recitationAddOrEditPane.setHgap(50);
        recitationAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.REC_ADD_OR_EDIT_TEXT.toString())), 0, 0);
        recitationAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.REC_SECTION_TEXT.toString())), 0, 1);
        recSectionTextField = new TextField("R01");
        recitationAddOrEditPane.add(recSectionTextField, 1, 1, 2, 1);
        recitationAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.REC_INSTRUCTOR_TEXT.toString())), 0, 2);
        recInstructorTextField = new TextField("McKenna");
        recitationAddOrEditPane.add(recInstructorTextField, 1, 2, 2, 1);
        recitationAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.REC_DAY_OR_TIME_TEXT.toString())), 0, 3);
        recDayOrTimeTextField = new TextField("Mondays, 3:30pm-4:23pm");
        recitationAddOrEditPane.add(recDayOrTimeTextField, 1, 3, 2, 1);
        recitationAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.REC_LOCATION_TEXT.toString())), 0, 4);
        recLocationTextField = new TextField("Old Computer Science 2114");
        recitationAddOrEditPane.add(recLocationTextField, 1, 4, 2, 1);
        recitationAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.REC_SUPERVISING_TA_TEXT.toString())), 0, 5);
        ObservableList<String> supervisingTAList = 
        FXCollections.observableArrayList(
            "Joe Shmo"
        );
        recSupervisingTAComboBox = new ComboBox(supervisingTAList);
        recSupervisingTAComboBox.setValue("Joe Shmo");
        recitationAddOrEditPane.add(recSupervisingTAComboBox, 1, 5);
        recitationAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.REC_SECTION_TEXT.toString())), 0, 6);
        ObservableList<String> supervisingTAList2 = 
        FXCollections.observableArrayList(
            "Jane Doe"
        );
        recSupervisingTA2ComboBox = new ComboBox(supervisingTAList2);
        recSupervisingTA2ComboBox.setValue("Jane Doe");
        recitationAddOrEditPane.add(recSupervisingTA2ComboBox, 1, 6);
        recAddOrUpdateButton = new Button(props.getProperty(TAManagerProp.ADD_OR_UPDATE_BUTTON_TEXT.toString()));
        recitationAddOrEditPane.add(recAddOrUpdateButton, 0, 7);
        recClearButton = new Button(props.getProperty(TAManagerProp.CLEAR_BUTTON_TEXT.toString()));
        recitationAddOrEditPane.add(recClearButton, 1, 7);
        
        recitationDataPane.getChildren().add(recitationsPane);
        recitationDataPane.getChildren().add(recitationTablePane);
        recitationDataPane.getChildren().add(recitationAddOrEditPane);
        
        // *******THIS IS THE INITIALIZATION OF ALL SCHEDULE DATA COMPONENTS*******
        scheduleDataPane = new VBox();
        
        scheduleHeaderPane = new HBox();
        scheduleHeaderPane.getChildren().add(new Label(props.getProperty(TAManagerProp.SCHEDULE_TEXT.toString())));
        
        calendarBoundariesPane = new GridPane();
        calendarBoundariesPane.setVgap(10);
        calendarBoundariesPane.setHgap(50);
        calendarBoundariesPane.add(new Label(props.getProperty(TAManagerProp.SCHED_CALENDAR_BOUNDARIES_TEXT.toString())), 0, 0);
        calendarBoundariesPane.add(new Label(props.getProperty(TAManagerProp.SCHED_STARTING_MONDAY_TEXT.toString())), 0, 1);
        startingDate = new DatePicker();
        calendarBoundariesPane.add(startingDate, 1, 1);
        calendarBoundariesPane.add(new Label(props.getProperty(TAManagerProp.SCHED_ENDING_FRIDAY_TEXT.toString())), 2, 1);
        endingDate = new DatePicker();
        calendarBoundariesPane.add(endingDate, 3, 1);
        
        scheduleItemsPane = new HBox(); 
        scheduleItemsPane.getChildren().add(new Label(props.getProperty(TAManagerProp.SCHEDULE_ITEMS_TEXT.toString())));
        deleteScheduleButton = new Button(props.getProperty(TAManagerProp.SCHED_BUTTON_TEXT));
        scheduleItemsPane.getChildren().add(deleteScheduleButton);
        
        scheduleItemsTablePane = new VBox();
        schedTable = new TableView();
        schedTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ScheduleData sData = (ScheduleData) app.getScheduleDataComponent();
        ObservableList<Schedule> scheduleData = sData.getScheduleData();
        schedTable.setItems(scheduleData);
        schedTypeColumn = new TableColumn(props.getProperty(TAManagerProp.SCHED_TYPE_COLUMN_TEXT.toString()));
        schedDateColumn = new TableColumn(props.getProperty(TAManagerProp.SCHED_DATE_COLUMN_TEXT.toString()));
        schedTitleColumn = new TableColumn(props.getProperty(TAManagerProp.SCHED_TITLE_COLUMN_TEXT.toString()));
        schedTopicColumn = new TableColumn(props.getProperty(TAManagerProp.SCHED_TOPIC_COLUMN_TEXT.toString()));
        schedTypeColumn.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("type")
        );
        schedDateColumn.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("date")
        );
        schedTitleColumn.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("title")
        );
        schedTopicColumn.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("topic")
        );
        schedTable.getColumns().add(schedTypeColumn);
        schedTable.getColumns().add(schedDateColumn);
        schedTable.getColumns().add(schedTitleColumn);
        schedTable.getColumns().add(schedTopicColumn);
        scheduleItemsTablePane.getChildren().add(schedTable);
    
        scheduleAddOrEditPane = new GridPane();
        scheduleAddOrEditPane.setVgap(10);
        scheduleAddOrEditPane.setHgap(50);
        scheduleAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.SCHED_ADD_OR_EDIT_TEXT.toString())), 0, 0);
        scheduleAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.SCHED_TYPE_TEXT.toString())), 0, 1);
        ObservableList<String> schedTypeList = 
        FXCollections.observableArrayList(
            "Holiday"
        );
        schedTypeComboBox = new ComboBox(schedTypeList);
        schedTypeComboBox.setValue("Holiday");
        scheduleAddOrEditPane.add(schedTypeComboBox, 1, 1);
        scheduleAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.SCHED_DATE_TEXT.toString())), 0, 2);
        scheduleDate = new DatePicker();
        scheduleAddOrEditPane.add(scheduleDate, 1, 2);
        scheduleAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.SCHED_TIME_TEXT.toString())), 0, 3);
        schedTimeTextField = new TextField();
        scheduleAddOrEditPane.add(schedTimeTextField, 1, 3);
        scheduleAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.SCHED_TITLE_TEXT.toString())), 0, 4);
        schedTitleTextField = new TextField();
        scheduleAddOrEditPane.add(schedTitleTextField, 1, 4);
        scheduleAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.SCHED_TOPIC_TEXT.toString())), 0, 5);
        schedTopicTextField = new TextField();
        scheduleAddOrEditPane.add(schedTopicTextField, 1, 5);
        scheduleAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.SCHED_LINK_TEXT.toString())), 0, 6);
        schedLinkTextField = new TextField();
        scheduleAddOrEditPane.add(schedLinkTextField, 1, 6);
        scheduleAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.SCHED_CRITERIA_TEXT.toString())), 0, 7);
        schedCriteriaTextField = new TextField();
        scheduleAddOrEditPane.add(schedCriteriaTextField, 1, 7);
        schedAddOrUpdateButton = new Button(props.getProperty(TAManagerProp.ADD_OR_UPDATE_BUTTON_TEXT.toString()));
        scheduleAddOrEditPane.add(schedAddOrUpdateButton, 0, 8);
        schedClearButton = new Button(props.getProperty(TAManagerProp.CLEAR_BUTTON_TEXT.toString()));
        scheduleAddOrEditPane.add(schedClearButton, 1, 8);
        
        scheduleOrderingPane = new VBox();
        scheduleOrderingPane.getChildren().add(scheduleItemsPane);
        scheduleOrderingPane.getChildren().add(scheduleItemsTablePane);
        scheduleOrderingPane.getChildren().add(scheduleAddOrEditPane);
        
        scheduleDataPane.getChildren().add(scheduleHeaderPane);
        scheduleDataPane.getChildren().add(calendarBoundariesPane);
        scheduleDataPane.getChildren().add(scheduleOrderingPane);

        // *******THIS IS THE INITIALIZATION OF ALL PROJECT DATA COMPONENTS*******
        projectDataPane = new VBox();
        projDataHeaderPane = new HBox();
        projDataHeaderPane.getChildren().add(new Label(props.getProperty(TAManagerProp.PROJECTS_TEXT.toString())));
        
        projTeamsPane = new HBox();
        projTeamsPane.getChildren().add(new Label(props.getProperty(TAManagerProp.PROJ_TEAMS_TEXT.toString())));
        deleteTeamsButton = new Button(props.getProperty(TAManagerProp.PROJ_BUTTON_TEXT.toString()));
        projTeamsPane.getChildren().add(deleteTeamsButton);
        
        projTeamsTablePane = new VBox();
        teamsTable = new TableView();
        teamsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TeamsData tData = (TeamsData) app.getTeamDataComponent();
        ObservableList<Teams> teamsData = tData.getTeamsData();
        teamsTable.setItems(teamsData);
        projTeamNameColumn = new TableColumn(props.getProperty(TAManagerProp.PROJ_NAME_COLUMN_TEXT.toString()));
        projColorColumn = new TableColumn(props.getProperty(TAManagerProp.PROJ_COLOR_COLUMN_TEXT.toString()));
        projTextColorColumn = new TableColumn(props.getProperty(TAManagerProp.PROJ_TEXT_COLOR_COLUMN_TEXT.toString()));
        projLinkColumn = new TableColumn(props.getProperty(TAManagerProp.PROJ_LINK_COLUMN_TEXT.toString()));
        projTeamNameColumn.setCellValueFactory(
                new PropertyValueFactory<Teams, String>("name")
        );
        projColorColumn.setCellValueFactory(
                new PropertyValueFactory<Teams, String>("color")
        );
        projTextColorColumn.setCellValueFactory(
                new PropertyValueFactory<Teams, String>("textColor")
        );
        projLinkColumn.setCellValueFactory(
                new PropertyValueFactory<Teams, String>("link")
        );
        teamsTable.getColumns().add(projTeamNameColumn);
        teamsTable.getColumns().add(projColorColumn);
        teamsTable.getColumns().add(projTextColorColumn);
        teamsTable.getColumns().add(projLinkColumn);
        projTeamsTablePane.getChildren().add(teamsTable);
        
        projTeamsAddOrEditPane = new GridPane();
        projTeamsAddOrEditPane.setVgap(10);
        projTeamsAddOrEditPane.setHgap(50);
        projTeamsAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.PROJ_ADD_OR_EDIT_TEXT.toString())), 0, 0);
        projTeamsAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.PROJ_NAME_TEXT.toString())), 0, 1);
        projTeamNameTextField = new TextField();
        projTeamsAddOrEditPane.add(projTeamNameTextField, 1, 1);
        projTeamsAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.PROJ_COLOR_TEXT.toString())), 0, 2);
        projTeamColor = new ColorPicker();
        projTeamsAddOrEditPane.add(projTeamColor, 1, 2, 1, 2);
        projTeamsAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.PROJ_TEXT_COLOR_TEXT.toString())), 2, 2);
        projTeamTextColor = new ColorPicker();
        projTeamsAddOrEditPane.add(projTeamTextColor, 3, 2, 1, 2);
        projTeamsAddOrEditPane.add(new Label(), 0, 3);
        projTeamsAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.PROJ_LINK_TEXT.toString())), 0, 4);
        projLinkTextField = new TextField();
        projTeamsAddOrEditPane.add(projLinkTextField, 1, 4);
        projAddOrEditTeamsButton = new Button(props.getProperty(TAManagerProp.ADD_OR_UPDATE_BUTTON_TEXT.toString()));
        projTeamsAddOrEditPane.add(projAddOrEditTeamsButton, 0, 5);
        projClearTeamsButton = new Button(props.getProperty(TAManagerProp.CLEAR_BUTTON_TEXT.toString()));
        projTeamsAddOrEditPane.add(projClearTeamsButton, 1, 5);
        
        projStudentsPane = new HBox();
        projStudentsPane.getChildren().add(new Label(props.getProperty(TAManagerProp.PROJ_STUDENTS_TEXT.toString())));
        deleteStudentsButton = new Button(props.getProperty(TAManagerProp.PROJ_BUTTON_TEXT.toString()));
        projStudentsPane.getChildren().add(deleteStudentsButton);
        
        projStudentsTablePane = new VBox();
        studentsTable = new TableView();
        studentsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        StudentsData stData = (StudentsData) app.getStudentDataComponent();
        ObservableList<Students> studentsData = stData.getStudentsData();
        studentsTable.setItems(studentsData);
        projFirstNameColumn = new TableColumn(props.getProperty(TAManagerProp.PROJ_FIRST_NAME_COLUMN_TEXT.toString()));
        projLastNameColumn = new TableColumn(props.getProperty(TAManagerProp.PROJ_LAST_NAME_COLUMN_TEXT.toString()));
        projTeamColumn = new TableColumn(props.getProperty(TAManagerProp.PROJ_TEAM_COLUMN_TEXT.toString()));
        projRoleColumn = new TableColumn(props.getProperty(TAManagerProp.PROJ_ROLE_COLUMN_TEXT.toString()));
        projFirstNameColumn.setCellValueFactory(
                new PropertyValueFactory<Students, String>("firstName")
        );
        projLastNameColumn.setCellValueFactory(
                new PropertyValueFactory<Students, String>("lastName")
        );
        projTeamColumn.setCellValueFactory(
                new PropertyValueFactory<Students, String>("team")
        );
        projRoleColumn.setCellValueFactory(
                new PropertyValueFactory<Students, String>("role")
        );
        studentsTable.getColumns().add(projFirstNameColumn);
        studentsTable.getColumns().add(projLastNameColumn);
        studentsTable.getColumns().add(projTeamColumn);
        studentsTable.getColumns().add(projRoleColumn);
        projStudentsTablePane.getChildren().add(studentsTable);
        
        projStudentsAddOrEditPane = new GridPane();
        projStudentsAddOrEditPane.setVgap(10);
        projStudentsAddOrEditPane.setHgap(50);
        projStudentsAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.PROJ_ADD_OR_EDIT_TEXT.toString())), 0, 0);
        projStudentsAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.PROJ_FIRST_NAME_TEXT.toString())), 0, 1);
        projFirstNameTextField = new TextField();
        projStudentsAddOrEditPane.add(projFirstNameTextField, 1, 1);
        projStudentsAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.PROJ_LAST_NAME_TEXT.toString())), 0, 2);
        projLastNameTextField = new TextField();
        projStudentsAddOrEditPane.add(projLastNameTextField, 1, 2);
        projStudentsAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.PROJ_TEAM_TEXT.toString())), 0, 3);
        ObservableList<String> projTeamList = 
        FXCollections.observableArrayList(
            "C4 Comics"
        );
        projTeamComboBox = new ComboBox(projTeamList);
        projTeamComboBox.setValue("C4 Comics");
        projStudentsAddOrEditPane.add(projTeamComboBox, 1, 3);
        projStudentsAddOrEditPane.add(new Label(props.getProperty(TAManagerProp.PROJ_ROLE_TEXT.toString())), 0, 4);
        projRoleTextField = new TextField();
        projStudentsAddOrEditPane.add(projRoleTextField, 1, 4);
        projAddOrEditStudentsButton = new Button(props.getProperty(TAManagerProp.ADD_OR_UPDATE_BUTTON_TEXT.toString()));
        projStudentsAddOrEditPane.add(projAddOrEditStudentsButton, 0, 5);
        projClearStudentsButton = new Button(props.getProperty(TAManagerProp.CLEAR_BUTTON_TEXT.toString()));
        projStudentsAddOrEditPane.add(projClearStudentsButton, 1, 5);
        
        projectOrderingPane = new VBox();
        projectOrderingPane.getChildren().add(projTeamsPane);
        projectOrderingPane.getChildren().add(projTeamsTablePane);
        projectOrderingPane.getChildren().add(projTeamsAddOrEditPane);
        projectOrderingPane2 = new VBox();
        projectOrderingPane2.getChildren().add(projStudentsPane);
        projectOrderingPane2.getChildren().add(projStudentsTablePane);
        projectOrderingPane2.getChildren().add(projStudentsAddOrEditPane);
        
        projectDataPane.getChildren().add(projDataHeaderPane);
        projectDataPane.getChildren().add(projectOrderingPane);
        projectDataPane.getChildren().add(projectOrderingPane2);
        
        // *******THIS IS THE INITIALIZATION OF ALL TA DATA COMPONENTS*******
        
        // INIT THE HEADER ON THE LEFT
        tasHeaderBox = new HBox();
        String tasHeaderText = props.getProperty(TAManagerProp.TAS_HEADER_TEXT.toString());
        tasHeaderLabel = new Label(tasHeaderText);
        tasHeaderBox.getChildren().add(tasHeaderLabel);

        // MAKE THE TABLE AND SETUP THE DATA MODEL
        taTable = new TableView();
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TAData data = (TAData) app.getDataComponent();
        ObservableList<TeachingAssistant> tableData = data.getTeachingAssistants();
        taTable.setItems(tableData);
        String undergradColumnText = props.getProperty(TAManagerProp.UNDERGRAD_COLUMN_TEXT.toString());
        String nameColumnText = props.getProperty(TAManagerProp.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(TAManagerProp.EMAIL_COLUMN_TEXT.toString());
        undergradColumn = new TableColumn(undergradColumnText);
        nameColumn = new TableColumn(nameColumnText);
        emailColumn = new TableColumn(emailColumnText);
        undergradColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<TeachingAssistant, Boolean>, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<TeachingAssistant, Boolean> p) {
                       return p.getValue().getUndergrad();
                    }           
                }
        );
        undergradColumn.setCellFactory(column -> new CheckBoxTableCell());
        undergradColumn.setEditable(true);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("name")
        );
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("email")
        );
        taTable.getColumns().add(undergradColumn);
        taTable.getColumns().add(nameColumn);
        taTable.getColumns().add(emailColumn);
        taTable.setEditable(true);

        // ADD BOX FOR ADDING A TA
        String namePromptText = props.getProperty(TAManagerProp.NAME_PROMPT_TEXT.toString());
        String emailPromptText = props.getProperty(TAManagerProp.EMAIL_PROMPT_TEXT.toString());
        String addButtonText = props.getProperty(TAManagerProp.ADD_BUTTON_TEXT.toString());
        String clearButtonText = props.getProperty(TAManagerProp.CLEAR_BUTTON_TEXT.toString());
        String updateTAText = props.getProperty(TAManagerProp.UPDATE_TA_TEXT.toString());
        String startTimeText = props.getProperty(TAManagerProp.START_TIME_TEXT.toString());
        String endTimeText = props.getProperty(TAManagerProp.END_TIME_TEXT.toString());
        String changeHoursText = props.getProperty(TAManagerProp.CHANGE_HOURS_TEXT.toString());
        nameTextField = new TextField();
        nameTextField.setPromptText(namePromptText);
        emailTextField = new TextField();
        emailTextField.setPromptText(emailPromptText);
        addButton = new Button(addButtonText);
        clearButton = new Button(clearButtonText);
        clearButton.setDisable(true);
        addBox = new HBox();
        nameTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        emailTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        addButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        clearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        addBox.getChildren().add(nameTextField);
        addBox.getChildren().add(emailTextField);
        addBox.getChildren().add(addButton);
        addBox.getChildren().add(clearButton);

        // INIT THE HEADER ON THE RIGHT
        officeHoursHeaderBox = new HBox();
        String officeHoursGridText = props.getProperty(TAManagerProp.OFFICE_HOURS_SUBHEADER.toString());
        officeHoursHeaderLabel = new Label(officeHoursGridText);
        officeHoursHeaderBox.getChildren().add(officeHoursHeaderLabel);
        
        // THESE WILL STORE PANES AND LABELS FOR OUR OFFICE HOURS GRID
        officeHoursGridPane = new GridPane();
        officeHoursGridTimeHeaderPanes = new HashMap();
        officeHoursGridTimeHeaderLabels = new HashMap();
        officeHoursGridDayHeaderPanes = new HashMap();
        officeHoursGridDayHeaderLabels = new HashMap();
        officeHoursGridTimeCellPanes = new HashMap();
        officeHoursGridTimeCellLabels = new HashMap();
        officeHoursGridTACellPanes = new HashMap();
        officeHoursGridTACellLabels = new HashMap();

        // ORGANIZE THE LEFT AND RIGHT PANES
        VBox leftPane = new VBox();
        leftPane.getChildren().add(tasHeaderBox);        
        leftPane.getChildren().add(taTable);        
        leftPane.getChildren().add(addBox);
        VBox rightPane = new VBox();
        rightPane.getChildren().add(officeHoursHeaderBox);
        rightPane.getChildren().add(officeHoursGridPane);
        comboBoxesPane = new VBox();
        ObservableList<String> startTimes = 
        FXCollections.observableArrayList(
            "12:00AM"
        );
        comboBox = new ComboBox(startTimes);
        ObservableList<String> endTimes = 
        FXCollections.observableArrayList(
            "12:00AM"
        );
        comboBox2 = new ComboBox(endTimes);
        for(int i = 1; i <= 23; i++){
            if(i >= 12){
                String time = ":00PM";
                if(i - 12 == 0){
                    comboBox.getItems().add(12 + time);
                    comboBox2.getItems().add(12 + time);
                }
                else{
                    comboBox.getItems().add((i - 12) + time);
                    comboBox2.getItems().add((i - 12) + time);
                }
            }
            else{
                String time = ":00AM";
                comboBox.getItems().add(i + time);
                comboBox2.getItems().add(i + time);
            }
        }
        Label comboBoxLabel = new Label(startTimeText);
        Label comboBox2Label = new Label(endTimeText);
        officeHoursButton = new Button(changeHoursText);
        comboBoxesPane.getChildren().add(comboBoxLabel);
        comboBoxesPane.getChildren().add(comboBox);
        comboBoxesPane.getChildren().add(comboBox2Label);
        comboBoxesPane.getChildren().add(comboBox2);
        comboBoxesPane.getChildren().add(officeHoursButton);
        
        // BOTH PANES WILL NOW GO IN A SPLIT PANE
        SplitPane sPane = new SplitPane(leftPane, new ScrollPane(rightPane));
        BorderPane workspaceTADataPane = new BorderPane();
        
        // AND PUT EVERYTHING IN THE WORKSPACE
        workspaceTADataPane.setCenter(sPane);
        workspaceTADataPane.setRight(comboBoxesPane);
        comboBoxesPane.prefWidthProperty().bind(workspaceTADataPane.widthProperty().multiply(.15));

        // MAKE SURE THE TABLE EXTENDS DOWN FAR ENOUGH
        taTable.prefHeightProperty().bind(workspaceTADataPane.heightProperty().multiply(1.9));

        // PLACE ALL TAB CONTENT INTO WORKSPACE
        workspace = new TabPane();
        Tab courseDetailsTab = new Tab();
        Tab taDataTab = new Tab();
        Tab recitationDataTab = new Tab();
        Tab scheduleDataTab = new Tab();
        Tab projectDataTab = new Tab();
        courseDetailsTab.setText(props.getProperty(TAManagerProp.COURSE_DETAILS_TEXT.toString()));
        taDataTab.setText(props.getProperty(TAManagerProp.TA_DATA_TEXT.toString()));
        recitationDataTab.setText(props.getProperty(TAManagerProp.RECITATION_DATA_TEXT.toString()));
        scheduleDataTab.setText(props.getProperty(TAManagerProp.SCHEDULE_DATA_TEXT.toString()));
        projectDataTab.setText(props.getProperty(TAManagerProp.PROJECT_DATA_TEXT.toString()));
        courseDetailsTab.setContent(courseDetailsPane);
        taDataTab.setContent(workspaceTADataPane);
        recitationDataTab.setContent(recitationDataPane);
        scheduleDataTab.setContent(scheduleDataPane);
        projectDataTab.setContent(projectDataPane);
        courseDetailsTab.setClosable(false);
        taDataTab.setClosable(false);
        recitationDataTab.setClosable(false);
        scheduleDataTab.setClosable(false);
        projectDataTab.setClosable(false);
        workspace.getTabs().add(courseDetailsTab);
        workspace.getTabs().add(taDataTab);
        workspace.getTabs().add(recitationDataTab);
        workspace.getTabs().add(scheduleDataTab);
        workspace.getTabs().add(projectDataTab);
        
        // NOW LET'S SETUP THE EVENT HANDLING
        controller = new TAController(app);
        controller.fixComboBoxes(comboBox, comboBox2);

        // CONTROLS FOR ADDING TAs
        nameTextField.setOnAction(e -> {
            if(addButton.getText().equals(addButtonText)){
                controller.handleAddTA();
            }
            else if(!addButton.isDisable()){
                controller.handleUpdateTA(nameTextField, emailTextField);
            }
        });
        nameTextField.setOnKeyReleased(e -> {
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if(selectedItem != null){
                TeachingAssistant ta = (TeachingAssistant)selectedItem;
                if(!nameTextField.getText().equals(ta.getName()) ||
                  !emailTextField.getText().equals(ta.getEmail())){
                    addButton.setDisable(false);
                }
                else{
                    addButton.setDisable(true);
                }
            }
        });
        emailTextField.setOnAction(e -> {
            if(addButton.getText().equals(addButtonText)){
                controller.handleAddTA();
            }
            else if(!addButton.isDisable()){
                controller.handleUpdateTA(nameTextField, emailTextField);
            }
        });
        emailTextField.setOnKeyReleased(e -> {
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if(selectedItem != null){
                TeachingAssistant ta = (TeachingAssistant)selectedItem;
                if(!nameTextField.getText().equals(ta.getName()) ||
                  !emailTextField.getText().equals(ta.getEmail())){
                    addButton.setDisable(false);
                }
                else{
                    addButton.setDisable(true);
                }
            }
        });
        addButton.setOnAction(e -> {
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if(selectedItem != null){
                TeachingAssistant ta = (TeachingAssistant)selectedItem;
                controller.handleUpdateTA(nameTextField, emailTextField);
                if(!nameTextField.getText().equals(ta.getName())){
                    addButton.setDisable(false);
                }
                else{
                    addButton.setDisable(true);
                }
            }
            else{
                controller.handleAddTA();
            }
        });    
        clearButton.setOnAction(e -> {
            taTable.getSelectionModel().clearSelection();
            nameTextField.setText("");
            emailTextField.setText("");
            addButton.setText(addButtonText);
            clearButton.setDisable(true);
            addButton.setDisable(false);
            nameTextField.requestFocus();
        });
        taTable.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case DELETE: controller.handleDeleteTA();
            }
            if(data.getTeachingAssistants().isEmpty()){
                addButton.setText(addButtonText);
                clearButton.setDisable(true);
                addButton.setDisable(false);
            }
        });
        taTable.setOnMouseClicked(e -> {
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if(selectedItem != null){
                clearButton.setDisable(false);
                TeachingAssistant ta = (TeachingAssistant)selectedItem;
                String taName = ta.getName();
                String taEmail = ta.getEmail();
                addButton.setText(updateTAText);
                nameTextField.setText(taName);
                emailTextField.setText(taEmail);
                addButton.setDisable(true);
            }
            else{
                nameTextField.setText("");
                emailTextField.setText("");
            }
        });
        comboBox.setOnAction(e -> {
            officeHoursButton.setDisable(false);
            int test = controller.handleMilitaryConversion(comboBox);
            int test2 = controller.handleMilitaryConversion(comboBox2);
            if(test == data.getStartHour() && test2 == data.getEndHour()){
                officeHoursButton.setDisable(true);
            }
            
        });
        comboBox2.setOnAction(e -> {
            officeHoursButton.setDisable(false);
            int test = controller.handleMilitaryConversion(comboBox);
            int test2 = controller.handleMilitaryConversion(comboBox2);
            if(test == data.getStartHour() && test2 == data.getEndHour()){
                officeHoursButton.setDisable(true);
            }
        });
        officeHoursButton.setOnAction(e -> {
            if(!controller.handleOfficeHoursInput()){
                controller.handleHourChanges();
            }
        });
        app.getGUI().getAppPane().setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.Y && e.isShortcutDown()){
                controller.jTPS.doTransaction();
                app.getGUI().getAppFileController().markAsEdited(app.getGUI());
            }
            
            if(e.getCode() == KeyCode.Z && e.isShortcutDown()){
                controller.jTPS.undoTransaction();
                app.getGUI().getAppFileController().markAsEdited(app.getGUI());
            }
        });
    }
    
    
    // WE'LL PROVIDE AN ACCESSOR METHOD FOR EACH VISIBLE COMPONENT
    // IN CASE A CONTROLLER OR STYLE CLASS NEEDS TO CHANGE IT
    
    
    public HBox getTAsHeaderBox() {
        return tasHeaderBox;
    }

    public Label getTAsHeaderLabel() {
        return tasHeaderLabel;
    }

    public TableView getTATable() {
        return taTable;
    }

    public HBox getAddBox() {
        return addBox;
    }

    public VBox getCourseOrderingPane() {
        return courseOrderingPane;
    }
    
    public VBox getCourseOrderingPane2() {
        return courseOrderingPane2;
    }
    
    public VBox getCourseOrderingPane3() {
        return courseOrderingPane3;
    }
    
    public GridPane getRecitationAddOrEditPane() {
        return recitationAddOrEditPane;
    }
    
    public GridPane getCalendarBoundariesPane() {
        return calendarBoundariesPane;
    }
    
    public VBox getScheduleOrderingPane() {
        return scheduleOrderingPane;
    }
    
    public VBox getProjectOrderingPane() {
        return projectOrderingPane;
    }
    
    public VBox getProjectOrderingPane2() {
        return projectOrderingPane2;
    }
    
    public HBox getCourseInfoHeaderPane() {
        return courseInfoHeaderPane;
    }
    
    public HBox getSiteTemplateHeaderPane() {
        return siteTemplateHeaderPane;
    }
    
    public HBox getPageStyleHeaderPane() {
        return pageStyleHeaderPane;
    }
    
    public HBox getScheduleHeaderPane() {
        return scheduleHeaderPane;
    }
    
    public HBox getProjDataHeaderPane() {
        return projDataHeaderPane;
    }
    
    public HBox getRecitationsPane() {
        return recitationsPane;
    }
    
    public HBox getScheduleItemsPane() {
        return scheduleItemsPane;
    }
    
    public HBox getProjTeamsPane() {
        return projTeamsPane;
    }
    
    public HBox getProjStudentsPane() {
        return projStudentsPane;
    }
    
    public VBox getCourseDetailsPane() {
        return courseDetailsPane;
    }
    
    public VBox getRecitationDataPane() {
        return recitationDataPane;
    }
    
    public VBox getScheduleDataPane() {
        return scheduleDataPane;
    }
    
    public VBox getProjectDataPane() {
        return projectDataPane;
    }
    
    public VBox getComboBoxesPane() {
        return comboBoxesPane;
    }
    
    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }
    
    public Button getAddButton() {
        return addButton;
    }
    
    public Button getClearButton() {
        return clearButton;
    }

    public Button getOfficeHoursButton() {
        return officeHoursButton;
    }
    
    public HBox getOfficeHoursSubheaderBox() {
        return officeHoursHeaderBox;
    }

    public Label getOfficeHoursSubheaderLabel() {
        return officeHoursHeaderLabel;
    }

    public GridPane getOfficeHoursGridPane() {
        return officeHoursGridPane;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeHeaderPanes() {
        return officeHoursGridTimeHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeHeaderLabels() {
        return officeHoursGridTimeHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridDayHeaderPanes() {
        return officeHoursGridDayHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridDayHeaderLabels() {
        return officeHoursGridDayHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeCellPanes() {
        return officeHoursGridTimeCellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeCellLabels() {
        return officeHoursGridTimeCellLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTACellPanes() {
        return officeHoursGridTACellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTACellLabels() {
        return officeHoursGridTACellLabels;
    }
    
    public String getCellKey(Pane testPane) {
        for (String key : officeHoursGridTACellLabels.keySet()) {
            if (officeHoursGridTACellPanes.get(key) == testPane) {
                return key;
            }
        }
        return null;
    }

    public Label getTACellLabel(String cellKey) {
        return officeHoursGridTACellLabels.get(cellKey);
    }

    public Pane getTACellPane(String cellPane) {
        return officeHoursGridTACellPanes.get(cellPane);
    }
    
    public ComboBox getComboBox1(){
        return comboBox;
    }
    
    public ComboBox getComboBox2(){
        return comboBox2;
    }
    
    public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
    }

    public String buildCellText(int militaryHour, String minutes) {
        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        if(hour == 0){
            hour = 12;
        }
        String cellText = "" + hour + ":" + minutes;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    @Override
    public void resetWorkspace() {
        controller.resetjTPS();
        // CLEAR OUT THE GRID PANE
        officeHoursGridPane.getChildren().clear();
        // AND THEN ALL THE GRID PANES AND LABELS
        officeHoursGridTimeHeaderPanes.clear();
        officeHoursGridTimeHeaderLabels.clear();
        officeHoursGridDayHeaderPanes.clear();
        officeHoursGridDayHeaderLabels.clear();
        officeHoursGridTimeCellPanes.clear();
        officeHoursGridTimeCellLabels.clear();
        officeHoursGridTACellPanes.clear();
        officeHoursGridTACellLabels.clear();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String addButtonText = props.getProperty(TAManagerProp.ADD_BUTTON_TEXT.toString());
        officeHoursButton.setDisable(true);
        taTable.getSelectionModel().clearSelection();
        nameTextField.setText("");
        emailTextField.setText("");
        addButton.setText(addButtonText);
        clearButton.setDisable(true);
        addButton.setDisable(false);
    }
    
    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        TAData taData = (TAData)dataComponent;
        reloadOfficeHoursGrid(taData);
        controller.fixComboBoxes(comboBox, comboBox2);
    }

    public void reloadOfficeHoursGrid(TAData dataComponent) {        
        ArrayList<String> gridHeaders = dataComponent.getGridHeaders();

        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            addCellToGrid(dataComponent, officeHoursGridTimeHeaderPanes, officeHoursGridTimeHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }
        
        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {
            addCellToGrid(dataComponent, officeHoursGridDayHeaderPanes, officeHoursGridDayHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));            
        }
        
        // THEN THE TIME AND TA CELLS
        int row = 1;
        for (int i = dataComponent.getStartHour(); i < dataComponent.getEndHour(); i++) {
            // START TIME COLUMN
            int col = 0;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(i, "00"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(i, "30"));

            // END TIME COLUMN
            col++;
            int endHour = i;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(endHour+1, "00"));
            col++;

            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row);
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row+1);
                col++;
            }
            row += 2;
        }

        // CONTROLS FOR TOGGLING TA OFFICE HOURS
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setOnMouseClicked(e -> {
                controller.handleCellToggle((Pane) e.getSource());
            });
            p.setOnMouseEntered(e -> {
                controller.handleGridHighlight(p);
            });
            p.setOnMouseExited(e -> {
                controller.handleGridHighlightFix(p);
            });
        }
        
        // AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
        TAStyle taStyle = (TAStyle)app.getStyleComponent();
        taStyle.initOfficeHoursGridStyle();
    }
    
    public void addCellToGrid(TAData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {       
        // MAKE THE LABEL IN A PANE
        Label cellLabel = new Label("");
        HBox cellPane = new HBox();
        cellPane.setAlignment(Pos.CENTER);
        cellPane.getChildren().add(cellLabel);

        // BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
        String cellKey = dataComponent.getCellKey(col, row);
        cellPane.setId(cellKey);
        cellLabel.setId(cellKey);
        
        // NOW PUT THE CELL IN THE WORKSPACE GRID
        officeHoursGridPane.add(cellPane, col, row);
        
        // AND ALSO KEEP IN IN CASE WE NEED TO STYLIZE IT
        panes.put(cellKey, cellPane);
        labels.put(cellKey, cellLabel);
        
        // AND FINALLY, GIVE THE TEXT PROPERTY TO THE DATA MANAGER
        // SO IT CAN MANAGE ALL CHANGES
        dataComponent.setCellProperty(col, row, cellLabel.textProperty());        
    }
}
