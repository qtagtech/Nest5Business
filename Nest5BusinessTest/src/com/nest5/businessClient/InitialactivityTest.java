package com.nest5.businessClient;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.nest5.businessClient.InitialactivityTest \
 * com.nest5.businessClient.tests/android.test.InstrumentationTestRunner
 */
public class InitialactivityTest extends ActivityInstrumentationTestCase2<Initialactivity> {

    private Solo solo;

    public InitialactivityTest() {
        super(Initialactivity.class);
    }

    @Override
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    public void testAddNote() throws Exception {
        solo.assertCurrentActivity("Expected InitialActivity activity", "Initialactivity");
        solo.clickOnButton("Pagar");
        solo.waitForFragmentById(com.nest5.businessClient.R.layout.order_form);
        //Takes a screenshot and saves it in "/sdcard/Robotium-Screenshots/".
        solo.takeScreenshot();
        boolean btnsFound = solo.searchText("Domicilio") && solo.searchText("Llevar");
        //Assertt Note 1 & Note 2 are found
        assertTrue("Botones domicilio y llevar encontrados", btnsFound);

    }

}
