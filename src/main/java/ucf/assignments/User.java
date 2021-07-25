package ucf.assignments;

import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class User {
    ObservableList<Item> inventory = FXCollections.observableArrayList();
    private String filePath = "resources/Example";
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
            //ascending order. not sure why but had to switch the order of compare
            return item_2Value.compareTo(item_1Value);
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

    public void setInventory(ObservableList<Item> inventory) {
        this.inventory = inventory;
    }

    public void setActive_item_index(int active_item_index) {
        this.active_item_index = active_item_index;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public void addItem(String name, Double value, String serial_number) {
        active_item_index = inventory.size();
        Item added_item = new Item(name, value, serial_number);
        inventory.add(added_item);

    }

    public void editItem(Item item, String name, Double value, String serial_number) {


        int item_index = inventory.indexOf(item);
        if(validateSerial(serial_number)){
            Item added_item = new Item(name, value, serial_number);
            inventory.set(item_index, added_item);
        }
    }

    public void searchByName(String search_string) {
        int match_index = 0;
        int item_index = 0;
        ObservableList<Item> placeholder_inventory = FXCollections.observableArrayList(inventory);

        for (Item item : placeholder_inventory) {
            if (item.getName().contains(search_string)) {
                //save the index of the match to be removed
                item_index = placeholder_inventory.indexOf(item);
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
        int item_index = 0;
        ObservableList<Item> placeholder_inventory = FXCollections.observableArrayList(inventory);

        for (Item item : placeholder_inventory) {
            if (item.getSerial_number().contains(search_string)) {
                //save the index of the match to be removed
                item_index = placeholder_inventory.indexOf(item);
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
        if (!checkUniqueSerial(saltStr)) {
            generateRandomSerial();
        }
        return saltStr;
    }

    private boolean checkUniqueSerial(String serial) {
        //iterates through the inventory and checks that the serial doesn't equal existing serials
        for (Item item : inventory) {
            if (item.getSerial_number().equals(serial)) {
                return false;
            }
        }
        return true;
    }

    public boolean validateName(String name) {
        //check if length is within range
        if (name.length() >= 2
                && name.length() <= 256) {
            return true;
        }
        return false;
    }

    public boolean validateValue(String value) {
        try { // check if the string contains a valid number
            Double.parseDouble(value);
        } catch (NumberFormatException e) { //if it doesn't return that it doesnt
            return false;
        }
        return true;
    }

    public boolean validateSerial(String serial) {
        //check for special characters
        Boolean serialContainsSpecialCharacters = Pattern.matches("[A-Za-z0-9]+[^A-Za-z0-9]", serial);
        //check for length and uniqueness in existing list
        if (serialContainsSpecialCharacters == Boolean.FALSE && serial.length() == 10) {
            if (active_item_index == inventory.size()
                    && checkUniqueSerial(serial)){
                return true;
            }
            if (checkUniqueSerial(serial) || (serial.equals(getInventory().get(active_item_index).getSerial_number()))){
                return true;
            }
        }

        return false;
    }

    public void save(String file_type) {
        if (file_type.equals("TSV")) {
            filePath = filePath + ".txt";
            saveasTSV();
        }
        if (file_type.equals("JSON")) {
            filePath = filePath + ".json";
            saveasJSON();
        }
        if (file_type.equals("HTML")) {
            filePath = filePath + ".html";
            saveasHTML();
        }
    }

    private void saveasTSV() {
        List<Item> itemList = new ArrayList<>();
        //convert to a simpler list
        for (Item item : inventory) {
            Item new_item = new Item(
                    item.getName(),
                    item.getValue().doubleValue(),
                    item.getSerial_number()
            );
            itemList.add(new_item);
        }
        try {
            //add the items to the json
            FileWriter fw = new FileWriter(filePath);
            for (Item item : itemList) {
                fw.write(item.getName() + "\t" + item.getValue() + "\t" + item.getSerial_number() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveasJSON() {
        //for each item in inventory
        Gson gson = new Gson();
        List<Item> itemList = new ArrayList<>();
        for (Item item : inventory) {
            Item new_item = new Item(
                    item.getName(),
                    item.getValue().doubleValue(),
                    item.getSerial_number()
            );
            itemList.add(new_item);
        }
        try {
            //add the items to the textfile
            FileWriter fw = new FileWriter(filePath);
            fw.write("{\"Inventory\":" + gson.toJson(itemList) + "}");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveasHTML() {
        List<Item> itemList = new ArrayList<>();
        //convert to a simpler list
        for (Item item : inventory) {
            Item new_item = new Item(
                    item.getName(),
                    item.getValue().doubleValue(),
                    item.getSerial_number()
            );
            itemList.add(new_item);
        }
        try {
            //add the items to the html table
            FileWriter fw = new FileWriter(filePath);
            fw.write("<!DOCTYPE html>\n" +
                    "<html>\n" + "<head>" +
                    "<style>" + "table, th, td {" + "  border: 1px solid black;" + "}" + "</style>" +
                    "</head>" +
                    "<body>\n" +
                    "\n" +
                    "<h2>Inventory</h2>\n" +
                    "\n" +
                    "<table style=\"width:50%\">\n" +
                    "  <tr>\n" +
                    "    <th>Name</th>\n" +
                    "    <th>Value</th> \n" +
                    "    <th>Serial Number</th>\n" +
                    "  </tr>");
            for (Item item : itemList) {
                fw.write("<tr><td>"
                        + item.getName().replaceAll( //replacing illegal xml characters
                        "&", "&amp;").replaceAll(
                        "<", "&lt;").replaceAll(
                        ">", "&gt;").replaceAll(
                        "\"", "&quot;").replaceAll(
                        "\'", "&apos;")
                        + "</td><td>"
                        + item.getValue() + "</td><td>"
                        + item.getSerial_number() + "</td></tr>\n");
            }
            fw.write("</table>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Item> load(String file_type) {
        List<Item> loaded_inventory = new ArrayList<>();
        if (file_type.equals("TSV")) {
            filePath = filePath + ".txt";
            loaded_inventory = loadTSV();
        }
        if (file_type.equals("JSON")) {
            filePath = filePath + ".json";
            loaded_inventory = loadJSON();
        }
        if (file_type.equals("HTML")) {
            filePath = filePath + ".html";
            loaded_inventory = loadHTML();
        }
        return loaded_inventory;
    }

    private List<Item> loadTSV() {
        //open textfile
        Path input = Path.of(this.filePath);
        try {
            String tsv_text = Files.readString(input);
            //take the textfile as a list split by new line
            List<String> split_tsv = new ArrayList<>(Arrays.asList(tsv_text.split("\n")));
            List<Item> inventory = new ArrayList<>();
            for (String item : split_tsv) {
                //split every line by tab and add it to the list of arrays
                String[] item_properties = item.split("\t");
                //the index of the properties is known - 0:Name 1:Value 2:Serial Number (See saveastsv())
                Item new_item = new Item(item_properties[0],
                        Double.parseDouble(item_properties[1]),
                        item_properties[2]);
                //create a new item from the file and add it to the list
                inventory.add(new_item);
            }
            return inventory;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Item> loadJSON() {
        try {
            //open json
            File input = new File(this.filePath);
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObject = fileElement.getAsJsonObject();
            JsonArray jsonArrayofItems = fileObject.get("Inventory").getAsJsonArray();
            List<Item> items = new ArrayList<>();
            //for each item in the json
            for (JsonElement itemElement : jsonArrayofItems) {
                JsonObject itemJsonObject = itemElement.getAsJsonObject();

                String name = itemJsonObject.get("name").getAsString();
                Double value = itemJsonObject.get("value").getAsDouble();
                String serial_number = itemJsonObject.get("serial_number").getAsString();
                //add each object to lists
                Item item = new Item(name, value, serial_number);
                items.add(item);
            }
            return items;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Item> loadHTML() {
        //open textfile
        Path input = Path.of(this.filePath);
        try {
            String tsv_text = Files.readString(input);
            //take the textfile as a list split by new table row
            List<String> split_html = new ArrayList<>(Arrays.asList(tsv_text.split("<tr><td>")));
            //remove the first entry to isolate table information
            split_html.remove(0);

            List<Item> inventory = new ArrayList<>();
            for (String item : split_html) {
                //split every line by new cell and add it to an array
                String[] item_properties = item.split("</td><td>");
                //get rid of the closing row

                item_properties[2] = item_properties[2].replaceAll("<\\/td(.+|\\s)+", "");
                //the index of the properties is known - 0:Name 1:Value 2:Serial Number (See saveashtml())
                Item new_item = new Item(item_properties[0],
                        Double.parseDouble(item_properties[1]),
                        item_properties[2]);
                //create a new item from the file and add it to the list
                inventory.add(new_item);
            }
            return inventory;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
