package vitek.bakalari.Homework.Adapters;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import vitek.bakalari.Homework.Fragments.ArchiveHomeworkFragment;
import vitek.bakalari.Homework.Fragments.DoneHomeworkFragment;
import vitek.bakalari.Homework.Fragments.ToDoHomeworkFragment;

public class MainHomeworkAdapter extends FragmentPagerAdapter {

    public MainHomeworkAdapter(FragmentManager fm) {
        super(fm);
        Log.d("DEBUG","Main homework adapter created");
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ToDoHomeworkFragment();
            case 1:
                return new DoneHomeworkFragment();
            case 2:
                return new ArchiveHomeworkFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        Log.d("DEBUG","Main homework adapter getCount() called");
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Zadané";
            case 1:
                return "Hotové";
            case 2:
                return "Archiv";
        }
        return null;
    }
}
