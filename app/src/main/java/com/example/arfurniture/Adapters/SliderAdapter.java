package com.example.arfurniture.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.arfurniture.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {
    String TAG="SliderAdapter";
    Context context;
    List<String> images;

    public SliderAdapter(List<String> images) {
        this.images = images;
        Log.d(TAG, "SliderAdapter: images= "+images);
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_layout, null);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: images link= "+images.get(position));
        Glide.with(context).asDrawable().load(images.get(position)).into(viewHolder.imageView);;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    public class SliderViewHolder extends SliderViewAdapter.ViewHolder{

        private ImageView imageView;

        public SliderViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slider_item_image);
        }
    }
}
