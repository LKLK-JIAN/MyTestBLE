package illuminometer.com.example.android.mytestble.fragmentTest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import illuminometer.com.example.android.mytestble.R;


public class Main2Activity extends AppCompatActivity {
    RecyclerView.LayoutManager  manager;
    MyAdapter adapter;
    RecyclerView  recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        manager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        adapter=new MyAdapter(getData());
        recyclerView=(RecyclerView) findViewById(R.id.cecycler_item);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        recyclerView.setLayoutManager(manager);
       // recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        //设置分割线
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator( new DefaultItemAnimator());

    }
    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = " item";
        for(int i = 1; i < 100; i++) {
            data.add( i+ temp);
        }
        return data;
    }

}
