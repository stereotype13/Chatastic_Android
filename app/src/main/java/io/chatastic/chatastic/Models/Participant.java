package io.chatastic.chatastic.Models;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Created by r on 9/8/2014.
 */
@Table("Participants")
public class Participant extends Model {

    @Key
    @AutoIncrement
    @Column("id")
    private long id;

    @Column("user_id")
    public long user_id;

    public User user;

    public long getId() {
        return id;
    }
}
