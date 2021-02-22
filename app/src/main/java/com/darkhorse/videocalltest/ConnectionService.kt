package com.darkhorse.videocalltest

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.telecom.*
import android.util.Log

class ConnService : ConnectionService() {

    override fun onCreateIncomingConnection(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?
    ): Connection {
        super.onCreateIncomingConnection(connectionManagerPhoneAccount, request)
        Log.i("CallConnectionService", "onCreateIncomingConnection")
        val conn = VoipConnection(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            conn.connectionProperties = Connection.PROPERTY_SELF_MANAGED
        }
        conn.setCallerDisplayName("test call", TelecomManager.PRESENTATION_ALLOWED)
        conn.setAddress(Uri.parse("tel:" + "+919582940055"), TelecomManager.PRESENTATION_ALLOWED)
        conn.setRinging()
        conn.setInitializing()
        conn.setActive()
        return conn
    }

}