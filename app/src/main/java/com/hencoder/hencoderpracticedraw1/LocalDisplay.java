package com.hencoder.hencoderpracticedraw1;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.WindowManager;

/**
 * 屏幕尺寸工具类
 */

public class LocalDisplay {
    public static int SCREEN_WIDTH_PIXELS;
    public static int SCREEN_HEIGHT_PIXELS;
    public static float SCREEN_DENSITY;
    public static float SCALED_DENSITY;
    public static int SCREEN_WIDTH_DP;
    public static int SCREEN_HEIGHT_DP;
    public static int NAV_BAR_HEIGHT_PIXELS; // 虚拟栏 高度
//    private static final boolean DEBUG = true;

    public static void init(Context context) {
        if (context == null)
            return;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);

        int navBarHeight = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navBarHeight = resources.getDimensionPixelSize(resourceId);
        }

        boolean hasPhysicalHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);

        int screen_height = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Point size = new Point();
            wm.getDefaultDisplay().getRealSize(size);
            screen_height = size.y;
        } else if (hasPhysicalHomeKey) {
            screen_height = dm.heightPixels;
        } else {
            screen_height = dm.heightPixels + navBarHeight;
        }

//        if (DEBUG) NSLog.i("display", "screen_height:" + screen_height);

        SCREEN_WIDTH_PIXELS = dm.widthPixels;
        SCREEN_HEIGHT_PIXELS = screen_height;
        NAV_BAR_HEIGHT_PIXELS = screen_height > dm.heightPixels ? screen_height - dm.heightPixels : 0;
        SCREEN_DENSITY = dm.density;
        SCALED_DENSITY = dm.scaledDensity;
        SCREEN_WIDTH_DP = (int) (SCREEN_WIDTH_PIXELS / dm.density);
        SCREEN_HEIGHT_DP = (int) (SCREEN_HEIGHT_PIXELS / dm.density);

//        if (DEBUG) {
//            NSLog.i("display", String.format("SCREEN PIXELS %s %s", SCREEN_WIDTH_PIXELS, SCREEN_HEIGHT_PIXELS));
//            NSLog.i("display", String.format("SCREEN DP %s %s", SCREEN_WIDTH_DP, SCREEN_HEIGHT_DP));
//            NSLog.i("display", String.format("density %s", dm.density));
//            NSLog.i("api version", String.format("version %s", Build.VERSION.SDK_INT));
//        }
    }

    public static int dp2px(float dp) {
//        return dp * SCREEN_DENSITY;
        return (int) (dp * SCREEN_DENSITY + 0.5f);
    }

    public static int px2dp(float dp) {
//        return dp / SCREEN_DENSITY;
        return (int) (dp / SCREEN_DENSITY + 0.5f);
    }

    public static int sp2px(float sp) {
//        return sp * SCALED_DENSITY;
        return (int) (sp * SCREEN_DENSITY + 0.5f);
    }
}
