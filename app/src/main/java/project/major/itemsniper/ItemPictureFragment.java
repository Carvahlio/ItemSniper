package project.major.itemsniper;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Kurt on 5/10/2017.
 */
public class ItemPictureFragment extends Fragment {

    Bitmap picture;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.picture_layout,container,false);
        ImageView pic =(ImageView) v.findViewById(R.id.picture);
        pic.setImageBitmap(this.picture);

        return v;
    }


    public static ItemPictureFragment creatInstance(Bitmap b){

        ItemPictureFragment x = new ItemPictureFragment();
        x.setPicture(b);

        return x;
    }

    public void setPicture(Bitmap picture){
        this.picture = picture;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }
}
