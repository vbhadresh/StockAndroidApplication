package com.example.vaish.stockapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/**
 * Created by vaish on 4/29/2018.
 */

public class Viewhandler extends RecyclerView.Adapter<Viewholder> {
    public ArrayList<Stock> arrayList;
    public Main_Activity ref;
    private static final String TAG = "Viewhandler";

    public Viewhandler(ArrayList<Stock> data, Main_Activity s) {
        arrayList = data;
        ref = s;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Came");
        View stockitem = LayoutInflater.from(parent.getContext()).inflate(R.layout.stockcards, parent, false);
        stockitem.setOnLongClickListener(ref);
        stockitem.setOnClickListener(ref);
        return new Viewholder(stockitem);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        Stock temp = arrayList.get(position);
        try {
            // String temps = temp.getPricechange().substring(0, 1);
            double value = Double.parseDouble(temp.getPercentage_Changes());
            double price = Double.parseDouble(temp.getPrice());
            double previousClose = Double.parseDouble(temp.getPreviousClose());
            double result = price - previousClose;
            if (result > 0) {
                holder.setTheme1();
            } else if (result < 0) {
                holder.setTheme2();
            } else {
                holder.setTheme3();
            }
            holder.textView.setText(temp.getStock_Sys());
            holder.textView1.setText(temp.getCompanys_Name());
            holder.textView2.setText(temp.getPrice());
            holder.textView4.setText(temp.getPricechange());
            holder.textView5.setText("(" + temp.getPercentage_Changes() + ")");
            Log.d(TAG, "onBindViewHolder: Came and setup the Recycle view");

        } catch (Exception e) {
            Log.d(TAG, "onBindViewHolder: Error in the Json Data Some are Empty");
            holder.setTheme3();
            holder.textView.setText(temp.getStock_Sys() + "- Error ");
            holder.textView1.setText("Some Data are Empty");
            holder.textView2.setText("");
            holder.textView4.setText("");
            holder.textView5.setText("");

            e.getStackTrace();

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
