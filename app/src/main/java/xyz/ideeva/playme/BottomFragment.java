package xyz.ideeva.playme;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class BottomFragment extends Fragment {

    String[] songs = {"Starboy (feat. Daft Punk)", "24K Magic", "Closer (feat. Halsey)", "Fake Love", "Treat You Better"};
    String[] singers = {"The Weeknd - 3:50", "Bruno Mars - 3:45", "The Chainsmokers - 4:04", "Drake - 3:27", "Shawn Mendes - 3:07"};
    int[] images = {R.drawable.photo1, R.drawable.photo2, R.drawable.photo3, R.drawable.photo4, R.drawable.photo5};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_fragment, container, false);

        CustomAdapter adapter = new CustomAdapter(view.getContext(), songs, singers, images);
        ListView list = (ListView) view.findViewById(R.id.listSection);
        list.setAdapter(adapter);

        return view;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
