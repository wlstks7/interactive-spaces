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

package interactivespaces.configuration;

import interactivespaces.evaluation.ExpressionEvaluator;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@link Configuration} based on Java properties files.
 *
 * @author Keith M. Hughes
 */
public class SimpleConfiguration extends BaseConfiguration {
	
	/**
	 * The properties file containing the app properties.
	 */
	private Map<String, String> values = new HashMap<String, String>();

	/**
	 * @param expressionEvaluator
	 *            the expression evaluator for this configuration.
	 */
	public SimpleConfiguration(ExpressionEvaluator expressionEvaluator) {
		super(expressionEvaluator);
	}
	
	@Override
	public boolean containsPropertyLocally(String property) {
		return values.containsKey(property);
	}

	@Override
	public String findValueLocally(String property) {
		return values.get(property);
	}
	
	@Override
	public void setValue(String property, String value) {
		values.put(property, value);
	}

	@Override
	public void clear() {
		values.clear();
	}
	
	@Override
	public void addCollapsedEntries(Map<String, String> map) {
		if (parent != null) {
			parent.addCollapsedEntries(map);
		}
	}
}
