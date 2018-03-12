package com.quickpick.model.menu.menunew;

import java.util.List;

/**
 * Created by Rajesh Kumar on 12-03-2018.
 */

public class Menu {

    private Boolean error;

    private String status;

    private List<Item> Items = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Item> getItems() {
        return Items;
    }

    public void setItems(List<Item> items) {
        this.Items = items;
    }
}
