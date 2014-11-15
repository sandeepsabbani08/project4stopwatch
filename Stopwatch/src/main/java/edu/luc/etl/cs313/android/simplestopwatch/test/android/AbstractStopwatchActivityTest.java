package edu.luc.etl.cs313.android.simplestopwatch.test.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import android.app.Activity;
import android.view.View;
import android.content.pm.ActivityInfo;
import android.widget.Button;
import android.widget.TextView;
import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.android.StopwatchAdapter;

import static edu.luc.etl.cs313.android.simplestopwatch.common.Constants.SEC_PER_MIN;

/**
 * Abstract GUI-level test superclass of several essential stopwatch scenarios.
 *
 * @author laufer
 *
 * TODO move this and the other tests to src/test once Android Studio supports
 * non-instrumentation unit tests properly.
 */
public abstract class AbstractStopwatchActivityTest {

	/**
	 * Verifies that the activity under test can be launched.
	 */
    @Test
	public void testActivityCheckTestCaseSetUpProperly() {
		assertNotNull("activity should be launched successfully", getActivity());
	}

    /**
     * Verifies the following scenario: time is 0.
     *
     * @throws Throwable
     */
/*    this test isn't used anywhere
    @Test
    public void testActivityScenarioInit() throws Throwable {
        getActivity().runOnUiThread(new Runnable() { @Override public void run() {
            assertEquals(0, getDisplayedValue());
        }});
    }*/

	/**
	 * Verifies the following scenario: time is 0, press button 5 times,
     * wait 4 seconds, expect time 3.
	 *
	 * @throws Throwable
	 */
    @Test
	public void testActivityScenarioRun() throws Throwable {
		getActivity().runOnUiThread(new Runnable() { @Override public void run() {
			assertEquals(0, getDisplayedValue());
			assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
            assertEquals(5, getDisplayedValue());
		}});
		Thread.sleep(4000); // <-- do not run this in the UI thread!
        runUiThreadTasks();
		getActivity().runOnUiThread(new Runnable() { @Override public void run() {
            //expect running state
            assertEquals(3, getDisplayedValue());
		}});
	}

	/**
	 * Verifies the following scenario: time is 0, press button 5 times, expect time 5,
	 * wait 4 seconds, expect time 3, press button, expect time 0.
	 *
	 * @throws Throwable
	 */
    @Test
	public void testActivityScenarioRunReset() throws Throwable {
		getActivity().runOnUiThread(new Runnable() { @Override public void run() {
			assertEquals(0, getDisplayedValue());
            assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
            assertEquals(5, getDisplayedValue());
		}});
		Thread.sleep(4000); // <-- do not run this in the UI thread!
        runUiThreadTasks();
		getActivity().runOnUiThread(new Runnable() { @Override public void run() {
            //expect running state
            assertEquals(3, getDisplayedValue());
			assertTrue(getButton().performClick());
		}});
        runUiThreadTasks();
		getActivity().runOnUiThread(new Runnable() { @Override public void run() {
			//expect stopped state
            assertEquals(0, getDisplayedValue());
		}});
	}

    /**
     * Verifies the following scenario: time is 0, press button 5 times,
     * wait 4 seconds, expect time 3, wait 3 seconds, expect time 0, indefinite beeping.
     *
     * @throws Throwable
     */
    @Test
    public void testActivityScenarioFullRun() throws Throwable {
        getActivity().runOnUiThread(new Runnable() { @Override public void run() {
            assertEquals(0, getDisplayedValue());
            assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
        }});
        Thread.sleep(4000); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        getActivity().runOnUiThread(new Runnable() { @Override public void run() {
            //expect running state
            assertEquals(3, getDisplayedValue());
        }});
        Thread.sleep(3000); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        getActivity().runOnUiThread(new Runnable() { @Override public void run() {
            //expect stopped state
            assertEquals(0, getDisplayedValue());
            //expect indefinite beeping
        }});
    }

    /**
     * Verifies the following scenario: time is 0, press button 5 times,
     * wait 7 seconds, expect time 0, indefinite beeping, press button, no beeping,
     * expect time 0.
     *
     * @throws Throwable
     */
    @Test
    public void testActivityBeepTest() throws Throwable {
        getActivity().runOnUiThread(new Runnable() { @Override public void run() {
            assertEquals(0, getDisplayedValue());
            assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
            assertTrue(getButton().performClick());
        }});
        Thread.sleep(7000); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        getActivity().runOnUiThread(new Runnable() { @Override public void run() {
            //expect stopped state
            assertEquals(0, getDisplayedValue());
            //expect indefinite beeping
            assertTrue(getButton().performClick());
            //expect no beeping
            assertEquals(0, getDisplayedValue());
        }});
    }

    //tests from ClickCounter
    // begin-method-testActivityScenarioInc
    @Test
    public void testActivityScenarioInc() {
        assertTrue(getResetButton().performClick());
        assertEquals(0, getDisplayedValue());
        assertTrue(getIncButton().isEnabled());
        assertFalse(getDecButton().isEnabled());
        assertTrue(getResetButton().isEnabled());
        assertTrue(getIncButton().performClick());
        assertEquals(1, getDisplayedValue());
        assertTrue(getIncButton().isEnabled());
        assertTrue(getDecButton().isEnabled());
        assertTrue(getResetButton().isEnabled());
        assertTrue(getResetButton().performClick());
        assertEquals(0, getDisplayedValue());
        assertTrue(getIncButton().isEnabled());
        assertFalse(getDecButton().isEnabled());
        assertTrue(getResetButton().isEnabled());
        assertTrue(getResetButton().performClick());
    }

    // begin-method-testActivityScenarioIncUntilFull
    @Test
    public void testActivityScenarioIncUntilFull() {
        assertTrue(getResetButton().performClick());
        assertEquals(0, getDisplayedValue());
        assertTrue(getIncButton().isEnabled());
        assertFalse(getDecButton().isEnabled());
        assertTrue(getResetButton().isEnabled());
        while (getIncButton().isEnabled()) {
            final int v = getDisplayedValue();
            assertTrue(getIncButton().performClick());
            assertEquals(v + 1, getDisplayedValue());
        }
        assertFalse(getIncButton().isEnabled());
        assertTrue(getDecButton().isEnabled());
        assertTrue(getResetButton().isEnabled());
        assertTrue(getResetButton().performClick());
    }

    // begin-method-testActivityScenarioRotation
    @Test
    public void testActivityScenarioRotation() {
        assertTrue(getResetButton().performClick());
        assertEquals(0, getDisplayedValue());
        assertTrue(getIncButton().performClick());
        assertTrue(getIncButton().performClick());
        assertTrue(getIncButton().performClick());
        assertEquals(3, getDisplayedValue());
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        assertEquals(3, getDisplayedValue());
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        assertEquals(3, getDisplayedValue());
        assertTrue(getResetButton().performClick());
    }


    // auxiliary methods for easy access to UI widgets

    protected abstract StopwatchAdapter getActivity();

	protected int tvToInt(final TextView t) {
		return Integer.parseInt(t.getText().toString().trim());
	}

	protected int getDisplayedValue() {
		final TextView ts = (TextView) getActivity().findViewById(R.id.seconds);
		final TextView tm = (TextView) getActivity().findViewById(R.id.minutes);
		return SEC_PER_MIN * tvToInt(tm) + tvToInt(ts);
	}

	protected Button getButton() {
		return (Button) getActivity().findViewById(R.id.startStop);
	}


    /**
     * Explicitly runs tasks scheduled to run on the UI thread in case this is required
     * by the testing framework, e.g., Robolectric.
     */
    protected void runUiThreadTasks() { }
}