package djf.components;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This interface provides the structure for file components in
 * our applications. This lets us call save and load methods from
 * the framework.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public interface AppFileComponent {

    /**
     * This function must be overridden in the actual component and would
     * write app data to a file in the necessary format.
     */
    public void saveData(AppDataComponent data, AppDataComponent cdata, AppDataComponent rdata, AppDataComponent schedData, AppDataComponent teamData, AppDataComponent studData, String filePath) throws IOException, FileNotFoundException;
    public void saveCourseData(AppDataComponent data, String filePath) throws IOException, FileNotFoundException;
    public void saveTAData(AppDataComponent data, String filePath) throws IOException, FileNotFoundException;
    public void saveRecData(AppDataComponent data, String filePath) throws IOException, FileNotFoundException;
    public void saveSchedData(AppDataComponent data, String filePath) throws IOException, FileNotFoundException;
    public void saveTeamAndStudentData(AppDataComponent tData, AppDataComponent sData, String filePath) throws IOException, FileNotFoundException;
    /**
     * This function must be overridden in the actual component and would
     * read app data from a file in the necessary format.
     */
    public void loadData(AppDataComponent data, AppDataComponent cdata, AppDataComponent rdata, AppDataComponent schedData, AppDataComponent teamData, AppDataComponent studData, String filePath) throws IOException;

    /**
     * This function must be overridden in the actual component and would
     * be used for exporting app data into another format.
     */
    public void exportData(AppDataComponent data, AppDataComponent cdata, AppDataComponent rdata, AppDataComponent schedData, AppDataComponent teamData, AppDataComponent studData, String filePath) throws IOException;

    /**
     * This function must be overridden in the actual component and would
     * be used for importing app data from another format.
     */
    public void importData(AppDataComponent data, AppDataComponent cdata, AppDataComponent rdata, AppDataComponent schedData, AppDataComponent teamData, AppDataComponent studData, String filePath) throws IOException;
}
