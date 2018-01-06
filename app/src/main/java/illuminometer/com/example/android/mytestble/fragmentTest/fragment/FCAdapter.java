package illuminometer.com.example.android.mytestble.fragmentTest.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import illuminometer.com.example.android.mytestble.R;

/**
 * Created by android on 2017/11/20.
 */
public class FCAdapter extends RecyclerView.Adapter<FCAdapter.FCViewHolder> {
    public ArrayList<String> data;
    FCViewHolder holder;
    public FCAdapter(ArrayList<String> data){
        this.data=data;
    }

    @Override
    public FCViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fc_line,parent,false);
        holder=new FCViewHolder(view);
        holder.fcdata=(TextView) view.findViewById(R.id.fcdata);
        return holder;
    }


    @Override
    public void onBindViewHolder(FCViewHolder holder, int position) {
        holder.fcdata.setText(data.get(position));

    }



    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
    class FCViewHolder extends RecyclerView.ViewHolder{
        public TextView fcdata;
        public FCViewHolder(View itemView) {
            super(itemView);
        }
    }
}
