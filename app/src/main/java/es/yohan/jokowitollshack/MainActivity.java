package es.yohan.jokowitollshack;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


import java.io.File;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView t = (TextView)findViewById(R.id.statustext);
        t.setText(getStatus());



    }
    String getStatus() {
        if (isInstalled()) {
            return "XPosed module installed. Test value: " + hooked("NotOK");
        } else {
            return "Xposed module not installed";
        }
    }

    boolean isInstalled() {
        return false; //will be set by true from XPosed module
    }

    String hooked(String inp) {
        return "Hooked-" + inp;
    }

}
