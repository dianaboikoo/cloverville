package com.example.residents;

import javafx.beans.property.*;

public class Resident {

    private final StringProperty name = new SimpleStringProperty();  // automatically updates the variable in the UI when it's changed
    private final IntegerProperty personalPoints = new SimpleIntegerProperty();
    private final IntegerProperty greenPoints = new SimpleIntegerProperty();

    public Resident(String name, int personalPoints, int greenPoints) {
        this.name.set(name);
        this.personalPoints.set(personalPoints);
        this.greenPoints.set(greenPoints);
    }

    public String getName() { return name.get(); }
    public void setName(String n) { name.set(n); }
    public StringProperty nameProperty() { return name; }

    public int getPersonalPoints() { return personalPoints.get(); }
    public void setPersonalPoints(int p) { personalPoints.set(p); }
    public IntegerProperty personalPointsProperty() { return personalPoints; }

    public int getGreenPoints() { return greenPoints.get(); }
    public void setGreenPoints(int g) { greenPoints.set(g); }
    public IntegerProperty greenPointsProperty() { return greenPoints; }

    @Override
    public String toString() {
        return getName();
    }
}
