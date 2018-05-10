package member;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class Member {

    public static final String _memberID = "_id";
    public static final String STATUS = "STATUS";
    public static final String USER_AUTHENTICATED = "USER_AUTHENTICATED";
    public static final String USER_DOES_NOT_EXIST = "USER_DOES_NOT_EXIST";
    public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
    public static final String SUCCESS = "SUCCESS";


    //    private String mChainID;
//    private String mFName;
//    private String mLName;
//    private String mPassword;
    private String mStatus;




    public Member(String mIsAuthorized) {
//        this.mChainID = mChainID;
//        this.mFName = mFName;
//        this.mLName = mLName;
//        this.mPassword = mPassword;
        this.mStatus = mIsAuthorized;
    }

    public static String parseMemberJSON(String memberJSON) throws JSONException {
        String memberStatus = Member.USER_DOES_NOT_EXIST;
        Log.i("Member","in Parse JSON");
        if (memberJSON != null) {
            JSONArray arr = new JSONArray(memberJSON);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                memberStatus = obj.getString(Member.STATUS);
            }
        }
        return memberStatus;
    }
    public static String parseMemberAddJSON(String memberJSON) throws JSONException {
        String memberStatus = Member.USER_DOES_NOT_EXIST;

        if (memberJSON != null) {
            JSONArray arr = new JSONArray(memberJSON);

            JSONObject obj = arr.getJSONObject(0);
            memberStatus = obj.getString("status");
        }
        return memberStatus;
    }


    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

}
