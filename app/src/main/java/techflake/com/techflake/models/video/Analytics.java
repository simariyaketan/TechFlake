
package techflake.com.techflake.models.video;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Analytics {

    @SerializedName("onload")
    @Expose
    private Onload onload;
    @SerializedName("onclick")
    @Expose
    private Onclick onclick;
    @SerializedName("onsent")
    @Expose
    private Onsent onsent;

    public Onload getOnload() {
        return onload;
    }

    public void setOnload(Onload onload) {
        this.onload = onload;
    }

    public Onclick getOnclick() {
        return onclick;
    }

    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }

    public Onsent getOnsent() {
        return onsent;
    }

    public void setOnsent(Onsent onsent) {
        this.onsent = onsent;
    }

}
