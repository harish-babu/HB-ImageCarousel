package com.example.imagecarousel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

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
            CarouselAdapter adapter = new CarouselAdapter(context, carouselItems);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new DotIndicatorDecoration(context));
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
