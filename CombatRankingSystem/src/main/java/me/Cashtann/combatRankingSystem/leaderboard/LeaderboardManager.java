package me.Cashtann.combatRankingSystem.leaderboard;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.files.PlayersStatsContainer;
import me.Cashtann.combatRankingSystem.ranking.PlayerStats;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.stream.Collectors;

public class LeaderboardManager {

    private final Map<String, List<Map.Entry<String, Integer>>> leaderboards;

    public LeaderboardManager() {
        this.leaderboards = new HashMap<>();
        updateLeaderboards();
    }

    public void updateLeaderboards() {
        leaderboards.clear();

        ConfigurationSection playersSection = PlayersStatsContainer.getConfig().getConfigurationSection("players");
        if (playersSection == null) return;

        Map<String, Integer> combatRatingMap = new HashMap<>();
        Map<String, Integer> killsMap = new HashMap<>();
        Map<String, Integer> deathsMap = new HashMap<>();
        Map<String, Integer> minedStoneMap = new HashMap<>();
        Map<String, Integer> distanceMap = new HashMap<>();
        Map<String, Integer> playtimeMap = new HashMap<>();

        for (String uuid : playersSection.getKeys(false)) {
            ConfigurationSection playerData = playersSection.getConfigurationSection(uuid);
            if (playerData == null) continue;

            String playerName = playerData.getString("playerName", "Unknown");

            combatRatingMap.put(playerName, playerData.getInt("combatRating", 0));
            killsMap.put(playerName, playerData.getInt("kills", 0));
            deathsMap.put(playerName, playerData.getInt("deaths", 0));
            minedStoneMap.put(playerName, playerData.getInt("minedStone", 0));
            distanceMap.put(playerName, playerData.getInt("distance_cm", 0) / 100 / 1000);
            playtimeMap.put(playerName, playerData.getInt("playtime_t", 0) / 20 / 60 / 60);
        }

        leaderboards.put("combatRating", sortTopEntries(combatRatingMap));
        leaderboards.put("kills", sortTopEntries(killsMap));
        leaderboards.put("deaths", sortTopEntries(deathsMap));
        leaderboards.put("minedStone", sortTopEntries(minedStoneMap));
        leaderboards.put("distance", sortTopEntries(distanceMap));
        leaderboards.put("playtime", sortTopEntries(playtimeMap));
    }

    private List<Map.Entry<String, Integer>> sortTopEntries(Map<String, Integer> data) {
        return data.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public int getPlayerPosition(String playerName, String category) {
        List<Map.Entry<String, Integer>> leaderboard = getTopPlayers(category);
        int position = 1;

        for (Map.Entry<String, Integer> entry : leaderboard) {
            if (entry.getKey().equals(playerName)) {
                return position; // Return the position if found
            }
            position++; // Increment position
        }

        return -1; // Return -1 if the player is not found in the leaderboard
    }


    public List<Map.Entry<String, Integer>> getTopPlayers(String category) {
        return leaderboards.getOrDefault(category, Collections.emptyList());
    }
}