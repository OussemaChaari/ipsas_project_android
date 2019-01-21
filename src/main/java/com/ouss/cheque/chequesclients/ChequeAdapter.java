package com.ouss.cheque.chequesclients;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.text.AlteredCharSequence;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChequeAdapter extends ArrayAdapter<ChequeEntity> implements View.OnClickListener{

    private ArrayList<ChequeEntity> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView client;
        TextView amount;
        TextView date;
        TextView state;
    }

    public ChequeAdapter(ArrayList<ChequeEntity> data, Context context) {
        super(context, R.layout.activity_cheque, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        ChequeEntity dataModel=(ChequeEntity) object;
    }

    private int lastPosition= -1;

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ChequeEntity dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_cheque, parent, false);
            viewHolder.amount = (TextView) convertView.findViewById(R.id.amount);
            viewHolder.date= (TextView) convertView.findViewById(R.id.date);
            viewHolder.state= (TextView) convertView.findViewById(R.id.state);
            viewHolder.client= (TextView) convertView.findViewById(R.id.client);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.client.setText(dataModel.getUserEmail());
        viewHolder.amount.setText(dataModel.getAmount() + " tnd");
        viewHolder.date.setText(dataModel.getDate());
        String amount=dataModel.getState();
        if (amount.toLowerCase().contains("no"))
            viewHolder.state.setTextColor(Color.RED);
        else
            viewHolder.state.setTextColor(Color.GREEN);
        viewHolder.state.setText(amount);

        viewHolder.amount.setOnClickListener(this);
        viewHolder.amount.setTag(position);

        // Return the completed view to render on screen
        return convertView;
    }
}
