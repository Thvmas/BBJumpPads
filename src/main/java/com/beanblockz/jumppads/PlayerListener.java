package com.beanblockz.jumppads;

import me.konsolas.aac.api.HackType;
import me.konsolas.aac.api.PlayerViolationEvent;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerListener implements Listener {

    private static final String PAD_USE_PERMISSION = "bbjumppads.jumppads.use";
    private static final String TRAMP_USE_PERMISSION = "bbjumppads.trampolines.use";

    private final Map<UUID, Long> jumping = new HashMap<>();

    private final int padsMaterialId;
    private final double padsHeight;
    private final double padsForward;
    private final Sound padsSound;
    private final Effect padsEffect;

    private final int trampMaterialId;
    private final double trampHeight;
    private final Sound trampSound;
    private final Effect trampEffect;

    private final long speedTimeAllocation;

    public PlayerListener(Plugin plugin) {
        FileConfiguration configuration = plugin.getConfig();

        // jumppads
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

        // trampolines
        ConfigurationSection trampSection = configuration.getConfigurationSection("trampolines");
        this.trampMaterialId = trampSection.getInt("material-id");
        this.trampHeight = trampSection.getDouble("height");
        if (trampSection.contains("sound")) {
            this.trampSound = Sound.valueOf(trampSection.getString("sound"));
        } else this.trampSound = null;
        if (trampSection.contains("effect")) {
            this.trampEffect = Effect.valueOf(trampSection.getString("effect"));
        } else this.trampEffect = null;

        // violations
        ConfigurationSection violationsSection = configuration.getConfigurationSection("violations");
        this.speedTimeAllocation = violationsSection.getInt("speed-time-allocation");
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = event.getTo();
        Block block = location.getBlock();
        int typeId = block.getTypeId();
        if (typeId == padsMaterialId) {
            if (!player.hasPermission(PAD_USE_PERMISSION)) {
                return;
            }
            Vector direction = location.getDirection();
            player.setVelocity(direction.setY(padsHeight).multiply(padsForward));
            if (padsSound != null) player.playSound(location, padsSound, 100, 100);
            if (padsEffect != null) player.playEffect(location, padsEffect, 4);
            jumping.put(player.getUniqueId(), System.currentTimeMillis());
        } else if (typeId == trampMaterialId) {
            if (!player.hasPermission(TRAMP_USE_PERMISSION)) {
                return;
            }
            Vector direction = location.getDirection();
            player.setVelocity(direction.setY(trampHeight).setX(0).setZ(0));
            if (trampSound != null) player.playSound(location, trampSound, 100, 100);
            if (trampEffect != null) player.playEffect(location, trampEffect, 4);
        }
    }

    @EventHandler
    public void onViolation(PlayerViolationEvent event) {
        HackType type = event.getHackType();
        if (type != HackType.SPEED) {
            return;
        }
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!jumping.containsKey(uuid)) {
            return;
        }
        long stored = jumping.get(uuid);
        if (System.currentTimeMillis() - stored < speedTimeAllocation) {
            event.setCancelled(true);
        } else {
            jumping.remove(uuid);
        }
    }

}
