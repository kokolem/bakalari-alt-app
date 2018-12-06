package vitek.bakalari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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
    private String mUsername;
    private String mPassword;
    private String mSchool;
    private Intent mChooseAccountIntent;


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

        mSharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        mUsername = mSharedPreferences.getString("last_username", "");
        if (mUsername.equals("")) {
            mChooseAccountIntent = new Intent(getApplicationContext(), SelectAccountActivity.class);
            startActivityForResult(mChooseAccountIntent, 1);
        }
        else {
            mPassword = mSharedPreferences.getString("last_password","");
            mSchool = mSharedPreferences.getString("last_school","");
            // TODO: Start listening for updates
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: Start listening for updates
    }

    @Override
    protected void onDestroy() {
        // TODO: Save last_username, etc. to shared preferences
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.change_account:
                mChooseAccountIntent = new Intent(getApplicationContext(), SelectAccountActivity.class);
                startActivityForResult(mChooseAccountIntent, 1);
                break;
            case R.id.about_app:
                Toast.makeText(this, "This feature is coming soon.", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
