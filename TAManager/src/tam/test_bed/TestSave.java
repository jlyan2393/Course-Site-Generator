/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.test_bed;

import static djf.settings.AppStartupConstants.APP_PROPERTIES_FILE_NAME;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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
import tam.file.TAFiles;
import tam.file.TimeSlot;

/**
 *
 * @author Jason Lyan
 */
public class TestSave {
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
    
    public static void main(String[] args) throws IOException{
        TAManagerApp app = new TAManagerApp();
        app.loadProperties(APP_PROPERTIES_FILE_NAME);
        CourseData cdataManager = new CourseData(app);
        TAData dataManager = new TAData(app);
        RecitationsData recitationManager = new RecitationsData(app);
        ScheduleData scheduleManager = new ScheduleData(app);
        TeamsData teamsManager = new TeamsData(app);
        StudentsData studentsManager = new StudentsData(app);
        ArrayList<TimeSlot> testArrayList = new ArrayList<TimeSlot>();
        TimeSlot ts1 = new TimeSlot("WEDNESDAY", "10_00am", "Bob");
        testArrayList.add(ts1);
        
        cdataManager.addSitePage("Home", "index.html", "HomeBuilder.js");
        dataManager.addTA("Bob","bob@stonybrook.edu");
        recitationManager.addRecitation("R02", "McKenna", "Wed 3:30pm-4:23pm", "Old CS 2114", "Jane Doe", "Joe Shmo");
        scheduleManager.addSchedule("Lecture", "2/14/2017", "Lecture 3", "Event Programming");
        teamsManager.addTeam("Atomic Comics", "552211", "ffffff", "http://atomicomic.com");
        studentsManager.addStudent("Beau", "Brummell", "Atomic Comics", "Lead Designer");
        
        JsonArrayBuilder courseInfoArrayBuilder = Json.createArrayBuilder();
        
        JsonObject cInfoJson = Json.createObjectBuilder()
                .add(JSON_COURSE_SUBJECT, "CSE")
                .add(JSON_COURSE_NUMBER, "219")
                .add(JSON_COURSE_SEMESTER, "Fall")
                .add(JSON_COURSE_YEAR, "2017")
                .add(JSON_COURSE_TITLE, "Computer Science III")
                .add(JSON_COURSE_INSTRUCTOR, "Richard McKenna")
                .add(JSON_COURSE_INSTRUCTOR_HOME, "http://www.cs.stonybrook.edu/~richard").build();
        courseInfoArrayBuilder.add(cInfoJson);
        
        JsonArray courseSiteInfoArray = courseInfoArrayBuilder.build();
        
        JsonArrayBuilder calendarBoundaryBuilder = Json.createArrayBuilder();
        
        JsonObject calendarInfoJson = Json.createObjectBuilder()
                .add(JSON_CALENDAR_START, "4/22/2012")
                .add(JSON_CALENDAR_END, "5/22/2012").build();
        calendarBoundaryBuilder.add(calendarInfoJson);
        
        JsonArray calendarBoundariesArray = calendarBoundaryBuilder.build();
        
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
	ArrayList<TimeSlot> officeHours = testArrayList;
	for (TimeSlot ts : testArrayList) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_COURSE_INFO, courseSiteInfoArray)
                .add(JSON_SITE_PAGES, courseSitesArray)
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .add(JSON_RECITATIONS, recitationsArray)
                .add(JSON_CALENDAR_BOUNDARIES, calendarBoundariesArray)
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
	OutputStream os = new FileOutputStream(".\\work\\SiteSaveTest.json");
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(".\\work\\SiteSaveTest.json");
	pw.write(prettyPrinted);
	pw.close();
    }
}
