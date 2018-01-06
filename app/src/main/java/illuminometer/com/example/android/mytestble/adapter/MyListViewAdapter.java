package illuminometer.com.example.android.mytestble.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import illuminometer.com.example.android.mytestble.ItemBean;
import illuminometer.com.example.android.mytestble.R;

/**
 * Created by android on 2017/12/6.
 */
public class MyListViewAdapter extends BaseAdapter
{
    private LayoutInflater inflater;
    private List<ItemBean> items;
    private ItemBean item;
    private OnShowItemClickListener onShowItemClickListener;

    public MyListViewAdapter(List<ItemBean> list,Context context)
    {
        items=list;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO 自动生成的方法存根
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO 自动生成的方法存根
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO 自动生成的方法存根
        ViewHolder holder;//使用ViewHolder，大神说能提升性能
        if(convertView==null)
        {
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.list_view, null);
            holder.img=(ImageView) convertView.findViewById(R.id.imageView1);
            holder.cb=(CheckBox) convertView.findViewById(R.id.checkBox1);
            holder.title=(TextView)convertView.findViewById(R.id.title);
            holder.teacher=(TextView) convertView.findViewById(R.id.teacher);
            holder.time=(TextView) convertView.findViewById(R.id.time);
            holder.poeple=(TextView)convertView.findViewById(R.id.peopleNum);
            convertView.setTag(holder);
        }else
        {
            holder=(ViewHolder) convertView.getTag();
        }
        item=items.get(position);
        if(item.isShow())
        {
            holder.cb.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.cb.setVisibility(View.GONE);
        }
        holder.img.setImageResource(R.drawable.ic_menu_camera);
        holder.title.setText(item.getTitle());
        holder.teacher.setText("主讲："+item.getTeacher());
        holder.time.setText("课时："+item.getTime()+"讲");
        holder.poeple.setText("学习人数："+item.getPeopleNum());

        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    item.setChecked(true);
                }
                else
                {
                    item.setChecked(false);
                }
                //回调方法，讲item加入已选择的
                onShowItemClickListener.onShowItemClick(item);
            }
        });
        //监听后设置选择状态
        holder.cb.setChecked(item.isChecked());
        return convertView;
    }

    public static class ViewHolder
    {
        ImageView img;
        CheckBox cb;
        TextView title,teacher,time,poeple;

    }

    public interface OnShowItemClickListener {
        public void onShowItemClick(ItemBean bean);
    }

    public void setOnShowItemClickListener(OnShowItemClickListener onShowItemClickListener) {
        this.onShowItemClickListener = onShowItemClickListener;
    }
}
