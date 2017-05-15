package project.major.itemsniper;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

/**
 * Created by carva on 6/5/2017.
 */

public class CustomClusterRenderer extends DefaultClusterRenderer<MyItem>{
    private final Context mContext;
    private final IconGenerator clusterIcon;

    public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
        mContext = context;
        clusterIcon = new IconGenerator(mContext.getApplicationContext());
    }

    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions){

        //Sets icon for markers. Will to be updated.
        final BitmapDescriptor marker= (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

        //Assign item attributed to markers.
        markerOptions.icon(marker);
        markerOptions.title(item.getVendor());
        markerOptions.snippet(item.getAvailable());
        super.onBeforeClusterItemRendered(item,markerOptions);
    }

    protected void onBeforeClusterRendered(Cluster<MyItem>cluster,MarkerOptions markerOptions){
        clusterIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle));
        clusterIcon.setTextAppearance(R.style.AppTheme_WhiteTextAppearance);

        //Cluster icon rendering
        final Bitmap icon = clusterIcon.makeIcon(String.valueOf(cluster.getSize()));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    protected void onClusterItemRendered(MyItem clusterItem, Marker marker){
      super.onClusterItemRendered(clusterItem,marker);
  }
}
