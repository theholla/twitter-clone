package com.epicodus.twitterclone.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.epicodus.twitterclone.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText mNameEdit;
    private Button mLoginButton;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNameEdit = (EditText) findViewById(R.id.nameText);
        mLoginButton = (Button) findViewById(R.id.registerButton);
        // creates "twitter" file if not already created, sets access to private
        mPreferences = getApplicationContext().getSharedPreferences("twitter", Context.MODE_PRIVATE);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameEdit.getText().toString();
                SharedPreferences.Editor editor = mPreferences.edit();
                // the new Shared Prefs editor saves name into shared prefs file
                editor.putString("username", name); // key, value
                editor.commit();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
