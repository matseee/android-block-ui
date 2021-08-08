package work.matse.blockui

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.*
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import androidx.core.view.doOnAttach

class BlockUIService : Service() {
    private var keyboardLayout: TableLayout? = null
    private var keyTextView: TextView? = null

    private var windowManager: WindowManager? = null
    private var viewOverlay: View? = null

    private var key: String = ""
    private var currentInput: String = ""

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        windowManager =
            applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager?

        windowManager!!.addView(createView(), generateLayoutParams())
        return START_NOT_STICKY
    }

    private fun onKeyInput(str: String) {
        currentInput += str
        processInput()
    }

    private fun processInput() {
        if (isFalseInput()) reset()
         else if (isComplete()) close()
    }

    private fun isFalseInput(): Boolean {
        for (index in currentInput.indices) {
            if (currentInput[index] != key[index]) return true
        }
        return false
    }

    private fun isComplete(): Boolean {
        return currentInput == key
    }

    private fun close() {
        reset()
        windowManager!!.removeView(viewOverlay)
    }

    private fun toggleVisibility() {
        if (keyboardLayout!!.visibility == View.VISIBLE) {
            keyboardLayout!!.visibility = View.INVISIBLE
            keyTextView!!.visibility = View.INVISIBLE
        } else {
            generateKey()
            keyboardLayout!!.visibility = View.VISIBLE
            keyTextView!!.visibility = View.VISIBLE
        }
    }

    private fun generateKey() {
        key = ""
        do {
            key += (1..9).random().toString()
        } while (key.length < 4)

        keyTextView!!.text = key
    }

    private fun reset() {
        currentInput = ""
        toggleVisibility()
    }

    private fun createView(): View {
        val inflater =
                baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        viewOverlay = inflater.inflate(R.layout.overlay, null)

        viewOverlay!!.doOnAttach {
            val windowInsetController = viewOverlay!!.windowInsetsController

            viewOverlay!!.setOnApplyWindowInsetsListener { v, insets ->
                windowInsetController?.hide(WindowInsets.Type.statusBars())
                return@setOnApplyWindowInsetsListener insets
            }
        }

        keyTextView = viewOverlay!!.findViewById(R.id.tvKey)
        keyboardLayout = viewOverlay!!.findViewById(R.id.tlKeyboard)
        viewOverlay!!.findViewById<Button>(R.id.btn1).setOnClickListener { onKeyInput("1") }
        viewOverlay!!.findViewById<Button>(R.id.btn2).setOnClickListener { onKeyInput("2") }
        viewOverlay!!.findViewById<Button>(R.id.btn3).setOnClickListener { onKeyInput("3") }
        viewOverlay!!.findViewById<Button>(R.id.btn4).setOnClickListener { onKeyInput("4") }
        viewOverlay!!.findViewById<Button>(R.id.btn5).setOnClickListener { onKeyInput("5") }
        viewOverlay!!.findViewById<Button>(R.id.btn6).setOnClickListener { onKeyInput("6") }
        viewOverlay!!.findViewById<Button>(R.id.btn7).setOnClickListener { onKeyInput("7") }
        viewOverlay!!.findViewById<Button>(R.id.btn8).setOnClickListener { onKeyInput("8") }
        viewOverlay!!.findViewById<Button>(R.id.btn9).setOnClickListener { onKeyInput("9") }

        viewOverlay!!.findViewById<Button>(R.id.btnHideApplication).setOnClickListener {
            toggleVisibility()
        }

        return viewOverlay!!
    }

    private fun generateLayoutParams(): WindowManager.LayoutParams {
        val layoutParams =
                WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        PixelFormat.TRANSLUCENT
                )

        layoutParams.gravity = Gravity.TOP or Gravity.RIGHT
        layoutParams.x = 0
        layoutParams.y = 0

        return layoutParams
    }
}