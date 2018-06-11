package es.yohan.jokowitollshack;


import android.content.Context;
import android.location.Location;

import java.util.Locale;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findConstructorExact;


public class JoeHackXposed implements IXposedHookLoadPackage, IXposedHookInitPackageResources {


    private void hackTest(final LoadPackageParam lpparam) {
        findAndHookMethod("es.yohan.jokowitolls.MainActivity", lpparam.classLoader, "isInstalled",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(true);
                    }
                });


        findAndHookMethod("es.yohan.jokowitolls.MainActivity", lpparam.classLoader, "hooked",
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.args[0] = "OK";
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    }
                }
        );
    }

    private void hackMap(final LoadPackageParam lpparam) {

    }

        public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("JK Loaded app: " + lpparam.packageName);
        if (lpparam.packageName.equals("es.yohan.jokowitolls")) {
            hackTest(lpparam);
        }


    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam res)
            throws Throwable {

        if (!res.packageName.equals("com.google.android.apps.maps")) {
            return;
        }

        String rep = "Avoid Jokowi era's tolls";

        if (Locale.getDefault().getISO3Country().equals("IDN")) {
            rep = "Hindari jalan tol era Jokowi";
        }
        res.res.setReplacement("com.google.android.apps.maps",
                "string",
                "DIRECTIONS_OPTIONS_AVOID_TOLLS",
                rep);


    }
}