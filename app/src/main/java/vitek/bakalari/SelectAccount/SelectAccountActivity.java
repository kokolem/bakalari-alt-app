package vitek.bakalari.SelectAccount;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vitek.bakalari.LoginActivity;
import vitek.bakalari.R;

public class SelectAccountActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    private int mAccountCount;
    private RecyclerView mRecyclerView;
    private ArrayList<Account> mAccounts = new ArrayList<Account>();
    private FloatingActionButton mFab;
    private Intent mNewAccountIntent;
    private AccountAdapter mAdapter;

    private String mUserName;
    private Boolean mAccountType;
    private String mAccountSchoolURL;
    private String mPassword;
    private String mRealName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account);
        mFab = findViewById(R.id.fab);

        mSharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        mAccountCount = mSharedPreferences.getInt("account_count", 0);

        mRecyclerView = findViewById(R.id.account_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AccountAdapter(mAccounts, this, mSharedPreferences, findViewById(R.id.first_time_image), findViewById(R.id.first_time_text));
        mRecyclerView.setAdapter(mAdapter);

        if (mAccountCount == 0) {
            findViewById(R.id.first_time_image).setVisibility(View.VISIBLE);
            findViewById(R.id.first_time_text).setVisibility(View.VISIBLE);
        } else {
            for (int i = 0; i < mAccountCount; i++) {
                mUserName = mSharedPreferences.getString("account_" + i + "_username", "");
                mAccountType = mSharedPreferences.getBoolean("account_" + i + "_is_parent  ", false);
                mAccountSchoolURL = mSharedPreferences.getString("account_" + i + "_school", "");
                mPassword = mSharedPreferences.getString("account_" + i + "_password", "");
                mRealName = mSharedPreferences.getString("account_" + i + "_real_name", "");

                mAccounts.add(new Account(mPassword, mUserName, mRealName, mAccountType, mAccountSchoolURL));
            }
        }

        mFab.setColorFilter(Color.WHITE); // make the FAB icon white
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewAccountIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivityForResult(mNewAccountIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            findViewById(R.id.first_time_image).setVisibility(View.GONE);
            findViewById(R.id.first_time_text).setVisibility(View.GONE);
            mUserName = data.getStringExtra("username");
            mPassword = data.getStringExtra("password");
            mRealName = data.getStringExtra("realName");
            mAccountType = data.getBooleanExtra("isParent", false);
            mAccountSchoolURL = data.getStringExtra("schoolURL");
            mAccounts.add(new Account(mPassword, mUserName, mRealName, mAccountType, mAccountSchoolURL));
            mAdapter.notifyItemInserted(mAccounts.size());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
