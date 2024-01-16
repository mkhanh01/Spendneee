package com.example.spendnee.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;

import com.example.spendnee.R;
import com.example.spendnee.databinding.ActivityUploadPictureBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UploadPictureActivity extends AppCompatActivity {

    private ActivityUploadPictureBinding binding;
    private Bitmap bitmap;
    private String encodeImg;

    private int MY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadPictureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        init();
    }

    private void init(){
//        binding.activityUploadPictureRlChoosePicture.setOnClickListener(v ->{
//            Dexter.withActivity(UploadPictureActivity.this)
//                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                    .withListener(new PermissionListener() {
//                        @Override
//                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                            Intent intent = new Intent(Intent.ACTION_PICK);
//                            intent.setType("image/*");
//                            startActivityForResult(Intent.createChooser(intent, "Select image"), MY_REQUEST_CODE);
//                        }
//
//                        @Override
//                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//                        }
//
//                        @Override
//                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                            permissionToken.continuePermissionRequest();
//                        }
//                    }).check();
//        });

        binding.activityUploadPictureBtnUpdate.setOnClickListener(v-> {

        });

        binding.activityUploadPictureTvBack.setOnClickListener(c ->{
            finish();
        });
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK && data!= null){
//            Uri filePath = data.getData();
//
//            try {
//                InputStream inputStream = getContentResolver().openInputStream(filePath);
//                bitmap = BitmapFactory.decodeStream(inputStream);
//                binding.activityUploadPictureImgChoose.setImageBitmap(bitmap);
//                imageStore(bitmap);
//
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void imageStore(Bitmap bitmap){
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] imageByte = stream.toByteArray();
//        encodeImg = android.util.Base64.encodeToString(imageByte, Base64.DEFAULT);
//    }
}