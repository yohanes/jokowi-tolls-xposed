package es.yohan.joehack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView t = (TextView)findViewById(R.id.statustext);
        t.setText(getStatus());
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
