
/*
 * MemberTest.java
 *
 * ChainGang <A hub for managing your complex processes>
 * Class to test the Member class by using JUnit
 *
 * Author: Michael Quandt
 * Author: James E Johnston
 * Author: Denis Yakovlev
 * Version: 23 May 2017
 */

package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.util.Log;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import member.Member;

/**
 * Class to test the Member class by using JUnit
 *
 * @author Michael Quandt
 * @author James E Johnston
 * @author Denis Yakovlev
 * @version 20 May 2017
 */
public class MemberTest {


    /**
     * Test Member class constructor for null
     */
    @Test
    public void testMemberConstructor() {
        assertNotNull(new Member("USER_ALREADY_EXISTS"));
    } // end testMemberConstructor


    /**
     * Test Member class parseMemberJSON for the valid input
     */
    /*
    @Test
    public void testParseMemberJSON() {

        try {

            Member.parseMemberJSON("[{\"STATUS\":\"SUCCESS\"}]");

            // since the exception was not thrown - the code does not catch wrong status value
            fail("Should not reach here, Setting status with invalid value");

        } catch (JSONException e) {
        }

    }
*/

    /**
     * Test Member class getmStatus() method for returning null
     */
    @Test
    public void testGetmStatus() {

        Member testMember = new Member("SUCCESS");
        assertNotNull(testMember.getmStatus());

    }


    /**
     * Test Member class setmStatus() method
     */
    @Test
    public void testSetmStatus() {

        try {

            Member testMember = new Member("SUCCESS");
            testMember.setmStatus("USER_DOES_NOT_NOT_NOT_EXIST");

            // since the exception was not thrown - the code does not catch wrong status value
            fail("Should not reach here, Setting status with invalid value");

        } catch (IllegalArgumentException e) {
        }

    }


    /**
     * Test MEmber isStatusNameValid method
     */
    @Test
    public void testIsStatusNameValid() {

        assertFalse("Member status sent with null was not caught"
                , Member.isStatusNameValid(null));
        assertFalse("Member status sent with the not exists status value was not caught"
                , Member.isStatusNameValid("qwerty"));
        assertTrue("Correct input USER_AUTHENTICATED gives wrong response (False)"
                , Member.isStatusNameValid("USER_AUTHENTICATED"));
        assertTrue("Correct input USER_DOES_NOT_EXIST gives wrong response (False)"
                , Member.isStatusNameValid("USER_DOES_NOT_EXIST"));
        assertTrue("Correct input USER_ALREADY_EXISTS gives wrong response (False)"
                , Member.isStatusNameValid("USER_ALREADY_EXISTS"));
        assertTrue("Correct input SUCCESS gives wrong response (False)"
                , Member.isStatusNameValid("SUCCESS"));

    }


    /**
     * Test MEmber isParseMemberJSONArgumentValid method
     */
    @Test
    public void testIsParseMemberJSONArgumentValid() {

        assertFalse("Member status sent with null was not caught"
                , Member.isParseMemberJSONArgumentValid(null));
        assertFalse("Member status sent with the not exists status value was not caught"
                , Member.isParseMemberJSONArgumentValid("qwerty"));
        assertTrue("Correct input USER_AUTHENTICATED gives wrong response (False)"
                , Member.isParseMemberJSONArgumentValid("[{\"STATUS\":\"USER_AUTHENTICATED\"}]"));
        assertTrue("Correct input USER_DOES_NOT_EXIST gives wrong response (False)"
                , Member.isParseMemberJSONArgumentValid("[{\"STATUS\":\"USER_DOES_NOT_EXIST\"}]"));
        assertTrue("Correct input USER_ALREADY_EXISTS gives wrong response (False)"
                , Member.isParseMemberJSONArgumentValid("[{\"STATUS\":\"USER_ALREADY_EXISTS\"}]"));
        assertTrue("Correct input SUCCESS gives wrong response (False)"
                , Member.isParseMemberJSONArgumentValid("[{\"STATUS\":\"SUCCESS\"}]"));

    }

}
