package me.none030.mortispayday.manager;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import me.none030.mortispayday.data.PaydayData;
import me.none030.mortispayday.payday.Payday;
import me.none030.mortispayday.payday.PaydayMenu;
import me.none030.mortispayday.payday.PaydaySettings;
import me.none030.mortispayday.payday.WarRestriction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PaydayCommand implements TabExecutor {

    private final MainManager mainManager;

    public PaydayCommand(MainManager mainManager) {
        this.mainManager = mainManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }
        if (args[0].equalsIgnoreCase("view")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(mainManager.getPaydayManager().getMessage("NO_CONSOLE"));
                return false;
            }
            Player player = (Player) sender;
            if (args.length != 1 && args.length != 2) {
                return false;
            }
            if (args.length == 1) {
                Town town = TownyAPI.getInstance().getTown(player);
                if (town == null) {
                    sender.sendMessage(mainManager.getPaydayManager().getMessage("NO_TOWN"));
                    return false;
                }
                PaydaySettings settings = mainManager.getPaydayManager().getSettings();
                if (settings.isTownRestrict()) {
                    sender.sendMessage(mainManager.getPaydayManager().getMessage("TOWN_RESTRICT"));
                    return false;
                }else {
                    if (!settings.isTownDemocracy()) {
                        if (!player.hasPermission("mortispayday.town.view")) {
                            sender.sendMessage(mainManager.getPaydayManager().getMessage("NO_PERMISSION"));
                            return false;
                        }
                    }
                }
                if (settings.isTownConqueredRestrict()) {
                    if (town.isConquered()) {
                        if (!settings.isConqueredDays(town.getConqueredDays())) {
                            sender.sendMessage(mainManager.getPaydayManager().getMessage("CONQUERED"));
                            return false;
                        }
                    }
                }
                WarRestriction restriction = settings.getWarRestriction();
                if (restriction.equals(WarRestriction.TRUE) || restriction.equals(WarRestriction.TOWN)) {
                    sender.sendMessage(mainManager.getPaydayManager().getMessage("WAR_RESTRICTED"));
                    return false;
                }
                PaydayMenu menu = new PaydayMenu(mainManager.getPaydayManager(), new PaydayData(town));
                menu.open(player);
            }
            if (args.length == 2) {
                Nation nation = TownyAPI.getInstance().getNation(player);
                if (nation == null) {
                    sender.sendMessage(mainManager.getPaydayManager().getMessage("NO_NATION"));
                    return false;
                }
                Town town = TownyAPI.getInstance().getTown(args[1]);
                if (town == null) {
                    sender.sendMessage(mainManager.getPaydayManager().getMessage("INVALID_TOWN"));
                    return false;
                }
                PaydaySettings settings = mainManager.getPaydayManager().getSettings();
                if (settings.isNationRestrict()) {
                    sender.sendMessage(mainManager.getPaydayManager().getMessage("NATION_RESTRICT"));
                    return false;
                }else {
                    if (!settings.isNationDemocracy()) {
                        if (!player.hasPermission("mortispayday.nation.view")) {
                            sender.sendMessage(mainManager.getPaydayManager().getMessage("NO_PERMISSION"));
                            return false;
                        }
                    }
                }
                WarRestriction restriction = settings.getWarRestriction();
                if (restriction.equals(WarRestriction.TRUE) || restriction.equals(WarRestriction.NATION)) {
                    sender.sendMessage(mainManager.getPaydayManager().getMessage("WAR_RESTRICTED"));
                    return false;
                }
                if (settings.isCapitalWarRestrict()) {
                    if (nation.getCapital().hasActiveWar()) {
                        sender.sendMessage(mainManager.getPaydayManager().getMessage("CAPITAL_WAR_RESTRICT"));
                        return false;
                    }
                }
                PaydayMenu menu = new PaydayMenu(mainManager.getPaydayManager(), new PaydayData(town));
                menu.open(player);
            }
        }
        if (args[0].equalsIgnoreCase("set")) {
            if (!sender.hasPermission("mortispayday.set")) {
                sender.sendMessage(mainManager.getPaydayManager().getMessage("NO_PERMISSION"));
                return false;
            }
            if (args.length != 3) {
                return false;
            }
            Town town = TownyAPI.getInstance().getTown(args[1]);
            if (town == null) {
                sender.sendMessage(mainManager.getPaydayManager().getMessage("INVALID_TOWN"));
                return false;
            }
            Payday payday = mainManager.getPaydayManager().getPaydayById().get(args[2]);
            if (payday == null) {
                sender.sendMessage(mainManager.getPaydayManager().getMessage("INVALID_PAYDAY"));
                return false;
            }
            PaydayData data = new PaydayData(town);
            data.setId(payday.getId());
            sender.sendMessage(mainManager.getPaydayManager().getMessage("PAYDAY_CHANGED"));
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("mortispayday.reload")) {
                sender.sendMessage(mainManager.getPaydayManager().getMessage("NO_PERMISSION"));
                return false;
            }
            mainManager.reload();
            sender.sendMessage(mainManager.getPaydayManager().getMessage("RELOADED"));
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("view");
            arguments.add("set");
            arguments.add("reload");
            return arguments;
        }
        if (args[0].equalsIgnoreCase("set")) {
            if (args.length == 2) {
                List<String> names = new ArrayList<>();
                for (Town town : TownyAPI.getInstance().getTowns()) {
                    names.add(town.getName());
                }
                return names;
            }
            if (args.length == 3) {
                return new ArrayList<>(mainManager.getPaydayManager().getPaydayById().keySet());
            }
        }
        return null;
    }
}
