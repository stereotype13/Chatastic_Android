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

/**
 * Created by r on 9/8/2014.
 */
public class MessagesFragment extends Fragment {

    private static final String CONVERSATION_BUNDLE_KEY = "CONVERSATION_BUNDLE_KEY";

    private ArrayList<Conversation> mConversations;

    public MessagesFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(savedInstanceState != null) {
           //mConversations = (ArrayList<Conversation>)savedInstanceState.getSerializable(CONVERSATION_BUNDLE_KEY);
        }

        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);
        ListView conversationsListView = (ListView)rootView.findViewById(R.id.lvConversations);

        ConversationsAdapter conversationsAdapter = new ConversationsAdapter(mConversations);
        conversationsListView.setAdapter(conversationsAdapter);

        return rootView;

    }

    public void setConversations(ArrayList<Conversation> conversations) {
        mConversations = conversations;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
       //outState.putSerializable(CONVERSATION_BUNDLE_KEY, mConversations);
       super.onSaveInstanceState(outState);
    }

    public class ConversationsAdapter extends BaseAdapter {

        private ArrayList<Conversation> mConversations;

        public ConversationsAdapter(ArrayList<Conversation> conversations) {
            mConversations = conversations;
        }

        @Override
        public int getCount() {
            return mConversations.size();
        }

        @Override
        public Object getItem(int i) {
            return mConversations.get(i);
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

            Conversation conversation = (Conversation)getItem(i);

            TextView textView = (TextView)cView.findViewById(R.id.tvConversationItem);
            textView.setText(conversation.title);

            return cView;
        }
    }
}
