package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Andrew Shepard
 */
public class Item {

    String name;
    BigDecimal value = null;
    String serial_number = null;
    int MAX_NAME_LENGTH = 256;

    public Item(String name, Long value, String serial_number) {
        int name_length = name.length() > MAX_NAME_LENGTH ? MAX_NAME_LENGTH : name.length();

        this.name = new String(name.substring(2, name_length));
        this.value = new BigDecimal(value);
        this.serial_number = serial_number;
    }

    public void setName(String name) {
        this.name = new String(name);
    }

    public void setValue(Long value) {
        this.value = new BigDecimal(value);
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getNameasString() {
        return name;
    }

    public SimpleStringProperty getName() {
        SimpleStringProperty simple_name = new SimpleStringProperty(name);
        return simple_name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getSerial_number() {
        return serial_number;
    }
}

