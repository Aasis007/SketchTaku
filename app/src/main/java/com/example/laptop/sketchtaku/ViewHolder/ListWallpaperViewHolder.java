package com.example.laptop.sketchtaku.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.laptop.sketchtaku.Interface.ItemClickListener;
import com.example.laptop.sketchtaku.Model.WallpaperItem;
import com.example.laptop.sketchtaku.R;

/**
 * Created by Laptop on 4/27/2018.
 */

public class ListWallpaperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

ItemClickListener itemClickListener;

    public ImageView Wallpaper;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ListWallpaperViewHolder(View itemView) {
        super(itemView);

        Wallpaper = (ImageView)itemView.findViewById(R.id.bkrImage);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition());

    }
}
