package hellofx;

import java.io.File;

public class FileHandler {

    public void processFile(File file) {
        String name = file.getName().toLowerCase();
        if (name.endsWith(".csv")) {
            System.out.println("Processing CSV...");
            // parseCSV(file);
        } else if (name.endsWith(".xml")) {
            System.out.println("Processing XML...");
            // parseXML(file);
        } else if (name.endsWith(".xlsx")) {
            System.out.println("Processing Excel...");
            // parseExcel(file);
        } else if (name.endsWith(".docx")) {
            System.out.println("Processing Word...");
            // parseWord(file);
        } else {
            System.out.println("Unsupported file type.");
        }
    }
}
