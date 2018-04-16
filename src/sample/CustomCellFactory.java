package sample;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sample.Task;

public class CustomCellFactory implements Callback<ListView<Task>,ListCell<Task>> {
    final Tooltip tip = new Tooltip();
    @Override
    public ListCell<Task> call(ListView param) {
        TextFieldListCell<Task> cell = new TextFieldListCell() {
            @Override
            public void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);

                //tip.setText("TEST");
                tip.setWrapText(true);
                setTooltip(tip);
                if (!empty && item != null) {
                    System.out.println("updating item: "+item.toString());
                    tip.setText(((Task)item).description);
                    if(((Task) item).getPriority()=="Low"){
                        setText(((Task) item).getTitle());
                        setTextFill(Color.GREEN);
                        //setStyle("-fx-background-color: green");
                    } else if(((Task) item).getPriority()=="Normal"){
                        setText(((Task) item).getTitle());
                        //setStyle("-fx-background-color: yellow");
                        setTextFill(Color.DARKGOLDENROD);
                    } else if(((Task) item).getPriority()=="High"){
                        setText(((Task) item).getTitle());
                        //setStyle("-fx-background-color: red");
                        setTextFill(Color.RED);
                    }
                } else {
                    setText(null);
                }
            }
            @Override
            public void commitEdit(Object newName) {
                ((Task)getItem()).setTitle((String)newName);
                super.commitEdit(getItem());
            }
        };
        cell.setConverter(new StringConverter() {
            @Override
            public String toString(Object person) {
                return ((Task)person).getTitle();
            }
            @Override
            public Object fromString(String string) {
                return string;
            }
        });
        return cell;
    }
}