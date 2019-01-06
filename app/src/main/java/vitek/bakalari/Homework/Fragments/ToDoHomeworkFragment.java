package vitek.bakalari.Homework.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vitek.bakalari.Homework.Adapters.ToDoHomeworkAdapter;
import vitek.bakalari.Homework.HomeworkDataHolder;
import vitek.bakalari.R;

public class ToDoHomeworkFragment extends Fragment {
    private View mFragmentView;
    private RecyclerView mRecyclerView;
    private ToDoHomeworkAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("DEBUG","Todo homework fragment created");
        mFragmentView = inflater.inflate(R.layout.homework_fragment_tab_selected, container, false);

        mRecyclerView = mFragmentView.findViewById(R.id.card_container);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ToDoHomeworkAdapter(HomeworkDataHolder.getInstance().getAllToDo(), getContext());
        mRecyclerView.setAdapter(mAdapter);
        HomeworkDataHolder.getInstance().setToDoHomeworkAdapter(mAdapter);

        return mFragmentView;
    }

}
