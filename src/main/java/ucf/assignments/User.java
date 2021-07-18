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

    public void sortName(){Collections.sort(inventory,nameComparator);}
    public void sortValue(){Collections.sort(inventory,valueComparator);}
    public void sortSerial(){Collections.sort(inventory,serial_numberComparator);}
    public void removeItem(Item item){inventory.remove(item);}
    public void addItem(String description, Long value, String serial_number ){
        //lists add new item with description, completion and due date
        Item added_item = new Item(description,value,serial_number);
        inventory.add(added_item);
    }
    public void editItem(Item item, String description, Long value, String serial_number ){
        int item_index = inventory.indexOf(item);
        Item added_item = new Item(description,value,serial_number);
        inventory.set(item_index,added_item);
    }
    public ObservableList<Item> searchByName(String search_string){
        ObservableList<Item> filtered_list = FXCollections.observableArrayList();
        for (Item item : inventory){
            if (item.getNameasString().equals(search_string)){
                filtered_list.add(item);
            }
        }
        return null;
    }
    public ObservableList<Item> searchBySerial(){
        return null;
    }
}
