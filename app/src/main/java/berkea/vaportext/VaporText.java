package berkea.vaportext;

/**
 * Created by berkeatac on 15/03/2018.
 */

public class VaporText {
    private String text;

    VaporText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
