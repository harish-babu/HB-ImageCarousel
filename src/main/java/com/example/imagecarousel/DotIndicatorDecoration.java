package com.example.imagecarousel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DotIndicatorDecoration extends RecyclerView.ItemDecoration {
    private int colorActive;
    private int colorInactive;
    private int dotRadius;
    private int dotMargin;
    private Paint paintActive;
    private Paint paintInactive;
    private Paint paintBackground;
    private int backgroundColor;

    public DotIndicatorDecoration(Context context) {
        colorActive = context.getResources().getColor(android.R.color.holo_blue_dark);
        colorInactive = context.getResources().getColor(android.R.color.darker_gray);
        backgroundColor = Color.parseColor("#4C000000"); // Black with 70% transparency
        dotRadius = 10;
        dotMargin = 10;
        paintActive = new Paint();
        paintActive.setColor(colorActive);
        paintInactive = new Paint();
        paintInactive.setColor(colorInactive);
        paintBackground = new Paint();
        paintBackground.setColor(backgroundColor);
        //paintBackground.setAlpha((int) (0.7 * 255)); // 70% transparency
        paintBackground.setAntiAlias(true);
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);

        if (parent.getAdapter() == null) {
            return;
        }

        int itemCount = parent.getAdapter().getItemCount();
        int totalWidth = (2 * dotRadius * itemCount) + (dotMargin * (itemCount - 1));
        int startX = (parent.getWidth() - totalWidth) / 2;
        int y = parent.getHeight() - 30;


        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int activePosition = layoutManager.findFirstCompletelyVisibleItemPosition();

        int indicatorPosY = parent.getHeight() - (dotRadius * 2 + 2 * dotMargin);

        // Draw the background behind all the dots
        int backgroundWidth = totalWidth + dotRadius * 2;
        int backgroundHeight = dotRadius * 2 + dotMargin * 2;
        int backgroundStartX = startX - ( dotRadius + dotMargin);
        int backgroundStartY = indicatorPosY - dotRadius;
        canvas.drawRoundRect(
                backgroundStartX,
                backgroundStartY,
                backgroundStartX + backgroundWidth,
                backgroundStartY + backgroundHeight,
                16, 16,
                paintBackground
        );


        if (activePosition == RecyclerView.NO_POSITION) {
            activePosition = layoutManager.findFirstVisibleItemPosition();
        }

        for (int i = 0; i < itemCount; i++) {
            int x = startX + (i * (2 * dotRadius + dotMargin));
            if (i == activePosition) {
                canvas.drawCircle(x, y, dotRadius, paintActive);
            } else {
                canvas.drawCircle(x, y, dotRadius, paintInactive);
            }
        }
    }
}
