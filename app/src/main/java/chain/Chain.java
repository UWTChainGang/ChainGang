
package chain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class provides functionality of a chain object
 */
public class Chain implements Serializable {

    /**
     * Chain ID constant
     */
    public static final String _CHAINID = "_id";

    /**
     * Chain title constant
     */
    public static final String CHAINTITLE = "chainTitle";

    /**
     * Chain description constant
     */
    public static final String CHAINDESC = "chainDesc";

    /**
     * Chain warden ID constant
     */
    public static final String WARDENID = "warden";

    /**
     * Chain member ID constant
     */
    public static final String MEMBERID = "member";

    /**
     * Chain link constant
     */
    public static final String LINKSTRING = "links";

    /**
     * Chain link ID constant
     */
    private static final String LINK_ID = "link_id";

    /**
     * Chain link text constant
     */
    private static final String LINK_TEXT = "link_text";

    /**
     * Chain link instruction constant
     */
    private static final String LINK_INST = "link_instructions";

    /**
     * Constant if a chain completed
     */
    private static final String IS_COMPLETED = "isCompleted";

    /**
     * Initial chain ID
     */
    private String mChainID;

    /**
     * Initial chain Title
     */
    private String mChainTitle;

    /**
     * Initial chain description
     */
    private String mChainDesc;

    /**
     * Initial warden ID
     */
    private String mWardenID;

    /**
     * Initial member ID
     */
    private String mMemberID;

    /**
     * Chain links
     */
    private ArrayList<Link> mchainsInLink = new ArrayList<>();

    /**
     * Class constructor
     *
     * @param theChainID given chain ID
     * @param theChainTitle given chain title
     * @param theChainDescription given chain description
     * @param theWardenID given warden ID
     * @param theMemberID given member ID
     */
    public Chain(String theChainID, String theChainTitle, String theChainDescription, String theWardenID,
                        String theMemberID) {

        setmChainID(theChainID);
        setmChainTitle(theChainTitle);
        setmChainDesc(theChainDescription);
        setmWardenID(theWardenID);
        setmMemberID(theMemberID);
        //mIsChainComplete = theChainComplete;

    }

    /**
     * Parsing the given JSON chain object and link chains
     *
     * @param chainJSON given JSON object
     * @return chain list
     * @throws JSONException complain about JSON object
     */
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

    /**
     * Creating a link between chains
     */
    public static class Link {

        /**
         * Initial link ID
         */
        private String mLinkID;

        /**
         * Initial link text
         */
        private String mLinkText;

        /**
         * Initial link instruction
         */
        private String mLinkInst;

        /**
         * Initial flag if it's the last chain
         */
        private boolean mIsCompleted;

        /**
         * Link class constructor
         *
         * @param theLinkID given link id
         * @param theLinkText given link text
         * @param theLinkInstructions given link instruction
         * @param theIsCompleted given flag if it's the last chain
         */
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

    /**
     * Add the given link to the link array
     *
     * @param theLinkToAdd the given link
     */
    public void addLink (Link theLinkToAdd) {
        this.mchainsInLink.add(theLinkToAdd);
    }

    /**
     * Return a link from link array
     *
     * @param x link index
     * @return found link
     */
    public Link getLink (int x) {
        return this.mchainsInLink.get(x);
    }

}

