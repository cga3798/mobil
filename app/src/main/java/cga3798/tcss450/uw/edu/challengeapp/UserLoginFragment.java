package cga3798.tcss450.uw.edu.challengeapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cga3798.tcss450.uw.edu.challengeapp.model.Credentials;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserLoginFragment extends Fragment implements View.OnClickListener {
    private OnLoginFragmentInteractionListener mListener;

    public UserLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_login, container, false);
        Button b = (Button) v.findViewById(R.id.loginButtonLoginFrag);
        b.setOnClickListener(this);
        b = (Button) v.findViewById(R.id.registerButtonLoginFrag);
        b.setOnClickListener(this);
        return v;
    }
    /**
     * Allows an external source to set an error message on this fragment. This may
     * be needed if an Activity includes processing that could cause login to fail.
     * @param err the error message to display.
     */
    public void setError(String err) {
        //Log in unsuccessful for reason: err. Try again.
        //you may want to add error stuffs for the user here.
        ((TextView) getView().findViewById(R.id.userNameLog))
                .setError("Login Unsuccessful");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentInteractionListener) {
            mListener = (OnLoginFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            switch (view.getId()) {
                case R.id.loginButtonLoginFrag:
                    EditText editText = (EditText) getActivity().findViewById(R.id.userNameLog);
                    if (editText.getText().toString().trim().length() == 0) {
                        editText.setError("Username may not be empty");
                    }

                    String userName = editText.getText().toString();
                    editText = (EditText) getActivity().findViewById(R.id.passLog);
                    if (editText.getText().toString().trim().length() == 0) {
                        editText.setError("password may not be empty");
                    }
                    Editable password = editText.getText();
                    if(!(userName.length() == 0 || password.length() == 0)) {
                        Credentials creds = new Credentials.Builder(userName, password).build();
                        mListener.onLogin(creds);
                    }
                    break;
                case R.id.registerButtonLoginFrag:
                    mListener.onRegister();

                default:
                    Log.wtf("", "Didn't expect to see me...");
            }
        }

    }

    public interface OnLoginFragmentInteractionListener {
        // TODO: Update argument type and name
        void onLogin(Credentials creds);
        void onRegister();
    }

}