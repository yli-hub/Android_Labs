package com.cst2335.li000713;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatRoomActivity<MyListAdapter> extends AppCompatActivity {
    private ListView chatlist;
    private ArrayList<Message> element = new ArrayList<>();
    SQLiteDatabase db;
    private ChatRoomActivity.MyListAdapter myAdapter;
    private Button sendBtn;
    private Button rcvBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        myAdapter = new ChatRoomActivity.MyListAdapter();

        chatlist = (ListView) findViewById(R.id.crlv);
        chatlist.setAdapter(myAdapter);
        EditText etext = findViewById(R.id.type);

        loadDataFromDatabase();
        chatlist.setOnItemLongClickListener((parent, view, row, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getString(R.string.rm_title))
                    .setMessage(getString(R.string.rm_msg1) + row +
                            "      " + getString(R.string.rm_msg2) + id)
                    .setPositiveButton(getString(R.string.p_msg1), (click, arg) -> {
                        element.remove(row);
                        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?",
                                new String[]{Long.toString(myAdapter.getItemId(row))});
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(getString(R.string.n_msg2), (click, arg) -> {
                    })
                    .create().show();
            return true;
        });


        sendBtn = findViewById(R.id.sendbtn);
        sendBtn.setOnClickListener(click -> {
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyOpener.COL_TEXT, etext.getText().toString());
            newRowValues.put(MyOpener.COL_TYPE, 0);
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

            Message msgSend = new Message(etext.getText().toString(), true, newId);
            element.add(msgSend);
            etext.setText("");
            Toast.makeText(this, "Inserted item id:" + newId, Toast.LENGTH_LONG).show();
            myAdapter.notifyDataSetChanged();
        });

        rcvBtn = findViewById(R.id.rcvbtn);
        rcvBtn.setOnClickListener(click -> {
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyOpener.COL_TEXT, etext.getText().toString());
            newRowValues.put(MyOpener.COL_TYPE, 1);
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);
            Message msgRcv = new Message(etext.getText().toString(), false, newId);
            element.add(msgRcv);
            etext.setText("");

            Toast.makeText(this, "Inserted item id:" + newId, Toast.LENGTH_LONG).show();
            myAdapter.notifyDataSetChanged();
        });
    }

    private void loadDataFromDatabase() {
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();
        String[] columns = {MyOpener.COL_TEXT, MyOpener.COL_TYPE, MyOpener.COL_ID};
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        printCursor(results, db.getVersion());

        int textMessageColumnIndex = results.getColumnIndex(MyOpener.COL_TEXT);
        int isSentIndex = results.getColumnIndex(MyOpener.COL_TYPE);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        while (results.moveToNext()) {
            String msg = results.getString(textMessageColumnIndex);
            int typeInt = results.getInt(isSentIndex);
            Long dataId = results.getLong(idColIndex);

            boolean msgType;
            if (typeInt == 0) {
                msgType = true;
            } else {
                msgType = false;
            }

            element.add(new Message(msg, msgType, dataId));
        }
    }

    protected void printCursor(Cursor c, int version) {
        int columN = c.getColumnCount();
        Log.e("Version", Integer.toString(db.getVersion()));
        Log.e("Number Columns", Integer.toString(columN));
        for (int i = 0; i < columN; i++) {
            Log.e("Column Name", c.getColumnName(i));
        }
        Log.e("Number Rows", Integer.toString(c.getCount()));
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            Log.e("row", Integer.toString(i+1)
                    + ";  message  " + c.getString(c.getColumnIndex(MyOpener.COL_TEXT))
                    + ";  isSend:  " + Integer.toString(c.getInt(c.getColumnIndex(MyOpener.COL_TYPE)))
                    + ";  _id: " + Integer.toString(c.getInt(c.getColumnIndex(MyOpener.COL_ID))));
            c.moveToNext();
        }
        c.moveToPosition(-1);
    }


    class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return element.size();
        }

        @Override
        public Object getItem(int row) {
            return element.get(row);
        }

        @Override
        public long getItemId(int row) {
            return element.get(row).getId();
        }

        @Override
        public View getView(int type, View convertView, ViewGroup parent) {
            Message textmsg = (Message) getItem(type);
            LayoutInflater inflater = getLayoutInflater();

            if (textmsg.getIsSend()) {
                View sView = inflater.inflate(R.layout.row_send_layout, parent, false);
                TextView thisRowText = sView.findViewById(R.id.tx_send);
                thisRowText.setText(textmsg.getMsg());
                return sView;
            } else {
                View rView = inflater.inflate(R.layout.row_rcv_layout, parent, false);
                TextView thisRowText = rView.findViewById(R.id.tx_rcv);
                thisRowText.setText(textmsg.getMsg());
                return rView;
            }
        }
    }
}

