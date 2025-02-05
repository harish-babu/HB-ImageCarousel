package com.example.imagecarousel;


import android.content.Context;
import android.util.AttributeSet;

public class AspectRatioImageView extends androidx.appcompat.widget.AppCompatImageView {

    public AspectRatioImageView(Context context) {
        super(context);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getDrawable() != null) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) ((float) width * (float) getDrawable().getIntrinsicHeight() / (float) getDrawable().getIntrinsicWidth());
            setMeasuredDimension(width, height);
        }
    }
}

