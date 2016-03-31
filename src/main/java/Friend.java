
import java.io.Serializable;
import javax.persistence.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Greg
 */
@Entity
public class Friend implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
//    @JoinColumn(name="id", insertable=false, updatable=false)
    private User user;
    
    @ManyToOne
//    @JoinColumn(name="id", insertable=false, updatable=false)
    private Summoner summoner;
    
//    private int user_id;
//    private int summoner_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Summoner getSummoner() {
        return summoner;
    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

//    public int getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(int user_id) {
//        this.user_id = user_id;
//    }
//
//    public int getSummoner_id() {
//        return summoner_id;
//    }
//
//    public void setSummoner_id(int summoner_id) {
//        this.summoner_id = summoner_id;
//    }
    
    
    
}
