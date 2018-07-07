package berkea.vaportext.ViewModel;

import android.arch.lifecycle.ViewModel;

public class DisplayMessageViewModel extends ViewModel {
    private String resultString;

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }
}
