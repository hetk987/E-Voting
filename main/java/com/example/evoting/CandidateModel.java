package com.example.evoting;

public class CandidateModel {
    private String name;
    private boolean selected;

    public CandidateModel(String name, boolean selected){
        this.name = name;
        this.selected = selected;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        if(selected){
            return name + "    Selected";
        }
        else{
            return name;
        }
    }
}
