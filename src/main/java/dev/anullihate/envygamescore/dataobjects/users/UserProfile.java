package dev.anullihate.envygamescore.dataobjects.users;

import dev.anullihate.envygamescore.EnvyGamesCore;
import dev.anullihate.envygamescore.datatables.UserTable;

import java.sql.SQLException;

public class UserProfile {

    private double BASE_EXP = 100;
    private int MAX_LEVEL = 100;

    private String playerName;
    private int level;
    private int exp;
    private int expTLU;
    private String race;
    private String division;
    private String divisionRank;
    private int divisionExp;

    private boolean isLoaded = false;

    public UserProfile(
            String playerName,
            int expTLU,
            boolean isLoaded
    ) {
        this(playerName, 0, expTLU, null, null, 0);
    }

    public UserProfile(
            String playerName,
            int exp,
            int expTLU,
            String race,
            String division,
            int divisionExp
    ) {
        this.playerName = playerName;
        this.exp = exp;
        this.expTLU = expTLU;
        this.race = race;
        this.division = division;
        this.divisionExp = divisionExp;

        this.isLoaded = true;
    }

    public static UserProfile createNew(String playerName) {
        UserTable newUser = new UserTable();
        newUser.setName(playerName);
        newUser.setExpTLU(100);

        try {
            EnvyGamesCore.userDao.create(newUser);
            return new UserProfile(playerName, 100, true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new UserProfile(playerName, 100, false);
    }

    public static UserProfile loadUserProfile(String playerName) {
        try {
            UserTable userTable = EnvyGamesCore.userDao.queryForId(playerName);
            return new UserProfile(
                    userTable.getName(),
                    userTable.getExp(),
                    userTable.getExpTLU(),
                    userTable.getRace(),
                    userTable.getDivision(),
                    userTable.getDivisionExp()
            );
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new UserProfile(playerName, 100, false);
    }

    public void saveUserProfile() {
        try {
            UserTable userTable = EnvyGamesCore.userDao.queryForId(this.playerName);

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
            EnvyGamesCore.userDao.createOrUpdate(userTable);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String getDivision() {
        return division;
    }

    public String getRace() {
        return race;
    }
}
