package com.example.imagecarousel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagecarousel.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        ViewPager viewPager = findViewById(R.id.viewPager);
//        WormDotsIndicator dotsIndicator = findViewById(R.id.dotsIndicator);
//
//        List<ImageModel> imageModelList = new ArrayList<>();
//        // Fetch image details from API and populate the list
//        // For demo purposes, let's add some sample data
//        imageModelList.add(new ImageModel("https://picsum.photos/id/12/600", ""));
//        imageModelList.add(new ImageModel("https://picsum.photos/id/16/600", ""));
//        imageModelList.add(new ImageModel("https://picsum.photos/id/18/600", ""));
//
//        ImagePagerAdapter adapter = new ImagePagerAdapter(this, imageModelList);
//        viewPager.setAdapter(adapter);
//        dotsIndicator.setViewPager(viewPager);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Object> items = new ArrayList<>();

        // Add carousel items
        List<ImageModel> carouselItems = new ArrayList<>();
        carouselItems.add(new ImageModel("https://picsum.photos/id/12/600", ""));
        carouselItems.add(new ImageModel("https://picsum.photos/id/16/600", ""));
        carouselItems.add(new ImageModel("https://picsum.photos/id/18/600", ""));
        items.add(carouselItems);

        // Add other items
        // items.add(new OtherModel(...));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}