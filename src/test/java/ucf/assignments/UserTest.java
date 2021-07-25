package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

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
        u.editItem(u.getInventory().get(0), "actual", 1999.0, "ABC1234567");
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
        Item expected = new Item("actual", 1999.0, "ABC1234567");
        u.editItem(u.getInventory().get(0), "actual", 1999.0, "ABC1234567");
        //assert if the values equal what it was changed to
        assertEquals("ABC1234567", u.getInventory().get(0).getSerial_number());
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
        String value = "";
        assertFalse(u.validateValue(value));
    }

    @Test
    void validateSerial() {
        User u = new User();
    }

    @Test
    void save() {
        User u = new User();
    }

    @Test
    void load() {
        User u = new User();
    }
}