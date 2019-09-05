package dev.anullihate.envyfactioncore.datatables;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class UserTable {

    @DatabaseField(id = true)
    private String name;
    @DatabaseField
    private double exp;
    @DatabaseField
    private double expTLU;

    @DatabaseField
    private String race;

    @DatabaseField
    private String division;
    @DatabaseField
    private double divisionExp;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private String[] professions;

    @DatabaseField
    private int kill;
    @DatabaseField
    private int death;

    public UserTable() {

    }

    // getters

    public String getName() {
        return name;
    }

    public double getExp() {
        return exp;
    }

    public double getExpTLU() {
        return expTLU;
    }

    public String getRace() {
        return race;
    }

    public String getDivision() {
        return division;
    }

    public double getDivisionExp() {
        return divisionExp;
    }

    public String[] getProfessions() {
        return professions;
    }

    // setters

    public void setName(String name) {
        this.name = name;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public void setExpTLU(double expTLU) {
        this.expTLU = expTLU;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setDivisionExp(double divisionExp) {
        this.divisionExp = divisionExp;
    }

    public void setProfessions(String[] professions) {
        this.professions = professions;
    }
}
