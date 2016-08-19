package es.yohan.joehack;


import android.content.Context;

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



    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("Loaded app: " + lpparam.packageName);
        if (lpparam.packageName.equals("com.bca.sakuku")) {
            hackSakuku(lpparam);
        }
        if (lpparam.packageName.equals("es.yohan.joehack")) {
            hackTest(lpparam);
        }

    }
}