package com.salazarev.hw26servisenotification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService()
    }

    private fun startService() {
        val intent = Intent(this, ServiceWorker::class.java)
        intent.action = ServiceWorker.ACTION_START_SERVICE
        startService(intent)
    }
}