package be.bendem.bot.storage.irc;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.kitteh.irc.client.library.AuthType;

@DatabaseTable(tableName = "Identities")
public class Identity {

    @DatabaseField(id = true, generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String nickname;

    @DatabaseField(canBeNull = false)
    private String username;

    @DatabaseField(canBeNull = false)
    private String realname;

    @DatabaseField(canBeNull = false)
    private AuthType authType;

    @DatabaseField(canBeNull = false)
    private String password;

}
