/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.test_bed;

import static djf.settings.AppStartupConstants.APP_PROPERTIES_FILE_NAME;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
import static tam.test_bed.TestSave.JSON_COURSE_SUBJECT;

/**
 *
 * @author Jase
 */
public class DataLoadingTester {
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
    
    String startHour;
    String endHour;
    String subject;
    String number;
    String semester;
    String year;
    String title;
    String insName;
    String insHome;
    String calStart;
    String calEnd;
    
    TAManagerApp app;
    CourseData cdataManager;
    TAData dataManager;
    RecitationsData recitationManager;
    ScheduleData scheduleManager;
    TeamsData teamsManager;
    StudentsData studentsManager;
    ObservableList<SitePages> sitePages;
    ObservableList<TeachingAssistant> teachingAssistants;
    ObservableList<Recitations> recitations;
    ObservableList<Schedule> schedules;
    ObservableList<Teams> teams;
    ObservableList<Students> students;
    ArrayList<TimeSlot> testArrayList;
    
    public DataLoadingTester() throws IOException {
        
        app = new TAManagerApp();
        app.loadProperties(APP_PROPERTIES_FILE_NAME);
        cdataManager = new CourseData(app);
        dataManager = new TAData(app);
        recitationManager = new RecitationsData(app);
        scheduleManager = new ScheduleData(app);
        teamsManager = new TeamsData(app);
        studentsManager = new StudentsData(app);

	// LOAD THE JSON FILE WITH ALL THE DATA
	InputStream is = new FileInputStream(".\\work\\SiteSaveTest.json");
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();

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
        testArrayList = new ArrayList<TimeSlot>();
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            testArrayList.add(new TimeSlot(day, time, name));
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
        
        sitePages = cdataManager.getCourseData();
        teachingAssistants = dataManager.getTeachingAssistants();
        recitations = recitationManager.getRecitationsData();
        schedules = scheduleManager.getScheduleData();
        teams = teamsManager.getTeamsData();
        students = studentsManager.getStudentsData();
        
        startHour = json.getString(JSON_START_HOUR);
        endHour = json.getString(JSON_END_HOUR);
        subject = cInfoJson.getString(JSON_COURSE_SUBJECT);
        number = cInfoJson.getString(JSON_COURSE_NUMBER);
        semester = cInfoJson.getString(JSON_COURSE_SEMESTER);
        year = cInfoJson.getString(JSON_COURSE_YEAR);
        title = cInfoJson.getString(JSON_COURSE_TITLE);
        insName = cInfoJson.getString(JSON_COURSE_INSTRUCTOR);
        insHome = cInfoJson.getString(JSON_COURSE_INSTRUCTOR_HOME);
        calStart = calendarInfoJson.getString(JSON_CALENDAR_START);
        calEnd = calendarInfoJson.getString(JSON_CALENDAR_END);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void checkIsUsedPage(){
        System.out.println("* DataLoadingTester: checkIsUsedPage()");
        SitePages sTest = new SitePages("", "", "");
        for(SitePages s: sitePages){
            if(s.getIsUsePage()){
                sTest = s;
            }
        }
        assertEquals(true, sTest.getIsUsePage());
    }
    
    @Test
    public void checkNavbarTitle(){
        System.out.println("* DataLoadingTester: checkNavbarTitle()");
        SitePages sTest = new SitePages("", "", "");
        for(SitePages s: sitePages){
            if(s.getNavbarTitle().equals("Home")){
                sTest = s;
            }
        }
        assertEquals("Home", sTest.getNavbarTitle());
    }
    
    @Test
    public void checkFileName(){
        System.out.println("* DataLoadingTester: checkFileName()");
        SitePages sTest = new SitePages("", "", "");
        for(SitePages s: sitePages){
            if(s.getFileName().equals("index.html")){
                sTest = s;
            }
        }
        assertEquals("index.html", sTest.getFileName());
    }
    
    @Test
    public void checkScript(){
        System.out.println("* DataLoadingTester: checkScript()");
        SitePages sTest = new SitePages("", "", "");
        for(SitePages s: sitePages){
            if(s.getScript().equals("HomeBuilder.js")){
                sTest = s;
            }
        }
        assertEquals("HomeBuilder.js", sTest.getScript());
    }
    
    @Test
    public void checkStartHour(){
        System.out.println("* DataLoadingTester: checkStartHour()");
        assertEquals(9, dataManager.getStartHour());
    }
    
    @Test
    public void checkEndHour(){
        System.out.println("* DataLoadingTester: checkEndHour()");
        assertEquals(20, dataManager.getEndHour());
    }
    
    @Test
    public void checkIsUndergrad(){
        System.out.println("* DataLoadingTester: checkIsUndergrad()");
        TeachingAssistant tTest = new TeachingAssistant("", "");
        for(TeachingAssistant t: teachingAssistants){
            if(!t.getIsUndergrad()){
                tTest = t;
            }
        }
        assertEquals(false, tTest.getIsUndergrad());
    }
    
    @Test
    public void checkTAName(){
        System.out.println("* DataLoadingTester: checkTAName()");
        TeachingAssistant tTest = new TeachingAssistant("", "");
        for(TeachingAssistant t: teachingAssistants){
            if(t.getName().equals("Bob")){
                tTest = t;
            }
        }
        assertEquals("Bob", tTest.getName());
    }
    
    @Test
    public void checkTAEmail(){
        System.out.println("* DataLoadingTester: checkTAEmail()");
        TeachingAssistant tTest = new TeachingAssistant("", "");
        for(TeachingAssistant t: teachingAssistants){
            if(t.getEmail().equals("bob@stonybrook.edu")){
                tTest = t;
            }
        }
        assertEquals("bob@stonybrook.edu", tTest.getEmail());
    }
    
    @Test
    public void checkOfficeHoursDay(){
        System.out.println("* DataLoadingTester: checkOfficeHoursDay()");
        TimeSlot tsTest = new TimeSlot("", "", "");
        for(TimeSlot t: testArrayList){
            if(t.getDay().equals("WEDNESDAY")){
                tsTest = t;
            }
        }
        assertEquals("WEDNESDAY", tsTest.getDay());
    }
    
    @Test
    public void checkOfficeHoursTime(){
        System.out.println("* DataLoadingTester: checkOfficeHoursTime()");
        TimeSlot tsTest = new TimeSlot("", "", "");
        for(TimeSlot t: testArrayList){
            if(t.getTime().equals("10_00am")){
                tsTest = t;
            }
        }
        assertEquals("10_00am", tsTest.getTime());
    }
    
    @Test
    public void checkOfficeHoursName(){
        System.out.println("* DataLoadingTester: checkOfficeHoursName()");
        TimeSlot tsTest = new TimeSlot("", "", "");
        for(TimeSlot t: testArrayList){
            if(t.getName().equals("Bob")){
                tsTest = t;
            }
        }
        assertEquals("Bob", tsTest.getName());
    }
    
    @Test
    public void checkRecitationSection(){
        System.out.println("* DataLoadingTester: checkRecitationSection()");
        Recitations rTest = new Recitations("", "", "", "", "", "");
        for(Recitations r: recitations){
            if(r.getSection().equals("R02")){
                rTest = r;
            }
        }
        assertEquals("R02", rTest.getSection());
    }
    
    @Test
    public void checkRecitationInstructor(){
        System.out.println("* DataLoadingTester: checkRecitationInstructor()");
        Recitations rTest = new Recitations("", "", "", "", "", "");
        for(Recitations r: recitations){
            if(r.getInstructor().equals("McKenna")){
                rTest = r;
            }
        }
        assertEquals("McKenna", rTest.getInstructor());
    }
    
    @Test
    public void checkRecitationDayTime(){
        System.out.println("* DataLoadingTester: checkRecitationDayTime()");
        Recitations rTest = new Recitations("", "", "", "", "", "");
        for(Recitations r: recitations){
            if(r.getDayandtime().equals("Wed 3:30pm-4:23pm")){
                rTest = r;
            }
        }
        assertEquals("Wed 3:30pm-4:23pm", rTest.getDayandtime());
    }
    
    @Test
    public void checkRecitationLocation(){
        System.out.println("* DataLoadingTester: checkRecitationLocation()");
        Recitations rTest = new Recitations("", "", "", "", "", "");
        for(Recitations r: recitations){
            if(r.getLocation().equals("Old CS 2114")){
                rTest = r;
            }
        }
        assertEquals("Old CS 2114", rTest.getLocation());
    }
    
    @Test
    public void checkRecitationTA(){
        System.out.println("* DataLoadingTester: checkRecitationTA()");
        Recitations rTest = new Recitations("", "", "", "", "", "");
        for(Recitations r: recitations){
            if(r.getTa().equals("Jane Doe")){
                rTest = r;
            }
        }
        assertEquals("Jane Doe", rTest.getTa());
    }
    
    @Test
    public void checkRecitationTA2(){
        System.out.println("* DataLoadingTester: checkRecitationTA2()");
        Recitations rTest = new Recitations("", "", "", "", "", "");
        for(Recitations r: recitations){
            if(r.getTa2().equals("Joe Shmo")){
                rTest = r;
            }
        }
        assertEquals("Joe Shmo", rTest.getTa2());
    }
    
    @Test
    public void checkScheduleType(){
        System.out.println("* DataLoadingTester: checkScheduleType()");
        Schedule sTest = new Schedule("", "", "", "");
        for(Schedule s: schedules){
            if(s.getType().equals("Lecture")){
                sTest = s;
            }
        }
        assertEquals("Lecture", sTest.getType());
    }
    
    @Test
    public void checkScheduleDate(){
        System.out.println("* DataLoadingTester: checkScheduleDate()");
        Schedule sTest = new Schedule("", "", "", "");
        for(Schedule s: schedules){
            if(s.getDate().equals("2/14/2017")){
                sTest = s;
            }
        }
        assertEquals("2/14/2017", sTest.getDate());
    }
    
    @Test
    public void checkScheduleTitle(){
        System.out.println("* DataLoadingTester: checkScheduleTitle()");
        Schedule sTest = new Schedule("", "", "", "");
        for(Schedule s: schedules){
            if(s.getTitle().equals("Lecture 3")){
                sTest = s;
            }
        }
        assertEquals("Lecture 3", sTest.getTitle());
    }
    
    @Test
    public void checkScheduleTopic(){
        System.out.println("* DataLoadingTester: checkScheduleTopic()");
        Schedule sTest = new Schedule("", "", "", "");
        for(Schedule s: schedules){
            if(s.getTopic().equals("Event Programming")){
                sTest = s;
            }
        }
        assertEquals("Event Programming", sTest.getTopic());
    }
    
    @Test
    public void checkTeamName(){
        System.out.println("* DataLoadingTester: checkTeamName()");
        Teams tTest = new Teams("", "", "", "");
        for(Teams t: teams){
            if(t.getName().equals("Atomic Comics")){
                tTest = t;
            }
        }
        assertEquals("Atomic Comics", tTest.getName());
    }
    
    @Test
    public void checkTeamColor(){
        System.out.println("* DataLoadingTester: checkTeamColor()");
        Teams tTest = new Teams("", "", "", "");
        for(Teams t: teams){
            if(t.getColor().equals("552211")){
                tTest = t;
            }
        }
        assertEquals("552211", tTest.getColor());
    }
    
    @Test
    public void checkTeamTextColor(){
        System.out.println("* DataLoadingTester: checkTeamTextColor()");
        Teams tTest = new Teams("", "", "", "");
        for(Teams t: teams){
            if(t.getTextColor().equals("ffffff")){
                tTest = t;
            }
        }
        assertEquals("ffffff", tTest.getTextColor());
    }
    
    @Test
    public void checkTeamLink(){
        System.out.println("* DataLoadingTester: checkTeamLink()");
        Teams tTest = new Teams("", "", "", "");
        for(Teams t: teams){
            if(t.getLink().equals("http://atomicomic.com")){
                tTest = t;
            }
        }
        assertEquals("http://atomicomic.com", tTest.getLink());
    }
    
    @Test
    public void checkStudentsFirstName(){
        System.out.println("* DataLoadingTester: checkStudentsFirstName()");
        Students sTest = new Students("", "", "", "");
        for(Students s: students){
            if(s.getFirstName().equals("Beau")){
                sTest = s;
            }
        }
        assertEquals("Beau", sTest.getFirstName());
    }
    
    @Test
    public void checkStudentsLastName(){
        System.out.println("* DataLoadingTester: checkStudentsLastName()");
        Students sTest = new Students("", "", "", "");
        for(Students s: students){
            if(s.getLastName().equals("Brummell")){
                sTest = s;
            }
        }
        assertEquals("Brummell", sTest.getLastName());
    }
    
    @Test
    public void checkStudentsTeamName(){
        System.out.println("* DataLoadingTester: checkStudentsTeamName()");
        Students sTest = new Students("", "", "", "");
        for(Students s: students){
            if(s.getTeam().equals("Atomic Comics")){
                sTest = s;
            }
        }
        assertEquals("Atomic Comics", sTest.getTeam());
    }
    
    @Test
    public void checkStudentsRole(){
        System.out.println("* DataLoadingTester: checkStudentsRole()");
        Students sTest = new Students("", "", "", "");
        for(Students s: students){
            if(s.getRole().equals("Lead Designer")){
                sTest = s;
            }
        }
        assertEquals("Lead Designer", sTest.getRole());
    }
    
    @Test
    public void checkCourseSubject(){
        System.out.println("* DataLoadingTester: checkCourseSubject()");
        assertEquals("CSE", subject);
    }
    
    @Test
    public void checkCourseNumber(){
        System.out.println("* DataLoadingTester: checkCourseNumber()");
        assertEquals("219", number);
    }
    
    @Test
    public void checkCourseSemester(){
        System.out.println("* DataLoadingTester: checkCourseSemester()");
        assertEquals("Fall", semester);
    }
    
    @Test
    public void checkCourseYear(){
        System.out.println("* DataLoadingTester: checkCourseYear()");
        assertEquals("2017", year);
    }
    
    @Test
    public void checkCourseTitle(){
        System.out.println("* DataLoadingTester: checkCourseTitle()");
        assertEquals("Computer Science III", title);
    }
    
    @Test
    public void checkCourseInstructor(){
        System.out.println("* DataLoadingTester: checkCourseInstructor()");
        assertEquals("Richard McKenna", insName);
    }
    
    @Test
    public void checkCourseInstructorHome(){
        System.out.println("* DataLoadingTester: checkCourseInstructorHome()");
        assertEquals("http://www.cs.stonybrook.edu/~richard", insHome);
    }
    
    @Test
    public void checkCalendarStartBound(){
        System.out.println("* DataLoadingTester: checkCalendarStartBound()");
        assertEquals("4/22/2012", calStart);
    }
    
    @Test
    public void checkCalendarEndBound(){
        System.out.println("* DataLoadingTester: checkCalendarEndBound()");
        assertEquals("5/22/2012", calEnd);
    }
}
