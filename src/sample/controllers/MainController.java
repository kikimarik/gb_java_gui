package sample.controllers;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.models.Response;
import sample.models.Template;

import java.io.*;
import java.util.*;

public class MainController extends VBox {

    private Stage stage;
    @FXML
    private TextField openedElement;
    private Template activeTemplate;
    @FXML
    private TextArea activeArea;
    @FXML
    private Button generateButton = new Button("Generate");
    private Pane inputParamPane;
    @FXML
    private ArrayList<TextField> inputParamList = new ArrayList<>(0);

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private MenuItem AboutButton;

    @FXML
    private AnchorPane templateList;

    @FXML
    private MenuItem saveAsButton;

    @FXML
    private MenuItem openButton;

    @FXML
    private MenuItem exportButton;

    @FXML
    private MenuItem closeButton;

    @FXML
    private AnchorPane paramList;

    @FXML
    private MenuItem newButton;

    @FXML
    private Font x1;

    @FXML
    private AnchorPane templateContent;

    @FXML
    private Color x2;

    @FXML
    private Font x3;

    @FXML
    private MenuItem saveButton;

    @FXML
    private Color x4;

    @FXML
    private MenuItem importButton;

    @FXML
    void initialize() {
        // TODO refactor all handlers and add new handlers
        openButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select the template file");
            fileChooser.setSelectedExtensionFilter(
                    new FileChooser.ExtensionFilter("JSON files", "*.json")
            );
            File selectedFile = fileChooser.showOpenDialog(this.stage);
            FileReader reader;
            try {
                reader = new FileReader(selectedFile);

                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder lineBuilder = new StringBuilder();
                String line;

                do {
                    line = bufferedReader.readLine();
                    if (line != null) {
                        lineBuilder.append(line);
                    }
                }
                while (line != null);
                String json = lineBuilder.toString();
                this.activeTemplate = Template.parseJSON(json);
                this.activeTemplate.activeFile = selectedFile;
                this.openedElement = new TextField(activeTemplate.getName());
                this.templateList.getChildren().add(this.openedElement);
                this.activeArea = new TextArea(activeTemplate.getBody());
                this.templateContent.getChildren().add(this.activeArea);
                this.saveButton.setDisable(false);
                //this.saveAsButton.setDisable(false);
                this.closeButton.setDisable(false);
                this.newButton.setDisable(true);
                this.openButton.setDisable(true);

                this.inputParamPane = new Pane();
                double layoutY = 0;
                for (String param :
                        this.activeTemplate.getParams()) {
                    TextField field = new TextField(param);
                    this.inputParamList.add(field);
                    field.setLayoutY(layoutY);
                    layoutY += 30;
                    this.inputParamPane.getChildren().add(field);
                }
                this.paramList.getChildren().add(this.inputParamPane);
                if (layoutY > 0) {
                    this.generateButton.setLayoutY(layoutY + 30);
                    this.paramList.getChildren().add(this.generateButton);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        newButton.setOnAction(event -> {
            this.activeTemplate = Template.parseJSON(Template.getEmptyJSON());
            this.openedElement = new TextField(activeTemplate.getName());
            this.templateList.getChildren().add(this.openedElement);
            this.activeArea = new TextArea(activeTemplate.getBody());
            this.templateContent.getChildren().add(this.activeArea);
            this.saveButton.setDisable(false);
            //this.saveAsButton.setDisable(false);
            this.closeButton.setDisable(false);
            this.openButton.setDisable(true);
            this.newButton.setDisable(true);
        });
        closeButton.setOnAction(event -> {
            this.templateContent.getChildren().clear();
            if (this.openedElement != null) {
                this.templateList.getChildren().remove(this.openedElement);
                this.paramList.getChildren().removeAll(this.inputParamPane, this.generateButton);
            }
            this.saveButton.setDisable(true);
            this.saveAsButton.setDisable(true);
            this.closeButton.setDisable(true);
            this.openButton.setDisable(false);
            this.newButton.setDisable(false);
            Template.instance = null;
        });
        saveButton.setOnAction(event -> {
            this.activeTemplate.setName(this.openedElement.getText());
            this.activeTemplate.setBody(this.activeArea.getText());
            this.activeTemplate.writeToFile();
        });
        generateButton.setOnAction(event -> {
            TextArea result = new TextArea(Response.generate(this.activeArea.getText(), this.inputParamList));
            result.setLayoutY(this.activeArea.getBoundsInLocal().getMaxY() + 20);
            result.setEditable(false);
            this.templateContent.getChildren().add(result);
        });
    }
}

