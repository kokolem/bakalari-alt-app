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
import vitek.bakalari.Homework.HomeworkDataHolder;
import vitek.bakalari.R;

public class ToDoHomeworkAdapter extends RecyclerView.Adapter<ToDoHomeworkAdapter.MyViewHolder> {
    private ArrayList<Homework> mDataSet;
    private Context mContext;

    public ToDoHomeworkAdapter(ArrayList<Homework> myDataSet, Context context) {
        Log.d("DEBUG","Todo homework adapter created");
        mDataSet = myDataSet;
        mContext = context;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mSecondaryTitle;
        public TextView mSupportingText;
        public Button mButtonFinished;
        public ImageView mIcon;

        public MyViewHolder(View itemView, TextView title, TextView secondaryTitle, TextView supportingText, ImageView icon, Button buttonFinished) {
            super(itemView);
            mTitle = title;
            mSecondaryTitle = secondaryTitle;
            mSupportingText = supportingText;
            mIcon = icon;
            mButtonFinished = buttonFinished;
        }
    }

    @NonNull
    @Override
    public ToDoHomeworkAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View homeworkCard = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homework_card, parent, false);
        TextView title = homeworkCard.findViewById(R.id.title);
        TextView secondaryTitle = homeworkCard.findViewById(R.id.secondaryTitle);
        TextView supportingText = homeworkCard.findViewById(R.id.supportingText);
        Button buttonFinished = homeworkCard.findViewById(R.id.buttonFinished);
        ImageView icon = homeworkCard.findViewById(R.id.icon);

        return new MyViewHolder(homeworkCard, title, secondaryTitle, supportingText, icon, buttonFinished);
    }

    @Override
    public void onBindViewHolder(@NonNull final ToDoHomeworkAdapter.MyViewHolder holder, int position) {
        holder.mTitle.setText(mDataSet.get(position).getTitle());
        holder.mSecondaryTitle.setText("Zadáno " + mDataSet.get(position).getAssigned() + " · Odevzdat " + mDataSet.get(position).getHandIn());
        holder.mSupportingText.setText(mDataSet.get(position).getSupportingText());
        holder.mIcon.setImageResource(mDataSet.get(position).getIcon());
        holder.mButtonFinished.setText(R.string.finished);
        holder.mButtonFinished.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Homework done = HomeworkDataHolder.getInstance().getToDoItem(holder.getAdapterPosition());
                done.setDone(true);
                HomeworkDataHolder.getInstance().removeToDoItem(holder.getAdapterPosition());
                HomeworkDataHolder.getInstance().addDoneItem(0, done);
                notifyItemRemoved(holder.getAdapterPosition());
                HomeworkDataHolder.getInstance().getDoneHomeworkAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("DEBUG","Todo homework adapter getCount() called");
        return mDataSet.size();
    }

}
