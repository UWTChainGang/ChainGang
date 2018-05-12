
package member;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Class takes care for parsing info about a member from/to a JSON object
 */
public class Member {

    /**
     * Member status constant
     */
    public static final String STATUS = "STATUS";

    /**
     * Constant of positive response if a member exists
     */
    public static final String USER_AUTHENTICATED = "USER_AUTHENTICATED";

    /**
     * Constant of negative response if a member does not exists
     */
    public static final String USER_DOES_NOT_EXIST = "USER_DOES_NOT_EXIST";

    /**
     * Response constant if a member was identified
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * Initial variable
     */
    private String mStatus;


    /**
     * Class constructor
     *
     * @param mIsAuthorized initial status
     */
    public Member(String mIsAuthorized) {
        this.mStatus = mIsAuthorized;
    }

    /**
     * Parsing status from the given JSON object
     *
     * @param memberJSON JSON object
     * @return member status
     * @throws JSONException complain about JSON object
     */
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

    /**
     * Parsing status to a JSON object
     *
     * @param memberJSON json object
     * @return member status
     * @throws JSONException complain about JSON object
     */
    public static String parseMemberAddJSON(String memberJSON) throws JSONException {
        String memberStatus = Member.USER_DOES_NOT_EXIST;

        if (memberJSON != null) {
            JSONArray arr = new JSONArray(memberJSON);

            JSONObject obj = arr.getJSONObject(0);
            memberStatus = obj.getString("status");
        }
        return memberStatus;
    }

    /**
     * Getter for the initial status
     *
     * @return initial status
     */
    public String getmStatus() {
        return mStatus;
    }

    /**
     * Setter for initial status
     *
     * @param mStatus new status
     */
    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

}
