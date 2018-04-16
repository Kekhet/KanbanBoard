package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.util.StringConverter;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public ListView<Task> toDoList;
    @FXML
    private ListView<Task> doneList;
    @FXML
    private Button newTaskButton;
    @FXML
    private MenuItem closeButton;
    @FXML
    private ListView<Task> inProgList;
    @FXML
    private MenuItem aboutButton;
    @FXML

    public ContextMenu contextMenu = new ContextMenu();
    private Task dragTask;
    int dragItemIndex;

    public void newTaskAction(ActionEvent event) throws Exception  {
        Stage newTaskStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/newTaskDialog.fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        newTaskStage.setTitle("Add new task");
        newTaskStage.setScene(new Scene(parent, 620, 400));
        newTaskStage.setResizable(false);

        newTaskDialogController controllerAlertBox = loader.getController();
        controllerAlertBox.setToDoList(this.toDoList);
        newTaskStage.show();
    }

    public void initialize(URL location, ResourceBundle resources) {
        toDoList.setCellFactory(new CustomCellFactory());
        inProgList.setCellFactory(new CustomCellFactory());
        doneList.setCellFactory(new CustomCellFactory());

        contextMenu.getItems().addAll(new MenuItem("Edit"), new MenuItem("Delete"));
        toDoList.setContextMenu(contextMenu);
        inProgList.setContextMenu(contextMenu);
        doneList.setContextMenu(contextMenu);



    }

    public void contextMenuRequested(ContextMenuEvent contextMenuEvent) {
        ListView<Task> list = (ListView<Task>) contextMenuEvent.getSource();

        contextMenu.getItems().get(1).setOnAction((event -> {
            list.getItems().remove(list.getSelectionModel().getSelectedIndex());
        }));

        contextMenu.getItems().get(0).setOnAction((event -> {
            Task selectedItem = list.getSelectionModel().getSelectedItem();

            Stage editStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/newTaskDialog.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent parent = loader.getRoot();
            editStage.setTitle("Edit");
            editStage.setScene(new Scene(parent, 272, 400));

            newTaskDialogController taskCreator = loader.getController();
            taskCreator.titleTextField.setText(selectedItem.title);
            taskCreator.priorityComboBox.getSelectionModel().select(selectedItem.getPriority());
            taskCreator.expDatePicker.setValue(selectedItem.date);
            taskCreator.descriptionTextArea.setText(selectedItem.description);
            taskCreator.setToDoList(list, selectedItem);

           // list.getItems().remove(list.getSelectionModel().getSelectedIndex());

            editStage.show();
        }));
    }

    public void handleDragDetect(MouseEvent mouseEvent) {
        ListView<Task> listToTakeFrom = (ListView<Task>) mouseEvent.getSource();
        dragTask = listToTakeFrom.getSelectionModel().getSelectedItem();
        if (dragTask == null) {
            mouseEvent.consume();
            return;
        }
        dragItemIndex = listToTakeFrom.getSelectionModel().getSelectedIndex();
        Dragboard dragboard = listToTakeFrom.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putString("Task");
        dragboard.setContent(content);
        mouseEvent.consume();
    }

    public void handleDragDropped(DragEvent dragEvent) {
        if (dragTask == null)
            return;
        ListView<Task> listToDrop = (ListView<Task>) dragEvent.getSource();
        ListView<Task> listToRemoveFrom = (ListView<Task>) dragEvent.getGestureSource();
        if (listToDrop == listToRemoveFrom)
            return;
        listToDrop.getItems().add(dragTask);
        listToRemoveFrom.getItems().remove(dragItemIndex);

        dragTask = null;
        dragEvent.consume();
    }

    public void handleDragOver(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.ANY);
        dragEvent.consume();
    }

    @FXML
    void closeButtonAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void getAboutDialog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Kanban board");
        alert.setContentText("Copyright Jakub Wenda");
        alert.showAndWait();
    }
}

