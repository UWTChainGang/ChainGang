

package member;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
     * Constant of positive response if a member exists
     */
    public static final String USER_INVALID_PASSWORD = "USER_INVALID_PASSWORD";

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
        if (mIsAuthorized != null) {
            this.mStatus = mIsAuthorized;
        } else {
            throw new NullPointerException("Illegal Argument passed into Member class constructor");
        }
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
        if (memberJSON != null && isParseMemberJSONArgumentValid(memberJSON)) {

            JSONArray arr = new JSONArray(memberJSON);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                memberStatus = obj.getString(Member.STATUS);
            }

        } else {
            throw new IllegalArgumentException("JSON object from server is not valid");
        }

        if (isStatusNameValid(memberStatus)) {
            Member member = new Member(memberStatus);
            return member;
        } else {
            throw new IllegalArgumentException("The parsed from the JSON object value for Member " +
                    "status is not valid.");
        }
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

        if (isStatusNameValid(mStatus)) {
            this.mStatus = mStatus;
        } else {
            throw new IllegalArgumentException("The new given value to set for Member " +
                    "status is not valid.");
        }
    }

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
                || statusNameToVerify.equals(USER_INVALID_PASSWORD)
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

        return (memberJSONtoParse.contains("\"STATUS\":\"USER_AUTHENTICATED\"")
                || memberJSONtoParse.contains("\"STATUS\":\"USER_DOES_NOT_EXIST\"")
                || memberJSONtoParse.contains("\"STATUS\":\"USER_ALREADY_EXISTS\"")
                || memberJSONtoParse.contains("\"STATUS\":\"USER_INVALID_PASSWORD\"")
                || memberJSONtoParse.contains("\"STATUS\":\"SUCCESS\""));
    }

}
