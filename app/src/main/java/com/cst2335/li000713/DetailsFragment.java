package com.cst2335.li000713;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Bundle dataFromActivity;
    private long id;
    private AppCompatActivity parentActivity;
  //  private boolean isTablet;
//    public void setTablet(boolean tablet) {
//        isTablet = tablet;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ChatRoomActivity.ITEM_ID );

        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_details, container, false);

        //show the message
        TextView message = (TextView)result.findViewById(R.id.fdmsg);
        message.setText(dataFromActivity.getString(ChatRoomActivity.ITEM_SELECTED));

        //show the id:
        TextView idView = (TextView)result.findViewById(R.id.fdid);
        idView.setText("ID=" + id);

        CheckBox check = result.findViewById(R.id.fdckbox);
        check.setChecked(dataFromActivity.getBoolean(ChatRoomActivity.ITEM_TYPE));

                // get the delete button, and add a click listener:
        Button hide = (Button)result.findViewById(R.id.hideButton);
        hide.setOnClickListener( clk -> {

            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }
}