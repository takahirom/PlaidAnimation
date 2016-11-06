package com.github.takahirom.plaidanimation;

import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.v4.graphics.ColorUtils;

class MyColorUtils {

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     */
    static @CheckResult @ColorInt int setAlphaComponent(@ColorInt int color,
                                                        @FloatRange(from = 0f, to = 1f) float alpha) {
        return ColorUtils.setAlphaComponent(color, (int) (255f * alpha));
    }
}