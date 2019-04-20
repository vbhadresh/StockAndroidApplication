package com.example.vaish.stockapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import com.example.vaish.stockapplication.List;

public class Main_Activity  extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    private EditText userName;
    private EditText password;
    private Button login;
    private TextView register;
    private ProgressBar progressbar;
    private Spinner userType;

    private RecyclerView recyclerView;
    private ArrayList<Stock> stockData;
    private Viewhandler viewadapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "Main_Activity";
    private String Url = "http://www.marketwatch.com/investing/stock/";
    boolean working = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: working");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        recyclerView = (RecyclerView) findViewById(R.id.R1);
        stockData = new ArrayList<Stock>();
        viewadapter = new Viewhandler(stockData, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.s1);
        recyclerView.setAdapter(viewadapter);
        List.getInstance(this).setupDb();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                onResume();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void getData(){
        userName = (EditText) findViewById(R.id.R1);
        password = (EditText) findViewById(R.id.R1);
        login = (Button) findViewById(R.id.R1);
        register = (TextView) findViewById(R.id.R1);
        progressbar=(ProgressBar)findViewById(R.id.R1);

        userType=(Spinner)findViewById(R.id.R1);
      /*  ArrayAdapter<String> userSpinner= new ArrayAdapter<String>(Main_Activity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.R1.userType));
        userSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(userSpinner);
        System.out.println(userType.getSelectedItem().toString());*/
    }

    public void SelectuserType(){
        String Utype= userType.getSelectedItem().toString();
        if(Utype.equals("PublicSafety")){
            Intent tent=new Intent(getApplicationContext(),Main_Activity.class);
            tent.putExtra("userType",Utype);
            tent.putExtra("userName",userName.getText().toString());
            startActivity(tent);
        }
        else{
            System.out.println("User");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater f = getMenuInflater();
        f.inflate(R.menu.addmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.m1:
                Log.d(TAG, "onOptionsItemSelected:Came ");
                DialogBox();

        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isNetworkConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected())
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        final int i = recyclerView.getChildLayoutPosition(v);
        Stock s = stockData.get(i);
        String stockUrl = Url + s.getStock_Sys();
        Intent i1 = new Intent(Intent.ACTION_VIEW);
        i1.setData(Uri.parse(stockUrl));
        startActivity(i1);
    }

    @Override
    protected void onDestroy() {
        List.getInstance(this).shutdown();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNetworkConnected()) {
            stockData.clear();
            ArrayList<String[]> dbdata = List.getInstance(this).Load();
            for (String[] s : dbdata) {
                Value create = new Value(this);
                create.execute(s[0], s[1], "load");
            }

        } else {
            noconnection();
        }
        super.onPostResume();
    }

    //User input Dialog
    public void DialogBox() {
        Log.d(TAG, "userinputDialog: came ");
        final Main_Activity app = this;
        if (isNetworkConnected()) {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            final EditText et = new EditText(this);
            et.setInputType(InputType.TYPE_CLASS_TEXT);
            et.setGravity(Gravity.CENTER_HORIZONTAL);

            builder.setView(et);


            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Log.d(TAG, "onClick: Came to ok button");
                    StockFile listaction = new StockFile(app);
                    listaction.execute(et.getText().toString());
                }
            });
            builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });


            builder.setMessage("Please enter a value:");
            builder.setTitle("Select Stock");

            AlertDialog dialog = builder.create();
            dialog.show();

        } else {

            noconnection();
        }
    }

    public void noconnection() {
        Log.d(TAG, "noconnection: came");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        Log.d(TAG, "noconnection: came1");
        builder.setMessage("App Requires Internet ");
        builder.setTitle("No Network");
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    //Task1
    public void updateTask1(ArrayList<String[]> s, boolean r) {
        final String[] arraylist = new String[s.size()];

        if (r) {
            Nostock();
        } else {
            Log.d(TAG, "updateTask1: came to else");
            final Main_Activity t = this;
            for (int i = 0; i < s.size(); i++) {
                arraylist[i] = s.get(i)[0] + "-" + s.get(i)[1];
                Log.d(TAG, "updateTask1: " + s.get(i)[0] + "-" + s.get(i)[1]);
            }
            if (arraylist.length == 0) {
                Nostock();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select a Stock");
                Log.d(TAG, "updateTask1: came before crash");
                builder.setItems(arraylist, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MakeSelection(arraylist[which]);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog dialog = builder.create();

                dialog.show();
            }
        }

    }

    public void Nostock() {
        Log.d(TAG, "updateTask1: came to no stock");
        AlertDialog.Builder userinput = new AlertDialog.Builder(this);
        userinput.setMessage("No Stock Found");
       // userinput.setMessage("Stock Symbol is incorrect ");
        userinput.setPositiveButton("close", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog d = userinput.create();
        d.show();

    }

    private void MakeSelection(String s) {
        String[] ss = s.split("-");
        ArrayList<String[]> sd = List.getInstance(this).Load();
        boolean a = false;
        for (String[] m : sd) {
            if (m[0].equals(ss[0])) {
                Log.d(TAG, "MakeSelection: came" +m[0]);
                alreadyExist();
                a = true;
            }
        }
        if (a) {
            //DO nothing
        } else {
            Value d = new Value(this);
            d.execute(ss[0], ss[1], "add");
            Log.d(TAG, "MakeSelection: Came here before crash" + ss[0] + ss[1]);
        }

    }


    public void alreadyExist() {
        AlertDialog.Builder userinput = new AlertDialog.Builder(this);
        userinput.setMessage(" Stock Duplication");
        userinput.setMessage("Stock Symbol is already Present ");
        userinput.setPositiveButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog d = userinput.create();
        d.show();

    }


    public void updateTask2(Stock ss, String decision) {
        if (decision.equals("load")) {
            stockData.add(ss);
            Collections.sort(stockData, Stock.sortit);
            viewadapter.notifyDataSetChanged();
        } else if (decision.equals("add")) {
            if (ss.getCompanys_Name() == null) {
                    Nostock();
            } else {
                stockData.add(ss);
                Collections.sort(stockData, Stock.sortit);
                Log.d(TAG, "updateTask2: " + ss.getCompanys_Name());
                List.getInstance(this).addentry(ss);
                viewadapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public boolean onLongClick(View v) {
        final Main_Activity v1 = this;
        final int i = recyclerView.getChildLayoutPosition(v);
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
        deleteDialog.setMessage("Do you want to Delete the Stock?");
        deleteDialog.setTitle("Delete");
        deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                List.getInstance(v1).Deleteentery(stockData.get(i).getStock_Sys());
                stockData.remove(i);
                viewadapter.notifyDataSetChanged();
            }

        });
        deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//Do nothing
            }
        });
        AlertDialog dialogbox = deleteDialog.create();
        dialogbox.show();

        return true;
    }
}
