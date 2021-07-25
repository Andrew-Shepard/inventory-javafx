package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Andrew Shepard
 */
public class Item {

    String name = null;
    BigDecimal value = null;
    String serial_number = null;

    public Item(String name, Double value, String serial_number) {
        this.name = name;
        this.value = new BigDecimal(value);
        this.serial_number = serial_number;
    }

    public void setName(String name) {
        this.name = new String(name);
    }

    public void setValue(Double value) {
        this.value = new BigDecimal(value);
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getSerial_number(){ return serial_number; }
}

