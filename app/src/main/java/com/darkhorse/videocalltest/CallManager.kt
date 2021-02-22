package com.darkhorse.videocalltest

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telecom.PhoneAccount
import android.telecom.PhoneAccountHandle
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.content.ContextCompat

class CallManager(context: Context) {

    val telecomManager: TelecomManager
    val telephonyManager: TelephonyManager
    var phoneAccountHandle: PhoneAccountHandle
    var context: Context

    init {
        telecomManager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        this.context = context
        val componentName = ComponentName(this.context, ConnService::class.java)
        phoneAccountHandle = PhoneAccountHandle(componentName,"com.darkhorse.videocalltest")

    }

    fun register(){
        val componentName = ComponentName(this.context, ConnService::class.java)
        phoneAccountHandle = PhoneAccountHandle(componentName,"com.darkhorse.videocalltest")

        val phoneAccount = PhoneAccount.builder(phoneAccountHandle,"com.darkhorse.videocalltest").setCapabilities(PhoneAccount.CAPABILITY_CONNECTION_MANAGER).setCapabilities(PhoneAccount.CAPABILITY_CALL_PROVIDER).build()

        try{
        telecomManager!!.registerPhoneAccount(phoneAccount)
        }
        catch(e: Exception){

        }
    }

    fun incomingCall(caller: String?){
        val callInfo = Bundle()
        callInfo.putString("from", caller)
        val componentName = ComponentName(this.context, ConnService::class.java)
        phoneAccountHandle = PhoneAccountHandle(componentName,"com.darkhorse.videocalltest")
        if(checkAccountConnection(this.context))
        telecomManager.addNewIncomingCall(phoneAccountHandle,callInfo)
        else
        Log.i("incomingCall", "no permission")
    }

    private fun checkAccountConnection(context: Context): Boolean {
        var isConnected = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_PHONE_STATE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val enabledAccounts: List<PhoneAccountHandle> =
                    telecomManager.getCallCapablePhoneAccounts()
                for (account in enabledAccounts) {
                    if (account.componentName.className == ConnService::class.java.getCanonicalName()) {
                        isConnected = true
                        break
                    }
                }
            }
        }
        return isConnected
    }
}