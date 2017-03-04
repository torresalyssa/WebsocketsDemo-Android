package tv.ourglass.alyssa.websocketsdemo_android;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by atorres on 3/3/17.
 */

public class UserListAdapter extends ArrayAdapter<JSONObject> {

    String TAG = "UserListAdapter";

    private Context context;

    public UserListAdapter(Context context, ArrayList<JSONObject> users) {
        super(context, 0, users);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        JSONObject user = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.user_option, parent, false);
        }

        TextView name = (TextView) view.findViewById(R.id.name);
        try {
            name.setText(user.getString("nickname"));

        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        TextView status = (TextView) view.findViewById(R.id.status);
        try {
            Boolean isConnected = user.getBoolean("isConnected");
            status.setText(isConnected ? "online" : "offline");
            status.setTextColor(isConnected ? Color.GREEN : Color.RED);

        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        view.setTag(user);

        return view;
    }
}
