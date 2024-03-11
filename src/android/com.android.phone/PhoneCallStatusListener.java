package com.android.phone;

import org.apache.cordova.*;
import android.app.Activity;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.telephony.PhoneStateListener;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;

/**
 * This class echoes a string called from JavaScript.
 */
public class PhoneCallStatusListener extends CordovaPlugin {

    private static CustomPhoneStateListener phoneStateListener;
    private static CallbackContext sCallbackContext;
    private BroadcastReceiver telephonyReceiver;
    private CordovaWebView mWebView;

    @Override
    public void initialize(final CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        mWebView = webView;
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("startPhoneListener")) {
            this.startPhoneListener(callbackContext, args);
            return true;
        } else if (action.equals("unRegisterPhoneCall")) {
            this.unRegisterPhoneCall();
            return true;
        }
        return false;
    }

    private void startPhoneListener(CallbackContext callbackContext, JSONArray args) {
        sCallbackContext = callbackContext;
        Activity activity = this.cordova.getActivity();
        Context context = this.cordova.getActivity().getApplicationContext();
        phoneStateListener = new CustomPhoneStateListener(context);
        initTelephonyReceiver(context);
//         TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//         if (telephonyManager != null) {
//             telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
//         }
    }

    private void initTelephonyReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        this.telephonyReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(sCallbackContext.isFinished()) {
                    sCallbackContext = new CallbackContext(System.currentTimeMillis() + "", mWebView);
                }
//                 mWebView.sendPluginResult(new PluginResult(PluginResult.Status.OK, "message"), System.currentTimeMillis() + "");
                Log.e("PhoneCallStatusListener", "Receiver start");
                // If state has changed
                if ((intent != null) && intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
                    Log.e("PhoneCallStatusListener", "Receiver start Listener");
                    if (intent.hasExtra(TelephonyManager.EXTRA_STATE)) {
                        String extraData = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                        Log.e("PhoneCallStatusListener", "Receiver TelephonyManager state" +  extraData);
                         Log.e("PhoneCallStatusListener", "callbackContext:" + sCallbackContext.getCallbackId());
                        if (extraData.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                             cordova.getThreadPool().execute(new Runnable() {
                                 public void run() {
                                    Log.e("PhoneCallStatusListener", "callbackContext:" + sCallbackContext + "   state:" +  "ringing");
                                    sCallbackContext.success("ringing");
//                                     mWebView.sendPluginResult(new PluginResult(PluginResult.Status.OK, "ringing"), System.currentTimeMillis() + "");
                                 }
                             });
                        } else if (extraData.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                            cordova.getThreadPool().execute(new Runnable() {
                                 public void run() {
                                    Log.e("PhoneCallStatusListener", "callbackContext:" + sCallbackContext + "   state:" +  "offhook");
                                    sCallbackContext.success("offhook");
//                                     mWebView.sendPluginResult(new PluginResult(PluginResult.Status.OK, "offhook"), System.currentTimeMillis() + "");
                                 }
                            });
                        } else if (extraData.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                            cordova.getThreadPool().execute(new Runnable() {
                                 public void run() {
                                    Log.e("PhoneCallStatusListener", "callbackContext:" + sCallbackContext + "   state:" +  "idle");
                                    sCallbackContext.success("idle");
//                                     mWebView.sendPluginResult(new PluginResult(PluginResult.Status.OK, "idle"), System.currentTimeMillis() + "");
                                 }
                            });
                        }
                    }
                }
            }
        };
        // Register the receiver
        context.registerReceiver(this.telephonyReceiver, intentFilter);
    }

    public static CustomPhoneStateListener getListener() {
        return phoneStateListener;
    }

    public static CallbackContext getCallbackContext() {
        return sCallbackContext;
    }

    private void unRegisterPhoneCall() {
        if(this.telephonyReceiver != null) {
            Activity activity = this.cordova.getActivity();
            Context context = this.cordova.getActivity().getApplicationContext();
            context.unregisterReceiver(this.telephonyReceiver);
        }
    }



}
