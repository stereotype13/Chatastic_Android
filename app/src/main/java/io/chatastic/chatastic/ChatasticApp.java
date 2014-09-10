package io.chatastic.chatastic;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.chatastic.chatastic.Models.Conversation;
import io.chatastic.chatastic.Models.Message;
import io.chatastic.chatastic.Models.Participant;
import io.chatastic.chatastic.Models.User;
import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.Migration;
import se.emilsjolander.sprinkles.ModelList;
import se.emilsjolander.sprinkles.Query;
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
                                "conversation_id INTEGER,"+
                                "participant_id INTEGER,"+
                                "body TEXT" +
                                ")"
                );
                db.execSQL(
                        "CREATE TABLE Participants (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                "user_id INTEGER"+
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

            }
        });

        //For testing purposes
        //Add some seed data


        CursorList conversationsCursorList = Query.all(Conversation.class).get();
        ModelList<Conversation> conversations = ModelList.from(conversationsCursorList);

        CursorList messagesCursorList = Query.all(Message.class).get();
        ModelList<Message> messages = ModelList.from(messagesCursorList);

        for(Conversation c : conversations) {
            c.delete();
        }

        for(Message m : messages) {
            m.delete();
        }

        Conversation conversation = new Conversation();
        conversation.title = "This is the first conversation";
        conversation.save();

        conversation = new Conversation();
        conversation.title = "This is the second conversation";
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
}
