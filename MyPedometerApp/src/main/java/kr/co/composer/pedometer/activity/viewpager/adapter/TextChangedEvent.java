package kr.co.composer.pedometer.activity.viewpager.adapter;

/**
 * Created by composer on 2015-07-16.
 */
public class TextChangedEvent {
    public int newText;
    public int maxText;

    public TextChangedEvent() {
    }

    //setter
    public void setText(int newText) {
        this.newText = newText;
    }

    public void setMaxText(int maxText) {
        this.maxText = maxText;
    }


    //getter

    public int getText(){
        return this.newText;
    }

    public int getMaxText() {
        return this.maxText;
    }
}
