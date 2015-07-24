package kr.co.composer.pedometer.activity.viewpager.adapter;

/**
 * Created by composer on 2015-07-16.
 */
public class TextChangedEvent {
    public int newText;

    public TextChangedEvent(int newText) {
        this.newText = newText;
    }

    public void setText(int text) {
        this.newText = text;
    }

    public int getText() {
        return newText;
    }
}
