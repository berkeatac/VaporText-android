package berkea.vaportext.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import berkea.vaportext.TextUtils;

public class MainViewModel extends ViewModel {

    private MutableLiveData<String> resultStringLiveData;

    public MainViewModel() {
        resultStringLiveData = new MutableLiveData<>();
    }

    public void createResultString(String str, Boolean caps) {
        resultStringLiveData.setValue(TextUtils.toVaporText(str, caps));
    }

    public MutableLiveData<String> getResultString() {
        return resultStringLiveData;
    }
}
