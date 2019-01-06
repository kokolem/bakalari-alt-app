package vitek.bakalari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import vitek.bakalari.Grades.GradesFragment;
import vitek.bakalari.Home.HomeFragment;
import vitek.bakalari.Homework.Fragments.MainHomeworkFragment;
import vitek.bakalari.Others.OthersFragment;
import vitek.bakalari.Schedule.ScheduleFragment;
import vitek.bakalari.SelectAccount.SelectAccountActivity;

public class MainActivity extends AppCompatActivity {

    private int mFragmentContainer;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment mSelectedFragment;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mUsername;
    private String mPassword;
    private String mSchoolURL;
    private Intent mIntent;
    private int mAccountCount;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_ukoly:
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mSelectedFragment = new MainHomeworkFragment();
                    mFragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    mFragmentTransaction.replace(mFragmentContainer, mSelectedFragment);
                    mFragmentTransaction.commit();
                    return true;
                case R.id.navigation_znamky:
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mSelectedFragment = new GradesFragment();
                    mFragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    mFragmentTransaction.replace(mFragmentContainer, mSelectedFragment);
                    mFragmentTransaction.commit();
                    return true;
                case R.id.navigation_rozvrh:
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mSelectedFragment = new ScheduleFragment();
                    mFragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    mFragmentTransaction.replace(mFragmentContainer, mSelectedFragment);
                    mFragmentTransaction.commit();
                    return true;
                case R.id.navigation_domu:
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mSelectedFragment = new HomeFragment();
                    mFragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    mFragmentTransaction.replace(mFragmentContainer, mSelectedFragment);
                    mFragmentTransaction.commit();
                    return true;
                case R.id.navigation_dalsi:
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mSelectedFragment = new OthersFragment();
                    mFragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    mFragmentTransaction.replace(mFragmentContainer, mSelectedFragment);
                    mFragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        mAccountCount = mSharedPreferences.getInt("account_count", 0);
        if (mAccountCount == 0) {
            mIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(mIntent, 2);
        } else if (mSharedPreferences.getString("last_username", "").equals("")) {
            mIntent = new Intent(this, SelectAccountActivity.class);
            startActivityForResult(mIntent, 3);
        } else {
            mUsername = mSharedPreferences.getString("last_username", "");
            mPassword = mSharedPreferences.getString("last_password", "");
            mSchoolURL = mSharedPreferences.getString("last_school_url", "");
            // TODO: Got school url, username and password, do stuff with it
        }

        setContentView(R.layout.activity_main);

        mFragmentContainer = R.id.fragment_container;
        mFragmentManager = getSupportFragmentManager();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_domu);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mSelectedFragment = new HomeFragment();
        mFragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        mFragmentTransaction.replace(mFragmentContainer, mSelectedFragment);
        mFragmentTransaction.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 1, 2 and 3 are codes of account changes or creations = in the Intent data we should get password, username and url
        if (requestCode == 1 || requestCode == 2 || requestCode == 3) {
            try {
                // try get the data
                mUsername = data.getStringExtra("username");
                mPassword = data.getStringExtra("password");
                mSchoolURL = data.getStringExtra("schoolURL");
            } catch (Exception ignored) {
                // if there was no data in the Intent finish the app because it cant work without user credentials
                // but if the request code was 1 it means the user voluntarily chose to change the account and is free to go back - his
                // credentials are still saved in the variables
                if (requestCode != 1) finish();
            }
            // this block only runs if there was data in the Intent in which case it saves the credentials from the Intent as the last credentials used
            // or if there was no data in the Intent but the request code was 1 in which case it saves the credentials used before the user went to the
            // change account activity (those credentials remained unchanged in the variables, they were only deleted from shared preferences) as the last
            // credentials used
            mEditor = mSharedPreferences.edit();
            mEditor.putString("last_username", mUsername);
            mEditor.putString("last_password", mPassword);
            mEditor.putString("last_school_url", mSchoolURL);
            mEditor.apply();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_account:
                mEditor = mSharedPreferences.edit();
                mEditor.remove("last_username");
                mEditor.remove("last_password");
                mEditor.remove("last_school_url");
                mEditor.apply();
                mIntent = new Intent(getApplicationContext(), SelectAccountActivity.class);
                startActivityForResult(mIntent, 1);
                break;
            case R.id.about_app:
                Toast.makeText(this, "This feature is coming soon.", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
