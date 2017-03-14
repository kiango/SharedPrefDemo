package com.example.torsh.sharedprefdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextBusiness;
    private TextView textViewName, textViewBusiness;
    private Switch switchPageColor;
    private LinearLayout pageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = (EditText) findViewById(R.id.editTextN);
        editTextBusiness = (EditText) findViewById(R.id.editTextB);
        textViewName = (TextView) findViewById(R.id.textViewN);
        textViewBusiness = (TextView) findViewById(R.id.textViewB);
        switchPageColor = (Switch) findViewById(R.id.switch1);
        pageLayout = (LinearLayout) findViewById(R.id.activity_main);

        switchPageColor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setPageColor(isChecked);
            }
        });

        // on app Create we want to check the background color by retrieving
        // color value from Activity level shared preferences
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        // set default as false.
        boolean isChecked = sharedPreferences.getBoolean("green", false);
        //  If 'green' is true from the above then switchPageColor will be called
        switchPageColor.setChecked(isChecked);
    }

    private void setPageColor(boolean isChecked) {

        // user color prefs initialized and stored at activity evel shared preferences
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("green", isChecked);
        editor.apply();

        // if isChecked is true the color will be green
        pageLayout.setBackgroundColor(isChecked ? Color.GREEN : Color.WHITE);
    }

    public void openSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void saveAccountData(View view) {

        // 'getSharedPreferences' creates file sharable for the whole application
        // application level access:
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName() + Constants.PREF_FILE_NAME, Context.MODE_PRIVATE);

        // getPreferences creates and xml-file with the name of the current activity
        // in the shared preferences folder
        // data in shared preferences is persistent and stays in the xml-file
        // until it is manually deleted by setting> application> clear data
        // getPreferences only for this MainActivity access level

        //SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPreferences.edit(); // access the sp editor
        spEditor.putString(Constants.KEY_NAME, editTextName.getText().toString()); // insert k,v in sp
        spEditor.putString(Constants.KEY_BUSINESS, editTextBusiness.getText().toString());
        spEditor.putInt(Constants.KEY_BIZ_ID, 387);
        // save the data by apply() asynchronously not acid, no return values
        spEditor.apply();
        // or spEditor.commit(), commit is synchronous and returns boolean (acid)
    }

    public void loadAccountData(View view) {
        // application level access:
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName() + Constants.PREF_FILE_NAME, Context.MODE_PRIVATE);
        // Activity level access:
        //SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        // get data from sp, N/A in case of not getting nullPointerExp if the value was not set
        // if data deleted manually, default values ("N/A"...) will be loaded
        String name = sharedPreferences.getString(Constants.KEY_NAME, "N/A");
        String business = sharedPreferences.getString(Constants.KEY_BUSINESS, "N/A");
        int prof_id = sharedPreferences.getInt(Constants.KEY_BIZ_ID, 0);

        textViewName.setText(name);
        String profStr = business + " " + prof_id;
        textViewBusiness.setText(profStr);

        // stored data can be found in Android Device Monitor>
        // DDMS tab> file explorer> find device> find data folder> find another data folder>
        // find application pkg-folder> find shared preferences folder> find main activity.xml

    }
}
