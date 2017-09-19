package com.example.vinay.dealmash;

/**
 * Created by Vinay on 12-02-2017.
 */
public class PreferenceObject {
    String name;
     boolean isSelected;

    public PreferenceObject(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
