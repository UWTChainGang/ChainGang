package chain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chain implements Serializable {
    public static final String _CHAINID = "_id";
    public static final String CHAINTITLE = "chainTitle";
    public static final String CHAINDESC = "chainDesc";
    public static final String WARDENID = "warden";
    public static final String MEMBERID = "member";
    public static final String LINKSTRING = "links";
    //public static final String ISCHAINCOMPLETE = "isChainCompleted";

    private static final String LINK_ID = "link_id";
    private static final String LINK_TEXT = "link_text";
    private static final String LINK_INST = "link_instructions";
    private static final String IS_COMPLETED = "isCompleted";


    private String mChainID;
    private String mChainTitle;
    private String mChainDesc;
    private String mWardenID;
    private String mMemberID;
    //private boolean mIsChainComplete;
    private ArrayList<Link> mchainsInLink = new ArrayList<>();


    public Chain(String theChainID, String theChainTitle, String theChainDescription, String theWardenID,
                        String theMemberID) {

        setmChainID(theChainID);
        setmChainTitle(theChainTitle);
        setmChainDesc(theChainDescription);
        setmWardenID(theWardenID);
        setmMemberID(theMemberID);
        //mIsChainComplete = theChainComplete;

    }

    public static List<Chain> parseChainJSON(String chainJSON) throws JSONException {
        List<Chain> chainList = new ArrayList<Chain>();
        if (chainJSON != null) {
            JSONArray arr = new JSONArray(chainJSON);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Chain chaincontent = new Chain(obj.getString(Chain._CHAINID),
                        obj.getString(Chain.CHAINTITLE), obj.getString(Chain.CHAINDESC),
                        obj.getString(Chain.WARDENID), obj.getString(Chain.MEMBERID));

                JSONArray linkArray = obj.getJSONArray(LINKSTRING);
                if (linkArray != null) {
                    for (int j = 0; j < linkArray.length(); j++) {
                        JSONObject linkobj = linkArray.getJSONObject(j);
                        Link newLink = new Link(linkobj.getString(Chain.LINK_ID),
                                linkobj.getString(Chain.LINK_TEXT),
                                linkobj.getString(Chain.LINK_INST),
                                linkobj.getBoolean(Chain.IS_COMPLETED));

                        chaincontent.addLink(newLink);


                    }


                }

                chainList.add(chaincontent);
            }

        }

        return chainList;
    }


    public static class Link {


        private String mLinkID;
        private String mLinkText;
        private String mLinkInst;
        private boolean mIsCompleted;


        public Link(String theLinkID, String theLinkText, String theLinkInstructions, boolean theIsCompleted) {

            setmLinkID(theLinkID);
            setmLinkText(theLinkText);
            setmLinkInst(theLinkInstructions);
            mIsCompleted = theIsCompleted;


        }

        public String getLinkId() {
            return getLINK_ID();
        }

        public String getLINK_ID() {
            return LINK_ID;
        }

        public String getLINK_TEXT() {
            return LINK_TEXT;
        }

        public String getLINK_INST() {
            return LINK_INST;
        }

        public String getIS_COMPLETED() {
            return IS_COMPLETED;
        }

        public String getmLinkID() {
            return mLinkID;
        }

        public void setmLinkID(String mLinkID) {
            this.mLinkID = mLinkID;
        }

        public String getmLinkText() {
            return mLinkText;
        }

        public void setmLinkText(String mLinkText) {
            this.mLinkText = mLinkText;
        }

        public String getmLinkInst() {
            return mLinkInst;
        }

        public void setmLinkInst(String mLinkInst) {
            this.mLinkInst = mLinkInst;
        }
    }


    public String getmChainID() {
        return mChainID;
    }

    public void setmChainID(String mChainID) {
        this.mChainID = mChainID;
    }

    public String getmChainTitle() {
        return mChainTitle;
    }

    public void setmChainTitle(String mChainTitle) {
        this.mChainTitle = mChainTitle;
    }

    public String getmChainDesc() {
        return mChainDesc;
    }

    public void setmChainDesc(String mChainDesc) {
        this.mChainDesc = mChainDesc;
    }

    public String getmWardenID() {
        return mWardenID;
    }

    public void setmWardenID(String mWardenID) {
        this.mWardenID = mWardenID;
    }

    public String getmMemberID() {
        return mMemberID;
    }

    public void setmMemberID(String mMemberID) {
        this.mMemberID = mMemberID;
    }

    public void addLink (Link theLinkToAdd) {
        this.mchainsInLink.add(theLinkToAdd);
    }

    public Link getLink (int x) {
        return this.mchainsInLink.get(x);
    }

}

