package vitek.bakalari.SelectAccount;

public class Account {
    private String mPassword;
    private String mUserName;
    private String mRealName;
    private boolean mType;
    private String mSchoolURL;

    public Account(String password, String userName, String realName, boolean type, String schoolURL) {
        mPassword = password;
        mUserName = userName;
        mRealName = realName;
        mType = type;
        mSchoolURL = schoolURL;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getRealName() {
        return mRealName;
    }

    public void setRealName(String realName) {
        this.mRealName = realName;
    }

    public boolean getType() {
        return mType;
    }

    public void setType(boolean type) {
        this.mType = type;
    }

    public String getSchoolURL() {
        return mSchoolURL;
    }

    public void setSchoolURL(String schoolURL) {
        this.mSchoolURL = schoolURL;
    }
}
