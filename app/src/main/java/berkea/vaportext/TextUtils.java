package berkea.vaportext;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class TextUtils {
    TextUtils() {

    }

    public static String toVaporText(String message, Boolean isUpperCase) {
        if (message == null || message.equals("")) {
            return null;
        }
        int len = message.length();
        if(isUpperCase) {
            message = message.toUpperCase();
        }

        StringBuilder result = new StringBuilder();
        for(int i = 0; i < len; i++) {
            char ch = message.charAt(i);
            result.append(ch).append(" ");
        }
        return result.toString();
    }

    public static void copyToClipboard(String copyText, Context context) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData
                .newPlainText("VaporText", copyText);
        if(clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }

        Toast toast = Toast.makeText(context,
                "Copied Text!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.show();
    }
}
