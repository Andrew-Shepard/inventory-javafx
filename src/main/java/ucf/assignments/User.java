package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class User {
    ObservableList<Item> inventory = FXCollections.observableArrayList();
    private String filePath = "resources/Example.json";
    public int active_item_index = 0;
    //comparators for sort methods
    public static Comparator<Item> nameComparator = new Comparator<Item>() {
        public int compare(Item item_1, Item item_2) {
            String item_1Name = item_1.getName();
            String item_2Name = item_2.getName();
            //ascending order
            return item_1Name.compareTo(item_2Name);
        }
    };
    public static Comparator<Item> valueComparator = new Comparator<Item>() {
        public int compare(Item item_1, Item item_2) {
            Long item_1Value = item_1.getValue().longValue();
            Long item_2Value = item_2.getValue().longValue();
            //ascending order
            return item_1Value.compareTo(item_2Value);
        }
    };
    public static Comparator<Item> serial_numberComparator = new Comparator<Item>() {
        public int compare(Item item_1, Item item_2) {
            String item_1Serial_number = item_1.getSerial_number();
            String item_2Serial_number = item_2.getSerial_number();
            //ascending order
            return item_1Serial_number.compareTo(item_2Serial_number);
        }
    };

    public ObservableList<Item> getInventory() {
        return inventory;
    }

    public void setActive_item_index(int active_item_index) {
        this.active_item_index = active_item_index;
    }

    //sorting functions using forementioned comparators
    public void sortName() {
        Collections.sort(inventory, nameComparator);
    }

    public void sortValue() {
        Collections.sort(inventory, valueComparator);
    }

    public void sortSerial() {
        Collections.sort(inventory, serial_numberComparator);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public void addItem(String description, Double value, String serial_number) {
        //lists add new item with description, completion and due date
        Item added_item = new Item(description, value, serial_number);
        inventory.add(added_item);
    }

    public void editItem(Item item, String description, Double value, String serial_number) {
        int item_index = inventory.indexOf(item);
        Item added_item = new Item(description, value, serial_number);
        inventory.set(item_index, added_item);
    }

    public void searchByName(String search_string) {
        int match_index = 0;
        for (Item item : inventory) {
            if (item.getName().equals(search_string)) {
                //save the index of the match to be removed
                int item_index = inventory.indexOf(item);
                //save the matched item
                Item match_item = item;
                //remove the place where the item was found
                inventory.remove(item_index);
                //add the matched item to the beginning of the list
                inventory.add(match_index, match_item);
                //have further matches be added after this match
                match_index++;
            }
        }
    }

    public void searchBySerial(String search_string) {
        int match_index = 0;
        for (Item item : inventory) {
            if (item.getSerial_number().equals(search_string)) {
                //save the index of the match to be removed
                int item_index = inventory.indexOf(item);
                //save the matched item
                Item match_item = item;
                //remove the place where the item was found
                inventory.remove(item_index);
                //add the matched item to the beginning of the list
                inventory.add(match_index, match_item);
                //have further matches be added after this match
                match_index++;
            }
        }
    }

    public String generateRandomSerial() {
        //https://stackoverflow.com/questions/20536566/creating-a-random-string-with-a-z-and-0-9-in-java
        //library of selected strings
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            //appends characters from library
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        //checks if the generated string was unique, otherwise generates another
        if(!checkUniqueSerial(saltStr)){
            generateRandomSerial();
        }
        return saltStr;
    }

    public int findItem_index(Item item) {
        //returns the index of an item in the inventory
        int item_index = inventory.indexOf(item);
        return item_index;
    }

    public boolean checkUniqueSerial(String serial) {
        //iterates through the inventory and checks that the serial doesn't equal existing serials
        for (Item item : inventory) {
            if (item.getSerial_number().equals(serial)) {
                return false;
            }
        }
        return true;
    }
    public boolean validateName(String name){

    }
    public boolean validateValue(String value){

    }
    public boolean validateSerial(String serial){

    }
}
