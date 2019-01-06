package vitek.bakalari.Homework.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import vitek.bakalari.Homework.Adapters.MainHomeworkAdapter;
import vitek.bakalari.Homework.Homework;
import vitek.bakalari.Homework.HomeworkDataHolder;
import vitek.bakalari.R;

public class MainHomeworkFragment extends Fragment {
    private Context mContext;
    private View mFragmentView;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TabLayout mTabLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("DEBUG","Main homework fragment created");
        mContext = getContext();
        mFragmentView = inflater.inflate(R.layout.homework_fragment_main, container, false);
        mViewPager = mFragmentView.findViewById(R.id.view_pager);
        mTabLayout = mFragmentView.findViewById(R.id.tabs);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTabLayout.setElevation(8);
        }


        // TODO: Get real data from parser
        ArrayList<Homework> mDataSet = new ArrayList<Homework>();
        mDataSet.add(new Homework(R.drawable.circle_icon, "Český jazyk a literatura", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam ligula pede, sagittis quis, interdum ultricies, scelerisque eu.", true, true, "24.12.", "22.12."));
        mDataSet.add(new Homework(R.drawable.circle_icon, "Matematika", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam ligula pede, sagittis quis.", true, true, "24.12.", "22.12."));
        mDataSet.add(new Homework(R.drawable.circle_icon, "Český jazyk a literatura", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam ligula pede, sagittis quis, interdum ultricies, scelerisque eu.", true, false, "24.12.", "22.12."));
        mDataSet.add(new Homework(R.drawable.circle_icon, "Matematika", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam ligula pede, sagittis quis.", false, false, "24.12.", "22.12."));
        mDataSet.add(new Homework(R.drawable.circle_icon, "Globální výchova", "Lorem ipsum dolor sit amet.", false, false, "24.12.", "22.12."));
        mDataSet.add(new Homework(R.drawable.circle_icon, "Přírodopis", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam ligula pede, sagittis quis, interdum.", false, false, "24.12.", "22.12."));
        HomeworkDataHolder.init(mDataSet);

        mAdapter = new MainHomeworkAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        return mFragmentView;
    }
    @Override
    public void onDestroyView() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(8);
        super.onDestroyView();
    }
}
