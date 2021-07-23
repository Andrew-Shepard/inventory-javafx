package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditItemPageController implements Initializable {
    ObservableList<String> completionChoiceList = FXCollections.observableArrayList("Complete","Incomplete");
    @FXML private TextField valueTextField;
    @FXML private TextField serialTextField;
    @FXML private TextArea nameTextArea;
    User u = new User();
    void initData(User u) {
        this.u = u;
        valueTextField.setText(u.getInventory().get(u.active_item_index).getValue().toString());
        serialTextField.setText(u.getInventory().get(u.active_item_index).getSerial_number());
        nameTextArea.setText(u.getInventory().get(u.active_item_index).getName());
    }
    public void ReturnToMainPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        MainPageController controller = loader.getController();
        controller.initData(u);

        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //sets up columns
    }

    public void SaveItem(ActionEvent actionEvent) {
        //validate input
        Boolean valueisNumber = Boolean.TRUE;
        Boolean serialisFormatted= Boolean.TRUE; // Is length 10 and does not contain special characters
        try{
            Double.parseDouble(valueTextField.getText());
        } catch (NumberFormatException e){
            valueisNumber = Boolean.FALSE;
        }
        Boolean serialContainsSpecialCharacters = Pattern.matches("[^A-Za-z0-9]",serialTextField.getText());

        if(valueisNumber == Boolean.TRUE )
        u.getInventory().get(u.active_item_index).setValue(Double.parseDouble(valueTextField.getText()));
        u.getInventory().get(u.active_item_index).setName(nameTextArea.getText());
        u.getInventory().get(u.active_item_index).setSerial_number(serialTextField.getText());
    }
}
