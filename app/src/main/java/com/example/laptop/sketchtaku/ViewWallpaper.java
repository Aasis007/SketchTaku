package com.example.laptop.sketchtaku;

import android.*;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.laptop.sketchtaku.Common.Common;

import com.example.laptop.sketchtaku.Helper.Saveimagehelper;
import com.example.laptop.sketchtaku.Model.WallpaperItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.Observable;
import java.util.UUID;

import dmax.dialog.SpotsDialog;


public class ViewWallpaper extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton floatingActionButton, fabdownlaod;
    CoordinatorLayout rootlayout;
    ImageView imageView;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Common.PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SpotsDialog dialog = new SpotsDialog(ViewWallpaper.this);
                    dialog.show();
                    dialog.setMessage("please wait...");

                    String filename = UUID.randomUUID().toString() + ".png";

                    Picasso.with(getBaseContext())
                            .load(Common.select_background.getUrl())
                            .into(new Saveimagehelper(getBaseContext(),
                                    dialog,
                                    getApplicationContext().getContentResolver(),
                                    filename,
                                    "SketchTaku"));
                } else {
                    Toast.makeText(this, "Please grant permimssion for external storage", Toast.LENGTH_SHORT).show();
                }
                break;
            }

        }

    }


    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try {
                wallpaperManager.setBitmap(bitmap);
                Snackbar.make(rootlayout, "Wallpaper was set", Snackbar.LENGTH_SHORT).show();

            } catch (IOException e) {
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


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Initialize items
        rootlayout = (CoordinatorLayout) findViewById(R.id.rootlayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBAr);
        collapsingToolbarLayout.setTitle(Common.CATEGORY_SELECTED);
        imageView = (ImageView) findViewById(R.id.WAllThumb);

        Picasso.with(this)
                .load(Common.select_background.getUrl())
                .into(imageView);


        floatingActionButton = (FloatingActionButton) findViewById(R.id.downlaodwall);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Picasso.with(getBaseContext())
                        .load(Common.select_background.getUrl())
                        .into(target);

            }
        });


        fabdownlaod = (FloatingActionButton) findViewById(R.id.downlaodwallpaper);
        fabdownlaod.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                //Request storage permission
                if (ActivityCompat.checkSelfPermission(ViewWallpaper.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, Common.PERMISSION_REQUEST_CODE);
                } else {

                    AlertDialog dialog = new SpotsDialog(ViewWallpaper.this);
                    dialog.show();
                    dialog.setMessage("Please Wait...");

                    String filename = UUID.randomUUID().toString() + ".png";
                    Picasso.with(getBaseContext())
                            .load(Common.select_background.getUrl())
                            .into(new Saveimagehelper(getBaseContext(),
                                    dialog,
                                    getApplicationContext().getContentResolver(),
                                    filename,
                                    "SketchTaku"));

                }

            }
        });

    }
}


