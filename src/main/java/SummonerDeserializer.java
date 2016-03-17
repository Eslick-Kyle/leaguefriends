
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KyleE
 */
public class SummonerDeserializer implements JsonDeserializer<Summoner>{

    @Override
    public Summoner deserialize(final JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jo = je.getAsJsonObject();
        String name = "";
        //This is a loop that runs once then breaks it gets the name of the person
        //there has to be a better way
        for (Map.Entry<String, JsonElement> e : jo.entrySet()){
            name = e.getKey();
            break;
        }
        jo = jo.get(name).getAsJsonObject();
        final Summoner summoner = new Summoner();

        summoner.setId(jo.get("id").getAsInt());
        summoner.setName(jo.get("name").getAsString());
        summoner.setProfileIconId(jo.get("profileIconId").getAsString());
        summoner.setSummonerLevel(jo.get("summonerLevel").getAsString());

        return summoner;
    }
    
}
