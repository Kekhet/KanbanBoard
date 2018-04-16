package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import sample.Controller;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class newTaskDialogController implements Initializable {

    @FXML
    public TextField titleTextField;

    @FXML
    public ComboBox<String> priorityComboBox;

    @FXML
    public DatePicker expDatePicker;

    @FXML
    public Button addTaskButton;

    @FXML
    public TextArea descriptionTextArea;

    private ListView<Task> toDoList = new ListView<>();

    private Task task;

    @FXML
    void addTaskPressed(ActionEvent event) {
        String title = titleTextField.getText();
        LocalDate expDate = expDatePicker.getValue();
        String prio = priorityComboBox.getValue();
        String description = descriptionTextArea.getText();
        Task buffer = new Task(title,expDate,prio,description);
       // Task buffer = new Task("title",expDatePicker.getValue(),"normal","a");
        toDoList.getItems().add(buffer);

        Stage stage=(Stage)addTaskButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        priorityComboBox.getItems().addAll("High", "Normal", "Low");
    }

    public void setToDoList(ListView<Task> toDoList) {
        this.toDoList = toDoList;
    }

    //###########
    public void setToDoList(ListView<Task> toDoList, Task task) {
        this.toDoList = toDoList;
        this.task = task;

        addTaskButton.setText("Edit");
        addTaskButton.setOnAction(this::editTask);
    }

    private void editTask(ActionEvent actionEvent){
        task.title = titleTextField.getText();
        task.date = expDatePicker.getValue();
        task.priority = priorityComboBox.getValue();
        task.description = descriptionTextArea.getText();
        toDoList.refresh();
        Stage stage=(Stage)addTaskButton.getScene().getWindow();
        stage.close();
    }

}
