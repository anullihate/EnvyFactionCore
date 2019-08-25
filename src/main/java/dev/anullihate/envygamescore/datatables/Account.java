package dev.anullihate.envygamescore.datatables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "accounts")
public class Account {

    @DatabaseField(id = true)
    private String name;
    @DatabaseField
    private int level;
    @DatabaseField
    private int experience;
    @DatabaseField
    private int experienceNeededTLU;

    public Account() {

    }

    public Account(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
}
