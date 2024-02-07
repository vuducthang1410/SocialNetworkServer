package wint.webchat.enums;

public enum Gender {
    MALE(1),
    FEMALE(0),
    OTHER(2);
    private final int genderValue;

    Gender(int genderValue) {
        this.genderValue = genderValue;
    }
    public int getGenderValue() {
        return genderValue;
    }
}
