package com.example.laptop.sketchtaku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.laptop.sketchtaku.Common.Common;
import com.example.laptop.sketchtaku.Model.CategoryItem;
import com.example.laptop.sketchtaku.Model.WallpaperItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Upload_Activity extends AppCompatActivity {

    ImageView imagepreview;
    Button browsebtn,uploadbtn;
    MaterialSpinner spinner;
    //data for spinner
    Map<String,String> spinnerData = new HashMap<>();

    private Uri filepath;
    String categoryIDSelect;

    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_);

        //Firebase Storage Initilization

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //View
        imagepreview = (ImageView)findViewById(R.id.image_preview);
        browsebtn = (Button)findViewById(R.id.selectbtn);
        uploadbtn = (Button)findViewById(R.id.uploadbtn);
        spinner = (MaterialSpinner) findViewById(R.id.spinner);
        //load spinner data
        loadcategoryspinner();

        //Buttonevents
        browsebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinner.getSelectedIndex() == 0)
                {
                    Toast.makeText(Upload_Activity.this, "Please Select a category", Toast.LENGTH_SHORT).show();
                }
                else
                upload();
            }
        });




    }

    private void upload() {

        if (filepath != null)
        {
            final ProgressDialog progressDialog =  new ProgressDialog(this);
            progressDialog.setMessage("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child(new StringBuilder("images/").append(UUID.randomUUID().toString())
                .toString());

            ref.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            saveUrlToCategory(categoryIDSelect,taskSnapshot.getDownloadUrl().toString());
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Upload_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0)*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Upload Complete..."+(int)progress+"%");
                        }
                    });
        }
    }

    private void saveUrlToCategory(String categoryIDSelect, String url) {
        FirebaseDatabase.getInstance().getReference(Common.STR_WALLPAPER)
                .push()//generate key
                .setValue(new WallpaperItem(url,categoryIDSelect))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Upload_Activity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Common.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                imagepreview.setImageBitmap(bitmap);
                uploadbtn.setEnabled(true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select your Sketch: "),Common.PICK_IMAGE_REQUEST);
    }

    private void loadcategoryspinner() {
        FirebaseDatabase.getInstance().getReference(Common.STR_CATEGORY_BACKGROUND)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postsnapshot : dataSnapshot.getChildren())
                        {
                            CategoryItem item = postsnapshot.getValue(CategoryItem.class);
                            String key = postsnapshot.getKey();
                            spinnerData.put(key,item.getName());

                            Object[] ValueArray = spinnerData.values().toArray();
                            List<Object> valueList = new ArrayList<>();
                            valueList.add(0,"Category");
                            valueList.addAll(Arrays.asList(ValueArray));
                            spinner.setItems(valueList);

                            spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                                    //we will get categoryID(key) wen user select category

                                    Object[] KeyArray = spinnerData.keySet().toArray();
                                    List<Object> keyList = new ArrayList<>();
                                    keyList.add(0,"Category_key");
                                    keyList.addAll(Arrays.asList(KeyArray));
                                    categoryIDSelect = keyList.get(position).toString();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
