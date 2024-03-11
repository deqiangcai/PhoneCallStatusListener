package com.android.phone;

import android.content.Context;
import org.apache.cordova.CallbackContext;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.content.Intent;

/**
 * 来去电监听
 */
public class CustomPhoneStateListener extends PhoneStateListener {

    private Context mContext;
    private CallbackContext callbackContext;

    public CustomPhoneStateListener(Context context) {
        mContext = context;
    }

    @Override
    public void onServiceStateChanged(ServiceState serviceState) {
        super.onServiceStateChanged(serviceState);
        Log.d("PhoneState", "CustomPhoneStateListener onServiceStateChanged: " + serviceState);
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        Log.d("PhoneState", "CustomPhoneStateListener state: " + state + " incomingNumber: " + incomingNumber);
//         this.callbackContext = PhoneCallStatusListener.getCallbackContext();
//         Log.d("PhoneState", "callbackContext: " + callbackContext);
    }
}
