package berkea.vaportext.ViewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import berkea.vaportext.TextUtils

class MainViewModel : ViewModel() {

    val resultString: MutableLiveData<String> = MutableLiveData()

    fun createResultString(str: String, caps: Boolean?) {
        resultString.value = TextUtils.toVaporText(str, caps)
    }
}
