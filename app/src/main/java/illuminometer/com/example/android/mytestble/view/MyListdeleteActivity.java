package illuminometer.com.example.android.mytestble.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import illuminometer.com.example.android.mytestble.R;
import illuminometer.com.example.android.mytestble.view.QQListView.DelButtonClickListener;

public class MyListdeleteActivity extends AppCompatActivity {
    private QQListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_listdelete);

        illuminometer.com.example.android.mytestble.view.TitanicTextView tv = (illuminometer.com.example.android.mytestble.view.TitanicTextView) findViewById(R.id.my_text_view);

        // set fancy typeface

        tv.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));

        // start animation
        new Titanic().start(tv);

        mListView = (QQListView) findViewById(R.id.deletelistview);
        // 不要直接Arrays.asList
        mDatas = new ArrayList<String>(Arrays.asList("HelloWorld", "Welcome", "Java", "Android", "Servlet", "Struts",
                "Hibernate", "Spring", "HTML5", "Javascript", "Lucene"));

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        Log.e("TAG", "onCreate: 88888"+mAdapter );
        Log.e("TAG", "onCreate: 88888"+mListView );
        mListView.setAdapter(mAdapter);
        mListView.setDelButtonClickListener(new DelButtonClickListener()
        {
            @Override
            public void clickHappend(final int position)
            {
                Toast.makeText(MyListdeleteActivity.this, position + " : " + mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                mAdapter.remove(mAdapter.getItem(position));
                mAdapter.notifyDataSetChanged();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(MyListdeleteActivity.this, position + " : " + mAdapter.getItem(position),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
