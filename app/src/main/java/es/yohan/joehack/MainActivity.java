package es.yohan.joehack;

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


import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView t = (TextView)findViewById(R.id.statustext);
        t.setText(getStatus());

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        //sharedPref = getSharedPreferences("JoeHack", MODE_WORLD_READABLE);

        //work around
        File prefsDir = new File(getApplicationInfo().dataDir, "shared_prefs");
        File prefsFile = new File(prefsDir, "MainActivity.xml");
        if (prefsFile.exists()) {
            Log.d("YOHANES", "Fixing perm");
            prefsFile.setReadable(true, false);
        } else {
            Log.d("YOHANES", "Cant find " + prefsFile.toString());
        }


        boolean enabled = sharedPref.getBoolean("poke_enable_offset", false);
        final CheckBox cb = (CheckBox)findViewById(R.id.cbEnableOffset);
        cb.setChecked(enabled);

        double lat_offset = getDouble(sharedPref, "lat_offset", 0);
        double lon_offset = getDouble(sharedPref, "lon_offset", 0);

        setPokeConfig(enabled, lat_offset, lon_offset);

        final EditText lat_view = (EditText)findViewById(R.id.latOffsetText);
        final EditText lon_view = (EditText)findViewById(R.id.lonOffsetText);

        lat_view.setText(Double.toString(lat_offset));
        lon_view.setText(Double.toString(lon_offset));

        Button apply = (Button)findViewById(R.id.applyButton);

        apply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean enabled = cb.isChecked();
                double lat_offset = getDouble(sharedPref, "lat_offset", 0);
                double lon_offset = getDouble(sharedPref, "lon_offset", 0);

                String lat_s = lat_view.getText().toString();
                try {
                    lat_offset = Double.parseDouble(lat_s);
                    Log.d("YOHANES", "lat_offset parsed " + lat_offset);
                } catch (NumberFormatException ne) {

                }
                String lon_s = lon_view.getText().toString();
                try {
                    lon_offset = Double.parseDouble(lon_s);
                    Log.d("YOHANES", "lon_offset parsed " + lon_offset);
                } catch (NumberFormatException ne) {

                }

                setPokeConfig(enabled, lat_offset, lon_offset);

                Log.d("YOHANES", "setting text");

                lat_view.setText(Double.toString(lat_offset));
                lon_view.setText(Double.toString(lon_offset));


            }
        }
        );


    }

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

    //this will be intercepted by module
    void setPokeConfig(boolean enabled, double lat_offset, double lon_offset) {
        Log.d("YOHANES", "Set poke config " + enabled + " " + lat_offset + "," + lon_offset);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPref.edit();
        editor.putBoolean("poke_enable_offset", enabled);
        putDouble(editor, "lat_offset", lat_offset);
        putDouble(editor, "lon_offset", lon_offset);
        editor.commit();

        //make sure still readable
        File prefsDir = new File(getApplicationInfo().dataDir, "shared_prefs");
        File prefsFile = new File(prefsDir, "MainActivity.xml");
        if (prefsFile.exists()) {
            Log.d("YOHANES", "Fixing perm");
            prefsFile.setReadable(true, false);
        } else {
            Log.d("YOHANES", "Cant find " + prefsFile.toString());
        }

    }

    String getStatus() {
        if (isInstalled()) {
            return "XPosed module installed. Test value: " + hooked("NotOK") + " : "+ static_hooked("NotOK");
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

    static String static_hooked(String inp) {
        return "StaticHooked-" + inp;
    }

}
