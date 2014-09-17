package io.chatastic.chatastic;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;

import com.parse.SignUpCallback;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import io.chatastic.chatastic.Events.BusProvider;
import io.chatastic.chatastic.Events.ConversationListItemClicked;
import io.chatastic.chatastic.Models.Conversation;
import io.chatastic.chatastic.Models.User;
import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.ModelList;
import se.emilsjolander.sprinkles.Query;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ConversationsFragment mConversationsFragment;
    private MessagesFragment mMessagesFragment;
    private ContactsFragment mContactsFragment;

    private static final String CONVERSATIONS_FRAGMENT = "CONVERSATIONS_FRAGMENT";
    private static final String MESSAGES_FRAGMENT = "MESSAGES_FRAGMENT";
    private static final String PLACEHOLDER_FRAGMENT = "PLACEHOLDER_FRAGMENT";
    private static final String CONTACTS_FRAGMENT = "CONTACTS_FRAGMENT";

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Test getting the phone number
        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        final String number = tm.getLine1Number();

        Toast.makeText(this, "+" + number, 1000).show();

        //////

        PushService.setDefaultPushCallback(this, MainActivity.class);

        ParseUser user = new ParseUser();
        user = ParseUser.getCurrentUser();
        if(user == null) {
            ParseUser.logInInBackground("+" + number, "", new LogInCallback() {
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        // Hooray! The user is logged in.
                        Toast.makeText(getParent(), "We loggin in!", 1000).show();
                    } else {
                        // Signup failed. Look at the ParseException to see what happened.
                        user.setUsername("+" + number);
                        user.setPassword("");


// other fields can be set just like with ParseObject
                        user.put("phone", "650-555-0000");

                        user.signUpInBackground(new SignUpCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    // Hooray! Let them use the app now.

                                } else {
                                    // Sign up didn't succeed. Look at the ParseException
                                    // to figure out what went wrong
                                    Log.e("TEST", "exception", e);
                                }
                            }
                        });
                        Log.e("TEST", "exception", e);
                    }
                }
            });
        }


        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("device_id", "number");
        installation.saveInBackground();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();

        switch (position) {
            case 0:

                mConversationsFragment = (ConversationsFragment)getFragmentManager().findFragmentByTag(CONVERSATIONS_FRAGMENT);
                mMessagesFragment = (MessagesFragment)getFragmentManager().findFragmentByTag(MESSAGES_FRAGMENT);

                Conversation conversation = new Conversation();


                CursorList cursorList = Query.all(Conversation.class).get();
                ModelList<Conversation> conversations = ModelList.from(cursorList);


                if(mConversationsFragment == null) {
                    mConversationsFragment = new ConversationsFragment();
                    mConversationsFragment.setConversations(conversations);
                    fragmentManager.beginTransaction()
                            .add(R.id.container, mConversationsFragment, CONVERSATIONS_FRAGMENT)
                            .addToBackStack(CONVERSATIONS_FRAGMENT)
                            .commit();
                }


                break;

            default:
                fragmentManager.beginTransaction()
                        .add(R.id.container, PlaceholderFragment.newInstance(position + 1), PLACEHOLDER_FRAGMENT)
                        .addToBackStack(PLACEHOLDER_FRAGMENT)
                        .commit();
        }


    }

    @Subscribe
    public void onConversationListItemClicked(ConversationListItemClicked event) {

        //Launch a conversation fragment based the conversation clicked
        Conversation conversation = new Conversation(event.conversationID);
        mMessagesFragment = new MessagesFragment();
        mMessagesFragment.setConversation(conversation);

        getFragmentManager().beginTransaction()
                .add(R.id.container, mMessagesFragment, MESSAGES_FRAGMENT)
                .addToBackStack(MESSAGES_FRAGMENT)
                .commit();

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    public ModelList<User> getContacts() {

        Uri PHONE_CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String CONTACT_ID =  ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER;
        String TYPE = ContactsContract.CommonDataKinds.Phone.TYPE;
        int TYPE_MOBILE = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;

        ModelList<User> contacts = new ModelList<User>();
        Cursor contactCursor = getContentResolver().query(PHONE_CONTENT_URI, new String[]{CONTACT_ID, NAME, NUMBER}, TYPE + " = ?", new String[]{String.valueOf(TYPE_MOBILE)}, null, null);
        contactCursor.moveToFirst();
        do {
            User user = new User();

            user.setDisplayName(contactCursor.getString(contactCursor.getColumnIndex(NAME)));
            String number = contactCursor.getString(contactCursor.getColumnIndex(NUMBER));
            if(number != null) {
                number = number.replaceAll("[\\W_]","");
            }
            user.setMobileNumber(number);
            contacts.add(user);
        } while(contactCursor.moveToNext());

        return contacts;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            mContactsFragment = new ContactsFragment();
            ModelList<User> contacts = getContacts();
            mContactsFragment.setContacts(contacts);
            getFragmentManager().beginTransaction().add(R.id.container, mContactsFragment, CONTACTS_FRAGMENT).addToBackStack(CONTACTS_FRAGMENT).commit();
            return true;
        }

        if(id == R.id.send_push) {
            ParseQuery query = ParseInstallation.getQuery();
            query.whereEqualTo("device_id", "1234567890");
            ParsePush push = new ParsePush();
            push.setQuery(query);
            push.setMessage("This is the push message! Woohoo!");
            push.sendInBackground();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_conversations, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


}
