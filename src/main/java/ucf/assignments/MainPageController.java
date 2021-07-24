package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML private TableView<Item> tableView;
    @FXML private TableColumn<Item, SimpleStringProperty> serialColumn;
    @FXML private TableColumn<Item, BigDecimal> valueColumn;
    @FXML private TableColumn<Item, SimpleStringProperty> nameColumn;
    @FXML private TextField filenameTextField;
    @FXML private TextField searchTextField;
    @FXML private ComboBox searchComboBox;
    @FXML private ComboBox filenameComboBox;

    ObservableList<String> searchChoiceList = FXCollections.observableArrayList("Serial","Name");
    ObservableList<String> filetypeChoiceList = FXCollections.observableArrayList("TSV","JSON","HTML");
    private User u = new User();

    void initData(User u){
        this.u = u;
        tableView.setItems(u.getInventory());
    }
    public void nameSortItems(ActionEvent actionEvent) {
        u.sortName();
        tableView.setItems(u.getInventory());
    }

    public void valueSortItems(ActionEvent actionEvent) {
        u.sortValue();
        tableView.setItems(u.getInventory());
    }

    public void serialSortItems(ActionEvent actionEvent) {
        u.sortSerial();
        tableView.setItems(u.getInventory());
    }

    public void editItem(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditItemPage.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        EditItemPageController controller = loader.getController();
        ObservableList<Item> item;
        item = tableView.getSelectionModel().getSelectedItems();
        //Set the active item to be accessed in the next scene
        u.setActive_item_index(u.findItem_index(item.get(0)));
        controller.initData(u);

        stage.show();
    }


    public void deleteItem(ActionEvent actionEvent) {
        //remove an item from a todolist arraylist using user.removeitem
        ObservableList<Item> item;
        item = tableView.getSelectionModel().getSelectedItems();
        u.removeItem(item.get(0));
        //update the list of todolists to not have that item within the todolist
        tableView.setItems(u.getInventory());
    }

    public void addItem(ActionEvent actionEvent) throws IOException{
        u.addItem("https://github.com/Andrew-Shepard/",0.0,u.generateRandomSerial());
        tableView.setItems(u.getInventory());
    }

    public void load(ActionEvent actionEvent) {
    }

    public void save(ActionEvent actionEvent) {
    }
    public void searchItem(ActionEvent actionEvent) {
        if (searchComboBox.getValue().equals("Serial")){ // if the combobox is set to serial
            //search by serial and update the table
            u.searchBySerial(searchTextField.getText());
            tableView.setItems(u.getInventory());
        } else if (searchComboBox.getValue().equals("Name")){ // if the combobox is set to name
            //search by name and update the table
            u.searchByName(searchTextField.getText());
            tableView.setItems(u.getInventory());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //sets up columns
        serialColumn.setCellValueFactory(new PropertyValueFactory<Item,SimpleStringProperty>("serial_number"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<Item,BigDecimal>("value"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Item,SimpleStringProperty>("name"));
        //set up comboboxes
        filenameComboBox.setItems(filetypeChoiceList);
        searchComboBox.setItems(searchChoiceList); //Need to safely initalize this outside of this function
        searchComboBox.setValue("Serial");
    }

}
