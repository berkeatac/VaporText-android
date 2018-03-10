package berkea.vaportext;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import berkea.vaportext.Activity.MainActivity;

/**
 * Created by berkeatac on 11/03/2018.
 */

public class TextUtils {
    TextUtils() {

    }

    public static String toVaporText(String message, Boolean isUpperCase) {
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

    public static void copyToClipboard(String copyText) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                MainActivity.model.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData
                .newPlainText("Your text", copyText);
        if(clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }

        Toast toast = Toast.makeText(MainActivity.model.getContext(),
                "Text is copied", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.show();
    }
}
