package com.webserviceprocess;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.webserviceprocess.adapter.ListAdapter;
import com.webserviceprocess.bean.Bean;
import com.webserviceprocess.process.GetListProcess;
import com.webserviceprocess.utils.WebInterface;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    public static ArrayList<Bean> arrayListItem = new ArrayList<>();
    private RecyclerView recycler_list;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recycler_list = (RecyclerView) findViewById(R.id.recyclerView_list);

        if (!WebInterface.isOnline(Dashboard.this)) {
            try {
                new GetListProcess(Dashboard.this, handler).execute();
            } catch (Exception e) {
                e.printStackTrace();
                // myLog.e(TAG, "signUp: " + e);
            }
        } else {
            Toast.makeText(Dashboard.this, "Network not available.", Toast.LENGTH_SHORT).show();
        }
    }

    /*    for handeling response from asynctask of GetListProcess.class */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            try {
                if (msg.arg1 == 1) {
                    if (msg.what == 1) { // success
                        adapter = new ListAdapter(Dashboard.this, arrayListItem);
                        recycler_list.setAdapter(adapter);
                    }

                } else if (msg.arg1 == 0) { // fail
                    Toast.makeText(Dashboard.this, msg.obj.toString() + "", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("handleMessage: ", e.toString());
            }
        }
    };
}
