package com.quickpick.model.menu.menunew;

import java.util.List;

/**
 * Created by Rajesh Kumar on 12-03-2018.
 */

public class Menucategory {

    private Boolean error;

    private String Status;

    private List<MenuDatum> MenuData = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public List<MenuDatum> getMenuData() {
        return MenuData;
    }

    public void setMenuData(List<MenuDatum> menuData) {
        this.MenuData = menuData;
    }

}
