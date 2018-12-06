package vitek.bakalari;

import android.os.AsyncTask;

public class DataDownloader extends AsyncTask {
    String mPassword;
    String mSchoolURL;
    String mUserName;

    public DataDownloader(String password, String schoolURL, String userName) {
        mPassword = password;
        mSchoolURL = schoolURL;
        mUserName = userName;
    }

    @Override
    protected Object doInBackground(Object... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
