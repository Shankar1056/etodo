package apextechies.etodo.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import apextechies.etodo.R;


public class DotsAdapter extends RecyclerView.Adapter<DotsAdapter.ViewHolder> {

    int selected=0;
    int count;
    int previous=0;

    public DotsAdapter(int count) {
       this.count=count;

    }

    public void setSelected(int selected) {
        this.previous=this.selected;
        this.selected = selected;
        notifyItemChanged(previous);
        notifyItemChanged(selected);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dot, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.e("inside","change");
        if(selected==position)
            holder.img_dot.setImageResource(R.drawable.round_rank_highlight);
        else
            holder.img_dot.setImageResource(android.R.color.transparent);


    }



    @Override
    public int getItemCount() {
        return count;
    }




    class ViewHolder extends RecyclerView.ViewHolder  {

    ImageView img_dot;

        ViewHolder(View itemView) {
            super(itemView);

            img_dot=(ImageView) itemView.findViewById(R.id.img_dot);


        }




    }

}

