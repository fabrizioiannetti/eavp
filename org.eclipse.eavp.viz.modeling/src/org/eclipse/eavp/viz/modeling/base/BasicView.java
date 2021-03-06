/*******************************************************************************
 * Copyright (c) 2015 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Robert Smith
 *******************************************************************************/
package org.eclipse.eavp.viz.modeling.base;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.eavp.viz.datastructures.VizObject.IManagedUpdateable;
import org.eclipse.eavp.viz.datastructures.VizObject.IManagedUpdateableListener;
import org.eclipse.eavp.viz.datastructures.VizObject.IVizUpdateableListener;
import org.eclipse.eavp.viz.datastructures.VizObject.SubscriptionType;
import org.eclipse.eavp.viz.datastructures.VizObject.UpdateableSubscriptionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A base implementation of IView.
 * 
 * @author Robert Smith
 */
@XmlRootElement(name = "BasicView")
public class BasicView
		implements IManagedUpdateableListener, IManagedUpdateable, IView {

	/**
	 * The list of listeners observing this object.
	 */
	@XmlTransient
	private ArrayList<IVizUpdateableListener> listeners;

	/**
	 * Logger for handling event messages and other information.
	 */
	@XmlTransient
	private static final Logger logger = LoggerFactory
			.getLogger(BasicController.class);

	/**
	 * The listeners registered for updates from this object.
	 */
	@XmlTransient
	protected UpdateableSubscriptionManager updateManager = new UpdateableSubscriptionManager(
			this);

	/**
	 * The default constructor.
	 */
	public BasicView() {
		// Initialize the class variables
		listeners = new ArrayList<IVizUpdateableListener>();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.eavp.viz.modeling.IView#getRepresentation()
	 */
	@Override
	public Representation getRepresentation() {
		// Nothing to do.
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.eavp.viz.modeling.IView#refresh(org.eclipse.eavp.viz.
	 * service.modeling.IMesh)
	 */
	@Override
	public void refresh(IMesh model) {
		// Nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.eavp.viz.service.datastructures.VizObject.IVizUpdateable#
	 * register(org.eclipse.eavp.viz.service.datastructures.VizObject.
	 * IVizUpdateableListener)
	 */
	@Override
	public void register(IManagedUpdateableListener listener) {
		updateManager.register(listener);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.eavp.viz.service.datastructures.VizObject.IVizUpdateable#
	 * unregister(org.eclipse.eavp.viz.service.datastructures.VizObject.
	 * IVizUpdateableListener)
	 */
	@Override
	public void unregister(IManagedUpdateableListener listener) {

		// Remove the listener from the list
		updateManager.unregister(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object otherObject) {

		// Check if the objects are the same
		if (this == otherObject) {
			return true;
		}

		// Check that the other object is an abstractview
		if (!(otherObject instanceof BasicView)) {
			return false;
		}

		Representation rep = getRepresentation();
		Representation otherRep = getRepresentation();

		// If otherRep exists while rep doesn't, the two are not equal
		if (rep == null) {
			if (otherRep != null) {
				return false;
			}
		}

		else {
			// If otherRep doesn't exist after rep did, the two are not equal
			if (otherRep == null) {
				return false;
			}

			// Check that the other object's representation is equal to this
			// one's.
			if (!getRepresentation()
					.equals(((IView) otherObject).getRepresentation())) {
				return false;
			}
		}

		// If all checks passed, the objects are equal
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.eavp.viz.service.datastructures.VizObject.
	 * IManagedVizUpdateableListener#update(org.eclipse.eavp.viz.service.
	 * datastructures.VizObject.IVizUpdateable,
	 * org.eclipse.eavp.viz.service.datastructures.VizObject.
	 * UpdateableSubscription[])
	 */
	@Override
	public void update(IManagedUpdateable component, SubscriptionType[] type) {

		// Pass the update to own listeners
		updateManager.notifyListeners(type);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() {

		// Create a new AbstractView and make it a copy of this
		IView clone = new BasicView();
		clone.copy(this);

		return clone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.eavp.viz.modeling.IView#copy(org.eclipse.eavp.viz.
	 * service.modeling.AbstractView)
	 */
	@Override
	public void copy(IView otherObject) {

		// Check that the source object is an IView, failing
		// silently if not and casting it if so
		if (!(otherObject instanceof BasicView)) {
			return;
		}
		BasicView castObject = (BasicView) otherObject;

		// Notify own listeners of the change
		SubscriptionType[] eventTypes = { SubscriptionType.PROPERTY };
		updateManager.notifyListeners(eventTypes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.eavp.viz.service.datastructures.VizObject.
	 * IManagedVizUpdateableListener#getSubscriptions(org.eclipse.eavp.viz.
	 * service.datastructures.VizObject.IVizUpdateable)
	 */
	@Override
	public ArrayList<SubscriptionType> getSubscriptions(
			IManagedUpdateable source) {
		ArrayList<SubscriptionType> types = new ArrayList<SubscriptionType>();
		types.add(SubscriptionType.ALL);
		return types;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 9;
		return hash;
	}
}
