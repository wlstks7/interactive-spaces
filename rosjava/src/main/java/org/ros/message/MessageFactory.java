/*
 * Copyright (C) 2011 Google Inc.
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

package org.ros.message;

/**
 * @author damonkohler@google.com (Damon Kohler)
 */
public interface MessageFactory {

  /**
   * @param messageType
   *          the type of message to create
   * @return a new message
   */
  <T> T newMessage(String messageType);

  /**
   * @param serviceType
   *          the type of service to create a request for
   * @return a new service request message
   */
  <T> T newServiceRequest(String serviceType);

  /**
   * @param serviceType
   *          the type of service to create a response for
   * @return a new service response message
   */
  <T> T newServiceResponse(String serviceType);
}