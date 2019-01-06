package vitek.bakalari.Homework.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import vitek.bakalari.Homework.Homework;
import vitek.bakalari.R;

public class ArchiveHomeworkAdapter extends RecyclerView.Adapter<ArchiveHomeworkAdapter.MyViewHolder> {
    private ArrayList<Homework> mDataSet;
    private Context mContext;

    public ArchiveHomeworkAdapter(ArrayList<Homework> myDataSet, Context context) {
        Log.d("DEBUG","Archive homework adapter created");
        mDataSet = myDataSet;
        mContext = context;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mSecondaryTitle;
        public TextView mSupportingText;
        public Button mButtonFinished;
        public ImageView mIcon;

        public MyViewHolder(View itemView, TextView title, TextView secondaryTitle, TextView supportingText, ImageView icon) {
            super(itemView);
            mTitle = title;
            mSecondaryTitle = secondaryTitle;
            mSupportingText = supportingText;
            mIcon = icon;
        }
    }

    @NonNull
    @Override
    public ArchiveHomeworkAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View homeworkCard = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homework_archive_card, parent, false);
        TextView title = homeworkCard.findViewById(R.id.title);
        TextView secondaryTitle = homeworkCard.findViewById(R.id.secondaryTitle);
        TextView supportingText = homeworkCard.findViewById(R.id.supportingText);
        ImageView icon = homeworkCard.findViewById(R.id.icon);

        return new MyViewHolder(homeworkCard, title, secondaryTitle, supportingText, icon);
    }

    @Override
    public void onBindViewHolder(@NonNull final ArchiveHomeworkAdapter.MyViewHolder holder, int position) {
        holder.mTitle.setText(mDataSet.get(position).getTitle());
        holder.mSecondaryTitle.setText("Zadáno " + mDataSet.get(position).getAssigned() + " · Odevzdáno " + mDataSet.get(position).getHandIn());
        holder.mSupportingText.setText(mDataSet.get(position).getSupportingText());
        holder.mIcon.setImageResource(mDataSet.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        Log.d("DEBUG","Archive homework adapter getItemCount() called");
        return mDataSet.size();
    }

}