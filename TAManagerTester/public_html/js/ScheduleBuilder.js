// DATA TO LOAD
var startMondayDate;
var endFridayDate;
var daysInMonth;
var isLeapYear;

function ScheduleItem(sType, sDate, sTitle, sTopic) {
	this.type = sType;
    this.date = sDate;
    this.title = sTitle;
    this.topic = sTopic;
}
function ScheduleDate(sMonth, sDay) {
    this.month = sMonth;
    this.day = sDay;
}
function initSchedule() {
    initDateData();
    var dataFile = "./js/ScheduleData.json";
    loadData(dataFile);
}
function loadData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
	loadJSONData(json);
        buildScheduleTable();
    });
}
function initDateData() {
    var currentYear = new Date().getFullYear();
    daysInMonth = new Array();
    if ((currentYear %4) == 0) {
        isLeapYear = true;
        daysInMonth[2] = 29;
    }
    else {
        isLeapYear = false;
        daysInMonth[2] = 28;
    }
    daysInMonth[1] = 31;
    daysInMonth[3] = 31;
    daysInMonth[4] = 30;
    daysInMonth[5] = 31;
    daysInMonth[6] = 30;
    daysInMonth[7] = 31;
    daysInMonth[8] = 31;
    daysInMonth[9] = 30;
    daysInMonth[10] = 31;
    daysInMonth[11] = 30;
    daysInMonth[12] = 31;
}
function loadJSONData(data) {
    // FIRST GET THE STARTING AND ENDING DATES
    var startingMondayMonth = parseInt(data.startingMondayMonth, 10);
    var startingMondayDay = parseInt(data.startingMondayDay, 10);
    startingMondayDate = new ScheduleDate(startingMondayMonth, startingMondayDay);
    var endingFridayMonth = parseInt(data.endingFridayMonth, 10);
    var endingFridayDay = parseInt(data.endingFridayDay, 10);
    endingFridayDate = new ScheduleDate(endingFridayMonth, endingFridayDay);
}

function buildScheduleTable() {
    var countMonth = startingMondayDate.month;
    var countDay = startingMondayDate.day;
    var countDate = new ScheduleDate(countMonth, countDay);
    var table = $("#schedule_table");
    while (firstDateIsBeforeSecond(countDate, endingFridayDate)) {
        table.append(
                  "<tr>"
                + "<th class=\"sch\">MONDAY</th>"
                + "<th class=\"sch\">TUESDAY</th>"
                + "<th class=\"sch\">WEDNESDAY</th>"
                + "<th class=\"sch\">THURSDAY</th>"
                + "<th class=\"sch\">FRIDAY</th>"
                + "</tr>");
        table.append("<tr>");
        for (var i = 0; i < 5; i++) {
            table.append(
                    "<td class=\"sch\" id=\"" + countDate.month + "_" + countDate.day + "\"><strong>" + countDate.month + "/" + countDate.day + "</strong><br /></td>");
            incDate(countDate);
        }
        table.append("</tr>");
        incDate(countDate);
        incDate(countDate);
    }
}

function incDate(dateToInc) {
    dateToInc.day++;
    var maxDays = daysInMonth[dateToInc.month];
    if (dateToInc.day > maxDays) {
        dateToInc.day = 1;
        dateToInc.month++;
    }
}

function firstDateIsBeforeSecond(firstDate, secondDate) {
    if (firstDate.month < secondDate.month)
        return true;
    if ((firstDate.month === secondDate.month)
        && (firstDate.day < secondDate.day))
        return true;
    return false;
}

