package io.chatastic.chatastic;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import io.chatastic.chatastic.Events.BusProvider;
import io.chatastic.chatastic.Events.ConversationListItemClicked;
import io.chatastic.chatastic.Models.Conversation;
import se.emilsjolander.sprinkles.ModelList;

/**
 * Created by r on 9/8/2014.
 */
public class ConversationsFragment extends Fragment {

    private static final String CONVERSATION_BUNDLE_KEY = "CONVERSATION_BUNDLE_KEY";

    private ModelList<Conversation> mConversations;

    public ConversationsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(savedInstanceState != null) {
           mConversations = (ModelList<Conversation>)savedInstanceState.getSerializable(CONVERSATION_BUNDLE_KEY);
        }

        View rootView = inflater.inflate(R.layout.fragment_conversations, container, false);
        ListView conversationsListView = (ListView)rootView.findViewById(R.id.lvConversations);

        if(mConversations != null) {
            ConversationsAdapter conversationsAdapter = new ConversationsAdapter(mConversations);
            conversationsListView.setAdapter(conversationsAdapter);
        }

        conversationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Conversation selectedConversation = mConversations.get(position);
                long conversation_id = selectedConversation.getId();
                BusProvider.getInstance().post(new ConversationListItemClicked(position, conversation_id));
            }
        });

        return rootView;

    }

    public void setConversations(ModelList<Conversation> conversations) {
        mConversations = conversations;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
       outState.putSerializable(CONVERSATION_BUNDLE_KEY, mConversations);
       super.onSaveInstanceState(outState);
    }

    public class ConversationsAdapter extends BaseAdapter {

        private ModelList<Conversation> mConversations;

        public ConversationsAdapter(ModelList<Conversation> conversations) {
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
                cView = inflater.inflate(R.layout.fragment_conversations_item, viewGroup, false);
            }

            Conversation conversation = (Conversation)getItem(i);

            TextView textView = (TextView)cView.findViewById(R.id.tvConversationItem);
            textView.setText(conversation.title);

            return cView;
        }
    }
}
