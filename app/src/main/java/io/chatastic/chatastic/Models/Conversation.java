package io.chatastic.chatastic.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Created by r on 9/8/2014.
 */

@Table("Conversations")
public class Conversation extends Model implements Parcelable {

    final static String ID_KEY = "ID_KEY";
    final static String TITLE_KEY = "TITLE_KEY";

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

    public Conversation() {

    }

    //Methods used to implement Parceleable Interface

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Conversation> CREATOR = new Parcelable.Creator<Conversation>() {
        public Conversation createFromParcel(Parcel in) {
            return new Conversation(in);
        }

        public Conversation[] newArray(int size) {
            return new Conversation[size];
        }
    };

    private Conversation(Parcel in) {
        id = in.readLong();
        title = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);

    }
}
