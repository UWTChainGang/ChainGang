
package chain;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import link.Link;


/**
 * Class provides functionality of a chain object
 */
public class Chain implements Serializable {

    public static final String CHAIN_CLASS ="CHAIN CLASS";
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
     * @param theMemberID given member ID
     */
    public Chain(String theChainID, String theChainTitle, String theChainDescription,
                        String theMemberID) {

        setmChainID(theChainID);
        setmChainTitle(theChainTitle);
        setmChainDesc(theChainDescription);

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
                        obj.getString(Chain.MEMBERID));

                JSONArray linkArray = obj.getJSONArray(LINKSTRING);
                if (linkArray != null) {
                    for (int j = 0; j < linkArray.length(); j++) {
                        JSONObject linkobj = linkArray.getJSONObject(j);
                        Link newLink = new Link(linkobj.getInt(Chain.LINK_ID),

                                linkobj.getString(Chain.LINK_TEXT),
                                linkobj.getString(Chain.LINK_INST),
                                linkobj.getBoolean(Chain.IS_COMPLETED),
                                linkobj.getString(Link.EXT_URL),
                                linkobj.getString(Link.EXT_URL_NAME));
                        Log.i(CHAIN_CLASS, newLink.getmLinkInst());
                        chaincontent.addLink(newLink);


                    }

                    //chaincontent.getLink(0).setmIsCompleted(true);
                }

                chainList.add(chaincontent);
            }

        }

        return chainList;
    }


    public ArrayList<Link> getMchainsInLink() {
        return mchainsInLink;
    }
    public void setMchainsInLink(ArrayList<Link> theLinks) {this.mchainsInLink = theLinks; }

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


    public int getChainLength() {
        return this.mchainsInLink.size();
    }

}

