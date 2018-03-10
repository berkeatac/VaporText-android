package berkea.vaportext;

import android.content.Context;

import java.util.Date;

/**
 * Created by berkeatac on 11/03/2018.
 */

public class Model {
    private static Context context;
    private static Date timestamp;

    public Model(Context context, Date timestamp) {
        this.context = context;
        this.timestamp = timestamp;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
