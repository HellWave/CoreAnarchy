package hell.lamacoreanarchy.Command;

import hell.lamacoreanarchy.Bans.BanAnimationTask;
import hell.lamacoreanarchy.CoreAnarchy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand implements CommandExecutor {

    private final CoreAnarchy plugin;

    public BanCommand(CoreAnarchy plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Команда доступна только игрокам.");
            return true;
        }

        if (!sender.hasPermission("coreban.ban")) {
            sender.sendMessage("У вас недостаточно прав для использования этой команды.");
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage("Используйте: /coreban <игрок> <время> <временная единица> <причина>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Игрок не найден или оффлайн.");
            return true;
        }

        int duration;
        try {
            duration = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Некорректное время бана.");
            return true;
        }

        String timeUnit = args[2].toLowerCase();
        long durationTicks = 0;
        switch (timeUnit) {
            case "минуты":
            case "минут":
                durationTicks = duration * 60;
                break;
            case "часы":
            case "час":
                durationTicks = duration * 60 * 60;
                break;
            case "дни":
            case "день":
                durationTicks = duration * 60 * 60 * 24;
                break;
            case "года":
            case "год":
                durationTicks = duration * 60 * 60 * 24 * 365;
                break;
            default:
                sender.sendMessage("Некорректная временная единица. Используйте: минуты, часы, дни, года.");
                return true;
        }

        if (durationTicks <= 0) {
            sender.sendMessage("Некорректное время бана.");
            return true;
        }

        String reason = "";
        for (int i = 3; i < args.length; i++) {
            reason += args[i] + " ";
        }

        BanAnimationTask banAnimation = new BanAnimationTask(target, plugin, durationTicks, reason.trim());
        banAnimation.runTaskTimer(plugin, 0, 1);

        return true;
    }
}
