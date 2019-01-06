package vitek.bakalari.SelectAccount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import vitek.bakalari.R;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder> {
    private ArrayList<Account> mDataSet;
    private Context mContext;
    private String translatedType;
    private SharedPreferences mSharedPreferences;
    private View mFirstText;
    private View mFirstImage;
    private Intent mResultIntent;


    public AccountAdapter(ArrayList<Account> dataSet, Context context, SharedPreferences sharedPreferences, View firstImage, View firstText) {
        mDataSet = dataSet;
        mContext = context;
        mSharedPreferences = sharedPreferences;
        mFirstText = firstText;
        mFirstImage = firstImage;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIcon;
        public TextView mTitle;
        public TextView mSecondaryTitle;
        public ImageView mDelete;

        public MyViewHolder(View itemView, ImageView icon, TextView title, TextView secondaryTitle, ImageView delete) {
            super(itemView);
            mIcon = icon;
            mTitle = title;
            mSecondaryTitle = secondaryTitle;
            mDelete = delete;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View account = LayoutInflater.from(parent.getContext()).inflate(R.layout.account, parent, false);
        ImageView icon = account.findViewById(R.id.icon);
        TextView title = account.findViewById(R.id.title);
        TextView secondaryTitle = account.findViewById(R.id.secondaryTitle);
        ImageView delete = account.findViewById(R.id.delete);
        return new MyViewHolder(account, icon, title, secondaryTitle, delete);
    }

    @Override
    public void onBindViewHolder(final AccountAdapter.MyViewHolder holder, int position) {
        holder.mIcon.setImageResource(R.drawable.circle_icon);
        if (mDataSet.get(position).getType()) translatedType = mContext.getString(R.string.parent);
        else translatedType = mContext.getString(R.string.pupil);
        holder.mTitle.setText(mDataSet.get(position).getRealName() + " (" + translatedType + ")");
        holder.mSecondaryTitle.setText(mDataSet.get(position).getSchoolURL());

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataSet.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.remove("account_" + holder.getAdapterPosition() + "_real_name");
                editor.remove("account_" + holder.getAdapterPosition() + "_type");
                editor.remove("account_" + holder.getAdapterPosition() + "_school");
                editor.remove("account_" + holder.getAdapterPosition() + "_username");
                editor.remove("account_" + holder.getAdapterPosition() + "_password");
                editor.putInt("account_count", mSharedPreferences.getInt("account_count", 1) - 1);
                editor.apply();
                if (mSharedPreferences.getInt("account_count", 0) == 0) {
                    mFirstImage.setVisibility(View.VISIBLE);
                    mFirstText.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResultIntent = new Intent();
                mResultIntent.putExtra("username", mDataSet.get(holder.getAdapterPosition()).getUserName());
                mResultIntent.putExtra("password", mDataSet.get(holder.getAdapterPosition()).getPassword());
                mResultIntent.putExtra("schoolURL", mDataSet.get(holder.getAdapterPosition()).getSchoolURL());
                ((Activity)mContext).setResult(Activity.RESULT_OK, mResultIntent);
                ((Activity)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
