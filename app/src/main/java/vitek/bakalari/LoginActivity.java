package vitek.bakalari;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class LoginActivity extends AppCompatActivity {
    private EditText mUserName;
    private EditText mPassword;
    private EditText mSchoolURL;
    private Button mSubmitButton;
    private ProgressBar mProgressBar;
    private ImageView mURLHelp;
    private Context mContext = this;
    private TextInputLayout mPasswordInputLayout;
    private TextInputLayout mSchoolURLInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // link variables with views
        mSubmitButton = findViewById(R.id.login_button);
        mURLHelp = findViewById(R.id.help);
        mProgressBar = findViewById(R.id.progress_bar);
        mUserName = findViewById(R.id.user_name);
        mPassword = findViewById(R.id.password);
        mSchoolURL = findViewById(R.id.school_url);
        mPasswordInputLayout = findViewById(R.id.password_input_layout);
        mSchoolURLInputLayout = findViewById(R.id.school_url_input_layout);

        // make the progress bar white
        mProgressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ON SUBMIT

                // show progress bar, hide text, make button not clickable
                mProgressBar.setVisibility(View.VISIBLE);
                mSubmitButton.setTextColor(getResources().getColor(android.R.color.transparent));
                mSubmitButton.setClickable(false);

                // init request queue
                final RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                Log.d("DEBUG", "(1) Sending TYS request on url: " + mSchoolURL.getText().toString() + "?gethx=" + mUserName.getText().toString());

                // TYP, IKOD, SALT REQUEST
                StringRequest TYSRequest = new StringRequest(Request.Method.GET, mSchoolURL.getText().toString() + "?gethx=" + mUserName.getText().toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // ON TYS RESPONSE

                        Log.d("DEBUG", "(2.1) TYS request response: \n" + response);
                        if (response.equals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><results><res>02</res></results>")) {
                            // WRONG USERNAME

                            Log.d("DEBUG", "(3.1) Username is wrong.");
                            mProgressBar.setVisibility(View.INVISIBLE);
                            mSubmitButton.setTextColor(mContext.getResources().getColor(android.R.color.white));
                            mSubmitButton.setClickable(true);
                            mSchoolURLInputLayout.setError(null);
                            ((ConstraintLayout.LayoutParams) mURLHelp.getLayoutParams()).setMargins(0, 8, 48, 0);
                            mPasswordInputLayout.setError("Špatný jméno");
                        } else {
                            // CALCULATE TOKEN

                            Log.d("DEBUG", "(3.2) Calculating token.");

                            // TODO: get typ, ikod and salt from xml
                            // String mToken = TokenCalculator.calculate("1", "2", "3");
                            String mToken = "h4LohADuPYBBvqkM0DsO9W8XuSo17srO1pnoff5vN6g4X4FcIZae-3CKjGKjPs4XwS5c2SG_kY2YEEFfO6lI4Q==";

                            // LOGIN REQUEST
                            StringRequest loginRequest = new StringRequest(Request.Method.GET, mSchoolURL.getText().toString() + "?hx=" + mToken + "&pm=login", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // ON LOGIN RESPONSE

                                    Log.d("DEBUG", "(5) Got login request response: \n" + response);

                                    if (response.equals("<?xml version=\"1.0\" encoding=\"windows-1250\"?><results><result>-1</result><message>Chyba přihlášení</message></results>")) {
                                        // WRONG PASSWORD

                                        Log.d("DEBUG", "(6.1) Password is wrong.");
                                        mProgressBar.setVisibility(View.INVISIBLE);
                                        mSubmitButton.setTextColor(mContext.getResources().getColor(android.R.color.white));
                                        mSubmitButton.setClickable(true);
                                        mSchoolURLInputLayout.setError(null);
                                        ((ConstraintLayout.LayoutParams) mURLHelp.getLayoutParams()).setMargins(0, 8, 48, 0);
                                        mPasswordInputLayout.setError(mContext.getString(R.string.error_wrong_password));

                                    } else {
                                        // GET REAL DATA

                                        // TODO: get users real name and type
                                        Log.d("DEBUG", "(6.2) Got real data. Saving...");
                                        saveParsedData("Peterka Vítek, 9.A", "žák");
                                    }
                                }
                            }, null);
                            Log.d("DEBUG", "(4.1) Sending login request to url: " + mSchoolURL.getText().toString() + "?hx=" + mToken + "&pm=login");
                            requestQueue.add(loginRequest);
                            Log.d("DEBUG", "(4.2) Login request sent.");
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // WRONG URL

                        mProgressBar.setVisibility(View.INVISIBLE);
                        mSubmitButton.setTextColor(mContext.getResources().getColor(android.R.color.white));
                        mSubmitButton.setClickable(true);
                        mPasswordInputLayout.setError(null);
                        mSchoolURLInputLayout.setError(mContext.getString(R.string.error_not_bklr_url));
                        ((ConstraintLayout.LayoutParams) mURLHelp.getLayoutParams()).setMargins(0, 0, 56, 24);
                        Log.d("DEBUG", "(2.2) Cannot send TYS request. (Wrong URL?) Error: \n" + error.toString());
                    }
                });
                requestQueue.add(TYSRequest);
            }
        });

        mURLHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ON URL HELP

                Toast.makeText(mContext, "This feature is coming soon.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveParsedData(String name, String type) {
        // activity result
        Intent resultIntent = new Intent();

        resultIntent.putExtra("schoolURL", mSchoolURL.getText().toString());
        resultIntent.putExtra("realName", name);
        resultIntent.putExtra("username", mUserName.getText().toString());
        resultIntent.putExtra("password", mPassword.getText().toString());
        if (type.equals("žák")) {
            resultIntent.putExtra("isParent", false);
        } else resultIntent.putExtra("isParent", true);
        ((Activity) mContext).setResult(Activity.RESULT_OK, resultIntent);

        Log.d("DEBUG", "(7.1) Activity result ready. Saving to shared preferences...");


        // shared preferences
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        int accountCount = sharedPreferences.getInt("account_count", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("account_count", accountCount + 1);
        editor.putString("account_" + accountCount + "_username", mUserName.getText().toString());
        editor.putString("account_" + accountCount + "_password", mPassword.getText().toString());
        editor.putString("account_" + accountCount + "_school", mSchoolURL.getText().toString());
        editor.putString("account_" + accountCount + "_real_name", name);
        if (type.equals("žák")) {
            editor.putBoolean("account_" + accountCount + "_is_parent", false);
        } else editor.putBoolean("account_" + accountCount + "_is_parent", true);
        editor.apply();

        Log.d("DEBUG", "(7.2) Data saved to shared preferences, Finishing...");


        // saving finished
        ((Activity) mContext).finish();
    }

}
