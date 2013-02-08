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

package interactivespaces.system;

import interactivespaces.activity.binary.NativeActivityRunnerFactory;
import interactivespaces.configuration.Configuration;
import interactivespaces.service.ServiceRegistry;
import interactivespaces.system.core.logging.LoggingProvider;
import interactivespaces.time.TimeProvider;

import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.logging.Log;

/**
 * The Interactive Spaces environment being run in.
 * 
 * @author Keith M. Hughes
 */
public interface InteractiveSpacesEnvironment {

	/**
	 * Configuration property giving the Interactive Spaces container type.
	 */
	public static final String CONFIGURATION_CONTAINER_TYPE = "interactivespaces.container.type";

	/**
	 * Configuration property value for the master Interactive Spaces container
	 * type.
	 */
	public static final String CONFIGURATION_CONTAINER_TYPE_MASTER = "master";

	/**
	 * Configuration property value for the controller Interactive Spaces
	 * container type.
	 */
	public static final String CONFIGURATION_CONTAINER_TYPE_CONTROLLER = "controller";

	/**
	 * Configuration property giving the Interactive Spaces type, e.g. prod,
	 * dev, local.
	 */
	public static final String CONFIGURATION_NETWORK_TYPE = "interactivespaces.network.type";

	/**
	 * Configuration property giving the host ID for the system.
	 */
	public static final String CONFIGURATION_HOSTID = "interactivespaces.hostid";

	/**
	 * Configuration property giving the hostname for the system.
	 */
	public static final String CONFIGURATION_HOSTNAME = "interactivespaces.host";

	/**
	 * The log level for warnings and above.
	 */
	public static final String LOG_LEVEL_WARN = LoggingProvider.LOG_LEVEL_WARN;

	/**
	 * The log level for trace and above.
	 */
	public static final String LOG_LEVEL_TRACE = LoggingProvider.LOG_LEVEL_TRACE;

	/**
	 * The log level for no logging.
	 */
	public static final String LOG_LEVEL_OFF = LoggingProvider.LOG_LEVEL_OFF;

	/**
	 * The log level for info and above.
	 */
	public static final String LOG_LEVEL_INFO = LoggingProvider.LOG_LEVEL_INFO;

	/**
	 * The log level for debug and above.
	 */
	public static final String LOG_LEVEL_DEBUG = LoggingProvider.LOG_LEVEL_DEBUG;

	/**
	 * The log level for fatal.
	 */
	public static final String LOG_LEVEL_FATAL = LoggingProvider.LOG_LEVEL_FATAL;

	/**
	 * The log level for error and above.
	 */
	public static final String LOG_LEVEL_ERROR = LoggingProvider.LOG_LEVEL_ERROR;

	/**
	 * Get the Interactive Spaces system configuration.
	 * 
	 * @return the system configuration
	 */
	Configuration getSystemConfiguration();

	/**
	 * Get the Interactive Spaces-wide filesystem.
	 * 
	 * @return the filesystem for the installation
	 */
	InteractiveSpacesFilesystem getFilesystem();

	/**
	 * Get the {@link ScheduledExecutorService} to be used inside Interactive
	 * Spaces.
	 * 
	 * <p>
	 * An executor service gives thread pools to be used. Interactive Spaces
	 * needs to control as many threads as possible, so anything in Interactive
	 * Spaces should try and use this service.
	 * 
	 * @return the executor service to be used for all thread usage
	 */
	ScheduledExecutorService getExecutorService();

	/**
	 * Get the container log.
	 * 
	 * @return the container's log
	 */
	Log getLog();

	/**
	 * Get a named log.
	 * 
	 * @param logName
	 *            the name the log should have
	 * 
	 * @return the requested named log
	 */
	Log getLog(String logName, String level);

	/**
	 * Modify the log level.
	 * 
	 * <p>
	 * This method will only work if the level is legal and the log is
	 * modifiable.
	 * 
	 * @param log
	 *            the log to modify
	 * @param level
	 *            the new level
	 * 
	 * @return {@code true} if able to modify the log.
	 */
	boolean modifyLogLevel(Log log, String level);

	/**
	 * Get the network type for the Interactive Spaces container.
	 * 
	 * <p>
	 * This allows distinguishing between different Interactive Spaces networks,
	 * e.g. localdev, prod, fredbot.
	 * 
	 * @return
	 */
	String getNetworkType();

	/**
	 * Get the time provider to use.
	 * 
	 * @return the time provider
	 */
	TimeProvider getTimeProvider();

	/**
	 * Get the service registry.
	 * 
	 * @return the service registry
	 */
	ServiceRegistry getServiceRegistry();

	/**
	 * Get a value from the environment.
	 * 
	 * @param valueName
	 *            the name of the value
	 * 
	 * @return the requested value, or {@code null} if not found
	 */
	<T> T getValue(String valueName);

	/**
	 * Set a value in the environment.
	 * 
	 * @param valueName
	 *            the name of the value
	 * @param value
	 *            the value for the name
	 */
	void setValue(String valueName, Object value);

	/**
	 * Remove a value from the environment.
	 * 
	 * <p>
	 * This does nothing if there is no value with the given name.
	 * 
	 * @param valueName
	 *            the name of the value
	 */
	void removeValue(String valueName);
}
