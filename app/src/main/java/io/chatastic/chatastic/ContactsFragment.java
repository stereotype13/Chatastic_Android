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

import io.chatastic.chatastic.Models.User;
import se.emilsjolander.sprinkles.ModelList;

/**
 * Created by r on 9/16/2014.
 */
public class ContactsFragment extends Fragment {

    private String CONTACTS_BUNDLE_KEY = "CONTACTS_BUNDLE_KEY";
    private ModelList<User> mContacts;

    public ContactsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            mContacts = (ModelList<User>)savedInstanceState.getSerializable(CONTACTS_BUNDLE_KEY);
        }

        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        ListView contactsListView = (ListView)rootView.findViewById(R.id.lvContacts);

        if(mContacts != null) {
            ContactsAdapter contactsAdapter = new ContactsAdapter(mContacts);
            contactsListView.setAdapter(contactsAdapter);
        }


        return rootView;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CONTACTS_BUNDLE_KEY, mContacts);
        super.onSaveInstanceState(outState);
    }

    public void setContacts(ModelList<User> contacts) {
        this.mContacts = contacts;
    }


    public class ContactsAdapter extends BaseAdapter {

        private ModelList<User> mContacts;

        public ContactsAdapter(ModelList<User> contacts) {
            mContacts = contacts;
        }

        @Override
        public int getCount() {
            return mContacts.size();
        }

        @Override
        public Object getItem(int i) {
            return mContacts.get(i);
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
                cView = inflater.inflate(R.layout.fragment_contacts_item, viewGroup, false);
            }

            User contact = (User)getItem(i);

            TextView nameTextView = (TextView)cView.findViewById(R.id.tvContactName);
            nameTextView.setText(contact.getDisplayName());

            TextView numberTextView = (TextView)cView.findViewById(R.id.tvContactNumber);
            numberTextView.setText(contact.getMobileNumber());

            return cView;
        }
    }
}
