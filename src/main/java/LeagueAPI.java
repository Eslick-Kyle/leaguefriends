
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KyleE
 */
public class LeagueAPI {
    private final String summonerURL = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/%s?api_key=72a4528f-1d22-4266-912e-59f7c3497efa";
    private final String gamesURL = "https://na.api.pvp.net/api/lol/na/v1.3/game/by-summoner/%s/recent?api_key=72a4528f-1d22-4266-912e-59f7c3497efa";

    public String getSummoner(String summonerName) {
        StringBuilder response = new StringBuilder();
        try {
            String url = String.format(summonerURL, summonerName.replace(" ", ""));
            System.out.println(url);
            URL sURL = new URL(url);
            URLConnection conn = sURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(LeagueAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LeagueAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println(response.toString());
        return response.toString();
    }
    
    public String getGames(String summonerId) {
        StringBuilder response = new StringBuilder();
        try {
            String url = String.format(gamesURL, summonerId);
            System.out.println(url);
            URL sURL = new URL(url);
            URLConnection conn = sURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(LeagueAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LeagueAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println(response.toString());
        return response.toString();
    }
    
    public String getSummonerURL() {
        return summonerURL;
    }

    public String getGamesURL() {
        return gamesURL;
    }
}
