package com.kkondratek.savingapp.logic;

public class UpdateTextEvent {
    private String sampleTextValue;
    public UpdateTextEvent(String textValue) {
        this.sampleTextValue = textValue;
    }
    public String getTextValue() {
        return sampleTextValue;
    }
}
