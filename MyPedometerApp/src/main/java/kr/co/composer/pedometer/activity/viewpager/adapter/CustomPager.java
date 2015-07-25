package kr.co.composer.pedometer.activity.viewpager.adapter;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by composer on 2015-07-25.
 */
public class CustomPager implements ViewPager.PageTransformer {
    private final float scalingStart = 1 - 0.9f;

    @Override
    public void transformPage(View page, float position) {

        if (position >= 0) {
            final int w = page.getWidth();
            float scaleFactor = 1 - scalingStart * position;

            page.setAlpha(1 - position);
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setTranslationX(w * (1 - position) - w);
        }
    }
}


