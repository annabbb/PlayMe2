package xyz.ideeva.playme;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {

    private int[] images;
    private String[] songs;
    private String[] singers;
    private Context c;
    LayoutInflater inflater;

    public CustomAdapter(Context context, String[] songs, String[] singers, int[] images) {
        super(context, R.layout.single_list, songs);

        this.c = context;
        this.songs = songs;
        this.singers = singers;
        this.images = images;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.single_list, parent, false);

        TextView textView1 = (TextView) customView.findViewById(R.id.textView1);
        TextView textView2 = (TextView) customView.findViewById(R.id.textView2);
        ImageView imageView = (ImageView) customView.findViewById(R.id.imageView);

        textView1.setText(songs[position]);
        textView2.setText(singers[position]);
        imageView.setImageResource(images[position]);

        return customView;
    }
}
