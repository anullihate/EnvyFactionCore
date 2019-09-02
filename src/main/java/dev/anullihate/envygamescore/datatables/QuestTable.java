package dev.anullihate.envygamescore.datatables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "quests")
public class QuestTable {

    @DatabaseField(generatedId = true)
    private int id;

    public QuestTable() {}
}
