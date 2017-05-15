package project.major.itemsniper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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

/**
 * Created by carva on 15/5/2017.
 */

public class ItemUploadActivity extends AppCompatActivity implements View.OnClickListener {

    GridView grid;
    private Button buttonSelect, buttonUpload;
    private ImageView imageView;
    private EditText editText;

    private static final int STORAGE_PERMISSION_CODE = 2342;
    private static final int PICK_IMAGE_REQUEST = 22;

    private Uri filePath;
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_upload_business);

        CustomGrid adapter = new CustomGrid(ItemUploadActivity.this);
        grid = (GridView) findViewById(R.id.grid);
            grid.setAdapter(adapter);
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(ItemUploadActivity.this, "You Clicked at" + String.valueOf(position),Toast.LENGTH_SHORT).show();
                }
            });
    }



    @Override
    public void onClick(View v) {
        Toast.makeText(ItemUploadActivity.this, "You dat" ,Toast.LENGTH_SHORT).show();
    }
}
