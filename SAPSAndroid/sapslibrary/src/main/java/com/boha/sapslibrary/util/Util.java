package com.boha.sapslibrary.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boha.sapslibrary.R;

import java.util.Random;

/**
 * Created by aubreymalabie on 4/6/16.
 */
public class Util {
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    static Random random;
    public static Drawable getRandomBackgroundImage(Context ctx) {
        random = new Random(System.currentTimeMillis());
        int index = random.nextInt(14);
        switch (index) {
            case 0:
                return ContextCompat.getDrawable(ctx, R.drawable.back1);
            case 1:
                return ContextCompat.getDrawable(ctx,
                        R.drawable.back2);
            case 2:
                return ContextCompat.getDrawable(ctx,
                        R.drawable.back3);
            case 3:
                return ContextCompat.getDrawable(ctx,
                        R.drawable.back4);
            case 4:
                return ContextCompat.getDrawable(ctx,
                        R.drawable.back5);
            case 5:
                return ContextCompat.getDrawable(ctx,
                        R.drawable.back6);
            case 6:
                return ContextCompat.getDrawable(ctx,
                        R.drawable.back7);
            case 7:
                return ContextCompat.getDrawable(ctx,
                        R.drawable.back8);
            case 8:
                return ContextCompat.getDrawable(ctx,
                        R.drawable.back9);
            case 9:
                return ContextCompat.getDrawable(ctx,
                        R.drawable.back10);
            case 10:
                return ContextCompat.getDrawable(ctx,
                        R.drawable.back6);
            case 11:
                return ContextCompat.getDrawable(ctx,
                        R.drawable.back12);
            case 12:
                return ContextCompat.getDrawable(ctx,
                        R.drawable.back13);
            case 13:
                return ContextCompat.getDrawable(ctx,
                        R.drawable.back14);
            case 14:
                return ContextCompat.getDrawable(ctx,
                        R.drawable.back15);
            default:
                return ContextCompat.getDrawable(ctx,
                        R.drawable.back3);

        }

    }
    /**
     * Create custom Action Bar
     *
     * @param ctx
     * @param actionBar
     * @param text
     * @param image
     * @return ImageView
     */
    public static ImageView setCustomActionBar(Context ctx,
                                               ActionBar actionBar, String text, Drawable image) {
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater)
                ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.action_bar_logo, null);
        TextView txt = (TextView) v.findViewById(R.id.ACTION_BAR_text);
        ImageView logo = (ImageView) v.findViewById(R.id.ACTION_BAR_logo);
        txt.setText(text);
        //
        logo.setImageDrawable(image);
        actionBar.setCustomView(v);
        actionBar.setTitle("");
        return logo;
    }

    public static ImageView setCustomActionBar(Context ctx,
                                               ActionBar actionBar, String text, String subText, Drawable image) {
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater)
                ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.action_bar_logo, null);
        TextView txt = (TextView) v.findViewById(R.id.ACTION_BAR_text);
        TextView sub = (TextView) v.findViewById(R.id.ACTION_BAR_subText);
        ImageView logo = (ImageView) v.findViewById(R.id.ACTION_BAR_logo);
        txt.setText(text);
        sub.setText(subText);
        //
        logo.setImageDrawable(image);
        actionBar.setCustomView(v);
        actionBar.setTitle("");
        return logo;
    }

}
