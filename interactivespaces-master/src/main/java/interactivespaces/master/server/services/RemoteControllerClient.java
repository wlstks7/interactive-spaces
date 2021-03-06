/*
 * Copyright (C) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package interactivespaces.master.server.services;

import interactivespaces.domain.basic.LiveActivity;
import interactivespaces.master.server.services.internal.RemoteControllerClientListenerHelper;

import interactivespaces_msgs.InteractiveSpacesContainerResource;
import interactivespaces_msgs.LiveActivityDeleteRequest;
import interactivespaces_msgs.LiveActivityDeployRequest;
import org.springframework.stereotype.Controller;

/**
 * A client for speaking to a remote controller.
 *
 * @author Keith M. Hughes
 */
public interface RemoteControllerClient {

  /**
   * Start up the client.
   */
  void startup();

  /**
   * Shut the client down.
   */
  void shutdown();

/**
	 * Connect to the given controller.
	 *
	 * <p>
	 * Connecting to a controller is necessary to receive status messages
	 * from it.
	 *
	 * <p>
	 * All operations which affect a controller, such as
	 * {@link #shutdownAllActivities(Controller) and
	 * {@link #activateActivity(LiveActivity) all will autoconnect to a
	 * controller if necessary.
	 *
	 * <p>
	 * Warning: In the current version, the first autoconnect attempt will have to
	 * happen at least twice with a suitable delay due to connect times. This needs
	 * to change.
	 *
	 * @param controller
	 */
  void connect(ActiveSpaceController controller);

  /**
   * Disconnect from the given controller.
   *
   * @param controller
   */
  void disconnect(ActiveSpaceController controller);

  /**
   * Request the shutdown of a controller.
   *
   * @param controller
   */
  void requestShutdown(ActiveSpaceController controller);

  /**
   * Request the status of a controller.
   *
   * @param controller
   */
  void requestStatus(ActiveSpaceController controller);

  /**
   * Request the shut down of all activities running on a controller.
   *
   * @param controller
   *          The controller containing the activities to stop running.
   */
  void shutdownAllActivities(ActiveSpaceController controller);

  /**
   * Capture the data bundle for the given controller.
   * @param controller
   *          Controller for which to capture the data bundle.
   */
  void captureControllerDataBundle(ActiveSpaceController controller);

  /**
   * Restore the data bundle for the given controller.
   * @param controller
   *          Controller for which to capture the data bundle.
   */
  void restoreControllerDataBundle(ActiveSpaceController controller);

  /**
   * Deploy an activity to its controller.
   *
   * @param liveActivity
   *          the live activity being deployed
   * @param request
   *          the deployment request
   */
  // TODO(keith): make this not depend on the ROS message or the live
  // activity, just a spec.
  void deployActivity(ActiveLiveActivity liveActivity, LiveActivityDeployRequest request);

  /**
   * Create a new deploy request.
   *
   * @return a new deploy request
   */
  LiveActivityDeployRequest newLiveActivityDeployRequest();

  /**
   * Delete a live activity from its controller.
   *
   * @param liveActivity
   *          the live activity being deleted
   * @param request
   *          the deletion request
   */
  // TODO(keith): make this not depend on the ROS message or the live
  // activity, just a spec.
  void deleteActivity(ActiveLiveActivity liveActivity, LiveActivityDeleteRequest request);

  /**
   * Create a new delete request.
   *
   * @return a new delete request
   */
  LiveActivityDeleteRequest newLiveActivityDeleteRequest();

  /**
   * Create a new delete request.
   *
   * @return a new delete request
   */
  InteractiveSpacesContainerResource newInteractiveSpacesContainerResource();

  /**
   * Fully configure an activity on its controller.
   *
   * @param activity
   *          The activity to configure
   */
  void fullConfigureLiveActivity(ActiveLiveActivity activity);

  /**
   * Start an activity on its controller.
   *
   * @param activity
   *          The activity to start
   */
  void startupActivity(ActiveLiveActivity activity);

  /**
   * Activate an activity on its controller.
   *
   * @param activity
   *          The activity to activate
   */
  void activateActivity(ActiveLiveActivity activity);

  /**
   * Deactivate an activity on its controller.
   *
   * @param activity
   *          The activity to deactivate
   */
  void deactivateActivity(ActiveLiveActivity activity);

  /**
   * Shut an activity down on its controller.
   *
   * @param activity
   *          The activity to shut down
   */
  void shutdownActivity(ActiveLiveActivity activity);

  /**
   * Get the status of an activity on its controller.
   *
   * @param activity
   *          The activity
   */
  void statusActivity(ActiveLiveActivity activity);

  /**
   * Add in a new event listener for events from the client.
   *
   * @param listener
   *          the new listener
   */
  void addRemoteSpaceControllerClientListener(RemoteSpaceControllerClientListener listener);

  /**
   * Remove an event listener for events from the client.
   *
   * <p>
   * Does nothing if the listener wasn't registered.
   *
   * @param listener
   *          the listener to remove
   */
  void removeRemoteSpaceControllerClientListener(RemoteSpaceControllerClientListener listener);

  /**
   * Get all the status listeners for controllers.
   *
   * @return the helper which handles the listeners
   */
  RemoteControllerClientListenerHelper getRemoteControllerClientListeners();
}
