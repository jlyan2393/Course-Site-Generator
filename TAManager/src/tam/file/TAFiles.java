package tam.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import tam.TAManagerApp;
import tam.data.CourseData;
import tam.data.Recitations;
import tam.data.RecitationsData;
import tam.data.Schedule;
import tam.data.ScheduleData;
import tam.data.SitePages;
import tam.data.Students;
import tam.data.StudentsData;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.data.Teams;
import tam.data.TeamsData;

/**
 * This class serves as the file component for the TA
 * manager app. It provides all saving and loading 
 * services for the application.
 * 
 * @author Richard McKenna
 * @co-author Jason Lyan
 */
public class TAFiles implements AppFileComponent {
    // THIS IS THE APP ITSELF
    TAManagerApp app;
    
    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_UNDERGRAD = "undergrad";
    static final String JSON_NAME = "name";
    static final String JSON_EMAIL = "email";
    static final String JSON_SITE_PAGES = "site_pages";
    static final String JSON_COURSE_INFO = "course_info";
    static final String JSON_COURSE_SUBJECT = "course_subject";
    static final String JSON_COURSE_NUMBER = "course_number";
    static final String JSON_COURSE_SEMESTER = "course_semester";
    static final String JSON_COURSE_YEAR = "course_year";
    static final String JSON_COURSE_TITLE = "course_title";
    static final String JSON_COURSE_INSTRUCTOR = "course_instructor";
    static final String JSON_COURSE_INSTRUCTOR_HOME = "course_instructor_home";
    static final String JSON_USE_PAGE = "use_page";
    static final String JSON_NAVBAR_TITLE = "navbar_title";
    static final String JSON_FILE_NAME = "file_name";
    static final String JSON_SCRIPT = "script";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_RECITATIONS = "recitations";
    static final String JSON_SECTION = "section";
    static final String JSON_INSTRUCTOR = "instructor";
    static final String JSON_DAY_AND_TIME = "day_time";
    static final String JSON_LOCATION = "location";
    static final String JSON_TA_NAME = "ta_1";
    static final String JSON_TA_NAME_2 = "ta_2";
    static final String JSON_SCHEDULES = "schedules";
    static final String JSON_CALENDAR_BOUNDARIES = "calendar_boundaries";
    static final String JSON_CALENDAR_START = "start_date";
    static final String JSON_CALENDAR_END = "end_date";
    static final String JSON_TYPE = "type";
    static final String JSON_DATE = "date";
    static final String JSON_TITLE = "title";
    static final String JSON_TOPIC = "topic";
    static final String JSON_TEAMS = "teams";
    static final String JSON_TEAM_NAME = "team_name";
    static final String JSON_COLOR = "color";
    static final String JSON_TEXT_COLOR = "text_color";
    static final String JSON_LINK = "link";
    static final String JSON_STUDENTS = "students";
    static final String JSON_FIRST_NAME = "first_name";
    static final String JSON_LAST_NAME = "last_name";
    static final String JSON_TEAM = "student_team";
    static final String JSON_ROLE = "student_role";
    
    public TAFiles(TAManagerApp initApp) {
        app = initApp;
    }

    @Override
    public void loadData(AppDataComponent data, AppDataComponent cdata, AppDataComponent rdata, AppDataComponent schedData, AppDataComponent teamData, AppDataComponent studData, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
        CourseData cdataManager = (CourseData)cdata;
	TAData dataManager = (TAData)data;
        RecitationsData recitationManager = (RecitationsData)rdata;
        ScheduleData scheduleManager = (ScheduleData)schedData;
        TeamsData teamsManager = (TeamsData)teamData;
        StudentsData studentsManager = (StudentsData)studData;

	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);

	// LOAD THE START AND END HOURS
	String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

        JsonArray jsonSitePagesArray = json.getJsonArray(JSON_SITE_PAGES);
        for(int i = 0; i < jsonSitePagesArray.size(); i++) {
            JsonObject jsonSitePage = jsonSitePagesArray.getJsonObject(i);
            boolean usePage = jsonSitePage.getBoolean(JSON_USE_PAGE);
            String navbarTitle = jsonSitePage.getString(JSON_NAVBAR_TITLE);
            String fileName = jsonSitePage.getString(JSON_FILE_NAME);
            String script = jsonSitePage.getString(JSON_SCRIPT);
            cdataManager.addSitePage(navbarTitle, fileName, script);
            cdataManager.getSitePage(fileName).setUsePage(usePage);
        }

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            boolean undergrad = jsonTA.getBoolean(JSON_UNDERGRAD);
            String name = jsonTA.getString(JSON_NAME);
            String email = "";
            try{
                email = jsonTA.getString(JSON_EMAIL);
            }
            catch(NullPointerException e){
                email = "";
            }
            dataManager.addTA(name, email);
            dataManager.getTA(name).setUndergrad(undergrad);
        }

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            dataManager.addOfficeHoursReservation(day, time, name);
        }
        
        JsonArray jsonRecitationArray = json.getJsonArray(JSON_RECITATIONS);
        for (int i = 0; i < jsonRecitationArray.size(); i++) {
            JsonObject jsonRecitation = jsonRecitationArray.getJsonObject(i);
            String section = jsonRecitation.getString(JSON_SECTION);
            String instructor = jsonRecitation.getString(JSON_INSTRUCTOR);
            String dayandtime = jsonRecitation.getString(JSON_DAY_AND_TIME);
            String location = jsonRecitation.getString(JSON_LOCATION);
            String ta = jsonRecitation.getString(JSON_TA_NAME);
            String ta2 = jsonRecitation.getString(JSON_TA_NAME_2);
            recitationManager.addRecitation(section, instructor, dayandtime, location, ta, ta2);
        }
        
        JsonArray jsonScheduleArray = json.getJsonArray(JSON_SCHEDULES);
        for (int i = 0; i < jsonScheduleArray.size(); i++) {
            JsonObject jsonSchedule = jsonScheduleArray.getJsonObject(i);
            String type = jsonSchedule.getString(JSON_TYPE);
            String date = jsonSchedule.getString(JSON_DATE);
            String title = jsonSchedule.getString(JSON_TITLE);
            String topic = jsonSchedule.getString(JSON_TOPIC);
            scheduleManager.addSchedule(type, date, title, topic);
        }
        
        JsonArray jsonTeamArray = json.getJsonArray(JSON_TEAMS);
        for (int i = 0; i < jsonTeamArray.size(); i++) {
            JsonObject jsonTeam = jsonTeamArray.getJsonObject(i);
            String teamName = jsonTeam.getString(JSON_TEAM_NAME);
            String color = jsonTeam.getString(JSON_COLOR);
            String textColor = jsonTeam.getString(JSON_TEXT_COLOR);
            String link = jsonTeam.getString(JSON_LINK);
            teamsManager.addTeam(teamName, color, textColor, link);
        }
        
        JsonArray jsonStudentArray = json.getJsonArray(JSON_STUDENTS);
        for (int i = 0; i < jsonStudentArray.size(); i++) {
            JsonObject jsonStudent = jsonStudentArray.getJsonObject(i);
            String fName = jsonStudent.getString(JSON_FIRST_NAME);
            String lName = jsonStudent.getString(JSON_LAST_NAME);
            String studentTeam = jsonStudent.getString(JSON_TEAM);
            String studentRole = jsonStudent.getString(JSON_ROLE);
            studentsManager.addStudent(fName, lName, studentTeam, studentRole);
        }
    }
      
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }

    @Override
    public void saveTAData(AppDataComponent data, String filePath) throws IOException{
        TAData dataManager = (TAData)data;
        
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	    
	    JsonObject taJson = Json.createObjectBuilder()
                    .add(JSON_UNDERGRAD, ta.getUndergrad().get())
		    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail()).build();
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();
        
        JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .build();
        
        Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();
        
        OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    @Override
    public void saveCourseData(AppDataComponent data, String filePath) throws IOException{
        CourseData cdataManager = (CourseData)data;
        
        JsonArrayBuilder courseArrayBuilder = Json.createArrayBuilder();
        ObservableList<SitePages> sitePages = cdataManager.getCourseData();
        for(SitePages s: sitePages){
            JsonObject siteJson = Json.createObjectBuilder()
                    .add(JSON_USE_PAGE, s.getUsePage().get())
                    .add(JSON_NAVBAR_TITLE, s.getNavbarTitle())
                    .add(JSON_FILE_NAME, s.getFileName())
                    .add(JSON_SCRIPT, s.getScript()).build();
            courseArrayBuilder.add(siteJson);
        }
        JsonArray courseSitesArray = courseArrayBuilder.build();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_SITE_PAGES, courseSitesArray)
                .build();
        
        Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    @Override
    public void saveRecData(AppDataComponent data, String filePath) throws IOException{
        RecitationsData recitationManager = (RecitationsData)data;
        
        JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
        ObservableList<Recitations> recitations = recitationManager.getRecitationsData();
        for(Recitations r: recitations){
            JsonObject recitationJson = Json.createObjectBuilder()
                    .add(JSON_SECTION, r.getSection())
                    .add(JSON_INSTRUCTOR, r.getInstructor())
                    .add(JSON_DAY_AND_TIME, r.getDayandtime())
                    .add(JSON_LOCATION, r.getLocation())
                    .add(JSON_TA_NAME, r.getTa())
                    .add(JSON_TA_NAME_2, r.getTa2()).build();                    
            recitationArrayBuilder.add(recitationJson);
        }
        JsonArray recitationsArray = recitationArrayBuilder.build();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_RECITATIONS, recitationsArray)
                .build();
        
        Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    @Override
    public void saveSchedData(AppDataComponent data, String filePath) throws IOException{
        ScheduleData scheduleManager = (ScheduleData)data;
        
        JsonArrayBuilder scheduleArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> schedules = scheduleManager.getScheduleData();
        for(Schedule s: schedules){
            JsonObject scheduleJson = Json.createObjectBuilder()
                    .add(JSON_TYPE, s.getType())
                    .add(JSON_DATE, s.getDate())
                    .add(JSON_TITLE, s.getTitle())
                    .add(JSON_TOPIC, s.getTopic()).build();
            scheduleArrayBuilder.add(scheduleJson);
        }
        JsonArray schedulesArray = scheduleArrayBuilder.build();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_SCHEDULES, schedulesArray)
                .build();
        
        Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    @Override
    public void saveTeamAndStudentData(AppDataComponent tData, AppDataComponent sData, String filePath) throws IOException{
        TeamsData teamsManager = (TeamsData)tData;
        StudentsData studentsManager = (StudentsData)sData;
        
        JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
        ObservableList<Teams> teams = teamsManager.getTeamsData();
        for(Teams t: teams){
            JsonObject teamJson = Json.createObjectBuilder()
                    .add(JSON_TEAM_NAME, t.getName())
                    .add(JSON_COLOR, t.getColor())
                    .add(JSON_TEXT_COLOR, t.getTextColor())
                    .add(JSON_LINK, t.getLink()).build();
            teamArrayBuilder.add(teamJson);
        }
        JsonArray teamsArray = teamArrayBuilder.build();
        
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
        ObservableList<Students> students = studentsManager.getStudentsData();
        for(Students s: students){
            JsonObject studentJson = Json.createObjectBuilder()
                    .add(JSON_FIRST_NAME, s.getFirstName())
                    .add(JSON_LAST_NAME, s.getLastName())
                    .add(JSON_TEAM, s.getTeam())
                    .add(JSON_ROLE, s.getRole()).build();
            studentArrayBuilder.add(studentJson);
        }
        JsonArray studentsArray = studentArrayBuilder.build();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_TEAMS, teamsArray)
                .add(JSON_STUDENTS, studentsArray)
                .build();
        
        Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    @Override
    public void saveData(AppDataComponent data, AppDataComponent cdata, AppDataComponent rdata, AppDataComponent schedData, AppDataComponent teamData, AppDataComponent studData, String filePath) throws IOException {
	// GET THE DATA
	CourseData cdataManager = (CourseData)cdata;
	TAData dataManager = (TAData)data;
        RecitationsData recitationManager = (RecitationsData)rdata;
        ScheduleData scheduleManager = (ScheduleData)schedData;
        TeamsData teamsManager = (TeamsData)teamData;
        StudentsData studentsManager = (StudentsData)studData;

        JsonArrayBuilder courseArrayBuilder = Json.createArrayBuilder();
        ObservableList<SitePages> sitePages = cdataManager.getCourseData();
        for(SitePages s: sitePages){
            JsonObject siteJson = Json.createObjectBuilder()
                    .add(JSON_USE_PAGE, s.getUsePage().get())
                    .add(JSON_NAVBAR_TITLE, s.getNavbarTitle())
                    .add(JSON_FILE_NAME, s.getFileName())
                    .add(JSON_SCRIPT, s.getScript()).build();
            courseArrayBuilder.add(siteJson);
        }
        JsonArray courseSitesArray = courseArrayBuilder.build();
        
	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	    
	    JsonObject taJson = Json.createObjectBuilder()
                    .add(JSON_UNDERGRAD, ta.getUndergrad().get())
		    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail()).build();
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

        JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
        ObservableList<Recitations> recitations = recitationManager.getRecitationsData();
        for(Recitations r: recitations){
            JsonObject recitationJson = Json.createObjectBuilder()
                    .add(JSON_SECTION, r.getSection())
                    .add(JSON_INSTRUCTOR, r.getInstructor())
                    .add(JSON_DAY_AND_TIME, r.getDayandtime())
                    .add(JSON_LOCATION, r.getLocation())
                    .add(JSON_TA_NAME, r.getTa())
                    .add(JSON_TA_NAME_2, r.getTa2()).build();                    
            recitationArrayBuilder.add(recitationJson);
        }
        JsonArray recitationsArray = recitationArrayBuilder.build();
        
        JsonArrayBuilder scheduleArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> schedules = scheduleManager.getScheduleData();
        for(Schedule s: schedules){
            JsonObject scheduleJson = Json.createObjectBuilder()
                    .add(JSON_TYPE, s.getType())
                    .add(JSON_DATE, s.getDate())
                    .add(JSON_TITLE, s.getTitle())
                    .add(JSON_TOPIC, s.getTopic()).build();
            scheduleArrayBuilder.add(scheduleJson);
        }
        JsonArray schedulesArray = scheduleArrayBuilder.build();
        
        JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
        ObservableList<Teams> teams = teamsManager.getTeamsData();
        for(Teams t: teams){
            JsonObject teamJson = Json.createObjectBuilder()
                    .add(JSON_TEAM_NAME, t.getName())
                    .add(JSON_COLOR, t.getColor())
                    .add(JSON_TEXT_COLOR, t.getTextColor())
                    .add(JSON_LINK, t.getLink()).build();
            teamArrayBuilder.add(teamJson);
        }
        JsonArray teamsArray = teamArrayBuilder.build();
        
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
        ObservableList<Students> students = studentsManager.getStudentsData();
        for(Students s: students){
            JsonObject studentJson = Json.createObjectBuilder()
                    .add(JSON_FIRST_NAME, s.getFirstName())
                    .add(JSON_LAST_NAME, s.getLastName())
                    .add(JSON_TEAM, s.getTeam())
                    .add(JSON_ROLE, s.getRole()).build();
            studentArrayBuilder.add(studentJson);
        }
        JsonArray studentsArray = studentArrayBuilder.build();
        
	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_SITE_PAGES, courseSitesArray)
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .add(JSON_RECITATIONS, recitationsArray)
                .add(JSON_SCHEDULES, schedulesArray)
                .add(JSON_TEAMS, teamsArray)
                .add(JSON_STUDENTS, studentsArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION

    @Override
    public void importData(AppDataComponent data, AppDataComponent cdata, AppDataComponent rdata, AppDataComponent schedData, AppDataComponent teamData, AppDataComponent studData, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, AppDataComponent cdata, AppDataComponent rdata, AppDataComponent schedData, AppDataComponent teamData, AppDataComponent studData, String filePath) throws IOException {
        app.getGUI().getAppFileController().handleExportRequest(filePath);
    }
}