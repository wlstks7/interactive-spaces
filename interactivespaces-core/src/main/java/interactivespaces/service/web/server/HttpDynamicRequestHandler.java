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

package interactivespaces.service.web.server;

/**
 * A handler for HTTP requests.
 *
 * @author Keith M. Hughes
 */
public interface HttpDynamicRequestHandler {

  /**
   * Handle an HTTP request
   *
   * @param request
   *          the request to handle
   * @param response
   *          the response
   */
  void handle(HttpRequest request, HttpResponse response);
}
