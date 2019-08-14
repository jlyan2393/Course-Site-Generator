/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jason Lyan
 */
public class SitePages<E extends Comparable<E>> implements Comparable<E>  {
    private final BooleanProperty usePage;
    private final StringProperty navbarTitle;
    private final StringProperty fileName;
    private final StringProperty script;
    
    public SitePages(String navbarTitle, String fileName, String script){
        usePage = new SimpleBooleanProperty(true);
        this.navbarTitle = new SimpleStringProperty(navbarTitle);
        this.fileName = new SimpleStringProperty(fileName);
        this.script = new SimpleStringProperty(script);
    }

    public BooleanProperty getUsePage() {
        return usePage;
    }

    public boolean getIsUsePage() {
        return usePage.get();
    }
    
    public void setUsePage(boolean usePage) {
        this.usePage.set(usePage);
    }

    public String getNavbarTitle() {
        return navbarTitle.get();
    }

    public void setNavbarTitle(String navbarTitle) {
        this.navbarTitle.set(navbarTitle);
    }

    public String getFileName() {
        return fileName.get();
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public String getScript() {
        return script.get();
    }

    public void setScript(String script) {
        this.script.set(script);
    }

    @Override
    public int compareTo(E otherSitePage) {
        return getNavbarTitle().compareTo(((SitePages)otherSitePage).getNavbarTitle());
    }
}
