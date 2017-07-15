package sunstar.com.colorsearchviewdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import sunstar.com.colorsearchview.ColorSearchView;

public class MainActivity extends AppCompatActivity {
    ColorSearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        searchView = (ColorSearchView) findViewById(R.id.search_view);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.search();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchView.searchComplete();
                    }
                },10000);
            }
        });
    }
}
