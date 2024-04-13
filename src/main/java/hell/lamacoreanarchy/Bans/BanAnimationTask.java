package hell.lamacoreanarchy.Bans;

import hell.lamacoreanarchy.CoreAnarchy;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BanAnimationTask extends BukkitRunnable {

    private final Player player;
    private final CoreAnarchy plugin;
    private int ticksPassed;
    private final long durationTicks;
    private final String reason;
    private final int banInterval;
    private final int particleInterval;
    private final double particleRadius;

    public BanAnimationTask(Player player, CoreAnarchy plugin, long durationTicks, String reason) {
        this.player = player;
        this.plugin = plugin;
        this.ticksPassed = 0;
        this.durationTicks = durationTicks;
        this.reason = reason;
        this.banInterval = plugin.getConfig().getInt("banInterval", 200);
        this.particleInterval = plugin.getConfig().getInt("particleInterval", 20);
        this.particleRadius = plugin.getConfig().getDouble("particleRadius", 1.0);
    }

    @Override
    public void run() {
        ticksPassed++;
        if (ticksPassed <= banInterval) {
            Location playerLocation = player.getLocation();
            if (ticksPassed % particleInterval == 0) {
                playerLocation.getWorld().spawnParticle(Particle.REDSTONE, playerLocation, 30, particleRadius, particleRadius, particleRadius, new Particle.DustOptions(org.bukkit.Color.YELLOW, 1));
                playerLocation.getWorld().spawnParticle(Particle.REDSTONE, playerLocation, 30, particleRadius, particleRadius, particleRadius, new Particle.DustOptions(org.bukkit.Color.ORANGE, 1));
            }
            player.setVelocity(new Vector(0, 0.1, 0));
        } else {
            player.setVelocity(new Vector(0, 0, 0));
            player.getLocation().getWorld().playEffect(player.getLocation(), Effect.FIREWORK_SHOOT, 3);
            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 50, 50);
            player.kickPlayer("Вы были забанены по причине: " + reason);
            Bukkit.getServer().getBanList(org.bukkit.BanList.Type.NAME).addBan(player.getName(), reason, null, null);
            cancel();
        }
    }
}
