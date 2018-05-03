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

import cga3798.tcss450.uw.edu.challengeapp.R;
import cga3798.tcss450.uw.edu.challengeapp.model.Credentials;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserRegistrationFragment extends Fragment implements View.OnClickListener{
    private UserRegistrationFragment.OnRegisterLoginFragmentInteractionListener mListener;

    public UserRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_registration, container, false);
        Button b = (Button) v.findViewById(R.id.register);
        b.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            switch (view.getId()) {
                case R.id.register:

                    EditText editText = (EditText) getActivity().findViewById(R.id.userNameReg);
                    String userName = editText.getText().toString();
                    if (editText.getText().toString().trim().length() == 0) {
                        editText.setError("Username may not be empty");
                    }

                    editText = (EditText) getActivity().findViewById(R.id.pass1Reg);
                    String password1 = editText.getText().toString();
                    if (editText.getText().toString().trim().length() == 0) {
                        editText.setError("Password may not be empty");
                    }
                    editText = (EditText) getActivity().findViewById(R.id.firstNameReg);
                    String firstName = editText.getText().toString();
                    if (editText.getText().toString().trim().length() == 0) {
                        editText.setError("First name may not be empty");
                    }
                    editText = (EditText) getActivity().findViewById(R.id.lastNameReg);
                    String lastName = editText.getText().toString();
                    if (editText.getText().toString().trim().length() == 0) {
                        editText.setError("Last name may not be empty");
                    }
                    editText = (EditText) getActivity().findViewById(R.id.emailReg);
                    String email = editText.getText().toString();
                    if (editText.getText().toString().trim().length() == 0) {
                        editText.setError("Email may not be empty");
                    }

                    editText = (EditText) getActivity().findViewById(R.id.pass2Reg);
                    Editable password2 = editText.getText();
                    String passwordTest = editText.getText().toString();
                    if (editText.getText().toString().trim().length() == 0) {
                        editText.setError("password may not be empty");
                    }

                    Log.wtf("ACTIVITY", "User: " + userName +
                            " Password: " + password1 + "pass2:" + password2);

                    if(!(userName.length() == 0 || password1.length() == 0 ||
                            password2.length() == 0 || firstName.length() == 0 ||
                            lastName.length() == 0 || email.length() == 0) ) {
                        if (!password1.equals(passwordTest)) {
                            editText.setError("Passwords must be equal");
                        } else{
                            Credentials creds = new Credentials.Builder(userName, password2)
                                    .addEmail(email).addFirstName(firstName)
                                    .addLastName(lastName).build();

                            mListener.onRegisterLogin(creds);
                        }

                    }
                    break;

                default:
                    Log.wtf("", "Didn't expect to see me...");
            }
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UserRegistrationFragment.OnRegisterLoginFragmentInteractionListener) {
            mListener = (UserRegistrationFragment.OnRegisterLoginFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    /**
     * Allows an external source to set an error message on this fragment. This may
     * be needed if an Activity includes processing that could cause login to fail.
     * @param err the error message to display.
     */
    public void setError(String err) {
        //Log in unsuccessful for reason: err. Try again.
        //you may want to add error stuffs for the user here.
        ((TextView) getView().findViewById(R.id.userNameReg))
                .setError("Login Unsuccessful");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnRegisterLoginFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRegisterLogin(Credentials creds);
    }
}