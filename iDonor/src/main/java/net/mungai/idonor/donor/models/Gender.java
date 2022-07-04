package net.mungai.idonor.donor.models;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String displayValue;

    private Gender(String displayValue){
        this.displayValue=displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
