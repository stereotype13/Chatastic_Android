package io.chatastic.chatastic;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by r on 9/8/2014.
 */
public class MessagesFragment extends Fragment {

    public MessagesFragment() {

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);

        return rootView;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
