package com.example.inventory_m_s.entities;

import javafx.beans.property.*;

public class Goods {
    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final IntegerProperty type = new SimpleIntegerProperty();
    private final IntegerProperty size = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty date = new SimpleStringProperty();
    private final IntegerProperty prize = new SimpleIntegerProperty();
    private final StringProperty status = new SimpleStringProperty();

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }


    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Integer getType() {
        return type.get();
    }

    public IntegerProperty typeProperty() {
        return type;
    }

    public void setType(Integer type) {
        this.type.set(type);
    }

    public int getSize() {
        return size.get();
    }

    public IntegerProperty sizeProperty() {
        return size;
    }

    public void setSize(int size) {
        this.size.set(size);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public int getPrize() {
        return prize.get();
    }

    public IntegerProperty prizeProperty() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize.set(prize);
    }
}
