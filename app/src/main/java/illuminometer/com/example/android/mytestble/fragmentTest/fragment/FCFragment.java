package illuminometer.com.example.android.mytestble.fragmentTest.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import illuminometer.com.example.android.mytestble.R;
import illuminometer.com.example.android.mytestble.fragmentTest.DividerItemDecoration;

/**
 * Created by android on 2017/11/15.
 */
public class FCFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View view;
    public FCAdapter adapter;
    RecyclerView fc_recyclerView;
    LinearLayoutManager  manager;
    SwipeRefreshLayout sreLayout;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_fc,container,false);
        fc_recyclerView=(RecyclerView) view.findViewById(R.id.fc_recyclerView);
        sreLayout=(SwipeRefreshLayout) view.findViewById(R.id.sreLayout);
        sreLayout.setOnRefreshListener(this);
        adapter=new FCAdapter(getData());
        manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        fc_recyclerView.setLayoutManager(manager);
        fc_recyclerView.setAdapter(adapter);
        fc_recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),LinearLayoutManager.HORIZONTAL));
        return view;
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = " item";
        for(int i = 1; i < 100; i++) {
            data.add( i+ temp);
        }
        return data;
    }

    @Override
    public void onRefresh() {
      sreLayout.setRefreshing(false);
    }


}
