package com.example.laptop.sketchtaku;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import com.example.laptop.sketchtaku.Common.Common;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

public class ViewWallpaper extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton floatingActionButton;
    CoordinatorLayout rootlayout;
    ImageView imageView;
    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try {
                wallpaperManager.setBitmap(bitmap);
                Snackbar.make(rootlayout, "Wallpaper was set",Snackbar.LENGTH_SHORT).show();

            }catch (IOException e){
                e.printStackTrace();

            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wallpaper);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() !=null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Initialize items
        rootlayout = (CoordinatorLayout)findViewById(R.id.rootlayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBAr);
        collapsingToolbarLayout.setTitle(Common.CATEGORY_SELECTED);
        imageView = (ImageView)findViewById(R.id.WAllThumb);

        Picasso.with(this)
                .load(Common.select_background.getUrl())
                .into(imageView);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.downlaodwall);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Picasso.with(getBaseContext())
                        .load(Common.select_background.getUrl())
                        .into(target);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
