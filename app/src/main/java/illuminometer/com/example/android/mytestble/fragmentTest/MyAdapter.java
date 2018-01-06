package illuminometer.com.example.android.mytestble.fragmentTest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import illuminometer.com.example.android.mytestble.R;

/**
 * Created by android on 2017/11/17.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.HolderRecycler> {
    public ArrayList<String> data;
    public HolderRecycler  view;
    public MyAdapter(ArrayList<String> data){
        this.data=data;
    }


    @Override
    public HolderRecycler onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        view=new HolderRecycler(v);
        view.tv=(TextView) v.findViewById(R.id.tv);
        return view ;
    }

    @Override
    public void onBindViewHolder(HolderRecycler holder, int position) {
        holder.tv.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class  HolderRecycler extends RecyclerView.ViewHolder{
          public TextView tv;
        public HolderRecycler(View itemView) {
            super(itemView);

        }
    }
}

