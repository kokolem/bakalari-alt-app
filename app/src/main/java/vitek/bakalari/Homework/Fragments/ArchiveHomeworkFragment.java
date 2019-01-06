package vitek.bakalari.Homework.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vitek.bakalari.Homework.Adapters.ArchiveHomeworkAdapter;
import vitek.bakalari.Homework.HomeworkDataHolder;
import vitek.bakalari.R;

public class ArchiveHomeworkFragment extends Fragment {
    private View mFragmentView;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("DEBUG","Archive homework fragment created");
        mFragmentView = inflater.inflate(R.layout.homework_fragment_tab_selected, container, false);

        mRecyclerView = mFragmentView.findViewById(R.id.card_container);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new ArchiveHomeworkAdapter(HomeworkDataHolder.getInstance().getAllArchived(), getContext()));

        return mFragmentView;
    }

}
