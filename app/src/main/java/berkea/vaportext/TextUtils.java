package berkea.vaportext;

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
}
