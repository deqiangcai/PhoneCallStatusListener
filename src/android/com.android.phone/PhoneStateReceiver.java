package com.android.phone;

import org.apache.cordova.CallbackContext;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("PhoneStateReceiver", "PhoneStateReceiver action: " + action);
        String resultData = this.getResultData();
        Log.d("PhoneStateReceiver", "PhoneStateReceiver getResultData: " + resultData);
        if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            // 去电，可以用定时挂断
            // 双卡的手机可能不走这个Action
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.d("PhoneStateReceiver", "PhoneStateReceiver EXTRA_PHONE_NUMBER: " + phoneNumber);
            if(PhoneCallStatusListener.getCallbackContext() != null) {
                PhoneCallStatusListener.getCallbackContext().success("ACTION_NEW_OUTGOING_CALL");
            }
        } else {
            // 来电去电都会走
            // 获取当前电话状态
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            Log.d("PhoneStateReceiver", "PhoneStateReceiver onReceive state: " + state);
            if(PhoneCallStatusListener.getCallbackContext() != null) {
                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    PhoneCallStatusListener.getCallbackContext().success("ringing");
                } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                     PhoneCallStatusListener.getCallbackContext().success("offhook");
                } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                     PhoneCallStatusListener.getCallbackContext().success("idle");
                }
            }
            // 获取电话号码
            String extraIncomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Log.d("PhoneStateReceiver", "PhoneStateReceiver onReceive extraIncomingNumber: " + extraIncomingNumber);
            if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {
                Log.d("PhoneStateReceiver", "PhoneStateReceiver onReceive endCall");
            }
        }
    }
}
