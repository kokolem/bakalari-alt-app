package vitek.bakalari.Grades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import vitek.bakalari.R;

public class GradesFragment extends Fragment {
    private View mDemoView;
    private TextView mDemoTextView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDemoView = inflater.inflate(R.layout.fragment_demo_main, container, false);
        mDemoTextView = mDemoView.findViewById(R.id.demoText);
        mDemoTextView.setText("Známky");
        return mDemoView;
    }
}
