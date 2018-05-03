package cga3798.tcss450.uw.edu.challengeapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cga3798.tcss450.uw.edu.challengeapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment implements View.OnClickListener{
    private OnDisplayFragmentInteractionListener mListener;

    public DisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_display, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null) {
//            String user = getArguments().getString("username");
//            String pass = getArguments().getString("password");
//
//            updateContent(user, pass);

        }

        SharedPreferences prefs =
                getActivity().getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);

        if (prefs.getBoolean(getString(R.string.keys_prefs_stay_logged_in), false)) {
            getView().findViewById(R.id.successLogoutButton)
                    .setOnClickListener(v -> mListener.onLogout());
        } else {
            getView().findViewById(R.id.successLogoutButton).setVisibility(View.GONE);
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDisplayFragmentInteractionListener) {
            mListener = (OnDisplayFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRegisterFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

    }




    public void updateContent(String username, String password) {
//        TextView tv = getActivity().findViewById(R.id.username_view);
//        tv.setText(username);
//        tv = getActivity().findViewById(R.id.password_view);
//        tv.setText(password);


    }

    public interface OnDisplayFragmentInteractionListener {
        void onLogout();
    }
}
