package Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

public class QuizCategory extends Fragment {

    ProgressDialog progressDialog;
    ListView lstViewQuizChallenge;
    ArrayList<LevelHolder> levels = new ArrayList<>();
    Bundle bundle;
    View view;
    TextView txtPoints;

    User person;
    CategoryHolder category;


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

    public class QuizChallengeAdapter extends ArrayAdapter<LevelHolder> {

        public QuizChallengeAdapter() {
            super(view.getContext(), R.layout.listview_row, levels);
        }

        public View getView(int pos, View convertView, ViewGroup parent)
        {
            View row = convertView;
            RoomDataHolder roomW;

            if(row == null)
            {
                LayoutInflater inflater = getLayoutInflater(bundle);
                row = inflater.inflate(R.layout.listview_row, parent, false);
                roomW = new RoomDataHolder(row);
                row.setTag(roomW);
            } else {
                roomW = (RoomDataHolder)row.getTag();
            }
            roomW.populateFrom(levels.get(pos));
            return row;
        }
    }
    static class RoomDataHolder
    {
        private TextView count = null;
        private TextView title = null;
        private ImageView icon = null;


        public RoomDataHolder(View row)
        {
            count = (TextView)row.findViewById(R.id.lblCount);
            title = (TextView)row.findViewById(R.id.lblTitle);
            icon = (ImageView)row.findViewById(R.id.imgLevelStatus);
        }

        void populateFrom(LevelHolder level)
        {
            count.setText(level.getCount() + "");
            title.setText(level.getTitle());
            icon.setImageResource((level.getStatus() == 1) ? R.drawable.unlocked : R.drawable.locked);
        }
    }

    private class ItemClickHandler implements AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
            LevelHolder levelHolder = levels.get(arg2);
            /*
            Intent intent = new Intent(getContext(), Quiz.class);
            intent.putExtra("category", category);
            intent.putExtra("level", levelHolder);
            intent.putExtra("person", person);
            intent.putExtra("port", port);
            intent.putExtra("ipaddress", ipAddress);
            startActivity(intent);*/
        }
    }
}
