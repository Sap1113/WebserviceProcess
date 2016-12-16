package com.webserviceprocess.process;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.webserviceprocess.bean.Bean;
import com.webserviceprocess.utils.ApiResponse;
import com.webserviceprocess.utils.WebInterface;

import static com.webserviceprocess.Dashboard.arrayListItem;

public class GetListProcess extends AsyncTask<Void, Void, Integer> {

    private ProgressDialog progressDialog;
    private Activity caller;
    private Handler handler;
    String message = "", status = null;
    // String GCMkey;

    public GetListProcess(Activity caller, Handler handler) {
        this.caller = caller;
        this.handler = handler;
    }

    @Override
    protected void onPreExecute() {

        progressDialog = new ProgressDialog(this.caller);
        progressDialog.setMessage("Fetching List Requests...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {

        ApiResponse apiResponse = null;
        String url = null;
        List<BasicNameValuePair> param = null;
        int result = 0;

        try {
            url = "set your url hear";

            // url = url + "userId=" + Prefs.getValue(caller, Const.USERID, "0")
            // + "&categoryType="
            // + Prefs.getValue(caller, Const.CATEGORY_TYPE, "");
            param = new ArrayList<BasicNameValuePair>();

            param.add(new BasicNameValuePair("userId", "22"));

            URI uri = new URI(url.replace(" ", "%20"));
            Log.e("** Url :: " + uri, "\n Param :: " + param.toString());

            apiResponse = WebInterface.doPost(uri.toString(), param);

            Log.e("apiResponse.response.toString() :: ", apiResponse.response.toString() + "");

            JSONObject jObj = new JSONObject(apiResponse.response.toString());

            status = jObj.getString("status");
            message = jObj.getString("message");
            if (status.equals("1")) {
                JSONArray Userarry = jObj.getJSONArray("List");

                if (arrayListItem != null) {
                    if (!arrayListItem.isEmpty()) {
                        arrayListItem.clear();
                        arrayListItem = null;
                    } else {
                        arrayListItem = null;
                    }
                }
                arrayListItem = new ArrayList<Bean>();
                for (int i = 0; i < Userarry.length(); i++) {
                    JSONObject usersobj = (JSONObject) Userarry.get(i);

                    Bean bean = new Bean();
                    bean.userName = usersobj.getString("userName");
                    bean.date = usersobj.getString("date");
                    bean.startTime = usersobj.getString("startTime");
                    bean.endTime = usersobj.getString("endTime");
                    arrayListItem.add(bean);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }

    @Override
    protected void onPostExecute(Integer result) {
        try {
            progressDialog.dismiss();
            Message msg = new Message();
            if (status != null) {
                msg.arg1 = Integer.parseInt(status);
                msg.what = 1;
                msg.obj = message;
            }

            this.handler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
