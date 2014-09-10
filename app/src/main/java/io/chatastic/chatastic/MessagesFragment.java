package io.chatastic.chatastic;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import io.chatastic.chatastic.Models.Conversation;
import io.chatastic.chatastic.Models.Message;
import se.emilsjolander.sprinkles.ModelList;

/**
 * Created by r on 9/9/2014.
 */
public class MessagesFragment extends Fragment {

    private Conversation mConversation;

    public MessagesFragment() {

    }

    public void setConversation(Conversation conversation) {
        this.mConversation = conversation;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);
        ListView lvMessages = (ListView)rootView.findViewById(R.id.lvMessages);

        if(mConversation.messages != null) {
            MessagesAdapter messagesAdapter = new MessagesAdapter(mConversation.messages);
            lvMessages.setAdapter(messagesAdapter);
        }

        return rootView;
    }

    //Inner classes
    public class MessagesAdapter extends BaseAdapter {

        private ArrayList<Message> mMessages;

        public MessagesAdapter(ArrayList<Message> messages) {
            mMessages = messages;
        }

        @Override
        public int getCount() {
            return mMessages.size();
        }

        @Override
        public Object getItem(int i) {
            return mMessages.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View cView = view;
            if(cView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                cView = inflater.inflate(R.layout.fragment_messages_item, viewGroup, false);
            }

            Message message = (Message)getItem(i);

            TextView textView = (TextView)cView.findViewById(R.id.tvMessages);
            textView.setText(message.body);

            return cView;
        }
    }
}
