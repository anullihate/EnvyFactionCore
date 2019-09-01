package dev.anullihate.envygamescore.datatables;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;
import java.util.Map;

@DatabaseTable(tableName = "users")
public class UserTable {

    @DatabaseField(id = true)
    private String name;
    @DatabaseField
    private int exp;
    @DatabaseField
    private int expTLU;

    @DatabaseField
    private String race;

    @DatabaseField
    private String division;
    @DatabaseField
    private int divisionExp;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private String[] professions;

    public UserTable() {

    }

    // getters

    public String getName() {
        return name;
    }

    public int getExp() {
        return exp;
    }

    public int getExpTLU() {
        return expTLU;
    }

    public String getRace() {
        return race;
    }

    public String getDivision() {
        return division;
    }

    public int getDivisionExp() {
        return divisionExp;
    }

    public String[] getProfessions() {
        return professions;
    }

    // setters

    public void setName(String name) {
        this.name = name;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setExpTLU(int expTLU) {
        this.expTLU = expTLU;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setDivisionExp(int divisionExp) {
        this.divisionExp = divisionExp;
    }

    public void setProfessions(String[] professions) {
        this.professions = professions;
    }
}
