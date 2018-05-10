package member;

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

    public static Member parseMemberJSON(String chainJSON) throws JSONException {
        List<Member> memberList = new ArrayList<Member>();
        if (chainJSON != null) {
            JSONArray arr = new JSONArray(chainJSON);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Member member = new Member(obj.getString(Member.STATUS));

                memberList.add(member);
            }

        }

        return memberList.get(0);
    }


    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

}
