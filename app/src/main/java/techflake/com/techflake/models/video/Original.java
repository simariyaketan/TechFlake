
package techflake.com.techflake.models.video;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Original implements Parcelable {

    @Id
    public long id;

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("frames")
    @Expose
    private String frames;
    @SerializedName("mp4")
    @Expose
    private String mp4;
    @SerializedName("mp4_size")
    @Expose
    private String mp4Size;
    @SerializedName("webp")
    @Expose
    private String webp;
    @SerializedName("webp_size")
    @Expose
    private String webpSize;
    @SerializedName("hash")
    @Expose
    public String hash = "";

    public Original() {
    }

    public Original(long id, String width,String height,String size,String frames,String mp4,String mp4Size,String webp,
                    String webpSize,String hash) {
            this.id = id;
            this.width = width;
            this.height = height;
            this.size = size;
            this.frames = frames;
            this.mp4 = mp4;
            this.mp4Size = mp4Size;
            this.webp = webp;
            this.webpSize = webpSize;
            this.hash = hash;
    }

    protected Original(Parcel in) {
        url = in.readString();
        height = in.readString();
        width = in.readString();
        size = in.readString();
        frames = in.readString();
        mp4 = in.readString();
        mp4Size = in.readString();
        webp = in.readString();
        webpSize = in.readString();
        hash = in.readString();
    }

    public static final Creator<Original> CREATOR = new Creator<Original>() {
        @Override
        public Original createFromParcel(Parcel in) {
            return new Original(in);
        }

        @Override
        public Original[] newArray(int size) {
            return new Original[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFrames() {
        return frames;
    }

    public void setFrames(String frames) {
        this.frames = frames;
    }

    public String getMp4() {
        return mp4;
    }

    public void setMp4(String mp4) {
        this.mp4 = mp4;
    }

    public String getMp4Size() {
        return mp4Size;
    }

    public void setMp4Size(String mp4Size) {
        this.mp4Size = mp4Size;
    }

    public String getWebp() {
        return webp;
    }

    public void setWebp(String webp) {
        this.webp = webp;
    }

    public String getWebpSize() {
        return webpSize;
    }

    public void setWebpSize(String webpSize) {
        this.webpSize = webpSize;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(width);
        dest.writeString(height);
        dest.writeString(size);
        dest.writeString(frames);
        dest.writeString(mp4);
        dest.writeString(mp4Size);
        dest.writeString(webp);
        dest.writeString(webpSize);
        dest.writeString(hash);
    }


}
