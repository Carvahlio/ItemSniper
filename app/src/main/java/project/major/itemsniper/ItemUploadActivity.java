package project.major.itemsniper;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by carva on 15/5/2017.
 */

public class ItemUploadActivity extends AppCompatActivity implements View.OnClickListener {

    GridView grid;
    private Button buttonSelect, buttonUpload;
    private ImageView imageView;
    private EditText editText, editText2;


    private static final int STORAGE_PERMISSION_CODE = 2342;
    private static final int PICK_IMAGE_REQUEST = 22;

    private static final String UPLOAD_URL = "http://www.topnhotch.com/itemsniper/uploadVendor.php";

    private Uri filePath;
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_upload_business);

        requestStoragePermission();
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        CustomGrid adapter = new CustomGrid(ItemUploadActivity.this);
        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void requestStoragePermission(){
        if(ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
            return;
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission granted", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Permission not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null){
            filePath = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private String getPath(Uri uri){
        Cursor cursor =  getContentResolver().query(uri,null,null,null,null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);

        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ",new String[]{document_id},null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;

    }

    private void uploadImage(){
        String name = editText.getText().toString().trim();
        String path = getPath(filePath);

        try{
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(this,uploadId, UPLOAD_URL)
                    .addFileToUpload(path,"image")
                    .addParameter("name",name)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload();
        }catch (Exception e){

        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(ItemUploadActivity.this, "You dat" ,Toast.LENGTH_SHORT).show();
        if(v == buttonUpload){
            uploadImage();
        }
    }
}
