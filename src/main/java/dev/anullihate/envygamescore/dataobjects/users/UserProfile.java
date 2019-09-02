package dev.anullihate.envygamescore.dataobjects.users;

import dev.anullihate.envygamescore.EnvyGamesCore;
import dev.anullihate.envygamescore.datatables.UserTable;

import java.sql.SQLException;

public class UserProfile {

    private double BASE_EXP = 100;
    private double EXP_GROWTH = 1.5;
    private int MAX_LEVEL = 100;
    private int DIVISION_MAX_LEVEL = 30;

    public String playerName;
    public double level;
    public double exp;
    public double expTLU;
    public String race;
    public String division;
    public String divisionRank;
    public double divisionExp;

    public boolean isLoaded = false;
    public boolean initial = false;

    public UserProfile(
            String playerName,
            double expTLU
    ) {
        this(playerName, 0, expTLU, null, null, 0);
    }

    public UserProfile(
            String playerName,
            double exp,
            double expTLU,
            String race,
            String division,
            double divisionExp
    ) {
        this.playerName = playerName;
        this.exp = exp;
        this.expTLU = expTLU;
        this.race = race;
        this.division = division;
        this.divisionExp = divisionExp;

        this.level = checkPlayerLvl(exp);
    }

    private int checkPlayerLvl(double exp) {
        int i = 0;
        this.expTLU = BASE_EXP;
        while (i < MAX_LEVEL) {
            if (this.expTLU > exp) {
                return i;
            }

            this.expTLU = expTLU * EXP_GROWTH;
            i++;
        }

        return MAX_LEVEL;
    }

    public static UserProfile loadUserProfile(String playerName) {
        try {
            UserTable userTable = EnvyGamesCore.userDao.queryForId(playerName);
            UserProfile userProfile = new UserProfile(
                    userTable.getName(),
                    userTable.getExp(),
                    userTable.getExpTLU(),
                    userTable.getRace(),
                    userTable.getDivision(),
                    userTable.getDivisionExp()
            );
            userProfile.isLoaded = true;
            return userProfile;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new UserProfile(playerName, 100);
    }

    public void saveUserProfile() {
        try {
            UserTable userTable = EnvyGamesCore.userDao.queryForId(this.playerName);

            if (userTable == null) {
                userTable = new UserTable();
                userTable.setName(this.playerName);
                userTable.setExp(this.exp);
                userTable.setExpTLU(this.expTLU);
                userTable.setDivision(this.division);
                userTable.setDivisionExp(this.divisionExp);
                userTable.setRace(this.race);
            } else {
                if (userTable.getExp() != this.exp) {
                    userTable.setExp(this.exp);
                }
                if (userTable.getExpTLU() != this.expTLU) {
                    userTable.setExpTLU(this.expTLU);
                }
                if (!userTable.getDivision().equals(this.division)) {
                    userTable.setDivision(this.division);
                }
                if (userTable.getDivisionExp() != this.divisionExp) {
                    userTable.setDivisionExp(this.divisionExp);
                }
                if (!userTable.getRace().equals(this.race)) {
                    userTable.setRace(this.race);
                }
            }
            EnvyGamesCore.userDao.createOrUpdate(userTable);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public String getDivision() {
        return division;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setDivision(String division) {
        this.division = division;
    }
}
