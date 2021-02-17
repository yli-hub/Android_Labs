package com.cst2335.li000713;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ChatRoomActivity<MyListAdapter> extends AppCompatActivity {
    private ListView chatlist;
    private ArrayList<Message> element = new ArrayList<>();
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

        chatlist.setOnItemLongClickListener((parent, view, row, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getString(R.string.rm_title))
                    .setMessage( getString(R.string.rm_msg1)+ row +
                            "      "+getString(R.string.rm_msg2) + id)
                    .setPositiveButton(getString(R.string.p_msg1), (click, arg) -> {
                        element.remove(row);
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(getString(R.string.n_msg2), (click, arg) -> {
                    })
                    .create().show();
            return true;
            });

        EditText etext = findViewById(R.id.type);
        sendBtn = findViewById(R.id.sendbtn);
        sendBtn.setOnClickListener(click -> {
            Message smsg = new Message(etext.getText().toString(), R.drawable.row_send, 0);
            element.add(smsg);
            myAdapter.notifyDataSetChanged();
        });

        rcvBtn = findViewById(R.id.rcvbtn);
        rcvBtn.setOnClickListener(click -> {
            Message rmsg = new Message(etext.getText().toString(), R.drawable.row_receive, 1);
            element.add(rmsg);
            myAdapter.notifyDataSetChanged();
        });
    }

    class Message {
        private String text;
        private int img;
        private int type;

        public Message(String text, int img, int type) {
            this.text = text;
            this.img = img;
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public int getImg() {
            return img;
        }

        public int getType() {
            return type;
        }
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
            return row;
        }

        @Override
        public View getView(int type, View convertView, ViewGroup parent) {
            Message textmsg = (Message) getItem(type);
            int msgLayout = textmsg.getType();
            LayoutInflater inflater = getLayoutInflater();

            switch (msgLayout) {
                case 0 : {
                    View sView = inflater.inflate(R.layout.row_send_layout, parent, false);
                    TextView thisRowText = sView.findViewById(R.id.tx_send);
                    ImageView thisImg = sView.findViewById(R.id.simgview);
                    thisRowText.setText(textmsg.getText());
                    thisImg.setImageResource(textmsg.getImg());
                    return sView;
                }
                case 1 : {

                    View rView = inflater.inflate(R.layout.row_rcv_layout, parent, false);
                    TextView thisRowText = rView.findViewById(R.id.tx_rcv);
                    ImageView thisImg = rView.findViewById(R.id.rimgview);
                    thisRowText.setText(textmsg.getText());
                    thisImg.setImageResource(textmsg.getImg());
                    return rView;
                }
                default:
                    return null;
            }
        }
    }
}
