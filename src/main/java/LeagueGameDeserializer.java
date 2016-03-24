
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KyleE
 */
public class LeagueGameDeserializer implements JsonDeserializer<LeagueGame> {

    @Override
    public LeagueGame deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jo = je.getAsJsonObject();

        final LeagueGame leagueGame = new LeagueGame();

        leagueGame.setGameId(jo.get("gameId").getAsLong());
        leagueGame.setGameMode(jo.get("gameMode").getAsString());
        leagueGame.setGameType(jo.get("gameType").getAsString());
        leagueGame.setSubType(jo.get("subType").getAsString());
        leagueGame.setMapId(jo.get("mapId").getAsInt());
        leagueGame.setChampionId(jo.get("championId").getAsInt());
        jo = jo.get("stats").getAsJsonObject();
        leagueGame.setLevel(jo.get("level").getAsInt());
        leagueGame.setGoldEarned(jo.get("goldEarned").getAsInt());
        if (jo.get("championsKilled") != null)
            leagueGame.setChampionsKilled(jo.get("championsKilled").getAsInt());
        if (jo.get("numDeaths") != null)
            leagueGame.setNumDeaths(jo.get("numDeaths").getAsInt());
        if (jo.get("assists") != null)
            leagueGame.setAssists(jo.get("assists").getAsInt());
        leagueGame.setMinionsKilled(jo.get("minionsKilled").getAsInt());
        leagueGame.setTotalDamageDealt(jo.get("totalDamageDealt").getAsInt());
        leagueGame.setTotalDamageTaken(jo.get("totalDamageTaken").getAsInt());
        if (jo.get("killingSprees") != null)
            leagueGame.setKillingSprees(jo.get("killingSprees").getAsInt());
        if (jo.get("largestKillingSpree") != null)
            leagueGame.setLargestKillingSpree(jo.get("largestKillingSpree").getAsInt());
        if (jo.get("win") != null)
            leagueGame.setWin(jo.get("win").getAsBoolean());
        if (jo.get("largestMultiKill") != null)
            leagueGame.setLargestMultiKill(jo.get("largestMultiKill").getAsInt());
        if (jo.get("totalDamageDealtToChampions") != null)
            leagueGame.setTotalDamageDealtToChampions(jo.get("totalDamageDealtToChampions").getAsInt());
        if (jo.get("timePlayed") != null)
            leagueGame.setTimePlayed(jo.get("timePlayed").getAsInt());
        if (jo.get("totalHeal") != null)
            leagueGame.setTotalHeal(jo.get("totalHeal").getAsInt());
        if (jo.get("wardKilled") != null)
            leagueGame.setWardKilled(jo.get("wardKilled").getAsInt());
        if (jo.get("wardPlaced") != null)
            leagueGame.setWardPlaced(jo.get("wardPlaced").getAsInt());
        if (jo.get("totalTimeCrowdControlDealt") != null)
            leagueGame.setTotalTimeCrowdControlDealt(jo.get("totalTimeCrowdControlDealt").getAsInt());
        if (jo.get("playerRole") != null)
            leagueGame.setPlayerRole(jo.get("playerRole").getAsInt());
        if (jo.get("playerPosition") != null)
            leagueGame.setPlayerPossition(jo.get("playerPosition").getAsInt());
        if (jo.get("visionWardsBought") != null)
            leagueGame.setVisionWardsBought(jo.get("visionWardsBought").getAsInt());
        if (jo.get("bountyLevel") != null)
            leagueGame.setBountyLevel(jo.get("bountyLevel").getAsInt());
        
        return leagueGame;
    }
    
}
