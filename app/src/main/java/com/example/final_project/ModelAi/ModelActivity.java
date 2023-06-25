package com.example.final_project.ModelAi;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.final_project.R;
import com.example.final_project.databinding.ActivityModelBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModelActivity extends AppCompatActivity {

    ActivityModelBinding binding;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getPremission();
        clickListeners();
    }

    private void clickListeners() {
        binding.selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 10);
                } else {
                    ActivityCompat.requestPermissions(ModelActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                binding.result.setVisibility(View.INVISIBLE);
                binding.result1.setVisibility(View.INVISIBLE);
                binding.resultPercentageM.setVisibility(View.INVISIBLE);
                binding.result1PercentageM.setVisibility(View.INVISIBLE);
                binding.result1Percentage1M.setVisibility(View.INVISIBLE);
                binding.resultNon.setVisibility(View.INVISIBLE);
                binding.result1Non.setVisibility(View.INVISIBLE);
                binding.resultPercentage.setVisibility(View.INVISIBLE);
                binding.result1Percentage.setVisibility(View.INVISIBLE);
                binding.result1Percentage1.setVisibility(View.INVISIBLE);
            }
        });

        binding.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 12);
                binding.result.setVisibility(View.INVISIBLE);
                binding.result1.setVisibility(View.INVISIBLE);
                binding.resultPercentageM.setVisibility(View.INVISIBLE);
                binding.result1PercentageM.setVisibility(View.INVISIBLE);
                binding.result1Percentage1M.setVisibility(View.INVISIBLE);
                binding.resultNon.setVisibility(View.INVISIBLE);
                binding.result1Non.setVisibility(View.INVISIBLE);
                binding.resultPercentage.setVisibility(View.INVISIBLE);
                binding.result1Percentage.setVisibility(View.INVISIBLE);
                binding.result1Percentage1.setVisibility(View.INVISIBLE);
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImage();
            }
        });
    }

    void getPremission(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(ModelActivity.this, new String[]{Manifest.permission.CAMERA},11);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==11){
            if(grantResults.length>0){
                if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    this.getPremission();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            Context context = ModelActivity.this;
            path = RealPathUtil.getRealPath(context, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            binding.imageView.setImageBitmap(bitmap);
        }
        else if(requestCode==12){
            Bitmap bitmap1 = (Bitmap) data.getExtras().get("data");
            Uri tempUri = getImageUri(this,bitmap1);
            Context context = ModelActivity.this;
            path = RealPathUtil.getRealPath(context, tempUri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            binding.imageView.setImageBitmap(bitmap);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path =
                MediaStore.Images.Media.insertImage(inContext.getContentResolver(),
                        inImage,
                        "Title", null);
        return Uri.parse(path);
    }

    public void  addImage(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.51.200:8000")
                .addConverterFactory(GsonConverterFactory.create()).build();

        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/from-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("img", file.getName(), requestFile);

        ApiService apiService = retrofit.create(ApiService.class);
        Call<img> call = apiService.img(body);
        call.enqueue(new Callback<img>() {
            @Override
            public void onResponse(Call<img> call, Response<img> response) {
                if (response.isSuccessful()){
                    String result = response.body().getPredictedclass().toString();
                    String resultP = response.body().getHighestprobability().toString();
                    if(result.equals("Malignant melanoma")){
                        binding.result.setVisibility(View.VISIBLE);
                        binding.result1.setVisibility(View.VISIBLE);
                        binding.resultPercentageM.setVisibility(View.VISIBLE);
                        binding.result1PercentageM.setVisibility(View.VISIBLE);
                        binding.result1Percentage1M.setVisibility(View.VISIBLE);
                        binding.result1.setText(result);
                        binding.result1PercentageM.setText(resultP);
                    }else {
                        binding.result.setVisibility(View.VISIBLE);
                        binding.result1.setVisibility(View.VISIBLE);
                        binding.resultNon.setVisibility(View.VISIBLE);
                        binding.result1Non.setVisibility(View.VISIBLE);
                        binding.resultPercentage.setVisibility(View.VISIBLE);
                        binding.result1Percentage.setVisibility(View.VISIBLE);
                        binding.result1Percentage1.setVisibility(View.VISIBLE);
                        binding.result1.setText("Non Melanoma");
                        binding.result1Non.setText(result);
                        binding.result1Percentage.setText(resultP);
                    }
                    Log.d(TAG, "onResponse: done");
                }
            }

            @Override
            public void onFailure(Call<img> call, Throwable t) {
                Log.d(TAG, "onFailure: error");
            }
        });
    }
}