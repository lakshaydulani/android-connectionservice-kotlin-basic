package com.darkhorse.videocalltest

import android.content.Context
import android.content.Intent
import android.telecom.Connection
import android.util.Log

class VoipConnection(ctx: Context) : Connection(){

    val TAG = "VoipConnection"
    val context: Context

    init {
        context = ctx
    }


    override fun onShowIncomingCallUi() {
        super.onShowIncomingCallUi()
        Log.i(TAG, "onShowIncomingCallUi")
    }

    override fun onAnswer() {
        super.onAnswer()
        val myIntent: Intent = Intent(context, MainActivity::class.java)
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(myIntent)
    }
}