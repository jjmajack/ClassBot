package Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import Activity.R;

import java.util.ArrayList;

import Model.CategoryHolder;
import Model.LevelHolder;
import Model.User;

/**
 * Created by Jackson on 11/11/2017.
 */

public class Groups extends Fragment {

    ProgressDialog progressDialog;
    ListView lstViewQuizChallenge;
    ArrayList<LevelHolder> levels = new ArrayList<>();
    Bundle bundle;
    View view;
    TextView txtPoints;

    User person;
    CategoryHolder category;

    private ViewPager mViewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        bundle = saveInstanceState;
        category = this.getArguments().getParcelable("category");
        person = this.getArguments().getParcelable("person");


        view = inflater.inflate(R.layout.activity_quiz_category, container, false);
        progressDialog = new ProgressDialog(getContext());
        lstViewQuizChallenge = (ListView)view.findViewById(R.id.lstViewChallenges);

        txtPoints = (TextView)view.findViewById(R.id.txtPoints);

        //String[] items = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, items);

        //levels = quizCategoryHelper.getLevels();

        return view;
    }
}
