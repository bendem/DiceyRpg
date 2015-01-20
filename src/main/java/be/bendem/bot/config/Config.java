package be.bendem.bot.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Config {

    private final Path configPath;
    private final Map<Key, String> config;

    public Config(Path c) {
        configPath = c;
        config = new EnumMap<>(Key.class);
    }

    /**
     * Loads the config from its file.
     *
     * @return <code>false</code> if the config just got created, <code>true</code> otherwise
     */
    public boolean load() {
        boolean existedBefore = true;
        if(!Files.exists(configPath)) {
            try {
                Files.createDirectories(configPath.getParent());
                saveDefaultFile(configPath);
            } catch(IOException e) {
                throw new RuntimeException("Could not create config file", e);
            }
            existedBefore = false;
        }
        List<String> configLines;
        try {
            configLines = Files.readAllLines(configPath, StandardCharsets.UTF_8).stream()
                .filter(l -> !l.startsWith("#"))
                .collect(Collectors.toList());
        } catch(IOException e) {
            throw new RuntimeException("Could not read config file", e);
        }
        for(String line : configLines) {
            for(Key key : Key.values()) {
                if(line.startsWith(key.path + '=')) {
                    config.put(key, line.substring(key.path.length() + 1).trim());
                    break;
                }
            }
        }
        return existedBefore;
    }

    private void saveDefaultFile(Path path) throws IOException {
        Files.write(
            path,
            Arrays.stream(Key.values())
                .map(key -> key.path + "=<insert value>")
                .collect(Collectors.toList()),
            StandardCharsets.UTF_8,
            StandardOpenOption.CREATE
        );
    }

    public String get(Key key) {
        return config.get(key);
    }

    public String get(Key key, String def) {
        return config.getOrDefault(key, def);
    }

    public enum Key {
        ServerAddress("irc.server.address"),
        ServerPort("irc.server.port"),
        ServerSsl("irc.server.ssl"),
        ServerUsername("irc.server.username"),
        ServerPassword("irc.server.password"),
        Realname("irc.realname"),
        Nickname("irc.nickname"),
        Channels("irc.channels"),
        ;

        private String path;

        Key(String path) {
            this.path = path;
        }
    }
}
