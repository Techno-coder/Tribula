package io.github.techno_brony.tribula.sql;

import io.github.techno_brony.tribula.sql.commands.CommandSQL;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        createAndLoadConfig();
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        getCommand("templatecmd").setExecutor(new CommandSQL(this));
    }

    @Override
    public void onDisable() {

    }

    private void createAndLoadConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Creating config.yml ...");
                saveDefaultConfig();
            } else {
                getLogger().info("Loading config.yml ...");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void parseConfig() {
    }
}
