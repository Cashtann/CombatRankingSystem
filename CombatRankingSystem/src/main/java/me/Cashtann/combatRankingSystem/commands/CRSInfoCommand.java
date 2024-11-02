package me.Cashtann.combatRankingSystem.commands;

import me.Cashtann.combatRankingSystem.ranking.RatingController;
import me.Cashtann.combatRankingSystem.utilities.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CRSInfoCommand extends CRSSubCommand {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Get the player combat rating";
    }

    @Override
    public String getSyntax() {
        return "/crs info <player>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length > 1) {
            if (Bukkit.getPlayerExact(args[1]) != null) {
                Player target = Bukkit.getPlayerExact(args[1]);

                String message = target.getName();
                message += "'s rating is ";
                message += String.valueOf(RatingController.getPlayerCombatRating(target));
                PlayerMessage.sendCommandOutput(true, player, message);
            } else {
                PlayerMessage.sendCommandOutput(false, player, "Specified player either doesn't exist or is offline");
            }
        } else if (args.length == 1) {
            PlayerMessage.sendCommandOutput(false, player, "You did not pass the player name, please try again");
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {

        if (args.length == 2) {
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                playerNames.add(players[i].getName());
            }
            return playerNames;
        }

        return List.of();
    }
}
