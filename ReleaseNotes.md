# Introduction #

This section contains release notes for releases of Interactive Spaces.


# Details #

Release 1.6.0:

This is a breaking release. There is a chance old code will stop working. We try very hard on this project to never do breaking changes, but these were necessary for stability, uniform usage, and improving testability. So we threw in everything we can think of that we wanted to change so that we don't have to do this again.

New Functionality:

OpenNI and NiTE are now supported (Linux and Mac). A user tracking service is now available, along with an example.

There is now a service for using a Leap Motion gesture detector.

A face detector service using Haar cascades and OpenCV is now available. An example is supplied which sends face detection coordinates on a route.

There is no longer a need to write your own activity.conf. The project.xml file has been extended to contain all information needed to automatically generate the activity.conf. See the interactivespaces.example.activity.routable.input.web and interactivespaces.example.activity.routable.output.python examples.

Much more of the Master API is now available through the Master Web Socket connection, which removes the need to use AJAX commands. The AJAX commands are deprecated, but will be left for the next several releases.

The Workbench now supports project templates as a way of defining initial implementations of groups of activities. This would help, for example, with a multi-activity project in which all of the activities must work together.

Status updates for live activities are now formatted much more cleanly.

There is now an Expect-type service which uses telnet to connect to a remote machine and control it.

There are now utility classes for doing data sampling, background detection, background subtraction, gaussian filtering, etc.

There is a small set of utility classes for modeling sensor detection events, tracked entities, etc.

There is now a blob detection class.

Signatures for activity bundles are now calculated and displayed.

The Master Web Admin now shows when activities were last started and activated.

Breaking changes:

Many of the activity components had not been written with an interface defining its functionality. When possible a new name was found for the interface so that code wouldn't break, but this wasn't always possible. So code creating the classes which are now interfaces will fail.

The event alerting mechanism from the web socket API used non-standard names for events giving the status update of a live activity. The field, which was called status, is now called runtimeState.

The folder repository in the top level directory of the master should be moved to master/repository. Though this is confusing, this means that if master is the name of your master's root folder, the repository folder should go to master/master/repository.

The activity.conf found in an activity's installed folder on a controller has moved from the activity data/ folder to a folder called internal/ in the installed directory.

The ManagedCommands interface now returns a ManagedCommand object. This object gives the ability to stop the underlying thread. This should be used instead of the ExecutorService for activity level commands.

The Master API sends status messages from live activities over the web socket connection as the master becomes aware of them. The message format has changed. The root object of the message used to contain the status update data. Now the format is of the form {‘type’: ‘statusUpdate’, ‘data’: previous\_data} where previous\_data is the data that used to come over the connection.

Bug Fixes:

It is no longer necessary to restart a controller if an activity with dependencies is deployed and one of the dependencies changes some of its internal code.

It was possible to save objects newly created in the Master Web Admin which lacked required fields. The UI now properly validates all necessary fields and will not allow saving objects lacking required fields.

The extras folder in the controller has been massively reorganized.

Large HTTP posts were dropping headers.

Release 1.5.4:

New Functionality:

OpenCV now provided in the controller (only Linux for now).

Workbench project dependencies for activities and libraries now must declare their dependencies if the dependencies are in the user bootstrap folder (called startup). Also, dependencies are now copied to the controller along with the activity, simplifying installation maintenance.

Java-based Workbench projects now support unit tests by having a main/test/java folder with JUnit tests.

Assembly projects which combines various files (usually Javascript files) into one combined file.

Java workbench projects can now pick up additional sources from outside the current project. This allows easy sharing of simple functionality that doesn't require doing a full library project.

A small Geometry and Math library are now available in interactivespaces.util.

Java Swing JPanels can now be used as a Managed Resource making it easy to provide a Swing UI.

A client for listening to UDP broadcast packets is now available.

The RestartStrategy for running native applications now provides a listener interface so that activities can be notified when the native activity crashes so that the activity can take part in the restart, e.g. clear a cache.


Release 1.5.3:

Bug Fixes:

There is now a global exception handler which logs.

The documentation was not complete in how to run multiple masters on a given machine. This has been corrected.

New Functionality:

UDP-based Open Sound Control Client plus example. Includes PureData patch for testing. Requires pd-extended to run the patch for the example.

The workbench now properly supports versioning of library projects. This means that your libraries can be versioned and, in your activities, you can specify which version of a given library is needed. This means you can have multiple versions of a given library running in your controller.

It is now possible to clean tmp and permanenent data directories of a controller and for all installed live activities on a controller.

Controllers now give more debugging information about a running activity. For example, an activity which has an embeded web server will show a URL for accessing that web server in the Master Web App. Also, live activities will have their startup configuration logged on the controller.

Release 1.5.2:

Bug Fixes:

PID file was not working properly.

The file control thread was dying on the master in some instances, making the master immune to autoshutdown.

New Functionality:

If an activity fails to go through a lifecycle change, the stack trace is shown in the Master Admin Webapp.

The UDP Client code has changed APIs to not only talk to one remote address and port.

Release 1.5.1:

Bug Fixes:

The PID file was annoying. A container can now start up if the process which created the PID file is gone.

JsonNavigator now has better array processing.

If an error was made when inputting information for a new Workbench project, proper input would never be accepted,

New Functionality:

Improved documentation.

Workbench has better error messaging.

Workbench supports creation of Javadoc for Java-based projects.

Workbench now supports turning a non-OSGi Java jar into an OSGi bundle.

There are now UDP communication services for client and server.

Release 1.5.0:

Bug Fixes:

XBee connections now fully shutdown.

On rare machines the master would not properly start up. Container startup now properly ensures master startup.

The workbench installer failed when trying to install assets in the bin/ folder.

The HTTP Copier bug was not properly fixed. If an error happened during transfer the copier was forever useless. This has actually been fixed this time (there is even a unit test).

New Functionality:

The XBee API has been somewhat simplified and extended. Frame numbers can now be automatically assigned, and it is possible to easily specify the desire for no return frames.

The master can now serve more than activities from its internal repository, such as library dependencies for activities.

The workbench now supports creating OSGi bundles for arbitrary libraries.

Release 1.4.3:

Bug Fixes:

[Issue 35](https://code.google.com/p/interactive-spaces/issues/detail?id=35) fixed. Attempting to configure a live activity which has never been deployed got a stack trace in the master webapp. Message key was added.

HTTP dynamic content handlers would make a browser hang if the generated content was too short. The CONTENT\_LENGTH HTTP headers were not being set, they are now.

If a network failure happened, the heartbeat thread on a Space Controller would be killed. The master would still be able to communicate with the controller, but the controller would not look live.

An HttpCopier instance would fail to copy any more items after a failed copy. This has been fixed.

New Functionality:

The Filesystem APIs now have calls to clean an activity's and the container's temp and permanent data directories.

It is now possible to delete a persisted map.

HTTP dynamic content handlers now permit setting a content type.

Add in support for working with JSON objects more cleanly.

An initial USB communication service was added. This includes a driver for the Wild Divine IOM biofeedback sensor.

The version of Interactive Spaces running is now available as a configuration property interactivespaces.version. This is available from the Support page in the Master Webapp.

Configurations now support required typed data.

A new experimental project file format has been created. Usage of this format will likely change for a bit.

The Master Webapp can now be labeled with a name. This is useful if you have Master Webapps open for multiple IS networks.

The XBee service now supports the IO Sample frame.

Release 1.4.2:

Bug fixes:

Routes with non 7-bit ASCII characters were having character decoding issues when the route message was received. This was discovered when using internationalized activities.

An Activity attempting to register publishers or subscribers with the ROS master would periodically receive a Connection Refused exception from the ROS master even though it was still running. This was because the ROS Master was using a web server allowing for very few connections simultaneous connections. This web server has been replaced with a high performance server that allows many simultaneous connections.

Managed Commands which were meant to repeat would stop repeating if an exception was ever thrown. The exception would also not be logged. The Managed Command infrastructure will now catch all uncaught exceptions and log them and permit the repeated command to continue.

There was contention for an Activity Component running lock, particularly during activity shutdown with many queued messages.

It is now possible to simultaneously run multiple Java-based live activities based on the same activity on the same controller.

Changing the executable or classname or name/version of activity for IS native activities now doesn't require a controller restart for the change to be noticed.

In some instances, trying to perform a command on the Master Webapp would make an activity look like it was starting up or shutting down, but would never change from that though it was obvious on the controller that the activity in question had done the appropriate state change.

New Functionality:

More documentation added for Advanced Master Usage.

The Master now has file control, which allows the master to be cleanly shutdown, live activities and live activity groups to be started, and Named scripts to be run, all by creating a file in a special directory. This makes it easier to control IS using, say, CRON jobs.

There are now several shell scripts in the bin directory which can be used to control an Interactive Spaces container, such as starting it up in the foreground or background, and shutting it down cleanly and harshly (a process kill). This permits easy autostart of an installation using job control such as CRON.


Release 1.4.1:

The 1.4.1 full binary release of Interactive Spaces contains bug fixes and some new functionality.

This release fixes several issues:

1. Due to a race condition during shutdown of activities using ROS communication, a live activity would appear to be stuck in the process of shutdown. The master could still send messages to the controller about it, but nothing would happen. The controller would then have to be killed by killing the Java process.

2. Cookies were not handled properly in HTTP dynamic content handlers for the Web Server.

3. HTTP dynamic content handlers were not properly flushing and closing their output stream. This meant some handlers would block when a remote client attempted to download from them.

New features:

1. It is now possible to get a list of all supported languages from an internationalization provider.

2. It is now possible to set the HTTP response code for HTTP dynamic content handlers.


Release 1.4.0:

This is a bug fix released, there is no new functionality.

[Issue 25](https://code.google.com/p/interactive-spaces/issues/detail?id=25): There was a thread leak in Interactive Spaces which happened during an intensive development cycle of deploy, run, repeat. In time a controller would lock up as no new threads could be obtained from the thread pool for any messages coming into the controller, and, in some cases, the master. Thread leaks were tracked down and repaired. The repo now contains a _tests_ folder which contains a Master Script which will do the deploy/run cycle continuously. After 2 hours no thread leak was seen.

Message dropping: rosjava is built to drop messages when internal message queues become full. While fine for sensing applications, this is problematic for core IS control functionality, such as live activity control. Though not fully exposed in the API yet, rosjava now has the ability to say whether or not all messages which a transmit attempt has been made will be transmitted or whether the typical rosjava functionality of message dropping is used. A pair of activities are in the _tests_ folder which will transmit a bunch of messages as quickly as possible and signal if any are dropped.