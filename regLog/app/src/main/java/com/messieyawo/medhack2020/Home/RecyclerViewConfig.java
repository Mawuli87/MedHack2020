package com.messieyawo.medhack2020.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.messieyawo.medhack2020.R;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class RecyclerViewConfig {

    //helps make calls to activities methods
    private Context mContext;

    private DailyVersesAdapter mDailyVersesAdapter;


    public void setConfg(RecyclerView recyclerView, Context context, List<DailySlots> dailyVers, List<String> keys){
        mContext = context;
        mDailyVersesAdapter = new DailyVersesAdapter(dailyVers,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mDailyVersesAdapter);
    }

    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
    String date = df.format(Calendar.getInstance().getTime());
    //inflate the recyclerview layout and populate it views
    class DailyVersesView extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mDescription;
        private TextView mDate;
        private TextView mVerses;
        private TextView mVerseType;
        private String key;
        private TextView mCurrentTime;
        //string variable for holding the keys of the variable

        public DailyVersesView(@NonNull View parent){
            super(LayoutInflater.from(mContext)
                    .inflate(R.layout.model, (ViewGroup) parent,false));


            mName = itemView.findViewById(R.id.name_publsher);
            mDescription = itemView.findViewById(R.id.description);
            mVerses = itemView.findViewById(R.id.verse_published);
            mDate = itemView.findViewById(R.id.date_published);
            mVerseType =itemView.findViewById(R.id.typeVerse);
            mCurrentTime = itemView.findViewById(R.id.date_current);

        }


        public void bind(DailySlots dailySlots, String key){
            //then published textview from the book objects
            mName.setText("Updated by:"+ dailySlots.getName());
            mDate.setText("Published at: "+ dailySlots.getDate());
            mVerses.setText("Reservation: "+ dailySlots.getVerse());
            mDescription.setText(dailySlots.getDescription());
            mVerseType.setText("Time:"+ dailySlots.getType());
            mCurrentTime.setText("Current time:"+date);
            this.key = key;
        }

    }
    //responsible for creating daily verses vew object and pass it to bind to bind view
    class DailyVersesAdapter extends RecyclerView.Adapter<DailyVersesView> {
        private List<DailySlots> mDailyList;
        private List<String> mKeys;

        public DailyVersesAdapter(List<DailySlots> mDailyList, List<String> mKeys) {
            this.mDailyList = mDailyList;
            this.mKeys = mKeys;
        }


        @NonNull
        @Override
        public DailyVersesView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DailyVersesView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DailyVersesView holder, int position) {
            holder.bind(mDailyList.get(position),mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mDailyList.size();
        }
    }
}