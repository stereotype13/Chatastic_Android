package io.chatastic.chatastic;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.chatastic.chatastic.Models.User;

/**
 * Created by r on 9/16/2014.
 */
public class ContactsFragment extends Fragment {

    private ArrayList<User> mContacts;

    public ContactsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        return rootView;

    }

    public void setContacts(ArrayList<User> contacts) {
        this.mContacts = contacts;
    }
}
