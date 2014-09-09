package io.chatastic.chatastic;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import io.chatastic.chatastic.Models.Conversation;
import io.chatastic.chatastic.Models.Message;
import io.chatastic.chatastic.Models.Participant;
import io.chatastic.chatastic.Models.User;
import se.emilsjolander.sprinkles.Migration;
import se.emilsjolander.sprinkles.Sprinkles;

/**
 * Created by r on 9/8/2014.
 */
public class ChatasticApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Sprinkles sprinkles = Sprinkles.init(getApplicationContext());

        sprinkles.addMigration(new Migration() {
            @Override
            protected void onPreMigrate() {
                // do nothing
            }

            @Override
            protected void doMigration(SQLiteDatabase db) {
                db.execSQL(
                        "CREATE TABLE Conversations (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                "title TEXT" +
                                ")"
                );
                db.execSQL(
                        "CREATE TABLE Messages (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                "conversation_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                "participant_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                "body TEXT" +
                                ")"
                );
                db.execSQL(
                        "CREATE TABLE Participants (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                "user_id INTEGER PRIMARY KEY AUTOINCREMENT"+
                                ")"
                );
                db.execSQL(
                        "CREATE TABLE Users (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT"+
                                ")"
                );
            }

            @Override
            protected void onPostMigrate() {
                //For testing purposes
                //Add some seed data
                Conversation conversation = new Conversation();
                conversation.title = "This is the first conversation";
                conversation.save();

                User user = new User();
                Participant participant = new Participant();
                participant.user = user;
                participant.user_id = user.getId();

                Message message = new Message();
                message.participant_id = participant.getId();
                message.body = "This is just some message!";
                message.conversation_id = conversation.getId();
                message.conversation = conversation;
            }
        });



    }
}
