package project.major.itemsniper;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kurt on 5/13/2017.
 */
public class CommentAdapter extends BaseAdapter {

    private ArrayList<Comment> comments;
    private Context context;
    @Override
    public int getCount() {
        return comments.size();
    }

    public CommentAdapter(ArrayList<Comment> coms , Context c){

        this.comments = coms;
        this.context = c;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.comment_layout, parent,false);

        TextView d = (TextView)v.findViewById(R.id.comment_description);
        TextView u  = (TextView)v.findViewById(R.id.comment_user);

        d.setText(comments.get(position).getDesc());
        u.setText(comments.get(position).getUser());

        return v;
    }
}
