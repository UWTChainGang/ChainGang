
/*
 * Member.java
 *
 * ChainGang <A hub for managing your complex processes>
 * Member class to handle a member status. Class takes care for parsing info about a member from/to
 * a JSON object.
 *
 * Author: Michael Quandt
 * Author: James E Johnston
 * Author: Denis Yakovlev
 * Version: 23 May 2017
 */

package member;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Member class to handle a member status. Class takes care for parsing info about a member from/to
 * a JSON object.
 *
 * @author Michael Quandt
 * @author James E Johnston
 * @author Denis Yakovlev
 * @version 23 May 2017
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
     * Initial variable for the user status
     */
    private String mStatus;


    /**
     * Class constructor
     *
     * @param mIsAuthorized initial status
     */
    public Member(String mIsAuthorized) {
        // *********************
        // old
        // this.mStatus = mIsAuthorized;
        // *********************

        // *********************
        // new
        if (mIsAuthorized != null) {
            this.mStatus = mIsAuthorized;
        } else {
            throw new NullPointerException("Illegal Argument passed into Member class constructor");
        }
        // *********************
    }

    /**
     * Parsing status from the given JSON object
     *
     * @param memberJSON JSON object
     * @return member status
     * @throws JSONException complain about JSON object
     */
    public static Member parseMemberJSON(String memberJSON) throws JSONException {

        // *********************
        // old
        /*
        String memberStatus = Member.USER_DOES_NOT_EXIST;
        Log.i("Member","in Parse JSON");
        if (memberJSON != null) {
            JSONArray arr = new JSONArray(memberJSON);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                memberStatus = obj.getString(Member.STATUS);
            }
        }
        */

        // *********************
        // new - with memberJSON validation
        String memberStatus = Member.USER_DOES_NOT_EXIST;
        Log.i("Member","in Parse JSON");
/*
        Log.d("TEST", "**************  00000  ***************");
        Log.v("memberJSON value: ", memberJSON);
        boolean testValue = memberJSON != null && isParseMemberJSONArgumentValid(memberJSON);
        String res = "";
        if(testValue) {
            res = "True";
        } else {
            res = "False";
        }
        Log.v("boolean value: ", res);
        Log.d("TEST", "**************  00000  ***************");
*/

        if (memberJSON != null && isParseMemberJSONArgumentValid(memberJSON)) {

            JSONArray arr = new JSONArray(memberJSON);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                memberStatus = obj.getString(Member.STATUS);
            }

        } else {
//            throw new JSONException("JSON object from server is not valid");
            throw new IllegalArgumentException("JSON object from server is not valid");
        }
        // *********************



        // *********************
        // old
        // Member member = new Member(memberStatus);
        // return member;
        // *********************

        // *********************
        // new - with member status validation
        if (isStatusNameValid(memberStatus)) {
            Member member = new Member(memberStatus);
            return member;
        } else {
            throw new IllegalArgumentException("The parsed from the JSON object value for Member " +
                    "status is not valid.");
        }
        // *********************
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

        // *********************
        // old
        // this.mStatus = mStatus;
        // *********************

        // *********************
        // new
        if (isStatusNameValid(mStatus)) {
            this.mStatus = mStatus;
        } else {
            throw new IllegalArgumentException("The new given value to set for Member " +
                    "status is not valid.");
        }
        // *********************
    }


    // *********************
    // ADDED
    /**
     * Checks for appropriate value passed for the member status.
     *
     * @param statusNameToVerify - the given status value(String)
     * @return - true if passed value is valid, false otherwise
     */
    public static boolean isStatusNameValid(String statusNameToVerify) {

        if (statusNameToVerify == null) {
            return false;
        }

        return (statusNameToVerify.equals(USER_AUTHENTICATED)
                || statusNameToVerify.equals(USER_DOES_NOT_EXIST)
                || statusNameToVerify.equals(USER_ALREADY_EXISTS)
                || statusNameToVerify.equals(SUCCESS));
    }

    /**
     * Checks appropriate value in a passed JSON object
     *
     * @param memberJSONtoParse - the given JSON object to test
     * @return - true if valid value, false otherwise
     */
    public static boolean isParseMemberJSONArgumentValid(String memberJSONtoParse) {

        if (memberJSONtoParse == null) {
            return false;
        }

/*
        Log.d("TEST", "**************  111111  ***************");
        Log.v("memberJSONtoParse: ", memberJSONtoParse);
        boolean testValue = memberJSONtoParse.contains("\"STATUS\":\"USER_AUTHENTICATED\"")
                || memberJSONtoParse.contains("\"STATUS\":\"USER_DOES_NOT_EXIST\"")
                || memberJSONtoParse.contains("\"STATUS\":\"USER_ALREADY_EXISTS\"")
                || memberJSONtoParse.contains("\"STATUS\":\"SUCCESS\"");
        String resParseJSON = "";
        if(testValue) {
            resParseJSON = "True";
        } else {
            resParseJSON = "False";
        }
        Log.v("boolean value: ", resParseJSON);
        Log.d("TEST", "**************  111111  ***************");
*/

        return (memberJSONtoParse.contains("\"STATUS\":\"USER_AUTHENTICATED\"")
                || memberJSONtoParse.contains("\"STATUS\":\"USER_DOES_NOT_EXIST\"")
                || memberJSONtoParse.contains("\"STATUS\":\"USER_ALREADY_EXISTS\"")
                || memberJSONtoParse.contains("\"STATUS\":\"SUCCESS\""));
    }

    // *********************

}
