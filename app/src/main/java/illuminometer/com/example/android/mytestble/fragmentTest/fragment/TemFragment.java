package illuminometer.com.example.android.mytestble.fragmentTest.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import illuminometer.com.example.android.mytestble.R;

/**
 * Created by android on 2017/11/15.
 */
public class TemFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_tem,container,false);
        return view;
    }
}
