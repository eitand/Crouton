/*
 * Copyright 2012 Neofonie Mobile GmbH
 *	
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package de.neofonie.mobile.app.android.widget.crouton;

import android.app.Activity;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/*
 * Based on an article by Cyril Mottier (http://android.cyrilmottier.com/?p=773) <br>
 */

/**
 * Crouton <br>
 * <br>
 * 
 * Displays information in a non-invasive context related manner. Like
 * {@link Toast}, but better.
 * 
 * @author weiss@neofonie.de
 * 
 */
public final class Crouton {
	private Activity activity;
	private View view;
	private CharSequence text;
	private Style style;
	private int mTopMargin = 0;
	private int mGravity = Gravity.TOP;

	/**
	 * Creates the {@link Crouton}.
	 * 
	 * @param activity
	 *            The {@link Activity} that the {@link Crouton} should be
	 *            attached to.
	 * @param text
	 *            The text you want to display.
	 * @param style
	 *            The style that this {@link Crouton} should be created with.
	 */
	private Crouton(Activity activity, CharSequence text, Style style) {
		if (activity == null || text == null || style == null) {
			throw new IllegalArgumentException(
					"Null parameters are NOT accepted");
		}
		this.activity = activity;
		this.text = text;
		this.style = style;
	}

	// /////////////////////////////////////////////////////////////////////////
	// Public methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Creates a {@link Crouton} with provided text and style for a given
	 * activity.
	 * 
	 * @param activity
	 *            The {@link Activity} that the {@link Crouton} should be
	 *            attached to.
	 * @param text
	 *            The text you want to display.
	 * @param style
	 *            The style that this {@link Crouton} should be created with.
	 * @return The created {@link Crouton}.
	 */

	public static Crouton makeText(Activity activity, CharSequence text,
			Style style) {
		return new Crouton(activity, text, style);
	}

	/**
	 * Creates a {@link Crouton} with provided text-resource and style for a
	 * given activity.
	 * 
	 * @param activity
	 *            The {@link Activity} that the {@link Crouton} should be
	 *            attached to.
	 * @param resId
	 *            The resource id of the text you want to display.
	 * @param style
	 *            The style that this {@link Crouton} should be created with.
	 * @return The created {@link Crouton}.
	 */
	public static Crouton makeText(Activity activity, int resId, Style style) {
		return makeText(activity, activity.getString(resId), style);
	}

	/**
	 * Cancels all queued {@link Crouton}s. If there is a {@link Crouton}
	 * displayed currently, it will be the last one displayed.
	 */
	public static void cancelAllCroutons() {
		Manager.getInstance().clearCroutonQueue();
	}

	/**
	 * Displays the {@link Crouton}. If there's another {@link Crouton} visible
	 * at the time, this {@link Crouton} will be displayed afterwards.
	 */
	public void show() {
		Manager manager = Manager.getInstance();
		manager.add(this);
	}

	/**
	 * Cancels a {@link Crouton} immediately.
	 */
	private void cancel() {
		// TODO think about exporting after Manager#removeCroutonImmediately has
		// been implemented.
		Manager manager = Manager.getInstance();
		manager.removeCroutonImmediately(this);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Package private methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * @return <code>true</code> if the {@link Crouton} is being displayed, else
	 *         <code>false</code>.
	 */
	boolean isShowing() {
		return view != null && view.getParent() != null;
	}

	/**
	 * @return the activity
	 */
	Activity getActivity() {
		return activity;
	}

	/**
	 * @return the style
	 */
	Style getStyle() {
		return style;
	}

	/**
	 * @return the text
	 */
	CharSequence getText() {
		return text;
	}

	/**
	 * @return the view
	 */
	View getView() {
		return view;
	}

	/**
	 * @param view
	 *            the view to set
	 */
	void setView(View view) {
		this.view = view;
	}

	int getYMargin() 
	{
		return mTopMargin;
	}
	
	/**
	 * 
	 * @param margin margin from top in dp
	 */
	public void setTMargin(int margin)
	{
//		Resources r = activity.getResources();
//		mTopMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
		mTopMargin = margin;
	}

	int getGravity() 
	{
		return mGravity ;
	}
	
	public void setGravity(int gravity)
	{
		if (((gravity | Gravity.TOP) != 0 ) ||
			((gravity | Gravity.CENTER) != 0 ) ||
			((gravity | Gravity.BOTTOM) != 0 ))
		{
			mGravity = gravity;
		}
		else
		{
			mGravity = Gravity.TOP;
		}
	}

}
