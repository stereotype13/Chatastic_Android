package io.chatastic.chatastic.Models;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Created by r on 9/8/2014.
 */
@Table("Messages")
public class Message extends Model {

    @Key
    @AutoIncrement
    @Column("id")
    private long id;

    @Column("conversation_id")
    public long conversation_id;

    @Column("participant_id")
    public long participant_id;

    @Column("body")
    public String body;

    public Participant participant;
    public Conversation conversation;

    public long getId() {
        return id;
    }

}
