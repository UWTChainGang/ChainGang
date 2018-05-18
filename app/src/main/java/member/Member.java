
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
     * Constant of negative response if a member already exists
     */
    public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";

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
    public static Member parseMemberJSON(String memberJSON) throws JSONException {
        String memberStatus = Member.USER_DOES_NOT_EXIST;
        Log.i("Member","in Parse JSON");
        if (memberJSON != null) {
            JSONArray arr = new JSONArray(memberJSON);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                memberStatus = obj.getString(Member.STATUS);
            }
        }
        Member member = new Member(memberStatus);
        return member;
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
