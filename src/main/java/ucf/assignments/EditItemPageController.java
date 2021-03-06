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
    ObservableList<String> completionChoiceList = FXCollections.observableArrayList("Complete", "Incomplete");
    @FXML
    private TextField valueTextField;
    @FXML
    private TextField serialTextField;
    @FXML
    private TextArea nameTextArea;
    @FXML private Label editItemErrorLabel;
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
    public void initialize(URL url, ResourceBundle rb) {
        //sets up columns
    }

    public void SaveItem(ActionEvent actionEvent) {
        //validate input
        String errorMessage = "Error:"; //Made so multiple errors can be thrown
        //value validate
        if(u.validateValue(valueTextField.getText()) == false){ // returns true if a number
            //throw an error if the input is not validated
            errorMessage = errorMessage + "\nValue must be a number";
            editItemErrorLabel.setText(errorMessage);
        }
        //serial validate
        if(u.validateSerial(serialTextField.getText()) == false){
            //throw an error if the input is not validated
            errorMessage = errorMessage + "\nSerial must be alphanumeric, unique\nand 10 characters";
            editItemErrorLabel.setText(errorMessage);
        }
        //name validate
        if(u.validateName(nameTextArea.getText()) == false){
            errorMessage = errorMessage + "\nName must be between 2 and 256\nlength";
            editItemErrorLabel.setText(errorMessage);
        }
        //set the value in the inventory if the input is validated
        if (u.validateName(nameTextArea.getText()) == true
                && u.validateValue(valueTextField.getText()) == true
                && u.validateSerial(serialTextField.getText()) == true){
            u.getInventory().get(u.active_item_index).setValue(Double.parseDouble(valueTextField.getText()));
            u.getInventory().get(u.active_item_index).setName(nameTextArea.getText());
            u.getInventory().get(u.active_item_index).setSerial_number(serialTextField.getText());
            editItemErrorLabel.setText("");
        }
    }
}
