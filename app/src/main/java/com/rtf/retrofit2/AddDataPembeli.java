package com.rtf.retrofit2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.io.File;
import com.rtf.retrofit2.Model.ResultPembeli;
import com.rtf.retrofit2.Rest.ApiClient;
import com.rtf.retrofit2.Rest.ApiInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddDataPembeli extends AppCompatActivity {
    Context mContext;
    ImageView mImageView;
    Button btAddPhotoId, btAddBack, btAddData;
    EditText edtAddIdPembeli, edtAddNamaPembeli, edtAddAlamatPembeli;
    EditText edtAddTelpnPembeli;
    TextView tvAddMessage;
    String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data_pembeli);
        mContext = getApplicationContext();
        mImageView = (ImageView) findViewById(R.id.imgAddPhotoId);
        btAddPhotoId = (Button) findViewById(R.id.btAddPhotoId);
        edtAddIdPembeli = (EditText) findViewById(R.id.edtAddIdPembeli);
        edtAddNamaPembeli = (EditText) findViewById(R.id.edtAddNamaPembeli);
        edtAddAlamatPembeli = (EditText) findViewById(R.id.edtAddAlamatPembeli);
        edtAddTelpnPembeli = (EditText) findViewById(R.id.edtAddTelpnPembeli);
        btAddData = (Button) findViewById(R.id.btAddData);
        btAddBack = (Button) findViewById(R.id.btAddBack);
        tvAddMessage = (TextView) findViewById(R.id.tvAddMessage);
        btAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);
                MultipartBody.Part body = null;
                if (!imagePath.isEmpty()) {
                    File file = new File(imagePath);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    body = MultipartBody.Part.createFormData("photo_id", file.getName(), requestFile);
                }
                RequestBody reqIdPembeli = MultipartBody.create(MediaType.parse("multipart/form-data"), (edtAddIdPembeli.getText().toString().isEmpty() == true) ? "" : edtAddIdPembeli.getText().toString());
                RequestBody reqNama = MultipartBody.create(MediaType.parse("multipart/form-data"), (edtAddNamaPembeli.getText().toString().isEmpty()) ? "" : edtAddNamaPembeli.getText().toString());
                RequestBody reqAlamat = MultipartBody.create(MediaType.parse("multipart/form-data"), (edtAddNamaPembeli.getText().toString().isEmpty()) ? "" : edtAddAlamatPembeli.getText().toString());
                RequestBody reqTelpn = MultipartBody.create(MediaType.parse("multipart/form-data"), (edtAddNamaPembeli.getText().toString().isEmpty()) ? "" : edtAddTelpnPembeli.getText().toString());
                RequestBody reqAction = MultipartBody.create(MediaType.parse("multipart/form-data"), "post");

                Call<ResultPembeli> mPembeliCall = mApiInterface.postPembeli(body, reqIdPembeli, reqNama, reqAlamat, reqTelpn, reqAction);

                mPembeliCall.enqueue(new Callback<ResultPembeli>() {
                    @Override
                    public void onResponse(Call<ResultPembeli> call, Response<ResultPembeli> response) {
                        if (response.body().getStatus().equals("failed")) {
                            tvAddMessage.setText("Retrofit Update \n Status = " + response.body().getStatus() + "\n" + " Message = " + response.body().getMessage() + "\n");
                        } else {
                            String detail = "\n" + "id_pembeli = " + response.body().getResult().get(0).getIdPembeli() + "\n" + "agama = " + response.body().getResult().get(0).getNama() + "\n" + "alamat = " + response.body().getResult().get(0).getAlamat() + "\n" + "telpn = " + response.body().getResult().get(0).getTelpn() + "\n" + "photo_id = " + response.body().getResult().get(0).getPhotoId() + "\n";
                            tvAddMessage.setText("Retrofit Update \n Status = " + response.body().getStatus() + "\n" + "Message = " + response.body().getMessage() + detail);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultPembeli> call, Throwable t) {
                        tvAddMessage.setText("Retrofit Update \n Status = " + t.getMessage());
                    }
                });
            }
        });

        btAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddDataPembeli.class);
                startActivity(intent);
            }
        });

        btAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GetPembeliActivity.class);
                startActivity(intent);
            }
        });

        btAddPhotoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);
                Intent intentChoose = Intent.createChooser(galleryIntent, "Pilih Gambar Untuk Di upload");
                startActivityForResult(intentChoose, 10);
            }
        });
    }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == 10) {
                if (data == null) {
                    Toast.makeText(mContext, "Gambar Gagal Di load", Toast.LENGTH_LONG).show();
                    return;
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imagePath = cursor.getString(columnIndex);
                    Glide.with(mContext).load(new File(imagePath)).into(mImageView);
                    cursor.close();
                } else {
                    Toast.makeText(mContext, "Gambar Gagal Di load", Toast.LENGTH_LONG).show();
                }
            }
        }
    }