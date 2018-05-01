package com.example.laptop.sketchtaku.Helper;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;

import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

import dmax.dialog.SpotsDialog;

/**
 * Created by Laptop on 5/1/2018.
 */

public class Saveimagehelper implements Target {
    private Context context;
    private WeakReference<android.app.AlertDialog> alertDialogWeakReference;
    private WeakReference<ContentResolver> contentResolverWeakReference;
    private String name,desc;


    public Saveimagehelper(Context context, android.app.AlertDialog alertDialog, ContentResolver contentResolver, String name, String desc) {
        this.context = context;
        this.alertDialogWeakReference = new WeakReference<AlertDialog>(alertDialog);
        this.contentResolverWeakReference = new WeakReference<ContentResolver>(contentResolver);
        this.name = name;
        this.desc = desc;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

        ContentResolver r = contentResolverWeakReference.get();
        android.app.AlertDialog alertDialog = alertDialogWeakReference.get();

        if (r != null)
        {
            MediaStore.Images.Media.insertImage(r,bitmap,name,desc);
            alertDialog.dismiss();
            Toast.makeText(context, "Downloaded Succesfully", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
