package work.matse.blockui

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()

        Handler().postDelayed(Runnable {
            initService()
        }, 200)
    }

    private fun initService() {
        if (checkOverlayDisplayPermission()) {
            startService()
        } else {
            requestOverlayDisplayPermission()
        }
    }

    private fun startService() {
        val overlayService = Intent(this, ToastService::class.java)
        ContextCompat.startForegroundService(this, overlayService)

        log("Started background service")
    }

    private fun checkOverlayDisplayPermission(): Boolean {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(this)
        } else {
            true
        }
    }

    private fun requestOverlayDisplayPermission() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle("Screen Overlay Permission Needed")
        builder.setMessage("Enable 'Display over other apps' from System Settings.")
        builder.setPositiveButton("Open Settings", DialogInterface.OnClickListener { _, _ ->
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, Activity.RESULT_OK)
        })

        val dialog = builder.create()
        dialog.show()
    }
}