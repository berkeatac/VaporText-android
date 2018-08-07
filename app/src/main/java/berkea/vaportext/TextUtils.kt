package berkea.vaportext

import android.content.Context
import android.view.Gravity
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
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = android.content.ClipData
                    .newPlainText("VaporText", copyText)
            if (clipboard != null) {
                clipboard.primaryClip = clip
            }

            val toast = Toast.makeText(context,
                    "Copied Text!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 0, 0)
            toast.show()
        }
    }
}
