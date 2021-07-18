package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;

public class User {
    ObservableList<Item> inventory = FXCollections.observableArrayList();
    private String filePath = "resources/Example.json";
    //comparators for sort methods
    public static Comparator<Item> nameComparator = new Comparator<Item>() {
        public int compare(Item item_1, Item item_2) {
            String item_1Name = item_1.getNameasString();
            String item_2Name = item_2.getNameasString();
            //ascending order
            return item_1Name.compareTo(item_2Name);
        }};
    public static Comparator<Item> valueComparator = new Comparator<Item>() {
        public int compare(Item item_1, Item item_2) {
            Long item_1Value = item_1.getValue().longValue();
            Long item_2Value = item_2.getValue().longValue();
            //ascending order
            return item_1Value.compareTo(item_2Value);
        }};
    public static Comparator<Item> serial_numberComparator = new Comparator<Item>() {
        public int compare(Item item_1, Item item_2) {
            String item_1Serial_number = item_1.getSerial_number();
            String item_2Serial_number = item_2.getSerial_number();
            //ascending order
            return item_1Serial_number.compareTo(item_2Serial_number);
        }};

    public ObservableList<Item> getInventory() {
        return inventory;
    }

    public void sortName(){
        Collections.sort(inventory,nameComparator);
    }

    public void sortValue(){Collections.sort(inventory,valueComparator);}
    public void sortSerial(){Collections.sort(inventory,serial_numberComparator);}
}
