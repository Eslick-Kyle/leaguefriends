
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author KyleE
 */
@Entity
public class Summoner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int riot_id;

    private String summoner_name;
    
    @Transient
    private String profileIconId;
    @Transient
    private String summonerLevel;
    @Transient
    private List<LeagueGame> games;

    public Summoner() {
        riot_id = 0;
        summoner_name = "";
        profileIconId = "";
        summonerLevel = "";
        games = new ArrayList<>();
    }

    public void addGames(String JSONGames) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LeagueGame.class, new LeagueGameDeserializer());
        Gson gson = gsonBuilder.create();
        String replace = String.format("{\"summonerId\":%s,\"games\":", this.riot_id);
        JSONGames = JSONGames.replace(replace, "");
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
        return riot_id;
    }

    public void setId(int id) {
        this.riot_id = id;
    }

    public String getName() {
        return summoner_name;
    }

    public void setName(String name) {
        this.summoner_name = name;
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

    public int getDbId() {
        return id;
    }

    public void setDbId(int id) {
        this.id = id;
    }

}
