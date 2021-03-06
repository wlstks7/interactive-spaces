/*
 * Copyright (C) 2013 Google Inc.
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

package interactivespaces.osgi.service;

import com.google.common.collect.Lists;

import interactivespaces.service.Service;
import interactivespaces.service.ServiceRegistry;
import interactivespaces.service.SupportedService;
import interactivespaces.system.InteractiveSpacesEnvironment;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A base class for creating OSGi BundleActivator subclasses for Interactive
 * Spaces services.
 *
 * @author Keith M. Hughes
 */
public abstract class InteractiveSpacesServiceOsgiBundleActivator implements BundleActivator {

  /**
   * All OSGi service registrations from this bundle.
   */
  private List<ServiceRegistration> osgiServiceRegistrations = Lists.newArrayList();

  /**
   * All services registered by this bundle.
   */
  private List<Service> registeredServices = Lists.newArrayList();

  /**
   * OSGi service tracker for the interactive spaces environment.
   */
  protected MyServiceTracker<InteractiveSpacesEnvironment> interactiveSpacesEnvironmentTracker;

  /**
   * OSGi bundle context for this bundle.
   */
  private BundleContext bundleContext;

  /**
   * All service trackers we have.
   */
  private Map<String, MyServiceTracker<?>> serviceTrackers =
      new HashMap<String, MyServiceTracker<?>>();

  /**
   * Object to give lock for putting this bundle's services together.
   */
  private Object serviceLock = new Object();

  @Override
  public void start(BundleContext context) throws Exception {
    this.bundleContext = context;

    interactiveSpacesEnvironmentTracker =
        newMyServiceTracker(InteractiveSpacesEnvironment.class.getName());

    // Get the registrations from the subclass.
    onStart();

    // Open all the trackers.
    for (MyServiceTracker<?> tracker : serviceTrackers.values()) {
      tracker.open();
    }
  }

  /**
   * The bundle is starting. Add any requests for services.
   */
  protected void onStart() {
    // Default is to do nothing.
  }

  @Override
  public void stop(BundleContext context) throws Exception {

    onStop();

    unregisterOsgiServices();
    unregisterInteractiveSpacesServices();

    // Close all the trackers.
    for (MyServiceTracker<?> tracker : serviceTrackers.values()) {
      tracker.close();
    }
    serviceTrackers.clear();
  }

  /**
   * Bundle is shutting down. Do any extra cleanup.
   */
  protected void onStop() {
    // Default is do nothing.
  }

  /**
   * Unregister all OSGi-registered services.
   */
  private void unregisterOsgiServices() {
    for (ServiceRegistration service : osgiServiceRegistrations) {
      service.unregister();
    }
    osgiServiceRegistrations.clear();
  }

  /**
   * Unregister and shutdown all services registered with interactive spaces.
   */
  private void unregisterInteractiveSpacesServices() {
    ServiceRegistry serviceRegistry =
        interactiveSpacesEnvironmentTracker.getMyService().getServiceRegistry();
    for (Service service : registeredServices) {
      serviceRegistry.unregisterService(service);

      if (SupportedService.class.isAssignableFrom(service.getClass())) {
        ((SupportedService) service).shutdown();
      }
    }
    registeredServices.clear();
  }

  /**
   * Register an InteractiveSpaces service as a new OSGi service.
   *
   * @param name
   *          name for the OSGi service
   * @param service
   *          the Interactive Spaces service
   */
  protected void registerOsgiService(String name, Service service) {
    osgiServiceRegistrations.add(bundleContext.registerService(name, service, null));
  }

  /**
   * Got another reference from a dependency.
   */
  protected void gotAnotherReference() {
    synchronized (serviceLock) {
      // If missing any of our needed services, punt.
      for (MyServiceTracker<?> tracker : serviceTrackers.values()) {
        if (tracker.getMyService() == null) {
          return;
        }
      }

      allRequiredServicesAvailable();
    }
  }

  /**
   * All required services are available.
   */
  protected abstract void allRequiredServicesAvailable();

  /**
   * Register a new service with IS.
   *
   * @param service
   *          the service to be registered
   */
  protected void registerNewInteractiveSpacesService(Service service) {
    interactiveSpacesEnvironmentTracker.getMyService().getServiceRegistry()
        .registerService(service);

    if (SupportedService.class.isAssignableFrom(service.getClass())) {
      ((SupportedService) service).startup();
    }

    registeredServices.add(service);
  }

  /**
   * Create a new service tracker.
   *
   * @param serviceName
   *          name of the service class
   *
   * @return the service tracker
   */
  protected <T> MyServiceTracker<T> newMyServiceTracker(String serviceName) {
    MyServiceTracker<T> tracker = new MyServiceTracker<T>(bundleContext, serviceName);

    serviceTrackers.put(serviceName, tracker);

    return tracker;
  }

  /**
   * An OSGi service tracking class.
   *
   * @author Keith M. Hughes
   */
  public final class MyServiceTracker<T> extends ServiceTracker {
    private AtomicReference<T> serviceReference = new AtomicReference<T>();

    public MyServiceTracker(BundleContext context, String serviceName) {
      super(context, serviceName, null);
    }

    @Override
    public Object addingService(ServiceReference reference) {
      @SuppressWarnings("unchecked")
      T service = (T) super.addingService(reference);

      if (serviceReference.compareAndSet(null, service)) {
        gotAnotherReference();
      }

      return service;
    }

    /**
     * Get the service needed.
     *
     * @return the service, or {@code null} if it hasn't been obtained yet.
     */
    public T getMyService() {
      return serviceReference.get();
    }
  }
}
