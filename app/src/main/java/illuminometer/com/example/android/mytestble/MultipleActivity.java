package illuminometer.com.example.android.mytestble;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import illuminometer.com.example.android.mytestble.adapter.MyListViewAdapter;

public class MultipleActivity extends AppCompatActivity implements MyListViewAdapter.OnShowItemClickListener {
    private ListView listView;
    private List<ItemBean> dataList,selectedList;
    private MyListViewAdapter myAdapter;
    private RelativeLayout rootView;
    private LinearLayout menuView;
    private LinearLayout openView;
    private static boolean isShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple);
        isShow=false;
        Button delbtn=(Button) findViewById(R.id.del);
        rootView=(RelativeLayout) findViewById(R.id.rootView);
        menuView=(LinearLayout) findViewById(R.id.button_group);
        listView=(ListView) findViewById(R.id.listView1);
        dataList=new ArrayList<ItemBean>();
        selectedList=new ArrayList<ItemBean>();
        for(int i=0;i<100;i++)
        {
            ItemBean item=new ItemBean();
            item.setId(i);
            item.setImgRes(R.drawable.buttonstyle);
            item.setTitle("第"+item.getId()+"个");
            item.setTeacher("杨老师");
            item.setTime("34");
            item.setPeopleNum(i+1*100);
            item.setChecked(false);
            item.setShow(isShow);
            dataList.add(item);
        }
        myAdapter=new MyListViewAdapter(dataList, this);
        myAdapter.setOnShowItemClickListener(this);
        listView.setAdapter(myAdapter);

        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 自动生成的方法存根
                showMenu();
                isShow=true;
                selectedList.clear();
                for(ItemBean item:dataList)
                {
                    item.setShow(isShow);
                }
                myAdapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 自动生成的方法存根
                if (isShow) {
                    ItemBean item = dataList.get(position);
                    boolean isChecked = item.isChecked();
                    if (isChecked) {
                        item.setChecked(false);
                    } else {
                        item.setChecked(true);
                    }
                    myAdapter.notifyDataSetChanged();
                    Log.d("select",selectedList.size()+"");
                }
            }
        });

    }
    //显示选择删除等的菜单
    private void showMenu()
    {
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        openView=(LinearLayout) inflater.inflate(R.layout.delmenu_layou, null);
        rootView.removeView(menuView);
        rootView.addView(openView,lp);
        final Button sBtn=(Button) openView.findViewById(R.id.selectAll);
        Button dBtn=(Button) openView.findViewById(R.id.del_button);
        Button cBtn= (Button) openView.findViewById(R.id.cancel_button);
        sBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 自动生成的方法存根
                if ("全选".equals(sBtn.getText().toString())) {
                    for (ItemBean bean : dataList) {
                        if (!bean.isChecked()) {
                            bean.setChecked(true);
                            selectedList.add(bean);
//                            if (!selectedList.contains(bean)) {
//                                selectedList.add(bean);
//                            }
                        }
                    }
                    myAdapter.notifyDataSetChanged();
                    sBtn.setText("反选");
                } else if ("反选".equals(sBtn.getText().toString())) {
                    for (ItemBean bean : dataList) {
                        bean.setChecked(false);
                        selectedList.clear();
//                        if (!selectedList.contains(bean)) {
//                            selectedList.remove(bean);
//                        }
                    }
                    myAdapter.notifyDataSetChanged();
                    sBtn.setText("全选");
                }
            }
        });

        dBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO 自动生成的方法存根
                if (selectedList!=null && selectedList.size()>0) {
                    dataList.removeAll(selectedList);
                    myAdapter.notifyDataSetChanged();
                    selectedList.clear();
                } else {
                    Toast.makeText(MultipleActivity.this, "请选择条目", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO 自动生成的方法存根
                if (isShow) {
                    selectedList.clear();
                    for (ItemBean bean : dataList) {
                        bean.setChecked(false);
                        bean.setShow(false);
                    }
                    myAdapter.notifyDataSetChanged();
                    isShow = false;
                    restoreView();
                }
            }
        });

    }
    @Override
    public void onShowItemClick(ItemBean bean) {
        // TODO 自动生成的方法存根
        if (bean.isChecked() && !selectedList.contains(bean)) {
            selectedList.add(bean);
        } else if (!bean.isChecked() && selectedList.contains(bean)) {
            selectedList.remove(bean);
        }
    }

    private void restoreView()
    {
        rootView.addView(menuView);
        if(openView!=null)
        {
            rootView.removeView(openView);
            openView=null;
        }

    }
}
