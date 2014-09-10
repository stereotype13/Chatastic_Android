package io.chatastic.chatastic.Events;

/**
 * Created by r on 9/9/2014.
 */
public class ConversationListItemClicked {

    public final int position;
    public final long conversationID;

    public ConversationListItemClicked(int position, long conversationID) {
        this.position = position;
        this.conversationID = conversationID;
    }

}
