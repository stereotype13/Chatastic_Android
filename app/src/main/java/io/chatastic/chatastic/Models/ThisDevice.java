package io.chatastic.chatastic.Models;

import android.content.Context;
import android.telephony.TelephonyManager;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Created by r on 9/17/2014.
 */
@Table("This_device")
public class ThisDevice extends Model {

    @Key
    @AutoIncrement
    @Column("id")
    private long id;

    @Column("mobile_number")
    private String mobileNumber;

    @Column("user_name")
    private String userName;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return id;
    }
}

