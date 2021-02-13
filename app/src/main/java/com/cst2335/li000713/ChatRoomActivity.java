package com.cst2335.li000713;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

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
        myAdapter = (MyListAdapter) new ChatRoomActivity.MyListAdapter();
        chatlist.setAdapter((ListAdapter) myAdapter);
    }
    private ArrayList<String> element = new ArrayList<>();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            ViewGroup viewGroup = null;
            View thisRow = inflater.inflate(R.layout.row_layout, viewGroup, false);
            return thisRow;
        }
    }
}