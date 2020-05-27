package com.cookandroid.listmembers.subin;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import java.util.ArrayList;

public class CustomMultipleDotSpan implements LineBackgroundSpan {

    private final float radius;
    private ArrayList<String> color;
    private int intColor;

    public CustomMultipleDotSpan(ArrayList<String> color) {
        radius = 5f;
        this.color=color;
    }

    @Override
    public void drawBackground(
            Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            CharSequence charSequence,
            int start, int end, int lineNum
    ) {
        int total = color.size()> 5 ? 5 : color.size();
        int leftMost = (total - 1) * -10;


        for (int i = 0; i < total; i++) {
            intColor=Color.parseColor(color.get(i));
            int oldColor = paint.getColor();
            if (intColor!= 0) {
                paint.setColor(intColor);

            }
            canvas.drawCircle((left + right) / 2 - leftMost, bottom + radius, radius, paint);
            leftMost = leftMost + 20;
            paint.setColor(oldColor);

        }
    }


   /* private final float radius;
    private int[] color = new int[0];


    public CustomMultipleDotSpan() {
        this.radius = DEFAULT_RADIUS;
        this.color[0] = 0;
    }


    public CustomMultipleDotSpan(int color) {
        this.radius = DEFAULT_RADIUS;
        this.color[0] = 0;
    }


    public CustomMultipleDotSpan(float radius) {
        this.radius = radius;
        this.color[0] = 0;
    }


    public CustomMultipleDotSpan(float radius, int[] color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void drawBackground(
            Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            CharSequence charSequence,
            int start, int end, int lineNum
    ) {

        int total = color.length > 5 ? 5 : color.length;
        int leftMost = (total - 1) * -10;

        for (int i = 0; i < total; i++) {
            int oldColor = paint.getColor();
            if (color[i] != 0) {
                paint.setColor(color[i]);
            }
            canvas.drawCircle((left + right) / 2 - leftMost, bottom + radius, radius, paint);
            paint.setColor(oldColor);
            leftMost = leftMost + 20;
        }
    }*/
}