package cga3798.tcss450.uw.edu.challengeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;

import org.json.JSONException;
import org.json.JSONObject;

import cga3798.tcss450.uw.edu.challengeapp.UserLoginFragment;
import cga3798.tcss450.uw.edu.challengeapp.model.Credentials;
import cga3798.tcss450.uw.edu.challengeapp.utils.SendPostAsyncTask;

public class MainActivity extends AppCompatActivity implements
        UserLoginFragment.OnLoginFragmentInteractionListener,
        UserRegistrationFragment.OnRegisterLoginFragmentInteractionListener,
        DisplayFragment.OnDisplayFragmentInteractionListener
{
    Credentials mCredentials;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            if (findViewById(R.id.fragmentContainer) != null) {
                SharedPreferences prefs =
                        getSharedPreferences(
                                getString(R.string.keys_shared_prefs),
                                Context.MODE_PRIVATE);

                if (prefs.getBoolean(getString(R.string.keys_prefs_stay_logged_in),
                        false)) {
                    loadSuccessFragment();
                } else {

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragmentContainer,
                                    new UserLoginFragment(),
                                    getString(R.string.keys_fragment_login))
                            .commit();
                }

            }
        }
    }


    @Override
    public void onLogin(Credentials credentials) {
////        Log.wtf("ACTIVITY", "User: " + creds.getUsername() +
//////                " Password: " + creds.getPassword());
////
////        DisplayFragment displayFragment;
////
////        displayFragment = (DisplayFragment) getSupportFragmentManager().findFragmentById(R.id.
////                userNameLog);
////
////        if(displayFragment != null) {
//////            displayFragment.updateContent(userName, password);
////        } else {
////            displayFragment = new DisplayFragment();
//////            Bundle args = new Bundle();
//////            args.putString("username", userName);
//////            args.putString("password", password);
//////            displayFragment.setArguments(args);
////            FragmentTransaction transaction = getSupportFragmentManager()
////                    .beginTransaction()
////                    .replace(R.id.fragmentContainer, displayFragment);
////
////            // Commit the transaction
////            transaction.commit();
////        }

        //build the web service URL
        Uri uri = new Uri.Builder()
                .scheme("https")
                .appendPath(getString(R.string.ep_base_url))
                .appendPath(getString(R.string.ep_login))
                .build();

        //build the JSONObject
        JSONObject msg = credentials.asJSONObject();

        mCredentials = credentials;

        //instantiate and execute the AsyncTask.
        //Feel free to add a handler for onPreExecution so that a progress bar
        //is displayed or maybe disable buttons. You would need a method in
        //LoginFragment to perform this.
        new SendPostAsyncTask.Builder(uri.toString(), msg)
                .onPostExecute(this::handleLoginOnPost)
                .onCancelled(this::handleErrorsInTask)
                .build().execute();


    }
    @Override
    public void onLogout() {
        SharedPreferences prefs =
                getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);

        prefs.edit().remove(getString(R.string.keys_prefs_username));

        prefs.edit().putBoolean(
                getString(R.string.keys_prefs_stay_logged_in),
                false)
                .apply();
        //the way to close an app programmaticaly
        finishAndRemoveTask();
    }
    @Override
    public void onRegister() {
        UserRegistrationFragment registerFragment;

        registerFragment = (UserRegistrationFragment) getSupportFragmentManager().
                findFragmentById(R.id.
                        userNameReg);

        if(registerFragment != null) {

        } else {
            registerFragment = new UserRegistrationFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, registerFragment)
                    .addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
    private void loadSuccessFragment() {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }




    @Override
    public void onRegisterLogin(Credentials credentials) {
////        Log.wtf("ACTIVITY", "User: " + userName +
////                " Password: " + password);
//
//        DisplayFragment displayFragment;
//
//        displayFragment = (DisplayFragment) getSupportFragmentManager().findFragmentById(R.id.
//                userNameReg);
//
//        if (displayFragment != null) {
////            displayFragment.updateContent(userName, password);
//        } else {
//            // in my case I get the support fragment manager, it should work with the native one too
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            // this will clear the back stack and displays no animation on the screen
//            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            displayFragment = new DisplayFragment();
////            Bundle args = new Bundle();
////            args.putString("username", userName);
////            args.putString("password", password);
////            displayFragment.setArguments(args);
//            FragmentTransaction transaction = getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragmentContainer, displayFragment);
//
//            // Commit the transaction
//            transaction.commit();
//
//        }


        //build the web service URL
        Uri uri = new Uri.Builder()
                .scheme("https")
                .appendPath(getString(R.string.ep_base_url))
                .appendPath(getString(R.string.ep_register))
                .build();

        //build the JSONObject
        JSONObject msg = credentials.asJSONObject();

        mCredentials = credentials;

        //instantiate and execute the AsyncTask.
        //Feel free to add a handler for onPreExecution so that a progress bar
        //is displayed or maybe disable buttons. You would need a method in
        //LoginFragment to perform this.
        new SendPostAsyncTask.Builder(uri.toString(), msg)
                .onPostExecute(this::handleRegisterOnPost)
                .onCancelled(this::handleErrorsInTask)
                .build().execute();
    }

    /**
     * Handle errors that may occur during the AsyncTask.
     * @param result the error message provide from the AsyncTask
     */
    private void handleErrorsInTask(String result) {
        Log.e("ASYNCT_TASK_ERROR",  result);
    }
    /**
     * Handle onPostExecute of the AsynceTask. The result from our webservice is
     * a JSON formatted String. Parse it for success or failure.
     * @param result the JSON formatted String response from the web service
     */
    private void handleLoginOnPost(String result) {
        try {
            JSONObject resultsJSON = new JSONObject(result);
            boolean success = resultsJSON.getBoolean("success");

            if (success) {
                //Login was successful. Switch to the loadSuccessFragment.
                checkStayLoggedIn();
                loadSuccessFragment();
            } else {
                //Login was unsuccessful. Don’t switch fragments and inform the user
                UserLoginFragment frag =
                        (UserLoginFragment) getSupportFragmentManager()
                                .findFragmentByTag(
                                        getString(R.string.keys_fragment_login));
                frag.setError("Log in unsuccessful");
            }

        } catch (JSONException e) {
            //It appears that the web service didn’t return a JSON formatted String
            //or it didn’t have what we expected in it.
            Log.e("JSON_PARSE_ERROR",  result
                    + System.lineSeparator()
                    + e.getMessage());
        }
    }

    private void handleRegisterOnPost(String result) {
        try {
            JSONObject resultsJSON = new JSONObject(result);
            boolean success = resultsJSON.getBoolean("success");

            if (success) {
                //Login was successful. Switch to the loadSuccessFragment.
                loadSuccessFragment();
            } else {
                //Login was unsuccessful. Don’t switch fragments and inform the user
                UserRegistrationFragment frag =
                        (UserRegistrationFragment) getSupportFragmentManager()
                                .findFragmentByTag(
                                        getString(R.string.keys_fragment_login));
                frag.setError("Log in unsuccessful");
            }

        } catch (JSONException e) {
            //It appears that the web service didn’t return a JSON formatted String
            //or it didn’t have what we expected in it.
            Log.e("JSON_PARSE_ERROR",  result
                    + System.lineSeparator()
                    + e.getMessage());
        }
    }



    private void checkStayLoggedIn() {
        if (((CheckBox) findViewById(R.id.logCheckBox)).isChecked()) {
            SharedPreferences prefs =
                    getSharedPreferences(
                            getString(R.string.keys_shared_prefs),
                            Context.MODE_PRIVATE);
            //save the username for later usage
            prefs.edit().putString(
                    getString(R.string.keys_prefs_username),
                    mCredentials.getUsername())
                    .apply();
            //save the users “want” to stay logged in
            prefs.edit().putBoolean(
                    getString(R.string.keys_prefs_stay_logged_in),
                    true)
                    .apply();
        }
    }


}
