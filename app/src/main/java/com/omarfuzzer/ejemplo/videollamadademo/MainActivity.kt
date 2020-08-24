package com.omarfuzzer.ejemplo.videollamadademo

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tbruyelle.rxpermissions.RxPermissions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()
    }

    private fun checkPermissions() {
        RxPermissions(this)
            .request(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .subscribe({ granted ->
                if (granted!!) {
                    try {
                        supportFragmentManager
                            .beginTransaction()
//                            .add(R.id.root_layout, FragmentVideoLlamada.newInstance("agente1@quobis"), FragmentVideoLlamada.TAG)
                            .add(R.id.root_layout, FragmentVideoLlamada.newInstance("user1@bancoazteca.com"), FragmentVideoLlamada.TAG)
                            .commit()
                    } catch (e: SecurityException) {
                        e.printStackTrace()
                    }
                }
            }, { error ->
                error.printStackTrace()
            })
    }
}