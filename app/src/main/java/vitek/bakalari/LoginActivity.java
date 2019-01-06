package vitek.bakalari;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;
    private EditText mSchoolURL;
    private Button mSubmitButton;
    private ProgressBar mProgressBar;
    private Context mContext = this;
    private TextInputLayout mPasswordInputLayout;
    private TextInputLayout mSchoolURLInputLayout;
    private TextInputLayout mUsernameInputLayout;
    private XmlPullParserFactory xmlPullParserFactory;
    private XmlPullParser xmlParser;
    private String mTokenTyp;
    private String mTokenIkod;
    private String mTokenSalt;
    private String mToken;
    private String mRealName;
    private String mType;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // link variables with views
        mSubmitButton = findViewById(R.id.login_button);
        mProgressBar = findViewById(R.id.progress_bar);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mSchoolURL = findViewById(R.id.school_url);
        mPasswordInputLayout = findViewById(R.id.password_input_layout);
        mSchoolURLInputLayout = findViewById(R.id.school_url_input_layout);
        mUsernameInputLayout = findViewById(R.id.username_input_layout);

        // make the progress bar white
        mProgressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ON "ADD ACCOUNT" BUTTON CLICKED

                // show progress bar, hide text, make button unclickable
                mProgressBar.setVisibility(View.VISIBLE);
                mSubmitButton.setTextColor(getResources().getColor(android.R.color.transparent));
                mSubmitButton.setClickable(false);

                // init request queue
                final RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                StringRequest TYSRequest = new StringRequest(Request.Method.GET, mSchoolURL.getText().toString() + "?gethx=" + mUsername.getText().toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><results><res>02</res></results>") || mUsername.getText().toString().equals("")) {
                            // WRONG USERNAME

                            mProgressBar.setVisibility(View.INVISIBLE);
                            mSubmitButton.setTextColor(mContext.getResources().getColor(android.R.color.white));
                            mSubmitButton.setClickable(true);
                            mPasswordInputLayout.setError(null);
                            mSchoolURLInputLayout.setError(null);
                            mPasswordInputLayout.setErrorEnabled(false);
                            mSchoolURLInputLayout.setErrorEnabled(false);
                            mUsernameInputLayout.setErrorEnabled(true);
                            mUsernameInputLayout.setError(getString(R.string.error_wrong_username));
                        } else {
                            // RIGHT USERNAME

                            // parse xml for salt and calculate token
                            try {
                                xmlPullParserFactory = XmlPullParserFactory.newInstance();
                                xmlParser = xmlPullParserFactory.newPullParser();
                                xmlParser.setInput(new StringReader(response));

                                int event = xmlParser.getEventType();
                                while (event != xmlParser.END_DOCUMENT) {
                                    String name = xmlParser.getName();
                                    if (event == XmlPullParser.START_TAG) switch (name) {
                                        case "typ":
                                            mTokenTyp = xmlParser.nextText();
                                            break;
                                        case "ikod":
                                            mTokenIkod = xmlParser.nextText();
                                            break;
                                        case "salt":
                                            mTokenSalt = xmlParser.nextText();
                                            break;
                                    }
                                    event = xmlParser.next();
                                }

                            } catch (XmlPullParserException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            mToken = TokenCalculator.calculate(mTokenTyp, mTokenIkod, mTokenSalt, mPassword.getText().toString(), mUsername.getText().toString());

                            StringRequest loginRequest = new StringRequest(Request.Method.GET, mSchoolURL.getText().toString() + "?hx=" + mToken + "&pm=login", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    // ON LOGIN RESPONSE

                                    if (response.equals("<?xml version=\"1.0\" encoding=\"windows-1250\"?><results><result>-1</result><message>Chyba přihlášení</message></results>") || mPassword.getText().toString().equals("")) {
                                        // WRONG PASSWORD

                                        mProgressBar.setVisibility(View.INVISIBLE);
                                        mSubmitButton.setTextColor(mContext.getResources().getColor(android.R.color.white));
                                        mSubmitButton.setClickable(true);
                                        mSchoolURLInputLayout.setError(null);
                                        mUsernameInputLayout.setError(null);
                                        mSchoolURLInputLayout.setErrorEnabled(false);
                                        mUsernameInputLayout.setErrorEnabled(false);
                                        mPasswordInputLayout.setErrorEnabled(true);
                                        mPasswordInputLayout.setError(getText(R.string.error_wrong_password));
                                    } else {
                                        // RIGHT PASSWORD

                                        try {
                                            xmlParser.setInput(new StringReader(response));

                                            int event = xmlParser.getEventType();
                                            while (event != xmlParser.END_DOCUMENT) {
                                                String name = xmlParser.getName();
                                                if (event == XmlPullParser.START_TAG)
                                                    switch (name) {
                                                        case "strtyp":
                                                            mType = xmlParser.nextText();
                                                            break;
                                                        case "jmeno":
                                                            mRealName = xmlParser.nextText();
                                                            break;
                                                    }
                                                event = xmlParser.next();
                                            }

                                        } catch (XmlPullParserException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        saveParsedData(mRealName, mType);
                                        finish();
                                    }
                                }
                            }, null);
                            requestQueue.add(loginRequest);
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
                        mUsernameInputLayout.setError(null);
                        mPasswordInputLayout.setErrorEnabled(false);
                        mUsernameInputLayout.setErrorEnabled(false);
                        mSchoolURLInputLayout.setErrorEnabled(true);
                        mSchoolURLInputLayout.setError(getString(R.string.error_wrong_url));
                    }
                });
                requestQueue.add(TYSRequest);
            }
        });

        mSchoolURL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (mSchoolURL.getRight() - mSchoolURL.getCompoundDrawables()[2].getBounds().width())) {

                        // ON URL HELP CLICKED

                        Toast.makeText(mContext, "This feature is coming soon.", Toast.LENGTH_LONG).show();

                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void saveParsedData(String name, String type) {

        // activity result
        Intent resultIntent = new Intent();

        resultIntent.putExtra("username", mUsername.getText().toString());
        resultIntent.putExtra("password", mPassword.getText().toString());
        resultIntent.putExtra("schoolURL", mSchoolURL.getText().toString());
        resultIntent.putExtra("realName", name);
        if (type.equals("žák")) {
            resultIntent.putExtra("isParent", false);
        } else resultIntent.putExtra("isParent", true);

        ((Activity) mContext).setResult(Activity.RESULT_OK, resultIntent);


        // shared preferences
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        int accountCount = sharedPreferences.getInt("account_count", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("account_count", accountCount + 1);
        editor.putString("account_" + accountCount + "_username", mUsername.getText().toString());
        editor.putString("account_" + accountCount + "_password", mPassword.getText().toString());
        editor.putString("account_" + accountCount + "_school", mSchoolURL.getText().toString());
        editor.putString("account_" + accountCount + "_real_name", name);
        if (type.equals("žák")) {
            editor.putBoolean("account_" + accountCount + "_is_parent", false);
        } else editor.putBoolean("account_" + accountCount + "_is_parent", true);
        editor.apply();

    }

}
