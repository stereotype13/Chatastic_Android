package io.chatastic.chatastic.Models;

import java.util.ArrayList;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Created by r on 9/8/2014.
 */

@Table("Conversations")
public class Conversation extends Model {

    @Key
    @AutoIncrement
    @Column("id")
    private long id;

    @Column("title")
    public String title;

    public long getId() {
        return id;
    }

    public ArrayList<Message> messages;
    public ArrayList<Participant> participants;

}
