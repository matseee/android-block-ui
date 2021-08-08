package work.matse.blockui

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.*
import android.widget.Button
import androidx.core.view.doOnAttach

class BlockUIService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("Received BlockUIReceiver intent")

        val layoutParams =
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSLUCENT
            )

//        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//        + WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//        + WindowManager.LayoutParams.FLAG_FULLSCREEN

        layoutParams.gravity = Gravity.TOP or Gravity.RIGHT
        layoutParams.x = 0
        layoutParams.y = 0

        val windowManager =
            applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager?

        val inflater =
            baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.overlay, null)

        view.doOnAttach {
            log("onViewAttached")
            val windowInsetController = view.windowInsetsController

            view.setOnApplyWindowInsetsListener { v, insets ->
                log("statusBars: " + insets.isVisible(WindowInsets.Type.statusBars()).toString())
                log("systemBars: " + insets.isVisible(WindowInsets.Type.systemBars()).toString())

                windowInsetController?.hide(WindowInsets.Type.statusBars())
                return@setOnApplyWindowInsetsListener insets
            }
        }

        val btnClose = view.findViewById<Button>(R.id.btnClose)
        btnClose.setOnClickListener {
            log("Button 'Close' clicked!")
            windowManager!!.removeView(view)
        }

        windowManager!!.addView(view, layoutParams)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}