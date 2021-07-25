package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void inventory_can_store_100_plus_items(){
        User u = new User();
        for(int i = 0; i<1000; i++){
            u.addItem("D", 99.0, "7BC1234567");
        }
        assertTrue(u.getInventory().size()>100);
    }
    @Test
    void sortName() {
        User u = new User();
        //create a sample list to be sorted
        u.addItem("D", 99.0, "7BC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //sort and assert if the alphabetically first name is the first indexed result
        u.sortName();
        //get the name of the first result
        String actual = u.getInventory().get(0).getName();
        assertEquals("AA", actual);
    }

    @Test
    void sortValue() {
        User u = new User();
        //create a sample list to be sorted
        u.addItem("D", 99.0, "7BC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //sort and assert if the numerically first value is the first indexed result
        u.sortValue();
        //get the name of the first result
        BigDecimal actual = u.getInventory().get(0).getValue();
        BigDecimal expected = new BigDecimal(9999999.0);
        assertEquals(expected, actual);
    }

    @Test
    void sortSerial() {
        User u = new User();
        //create a sample list to be sorted
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //sort and assert if the alphabetically first serial is the first indexed result
        u.sortValue();
        //get the name of the first result
        String expected = "ABC1234567";
        String actual = u.getInventory().get(0).getSerial_number();
        assertEquals(expected, actual);
    }

    @Test
    void removeItem() {
        User u = new User();
        //create a sample list to be transformed
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //remove an item
        int length_before_removal = u.getInventory().size();
        Item remove = u.getInventory().get(4);
        u.removeItem(remove);
        //assert that the length decreased by one
        assertEquals(length_before_removal - 1, u.getInventory().size());
    }

    @Test
    void addItem() {
        User u = new User();
        //create a sample list to be transformed
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //assert that the size is equal to the number of added items
        assertEquals(5, u.getInventory().size());
    }

    @Test
    void editItem_changes_name() {
        User u = new User();
        //create a sample list to be transformed
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //change the values of an item
        Item expected = new Item("actual", 1999.0, "ABC1234567");
        u.editItem(u.getInventory().get(0), "actual", 1999.0, "ABC1234567");
        //assert if the values equal what it was changed to
        assertEquals("actual", u.getInventory().get(0).getName());
    }

    @Test
    void editItem_changes_value() {
        User u = new User();
        //create a sample list to be transformed
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //change the values of an item
        Item expected = new Item("actual", 1999.0, "ABC1234567");
        u.editItem(u.getInventory().get(0), "actual", 1999.0, "ABCR234567");
        //assert if the values equal what it was changed to
        assertEquals(1999.0, u.getInventory().get(0).getValue().doubleValue());
    }

    @Test
    void editItem_changes_serial() {
        User u = new User();
        //create a sample list to be transformed
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //change the values of an item
        u.editItem(u.getInventory().get(0), "actual", 1999.0, "ABC12345R7");
        //assert if the values equal what it was changed to
        assertEquals("ABC12345R7", u.getInventory().get(0).getSerial_number());
    }
    @Test
    void editItem_rejects_duplicate() {
        User u = new User();
        //create a sample list to be transformed
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //change the values of an item
        u.editItem(u.getInventory().get(0), "actual", 1999.0, "WBC1234567");
        //assert if the values equal what it was changed to
        assertEquals("ZBC1234567", u.getInventory().get(0).getSerial_number());
    }

    @Test
    void searchByName() {
        User u = new User();
        //create a sample list to be transformed
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //search by name
        u.searchByName("sports");
        //assert if the first item matches what was searched
        assertEquals("EA sports, get in the game", u.getInventory().get(0).getName());
    }

    @Test
    void searchBySerial() {
        User u = new User();
        //create a sample list to be transformed
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //search by serial
        u.searchBySerial("TBC");
        //assert if the first item matches what was searched
        assertEquals("TBC1234567", u.getInventory().get(0).getSerial_number());
    }

    @Test
    void generateRandomSerial() {
        User u = new User();
        //generate a random serial and see if it qualifies as one using validateserial
        assertTrue(u.validateSerial(u.generateRandomSerial()));
    }

    @Test
    void validateName_returns_false_for_256plus() {
        User u = new User();
        String name = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        assertFalse(u.validateName(name));
    }
    @Test
    void validateName_returns_false_for_under2() {
        User u = new User();
        String name = "a";
        assertFalse(u.validateName(name));
    }
    @Test
    void validateName_returns_true() {
        User u = new User();
        String name = "abc!!!!!!!";
        assertTrue(u.validateName(name));
    }

    @Test
    void validateValue_returns_false_for_letters() {
        User u = new User();
        String value = "abc123!";
        assertFalse(u.validateValue(value));
    }
    @Test
    void validateValue_returns_true_for_numbers() {
        User u = new User();
        String value = "123";
        assertTrue(u.validateValue(value));
    }

    @Test
    void validateSerial_returns_false_for_less_than_10_characters() {
        User u = new User();
        //create a sample list because validateserial checks uniqueness in a list
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");

        String serial = "123";
        assertFalse(u.validateSerial(serial));
    }

    @Test
    void validateSerial_returns_false_for_more_than_10_characters() {
        User u = new User();
        //create a sample list because validateserial checks uniqueness in a list
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");

        String serial = "12345678910";
        assertFalse(u.validateSerial(serial));
    }

    @Test
    void validateSerial_returns_false_for_special_characters() {
        User u = new User();
        //create a sample list because validateserial checks uniqueness in a list
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");

        String serial = "112345678!";
        assertFalse(u.validateSerial(serial));
    }
    @Test
    void validateSerial_returns_false_for_repeated_serials() {
        User u = new User();
        //create a sample list because validateserial checks uniqueness in a list
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "RBC1234567");

        String serial = u.getInventory().get(0).getSerial_number();
        assertFalse(u.validateSerial(serial));
    }
    @Test
    void validateSerial_returns_true() {
        User u = new User();
        //create a sample list because validateserial checks uniqueness in a list
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");

        String serial = "ZBC1234569";
        assertTrue(u.validateSerial(serial));
    }


    @Test
    void save_creates_a_txt() {
        User u = new User();
        //create a sample list
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //set filepath to some testing location
        String TEST_PATH = "resources/UserTest";
        u.setFilePath(TEST_PATH);
        u.save("TSV");
        //load the file from the testing location
        //check if the file exists
        File f = new File(TEST_PATH+".txt");
        assertTrue(f.exists());;
    }
    @Test
    void save_creates_a_json() {
        User u = new User();
        //create a sample list
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //set filepath to some testing location
        String TEST_PATH = "resources/UserTest";
        u.setFilePath(TEST_PATH);
        u.save("JSON");
        //load the file from the testing location
        //check if the file exists
        File f = new File(TEST_PATH+".json");
        assertTrue(f.exists());;
    }
    @Test
    void save_creates_a_html() {
        User u = new User();
        //create a sample list
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //set filepath to some testing location
        String TEST_PATH = "resources/UserTest";
        u.setFilePath(TEST_PATH);
        u.save("HTML");
        //load the file from the testing location
        //check if the file exists
        File f = new File(TEST_PATH+".html");
        assertTrue(f.exists());;
    }

    @Test
    void load_loads_saved_HTML() {
        User u = new User();
        //create a sample list
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //set filepath to some testing location
        String TEST_PATH = "resources/UserTest";
        u.setFilePath(TEST_PATH);
        u.save("HTML");
        //load the file from the testing location
        //Path must be refreshed because of the design.
        u.setFilePath(TEST_PATH);
        List<Item> test = new ArrayList<>(u.load("HTML"));
        //check if it loaded the expected amount of items
        assertEquals(5,test.size());
    }
    @Test
    void load_loads_saved_JSON() {
        User u = new User();
        //create a sample list
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //set filepath to some testing location
        String TEST_PATH = "resources/UserTest";
        u.setFilePath(TEST_PATH);
        u.save("JSON");
        //load the file from the testing location
        //Path must be refreshed because of the design.
        u.setFilePath(TEST_PATH);
        List<Item> test = new ArrayList<>(u.load("JSON"));
        //check if it loaded the expected amount of items
        assertEquals(5,test.size());
    }
    @Test
    void load_loads_saved_TSV() {
        User u = new User();
        //create a sample list
        u.addItem("D", 99.0, "ZBC1234567");
        u.addItem("CA", 999.0, "WBC1234567");
        u.addItem("AA", 9999.0, "XBC1234567");
        u.addItem("FA", 99999.0, "TBC1234567");
        u.addItem("EA sports, get in the game", 9999999.0, "ABC1234567");
        //set filepath to some testing location
        String TEST_PATH = "resources/UserTest";
        u.setFilePath(TEST_PATH);
        u.save("TSV");
        //load the file from the testing location
        //Path must be refreshed because of the design.
        u.setFilePath(TEST_PATH);
        List<Item> test = new ArrayList<>(u.load("TSV"));
        //check if it loaded the expected amount of items
        assertEquals(5,test.size());
    }
}