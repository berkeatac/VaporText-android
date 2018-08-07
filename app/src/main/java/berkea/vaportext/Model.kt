package berkea.vaportext

import android.content.Context

import java.util.Date

/**
 * Created by berkeatac on 11/03/2018.
 */

class Model(context: Context, timestamp: Date) {
    private var context: Context? = null
    private var timestamp: Date? = null

    init {
        this.context = context
        this.timestamp = timestamp
    }

    fun getContext(): Context? {
        return context
    }

    fun setContext(context: Context) {
        this.context = context
    }

    fun getTimestamp(): Date? {
        return timestamp
    }

    fun setTimestamp(timestamp: Date) {
        this.timestamp = timestamp
    }


}
