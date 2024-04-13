package hell.lamacoreanarchy;

import hell.lamacoreanarchy.Command.BanCommand;
import hell.lamacoreanarchy.Listener.FirstJoinListener;
import hell.lamacoreanarchy.config.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoreAnarchy extends JavaPlugin {
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);

        getServer().getPluginManager().registerEvents(new FirstJoinListener(), this);
        getCommand("coreban").setExecutor(new BanCommand(this));
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}