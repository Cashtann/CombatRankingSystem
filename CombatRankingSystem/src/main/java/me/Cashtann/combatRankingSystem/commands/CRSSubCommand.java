package me.Cashtann.combatRankingSystem.commands;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class CRSSubCommand {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void perform(Player player, String args[]);

    public abstract List<String> getSubcommandArguments(Player player, String args[]);
}
