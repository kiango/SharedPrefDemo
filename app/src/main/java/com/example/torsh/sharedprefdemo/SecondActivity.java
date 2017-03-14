package com.example.torsh.sharedprefdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    //private Button button2load, button2Clear, button2remove;
    private TextView textView2N, textView2B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView2N = (TextView) findViewById(R.id.txtV2Name);
        textView2B = (TextView) findViewById(R.id.txtV2Biz);
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

        //Toast.makeText(getApplicationContext(), name+business+prof_id, Toast.LENGTH_SHORT).show();

        textView2N.setText(name);
        String profStr = business + " " + prof_id;
        textView2B.setText(profStr);

        // stored data can be found in Android Device Monitor>
        // DDMS tab> file explorer> find device> find data folder> find another data folder>
        // find application pkg-folder> find shared preferences folder> find main activity.xml

    }

    public void clearAccountData(View view) {
        SharedPreferences sp = getSharedPreferences(getPackageName() + Constants.PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    public void removeBizKey(View view) {
        SharedPreferences sp = getSharedPreferences(getPackageName() + Constants.PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(Constants.KEY_BUSINESS);
        editor.apply();
    }
}
