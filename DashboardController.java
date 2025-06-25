package hellofx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;

public class DashboardController {
    @FXML
    private Button uploadBtn;

    @FXML
    private Label fileNameLabel;

    private final FileHandler fileHandler = new FileHandler();
    private final FinancialService financialService = new FinancialService();

    @FXML
    private void initialize() {
        uploadBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"),
                new FileChooser.ExtensionFilter("Word Files", "*.docx"),
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg"),
                new FileChooser.ExtensionFilter("XML Files", "*.xml")
            );
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                fileNameLabel.setText("Selected: " + file.getName());
                fileHandler.processFile(file);
                financialService.calculateMetrics(file);
            }
        });
    }
}


