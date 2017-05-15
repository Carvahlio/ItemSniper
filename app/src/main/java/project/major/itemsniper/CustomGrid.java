package project.major.itemsniper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by carva on 15/5/2017.
 */

public class CustomGrid extends BaseAdapter implements View.OnClickListener{

    private Context mContext;
    private Button buttonSelect, buttonUpload;
    private ImageView imageView;
    private EditText editText;

    private static final int STORAGE_PERMISSION_CODE = 2342;
    private static final int PICK_IMAGE_REQUEST = 22;

    private Uri filePath;
    private Bitmap bitmap;

    public CustomGrid(Context c){
        mContext = c;
    }
    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);
            Button textView = (Button) grid.findViewById(R.id.selectBtn);
            EditText nameTxt = (EditText) grid.findViewById(R.id.item_upload_name);
            EditText priceTxt = (EditText) grid.findViewById(R.id.item_upload_price);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);


        } else {
            grid = (View) convertView;
        }

        return grid;
    }



    @Override
    public void onClick(View v) {

    }
}
