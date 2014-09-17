package io.chatastic.chatastic.Models;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Created by r on 9/8/2014.
 */
@Table("Users")
public class User extends Model {

    @Key
    @AutoIncrement
    @Column("id")
    private long id;

    @Column("mobile_number")
    private String mobileNumber;

    @Column("display_name")
    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public long getId() {
        return id;
    }
}
