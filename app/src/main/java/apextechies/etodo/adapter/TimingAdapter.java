package apextechies.etodo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import apextechies.etodo.R;
import apextechies.etodo.model.TimingModel;
import apextechies.etodo.network.Utilz;


/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 09 Aug 2017 at 5:15 PM
 */

public class TimingAdapter extends RecyclerView.Adapter<TimingAdapter.MyViewHolder> {

    private List<TimingModel> myCartModels;
    private Context context;
    private int daycount;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView day, opening, closing, to;

        public MyViewHolder(View view) {
            super(view);
            day = (TextView) view.findViewById(R.id.day);
            opening = (TextView) view.findViewById(R.id.opening);
            closing = (TextView) view.findViewById(R.id.closing);
            to = (TextView) view.findViewById(R.id.to);
        }
    }


    public TimingAdapter(Context context, List<TimingModel> myCartModels, int daycount) {
        this.context = context;
        this.myCartModels = myCartModels;
        this.daycount = daycount;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timingadapter_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TimingModel sa = myCartModels.get(position);
        try {

            holder.day.setTypeface(Utilz.font(context, sa.getTypefacetype()));
            holder.opening.setTypeface(Utilz.font(context, sa.getTypefacetype()));
            holder.closing.setTypeface(Utilz.font(context, sa.getTypefacetype()));
            holder.to.setTypeface(Utilz.font(context, sa.getTypefacetype()));

            String upToNCharacters = sa.getDay().substring(0, Math.min(sa.getDay().length(), 3));

            holder.day.setText(upToNCharacters);

            holder.opening.setText(sa.getOpen_time() + "\t");

            holder.closing.setText("to" + "\t" + sa.getClose_time());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return myCartModels.size();
    }

}
