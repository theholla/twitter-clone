package com.epicodus.twitterclone.ui;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epicodus.twitterclone.R;
import com.epicodus.twitterclone.models.Tweet;
import com.epicodus.twitterclone.models.User;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    private SharedPreferences mPreferences;
    private User mUser;
    private EditText mTweetText;
    private Button mSubmitButton;
    private ArrayList<Tweet> mTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getApplicationContext().getSharedPreferences("twitter", Context.MODE_PRIVATE);

        mTweetText = (EditText) findViewById(R.id.newTweetEdit);
        mSubmitButton = (Button) findViewById(R.id.tweetSubmitButton);
        mTweets = (ArrayList) Tweet.all();

        // redirect user to registration page if not registered
        if (!isRegistered()) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = mTweetText.getText().toString();
                Tweet tweet = new Tweet(tweetContent, mUser);
                tweet.save();
                mTweets.add(tweet);

                //clear focus
                mTweetText.setText("");
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    private boolean isRegistered() {
        String username = mPreferences.getString("username", null); //key, value
        if (username != null) {
            setUser(username);
            return true;
        } else {
            return false;
        }
    }

    private void setUser(String username) {
        User user = User.find(username);
        if (user != null) {
            mUser = user;
        } else {
            mUser = new User(username);
            mUser.save();
        }
        Toast.makeText(this, "Welcome " + mUser.getName(), Toast.LENGTH_LONG).show();
    }

}
