package com.beanblockz.jumppads;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class PlayerListener implements Listener {

    private static final String PAD_USE_PERMISSION = "bbjumppads.jumppads.use";

    private final int padsMaterialId;
    private final double padsHeight;
    private final double padsForward;
    private final Sound padsSound;
    private final Effect padsEffect;

    public PlayerListener(Plugin plugin) {
        FileConfiguration configuration = plugin.getConfig();
        ConfigurationSection padsSection = configuration.getConfigurationSection("jumppads");
        this.padsMaterialId = padsSection.getInt("material-id");
        this.padsHeight = padsSection.getDouble("height");
        this.padsForward = padsSection.getDouble("forward");
        if (padsSection.contains("sound")) {
            this.padsSound = Sound.valueOf(padsSection.getString("sound"));
        } else this.padsSound = null;
        if (padsSection.contains("effect")) {
            this.padsEffect = Effect.valueOf(padsSection.getString("effect"));
        } else this.padsEffect = null;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = event.getTo();
        Block block = location.getBlock();
        if (block.getTypeId() != padsMaterialId) {
            return;
        }
        if (!player.hasPermission(PAD_USE_PERMISSION)) {
            return;
        }
        Vector direction = location.getDirection();
        player.setVelocity(direction.setY(padsHeight).multiply(padsForward));
        if (padsSound != null) player.playSound(location, padsSound, 100, 100);
        if (padsEffect != null) player.playEffect(location, padsEffect, 4);
    }

}
