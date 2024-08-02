package com.example.imagecarousel;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_CAROUSEL = 0;
    private static final int TYPE_OTHER = 1;

    private Context context;
    private List<Object> items;

    public RecyclerViewAdapter(Context context, List<Object> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof List) {
            return TYPE_CAROUSEL;
        } else {
            return TYPE_OTHER;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_CAROUSEL) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_carousel, parent, false);
            return new CarouselViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_other, parent, false);
            return new OtherViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_CAROUSEL) {
            List<ImageModel> carouselItems = (List<ImageModel>) items.get(position);
            ((CarouselViewHolder) holder).bind(carouselItems);
        } else {
            // Handle binding for other view types
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CarouselViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerViewCarousel);
        }

        public void bind(List<ImageModel> carouselItems) {
            CarouselAdapter adapter = new CarouselAdapter(context, carouselItems, new ImpressionTracker() {
                private int lastPosition = RecyclerView.NO_POSITION;
                @Override
                public void onImpression(int position, boolean completelyVisibleItem) {
                    // Handle the impression count logic here
                    if (lastPosition == RecyclerView.NO_POSITION) {
                        Log.d("ImpressionTracker", "First Time Impression for a position : " + position);
                        lastPosition = position;
                    }
                    else if (!completelyVisibleItem && position == lastPosition - 1) {
                        // No Op
                    }
                    else if (position != lastPosition) {
                        Log.d("ImpressionTracker", "Impression for new position: " + position);
                        lastPosition = position;
                    }
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

            // Add LinearSnapHelper for snapping behavior
            LinearSnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);

            // Add the dot indicator decoration
            recyclerView.addItemDecoration(new DotIndicatorDecoration(context));
            // Add item decoration for spacing
            int space = 25; // Adjust this value to set the desired space between items
            recyclerView.addItemDecoration(new ImageSpacerItemDecoration(space));

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        handleImpressions();
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    handleImpressions();
                }

                private void handleImpressions() {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (layoutManager != null) {
                        int activePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                        if (activePosition == RecyclerView.NO_POSITION) {
                            activePosition = layoutManager.findFirstVisibleItemPosition();
                        }
                        adapter.getImpressionTracker().onImpression(activePosition, layoutManager.findFirstCompletelyVisibleItemPosition() != RecyclerView.NO_POSITION);
                    }
                }
            });

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    //recyclerView.invalidateItemDecorations();
                }
            });
        }
    }

    public class OtherViewHolder extends RecyclerView.ViewHolder {
        // Define other view holder components here

        public OtherViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize other view holder components here
        }
    }
}
