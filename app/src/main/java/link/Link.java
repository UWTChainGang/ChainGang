package link;

import java.io.Serializable;

/**
 * Creating a link between chains
 */
public  class Link implements Serializable {

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

    public boolean ismIsCompleted() {
        return mIsCompleted;
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