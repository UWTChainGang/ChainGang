
/*
 * ChainDB.java
 *
 * ChainGang <A hub for managing your complex processes>
 * Class to handle SQLite database for storing data, retrieved from a server
 *
 * Author: Michael Quandt
 * Author: James E Johnston
 * Author: Denis Yakovlev
 * Version: 20 May 2017
 */

package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import chain.Chain;
import edu.tacoma.uw.css.uwtchaingang.chaingang.R;

/**
 * Class to handle SQLite database for storing data, retrieved from a server
 *
 * @author Michael Quandt
 * @author James E Johnston
 * @author Denis Yakovlev
 * @version 20 May 2017
 */
public class ChainDB {

    /**
     * Database version
     */
    public static final int DB_VERSION = 1;

    /**
     * Database name
     */
    public static final String DB_NAME = "Chain.db";

    /**
     * DB helper variable of the inner class ChainDBHelper
     */
    private ChainDBHelper mChainDBHelper;

    /**
     * BD variable
     */
    private SQLiteDatabase mSQliteDatabase;

    private static final String CHAIN_TABLE = "Chain";

    /**
     * Class constructor
     *
     * @param context
     */
    public ChainDB(Context context) {
        mChainDBHelper = new ChainDBHelper(context, DB_NAME,null, DB_VERSION);
        mSQliteDatabase = mChainDBHelper.getWritableDatabase();
    }

    /**
     * Inserts the chain into the local SQLite table.
     * Returns true if successful, false otherwise.
     *
     * @param _id
     * @param chainTitle
     * @param chainDesc
     * @param warden
     * @param member
    //     * @param isChainCompleted
    //     * @param dtg_Created
    //     * @param dtg_LastUpdated
    //     * @param linkArray
     * @return
     */
  /*
    public boolean insertChain(String _id,
                               String chainTitle,
                               String chainDesc,
                               String warden,
                               String member,
                               int isChainCompleted,
                               String dtg_Created,
                               String dtg_LastUpdated,
                               String linkArray) {
*/
    public boolean insertChain(String _id,
                               String chainTitle,
                               String chainDesc,
                               String warden,
                               String member) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", _id);
        contentValues.put("chainTitle", chainTitle);
        contentValues.put("chainDesc", chainDesc);
        contentValues.put("warden", warden);
        contentValues.put("member", member);
/*
        contentValues.put("isChainCompleted", isChainCompleted);
        contentValues.put("dtg_Created", dtg_Created);
        contentValues.put("dtg_LastUpdated", dtg_LastUpdated);
        contentValues.put("linkArray", linkArray);
*/

        long rowId = mSQliteDatabase.insert("Chain", null, contentValues);
        return rowId != -1;
    }

    /**
     * Delete all data from the CHAIN_TABLE
     */
    public void deleteChains() {
        mSQliteDatabase.delete(CHAIN_TABLE, null, null);
    }


    /**
     * Inner class to handle data manipulation in the SQLite DB
     */
    class ChainDBHelper extends SQLiteOpenHelper {

        /**
         * Constant to create SQLite table
         */
        private final String CREATE_CHAIN_SQL;

        /**
         * Constant to drop SQLite table
         */
        private final String DROP_CHAIN_SQL;

        /**
         * Inner class constructor
         *
         * @param context
         * @param name
         * @param factory
         * @param version
         */
        public ChainDBHelper(Context context,
                             String name,
                             SQLiteDatabase.CursorFactory factory,
                             int version) {

            super(context, name, factory, version);
            CREATE_CHAIN_SQL = context.getString(R.string.CREATE_CHAIN_SQL);
            DROP_CHAIN_SQL = "DROP TABLE IF EXISTS Chain";

        }

        /**
         * Create a SQLite table
         *
         * @param sqLiteDatabase
         */
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_CHAIN_SQL);
        }

        /**
         * Update data in the SQLite DB
         *
         * @param sqLiteDatabase
         * @param i
         * @param i1
         */
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_CHAIN_SQL);
            onCreate(sqLiteDatabase);
        }

    }

    /**
     * Returns the list of chains from the local Chain table.
     *
     * @return list.
     */
    public List<Chain> getChains() {

        String[] columns = {
                "_id",
                "chainTitle",
                "chainDesc",
                "warden",
                "member"
        };

        Cursor c = mSQliteDatabase.query(
                CHAIN_TABLE,       // The table to query
                columns,           // The columns to return
                null,       // The columns for the WHERE clause
                null,   // The values for the WHERE clause
                null,       // Don't group the rows
                null,        // Don't filter by row groups
                null        // The sort order
        );

        c.moveToFirst();

        List<Chain> list = new ArrayList<Chain>();

        for (int i = 0; i < c.getCount(); i++) {

            String _id = c.getString(0);
            String chainTitle = c.getString(1);
            String chainDesc = c.getString(2);
            String warden = c.getString(3);
            String member = c.getString(4);

            Chain chain = new Chain(_id, chainTitle, chainDesc, warden, member);
            list.add(chain);
            c.moveToNext();
        }

        return list;
    }

}
