package com.androidsrc.server;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tkb on 2017-03-10.
 */

public class MindfulMorningListAdapter extends ArrayAdapter<DataModel> {

    private final Activity context;
    List<DataModel> activitiesModel;
    TickCount mainActivity;
    public MindfulMorningListAdapter(Activity context, List<DataModel> activitiesModel, TickCount tickCount) {
        super(context, R.layout.mm_list_row, activitiesModel);
        this.context = context;
        this.activitiesModel = activitiesModel;
        this.mainActivity = tickCount;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.mm_list_row, null, false);
        TextView txt_time = (TextView) rowView.findViewById(R.id.txt_time);

        TextView txt_title = (TextView) rowView.findViewById(R.id.txt_title);
        CheckBox checkBox = (CheckBox)rowView.findViewById(R.id.checkBox);

        txt_title.setText(activitiesModel.get(position).getName());
        txt_time.setText(activitiesModel.get(position).getTask()+"");

        checkBox.setChecked(getItem(position).isChecked());
        checkBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //remove(getItem(position));
                DataModel dataModel = getItem(position);

                if (!getItem(position).isChecked()){
                    dataModel.setChecked(true);
                    remove(dataModel);
                    insert(dataModel,getCount());
                    mainActivity.setTickCount();
                }else {
                    mainActivity.removeTickCount();
                    dataModel.setChecked(false);

                }


            }
        });
        return rowView;
    }

/*    @Override
    public int getCount() {
        return activitiesModel.size();

    }*/
}
