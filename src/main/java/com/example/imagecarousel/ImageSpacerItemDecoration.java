package com.example.imagecarousel;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageSpacerItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;

    public ImageSpacerItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        if (position != RecyclerView.NO_POSITION) {
            outRect.left = space / 2;
            outRect.right = space / 2;
            if (position == 0) {
                outRect.left = space;
            }
            if (position == state.getItemCount() - 1) {
                outRect.right = space;
            }
        }
    }
}
