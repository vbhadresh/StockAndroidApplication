package com.example.vaish.stockapplication;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by vaish on 4/29/2018.
 */

public class Viewholder extends RecyclerView.ViewHolder {
    public TextView textView;
    public TextView textView1;
    public TextView textView2;
    public TextView textView3;
    public TextView textView4;
    public TextView textView5;

    public Viewholder(View itemView) {
        super(itemView);
        textView =(TextView)itemView.findViewById(R.id.t1);
        textView1 =(TextView)itemView.findViewById(R.id.t2);
        textView2 =(TextView)itemView.findViewById(R.id.p1);
        textView3 =(TextView)itemView.findViewById(R.id.p2);
        textView4 =(TextView)itemView.findViewById(R.id.p3);
        textView5 =(TextView)itemView.findViewById(R.id.p4);
    }
    public void setTheme1(){
        textView.setTextColor(Color.parseColor("#00cc00"));
        textView1.setTextColor(Color.parseColor("#00cc00"));
        textView2.setTextColor(Color.parseColor("#00cc00"));
        textView4.setTextColor(Color.parseColor("#00cc00"));
        textView5.setTextColor(Color.parseColor("#00cc00"));
        textView3.setTextColor(Color.parseColor("#00cc00"));
        textView3.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.greenup,0,0);


    }
    public void setTheme2(){
        //#ff0000
        textView.setTextColor(Color.parseColor("#ff0000"));
        textView1.setTextColor(Color.parseColor("#ff0000"));
        textView2.setTextColor(Color.parseColor("#ff0000"));
        textView4.setTextColor(Color.parseColor("#ff0000"));
        textView5.setTextColor(Color.parseColor("#ff0000"));
        textView3.setTextColor(Color.parseColor("#ff0000"));
        textView3.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.reddown,0,0);

    }

    public void setTheme3() {
        textView.setTextColor(Color.parseColor("#ffff33"));
        textView1.setTextColor(Color.parseColor("#ffff33"));
        textView2.setTextColor(Color.parseColor("#ffff33"));
        textView4.setTextColor(Color.parseColor("#ffff33"));
        textView5.setTextColor(Color.parseColor("#ffff33"));
        textView3.setTextColor(Color.parseColor("#ffff33"));
        textView3.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
    }
}
