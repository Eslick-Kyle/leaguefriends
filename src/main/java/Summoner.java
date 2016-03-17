
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KyleE
 */
public class Summoner {
    private int id;
    private String name;
    private String profileIconId;
    private String summonerLevel;
    private List<LeagueGame> games;

    public Summoner() {
        id = 0;
        name = "";
        profileIconId = "";
        summonerLevel = "";
        games = new ArrayList<>();
    }
    
    public void addGames(String JSONGames) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LeagueGame.class, new LeagueGameDeserializer());
        Gson gson = gsonBuilder.create();
        JSONGames = JSONGames.replace("{\"summonerId\":21009618,\"games\":", "");
        JSONGames = JSONGames.replace("}}]}", "}}]");
        LeagueGame leagueGames[] = gson.fromJson(JSONGames, LeagueGame[].class);
        for (LeagueGame e : leagueGames) {
            this.addGame(e);
        }
        
    }

    public List<LeagueGame> getGames() {
        return games;
    }

    public void setGames(List<LeagueGame> games) {
        this.games = games;
    }
    
    public void addGame(LeagueGame toadd) {
        games.add(toadd);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(String profileIconId) {
        this.profileIconId = profileIconId;
    }

    public String getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(String summonerLevel) {
        this.summonerLevel = summonerLevel;
    }
    
}
