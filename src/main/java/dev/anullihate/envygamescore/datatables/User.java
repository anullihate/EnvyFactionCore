package dev.anullihate.envygamescore.datatables;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;
import java.util.Map;

@DatabaseTable(tableName = "accounts")
public class User {

    @DatabaseField(id = true)
    private String name;
    @DatabaseField
    private int level;
    @DatabaseField
    private int experience;
    @DatabaseField
    private int experienceNeededTLU;

    @DatabaseField
    private String division;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private String[] professions;

    public User() {

    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void addExperience(int experience) {
        this.experience += experience;
    }

    public void reduceExperience(int experience) {
        this.experience -= experience;
    }

    public void addExperienceNeededTLU(int experience) {
        this.experienceNeededTLU += experience;
    }

    public void levelUp() {
        this.level += 1;
    }

    public boolean isReadyToLevelUp() {
        return this.experience >= this.experienceNeededTLU;
    }

    public void processLevelUp() {
        this.levelUp();
        this.experienceNeededTLU = (int)Math.round(this.experienceNeededTLU * 1.5);
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
}
