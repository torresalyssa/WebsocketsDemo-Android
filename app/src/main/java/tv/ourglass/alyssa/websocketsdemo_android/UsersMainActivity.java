package tv.ourglass.alyssa.websocketsdemo_android;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.socket.emitter.Emitter;

public class UsersMainActivity extends AppCompatActivity {

    String TAG = "UsersMainActivity";

    String nickname = null;

    UserListAdapter userListAdapter;

    ArrayList<JSONObject> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_main);

        userListAdapter = new UserListAdapter(this, users);

        ListView listView = (ListView) findViewById(R.id.userList);
        listView.setAdapter(userListAdapter);

        if (nickname == null) {
            askForNickname();
        }
    }

    private void askForNickname() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please enter a nickname.");

        // set up input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // set up buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (input.getText().toString().isEmpty()) {
                    askForNickname();

                } else {
                    Log.e(TAG, "here");
                    nickname = input.getText().toString();

                    SocketIOManager.getInstance().connectToServerWithNickname(nickname);
                    SocketIOManager.getInstance().getUserList(onUserList);
                }
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void exitChat(View view) {
        SocketIOManager.getInstance().exitChatWithNickname(nickname);
        nickname = null;
        users.clear();
        askForNickname();
    }

    public void joinChat(View view) {

    }

    private Emitter.Listener onUserList = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            UsersMainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray data = (JSONArray) args[0];
                    users.clear();

                    try {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject o = (JSONObject) data.get(i);
                            users.add(o);
                        }

                    } catch (JSONException e) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }

                    userListAdapter.notifyDataSetChanged();
                }
            });
        }
    };
}
