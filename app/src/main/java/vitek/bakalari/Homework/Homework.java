package vitek.bakalari.Homework;

import android.os.Parcel;
import android.os.Parcelable;

public class Homework implements Parcelable {
    private int mIcon;
    private String mTitle;
    private String mSupportingText;
    private Boolean mIsDone;
    private Boolean mIsInArchive;
    private String mHandIn;
    private String mAssigned;

    public Homework(int icon, String title, String supportingText, Boolean isDone, Boolean isInArchive, String handIn, String assigned) {
        mIcon = icon;
        mTitle = title;
        mSupportingText = supportingText;
        mIsDone = isDone;
        mIsInArchive = isInArchive;
        mHandIn = handIn;
        mAssigned = assigned;
    }

    protected Homework(Parcel in) {
        mIcon = in.readInt();
        mTitle = in.readString();
        mSupportingText = in.readString();
        byte tmpMIsDone = in.readByte();
        mIsDone = tmpMIsDone == 0 ? null : tmpMIsDone == 1;
        byte tmpMIsInArchive = in.readByte();
        mIsInArchive = tmpMIsInArchive == 0 ? null : tmpMIsInArchive == 1;
        mHandIn = in.readString();
        mAssigned = in.readString();
    }

    public static final Creator<Homework> CREATOR = new Creator<Homework>() {
        @Override
        public Homework createFromParcel(Parcel in) {
            return new Homework(in);
        }

        @Override
        public Homework[] newArray(int size) {
            return new Homework[size];
        }
    };

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSupportingText() {
        return mSupportingText;
    }

    public void setSupportingText(String supportingText) {
        mSupportingText = supportingText;
    }

    public Boolean getDone() {
        return mIsDone;
    }

    public void setDone(Boolean done) {
        mIsDone = done;
    }

    public Boolean getInArchive() {
        return mIsInArchive;
    }

    public void setInArchive(Boolean inArchive) {
        mIsInArchive = inArchive;
    }

    public String getHandIn() {
        return mHandIn;
    }

    public void setHandIn(String handIn) {
        mHandIn = handIn;
    }

    public String getAssigned() {
        return mAssigned;
    }

    public void setAssigned(String assigned) {
        mAssigned = assigned;
    }

    public String toString(){
        return mTitle + " is done:" + mIsDone.toString() + " is archived:" + mIsInArchive.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mIcon);
        parcel.writeString(mTitle);
        parcel.writeString(mSupportingText);
        parcel.writeByte((byte) (mIsDone == null ? 0 : mIsDone ? 1 : 2));
        parcel.writeByte((byte) (mIsInArchive == null ? 0 : mIsInArchive ? 1 : 2));
        parcel.writeString(mHandIn);
        parcel.writeString(mAssigned);
    }
}
