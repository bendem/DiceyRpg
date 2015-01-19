package be.bendem.bot.storage.irc;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "Servers")
public class Server {

    @DatabaseField(id = true)
    private String networkName;

    @DatabaseField(canBeNull = false)
    private String address;

    @DatabaseField(canBeNull = false)
    private int port;

    @DatabaseField(canBeNull = false)
    private boolean secure;

    @DatabaseField(canBeNull = false)
    List<String> channels;

    @DatabaseField(canBeNull = false, foreign = true)
    private Identity identity;

}
