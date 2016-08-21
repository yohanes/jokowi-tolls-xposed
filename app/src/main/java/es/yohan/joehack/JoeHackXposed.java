package es.yohan.joehack;


import android.content.Context;
import android.location.Location;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.callbacks.XCallback;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findConstructorExact;

import de.robv.android.xposed.XSharedPreferences;

/**
 * Created by yohanes on 8/19/16.
 */
public class JoeHackXposed implements IXposedHookLoadPackage {



    //testing before and after hook
    private void hackTest(final LoadPackageParam lpparam) {
        findAndHookMethod("es.yohan.joehack.MainActivity", lpparam.classLoader, "isInstalled",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(true);
                    }
                });

        findAndHookMethod("es.yohan.joehack.MainActivity", lpparam.classLoader, "setPokeConfig",
                boolean.class,
                double.class,
                double.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        boolean offset_enabled = (boolean)param.args[0];
                        double lat_offset = (double)param.args[1];
                        double lon_offset = (double)param.args[2];
                        XposedBridge.log("Change pokeconfig " + offset_enabled + " " + lat_offset + " " + lon_offset);
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XSharedPreferences pref = new XSharedPreferences(JoeHackXposed.class.getPackage().getName(), "MainActivity");
                        pref.makeWorldReadable();
                        pref.reload();
                        boolean offset_enabled = pref.getBoolean("poke_enable_offset", false);
                        double lat_offset = getDouble(pref, "lat_offset", 0);
                        double lon_offset = getDouble(pref, "lon_offset", 0);
                        XposedBridge.log("Change pokeconfig result: " + offset_enabled + " " + lat_offset + " " + lon_offset);
                    }
                });


        findAndHookMethod("es.yohan.joehack.MainActivity", lpparam.classLoader, "hooked",
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.args[0] = "OK";
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    }
                });

        findAndHookMethod("es.yohan.joehack.MainActivity", lpparam.classLoader, "static_hooked",
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.args[0] = "OK";
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    }
                });

    }

    private void hackSakuku(final LoadPackageParam lpparam) {
        findAndHookMethod("com.d.c", lpparam.classLoader, "j",
                String.class,
                new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method
                String xml = (String) param.args[0];
//                XposedBridge.log("Digipass XML: " + xml);
//                try {
//                    File ff = new File("/data/local/tmp/digipass.xml"); //file must exist && chmod 777
//                    PrintWriter pw = new PrintWriter(new FileWriter(ff));
//                    pw.println(xml);
//                    pw.close();
//                } catch (Exception e) {
//                    XposedBridge.log("Error writing file" + e.toString());
//                }
                param.args[0] = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><RootDetection version=\"11\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"signature.xsd\"><Android><files><path>/system/xbin/suxx</path></files><properties><name>test-xxxxx</name></properties><systemProperties><property><name>ro.securexxx</name><value>0</value></property><property><name>ro.debuggablexxx</name><value>1</value></property></systemProperties><applications><name>com.noshufou.android.suxx</name></applications><directories><writable><path>/xxxxxx</path></writable><readable><path>/dataxxxx</path></readable></directories><commands><command><line>which suxxx</line><result>0</result></command></commands><permissions><name>android.permission.REQUEST_SUPERUSERXXXX</name></permissions><processes><name>supersuxxxx</name></processes></Android><iOS><files><path>/Applications/Cydia.appxxx</path><path>/System/Library/LaunchDaemons/io.pangu.axe.untether.plistxxx</path></files><libraries><name>Cydiaxxxx</name></libraries><symbolicLinks><path>/Applicationsxxx</path></symbolicLinks><privateFolders><path>/private/var/mobile/Applicationsxxx</path></privateFolders><urlSchemes><url>xxxxcydia://package/com.example.package</url></urlSchemes></iOS></RootDetection>";
                XposedBridge.log("Changing digipass XML");
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("Change result " + param.getResult());
            }
        });
    }

    double getDouble(final XSharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }


    private void hackPokemonGo(final LoadPackageParam lpparam) {
        XposedBridge.log("Pokemon go hack: " + lpparam.packageName);
        int[] data = new int[0];
        findAndHookMethod("com.nianticlabs.nia.location.NianticLocationManager", lpparam.classLoader,
                "nativeLocationUpdate",
                Location.class,
                data.getClass(),
                Context.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        if (param.args[0]!=null) {
                            Location l = (Location) param.args[0];
                            XposedBridge.log("Pokemon GPS " + l.toString());
                            XposedBridge.log("Pokemon Provider" + l.getProvider());
                            XposedBridge.log("Pokemon Location" + l.getLatitude() + " " + l.getLongitude());
                            double lat = l.getLatitude();
                            double lon = l.getLongitude();

                            XSharedPreferences pref = new XSharedPreferences(JoeHackXposed.class.getPackage().getName(), "MainActivity");
                            pref.makeWorldReadable();
                            pref.reload();
                            boolean offset_enabled = pref.getBoolean("poke_enable_offset", false);
                            double lat_offset = getDouble(pref, "lat_offset", 0);
                            double lon_offset = getDouble(pref, "lon_offset", 0);

                            XposedBridge.log("pokeconfig noe " + offset_enabled + " " + lat_offset + " " + lon_offset);

                            if (offset_enabled) {
                                lat += lat_offset;
                                lon += lon_offset;
                                XposedBridge.log("Pokemon Location now" + l.getLatitude() + " " + l.getLongitude());
                            }
                            l.setLatitude(lat);
                            l.setLongitude(lon);
                        }
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    }
                });
        XposedBridge.log("Pokemon go hooked: " + lpparam.packageName);
    }



        public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("Loaded app: " + lpparam.packageName);
        if (lpparam.packageName.equals("com.bca.sakuku")) {
            hackSakuku(lpparam);
        }
        if (lpparam.packageName.equals("es.yohan.joehack")) {
            hackTest(lpparam);
        }

        if (lpparam.packageName.equals("com.nianticlabs.pokemongo")) {
            hackPokemonGo(lpparam);
        }


    }
}