package com.miracle.miraclediary;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoalSimpleAdapter extends SimpleAdapter {

    private ArrayList<String> modes;
    private ArrayList<Boolean> isFinished;

    /**
     * Constructor
     *  @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
 *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
*                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     * @param isFinished
     */
    public GoalSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource,
                             String[] from, int[] to, ArrayList<String> modes, ArrayList<Boolean> isFinished) {
        super(context, data, resource, from, to);

        this.modes = modes;
        this.isFinished = isFinished;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        char mode[] = null;
        boolean isImportant;
        boolean isFinished;

        try {
            mode = modes.get(position).toCharArray();
            isImportant = mode[0] == '1';
            isFinished = this.isFinished.get(modes.size() - position - 1);

        } catch (Exception e) {
            Log.e("GoalSimpleAdapter", "Error! mode size is " + mode.length + ", pos is " + position);
            return view;
        }

        ImageView icon = view.findViewById(R.id.goal_icon);

        int _id = R.drawable.goal_icon_no_imp;

        if (isImportant) {
            _id = R.drawable.goal_icon_imp;
        }
        if (isFinished) {
            _id = R.drawable.goal_icon_clear;
        }
        icon.setImageResource(_id);

        return view;
    }
}
