package com.cst2335.li000713;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity<MyListAdapter> extends AppCompatActivity {
     MyListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        element.add("one");
        element.add("two");
        element.add("Three");
        element.add("Four");
        element.add("Five");

        setContentView(R.layout.activity_chat_room);
        ListView chatlist = (ListView) findViewById(R.id.crlv);
        EditText etext = findViewById(R.id.type);
        Button sendBtn = findViewById(R.id.sendbtn);
        sendBtn.setOnClickListener( click ->{
            Message smsg = new Message(etext.toString(), R.drawable.row_send,0);
            element.add(smsg);
            myAdapter;
        });
        Button rcvBtn = findViewById(R.id.rcvbtn);
        rcvBtn.setOnClickListener( click -> {
            Message rmsg = new Message(etext.toString(), R.drawable.row_receive, 1);
                    element.add(rmsg);
        });
        myAdapter = (MyListAdapter) new ChatRoomActivity.MyListAdapter();
        chatlist.setAdapter((ListAdapter) myAdapter);
    }
    class Message {
        private String text;
        private int img;
        private int type;

        public Message (String text ,int img ,int type){
            this.text = text;
            this.img = img;
            this.type = type;
        }
        public String getText(){
            return text;
        }
        public int getImg(){
            return img;
        }
        public int getType(){
            return type;
        }


    }
    private ArrayList<Message> element = new ArrayList<>();
    class MyListAdapter extends BaseAdapter{


        @Override
        public int getCount() {

            return element.size();
        }

        @Override
        public String getItem(int row) {
            return "This is row"+row;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int row, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            ViewGroup viewGroup = null;
            View thisRow = inflater.inflate(R.layout.row_send_layout, viewGroup, false);
            TextView thisRowText= thisRow.findViewById(R.id.tx_send);
            thisRowText.setText(getItem(row));
            return thisRow;
        }
    }
}