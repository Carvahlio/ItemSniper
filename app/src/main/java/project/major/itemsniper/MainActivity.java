package project.major.itemsniper;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import project.major.itemsniper.Reusables.ScaleDrawable;


public class MainActivity extends AppCompatActivity {

    private ListView navigation_drawer;
    private NavigationView navigationView;
    private ViewPager pager;
    private CustomPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragmentList;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private EditText Search;



    @Override
    public void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);

        Toast.makeText(getApplicationContext(),"Fragment attached " + fragment.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeDrawer();
        inititializeToolbarAndTabs();
        initializePager();
    }


    private void inititializeToolbarAndTabs(){
        toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        this.setSupportActionBar(toolbar);


        final EditText t = (EditText)findViewById(R.id.toolbar_search);


        Drawable d = new ScaleDrawable(R.drawable.snipe,getApplicationContext()).scale(105, 80);
        t.setCompoundDrawablesWithIntrinsicBounds(d,null,null,null);

        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        tabLayout = (TabLayout)findViewById(R.id.main_tablayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(
                R.drawable.itemswhitenocircle
        ));
        tabLayout.addTab(tabLayout.newTab().setIcon(
                R.drawable.storeswhitenocircle
        ));


        this.Search = (EditText)findViewById(R.id.toolbar_search);
        this.Search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.i("SEARCH:", "Search Clicked");
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.i("SEARCH:", "Attempting search......");
                    Intent i = new Intent(getApplicationContext(),MapActivity.class);
                    i.putExtra("query",Search.getText().toString());
                    startActivity(i);
                    return true;
                }
                return false;
            }
        });

    }



    private void initializePager(){

        //The fragments for the pager
        fragmentList = new ArrayList<>();
        fragmentList.add(new ItemsPageFragment());
        fragmentList.add(new StoresPagesFragment());

        //The adapter
        pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(),fragmentList);

        //The pager it self
        pager = (ViewPager)findViewById(R.id.main_pager);
        pager.setAdapter(pagerAdapter);
    }

    //THIS IS WHERE THE NAVIGATION DRAWER IS SETUP AND POPULATED WITH ITEMS
    private void initializeDrawer(){
        //navigationView = (NavigationView)findViewById(R.id.naviagtion_view);
        navigation_drawer = (ListView)findViewById(R.id.navigation_drawer);
        ArrayList<DrawerListItem> items = new ArrayList<>();
        items.add(new DrawerListItem("Subscriptions", R.drawable.subscriptions));
        items.add(new DrawerListItem("Settings", R.drawable.settings));

        DrawerBuilderInterface listItemBuilder = new DrawerBuilderInterface() {
            @Override
            public void modifyView(View v) {

            }
        };
        DrawerListItemBuilder builder = new DrawerListItemBuilder(getApplicationContext())
                .add(R.layout.drawer_header, new DrawerBuilderInterface() {
                    @Override
                    public void modifyView(View v) {
                        //Just the header do nothing
                    }
                })
                .add(R.layout.drawer_list_item, new DrawerBuilderInterface() {
                    @Override
                    public void modifyView(View v) {
                        //Subscriptions
                        TextView title = (TextView)v.findViewById(R.id.drawer_list_item_title);
                        ImageView icon = (ImageView)v.findViewById(R.id.drawer_list_item_icon);

                        title.setText("Subscriptions");
                        icon.setImageResource(R.drawable.subscriptions);
                    }
                })
                .add(R.layout.drawer_list_item, new DrawerBuilderInterface() {
                    @Override
                    public void modifyView(View v) {
                        //Settings
                        TextView title = (TextView)v.findViewById(R.id.drawer_list_item_title);
                        ImageView icon = (ImageView)v.findViewById(R.id.drawer_list_item_icon);

                        title.setText("Settings");
                        icon.setImageResource(R.drawable.settings);
                    }
                });
        navigation_drawer.setAdapter(new DrawerAdapter(builder, getApplicationContext()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return  true;
    }


}
