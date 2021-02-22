package com.darkhorse.videocalltest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telecom.PhoneAccount
import android.telecom.PhoneAccountHandle
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val cm: CallManager = CallManager(this)
        val telecomManager: TelecomManager
        val telephonyManager: TelephonyManager
        var phoneAccountHandle: PhoneAccountHandle

        telecomManager = this.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        telephonyManager = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        val componentName = ComponentName(this, ConnService::class.java)
        phoneAccountHandle = PhoneAccountHandle(componentName, "com.darkhorse.videocalltest")
        val phoneAccount = PhoneAccount.builder(phoneAccountHandle, "com.darkhorse.videocalltest").setCapabilities(
            PhoneAccount.CAPABILITY_CONNECTION_MANAGER
        ).setCapabilities(PhoneAccount.CAPABILITY_CALL_PROVIDER).build()


        findViewById<Button>(R.id.button).setOnClickListener { view ->
            try {
                telecomManager.registerPhoneAccount(phoneAccount)
                    val intent = Intent()
                    intent.component = ComponentName(
                        "com.android.server.telecom",
                        "com.android.server.telecom.settings.EnableAccountPreferenceActivity"
                    )
                    startActivity(intent)

            } catch (e: Exception) {
                Log.e("main activity register", e.toString())
            }
        }

        findViewById<Button>(R.id.button2).setOnClickListener { view ->
            try {
                val callInfo = Bundle()
                callInfo.putString("from", "test")
                telecomManager.addNewIncomingCall(phoneAccountHandle, callInfo)
            } catch (e: Exception) {
                Log.e("main activity incoming", e.toString())
            }
        }
    }

}