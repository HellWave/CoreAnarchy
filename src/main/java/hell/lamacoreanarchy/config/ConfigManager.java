package hell.lamacoreanarchy.config;
import hell.lamacoreanarchy.CoreAnarchy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private final CoreAnarchy plugin;
    private File configFile;
    private FileConfiguration config;

    public ConfigManager(CoreAnarchy plugin) {
        this.plugin = plugin;
    }

    public void reloadConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        config.addDefault("particleRadius", 1.0);
        config.addDefault("particleInterval", 20);
        config.addDefault("banInterval", 200);
        config.options().copyDefaults(true);

        saveConfig();
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }
}
