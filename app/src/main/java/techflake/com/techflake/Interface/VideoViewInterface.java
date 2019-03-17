package techflake.com.techflake.Interface;

import techflake.com.techflake.models.video.VideoClass;

public interface VideoViewInterface {
    void showToast(String s);
    void displayClientOrder(VideoClass videoClass);
    void displayError(String s);
}
