package com.android.project.todolist.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.project.todolist.R;
import com.android.project.todolist.domain.ListItem;
import com.android.project.todolist.tools.Tools;


import java.util.ArrayList;


public class ListItemAdapter extends ArrayAdapter<ListItem> {

    private Context context;
    private ArrayList<ListItem> listItemArrayList;
    private int sub_menu_single_listitem;


    public ListItemAdapter(Context context, ArrayList<ListItem> listItemArrayList) {

        super(context, R.layout.sub_menu_single_listitem, listItemArrayList);
        this.context = context;
        this.listItemArrayList = listItemArrayList;
        this.sub_menu_single_listitem = R.layout.sub_menu_single_listitem;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(sub_menu_single_listitem, null);

            //Animation für neu angelegte ListItems
            view.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
        } else {
            view = convertView;
        }

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");

        final ListItem currentListItem = listItemArrayList.get(position);

        final CheckBox title = (CheckBox) view.findViewById(R.id.listItem_checkBox);
        title.setText(currentListItem.getTitle());
        title.setTypeface(font);
        title.setClickable(false);
        title.setChecked(currentListItem.getIsDone());

        boolean dateIsEmpty = currentListItem.getFormatedDueDate().equals("");
        boolean noteIsEmpty = currentListItem.getNote().equals("");

        TextView date = (TextView) view.findViewById(R.id.listItem_date);
        TextView note = (TextView) view.findViewById(R.id.listItem_note);

        if(dateIsEmpty) {
            if(!noteIsEmpty) {
                setNote(date, font, currentListItem);
                date.setTextColor(context.getResources().getColor(R.color.grey));
            }
        } else {
            date.setText(currentListItem.getFormatedDueDate());
            date.setTypeface(font);
            date.setTextColor(context.getResources().getColor(Tools.currentListColor));
            if(!noteIsEmpty) {
                setNote(note, font, currentListItem);
            }
        }




        TextView priority = (TextView) view.findViewById(R.id.listItem_priority);
        priority.setText(currentListItem.getPriority()+ "");
        priority.setTypeface(font);

        ImageView reminder = (ImageView) view.findViewById(R.id.listItem_reminder);
        if(currentListItem.getReminder()) {
            reminder.setImageResource(R.drawable.ic_list_item_alarm_white);
            reminder.setColorFilter(context.getResources().getColor(Tools.currentListColor));
        } else {
            reminder.setImageResource(0);
        }

        return view;
    }

    private void setNote(TextView textView, Typeface font, ListItem currentListItem) {
        textView.setTypeface(font);
        String userNote = currentListItem.getNote();
        if(userNote.length() > 15) {
            textView.setText("" + userNote.substring(0, 15) + "...");
        } else {
            textView.setText(userNote);
        }
    }

    @Override
    public int getCount() {
        return listItemArrayList.size();
    }


}
