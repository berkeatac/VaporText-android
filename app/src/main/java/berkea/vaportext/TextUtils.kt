package berkea.vaportext

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast

class TextUtils internal constructor() {
    companion object {

        fun toVaporText(message: String?, isUpperCase: Boolean?): String? {
            var message = message
            if (message == null || message == "") {
                return null
            }
            val len = message.length
            if (isUpperCase!!) {
                message = message.toUpperCase()
            }

            val result = StringBuilder()
            for (i in 0 until len) {
                val ch = message[i]
                result.append(ch).append(" ")
            }
            return result.toString()
        }

        fun copyToClipboard(copyText: String, context: Context) {
            val currentSdk = android.os.Build.VERSION.SDK_INT

            if (currentSdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                val manager: android.text.ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE)
                        as android.text.ClipboardManager
                try {
                    manager.text = copyText
                } catch (e: Exception) {
                    // ignored
                }
            } else {
                val manager: android.content.ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE)
                        as android.content.ClipboardManager
                manager.primaryClip = android.content.ClipData.newPlainText("label", copyText)
            }

            val toast = Toast.makeText(context,
                    "Copied Text!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 0, 0)
            toast.show()
        }

        /**
         * Copies TextView text to clipboard with given label
         */
        fun TextView.copyToClipboard(label : String) {

            val currentSdk = android.os.Build.VERSION.SDK_INT

            if (currentSdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                val manager: android.text.ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE)
                        as android.text.ClipboardManager
                try {
                    manager.text = text
                } catch (e: Exception) {
                    // ignored
                }
            } else {
                val manager: android.content.ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE)
                        as android.content.ClipboardManager
                manager.primaryClip?.let {
                    android.content.ClipData
                            .newPlainText(label, text)
                }
            }
        }
    }
}
