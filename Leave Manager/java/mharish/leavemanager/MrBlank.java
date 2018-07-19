package mharish.leavemanager;
/**
 * Created on: 26-09-2016.
 * Author: Harish Mohan
 */

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MrBlank.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MrBlank#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MrBlank extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mr_blank, container, false);
    }


}
