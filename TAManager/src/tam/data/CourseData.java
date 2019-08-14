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
public class CourseData implements AppDataComponent {
    TAManagerApp app;
    ObservableList<SitePages> sitePages;
    
    public CourseData(TAManagerApp initApp) {
        app = initApp;
        sitePages = FXCollections.observableArrayList();
    }
    
    @Override
    public void resetData() {
        sitePages.clear();
    }
    
    public ObservableList getCourseData(){
        return sitePages;
    }
    
    public SitePages getSitePage(String testName){
        for(SitePages s: sitePages){
            if(s.getFileName().equals(testName)){
                return s;
            }
        }
        return null;
    }
    
    public void addSitePage(String navbarTitle, String fileName, String script){
        SitePages sitePage = new SitePages(navbarTitle, fileName, script);
        sitePages.add(sitePage);
        Collections.sort(sitePages);
    }
}
