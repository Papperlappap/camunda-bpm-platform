/*
 * Copyright 2016 camunda services GmbH.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.test.api.authorization.externaltask;

import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Tests the authorization of the bpmn error handling of an external task.
 * 
 * @author Christopher Zell <christopher.zell@camunda.org>
 */
@RunWith(Parameterized.class)
public class HandleExternalTaskBpmnErrorAuthorizationTest extends HandleExternalTaskAuthorizationTest {

  @Override
  public void testExternalTaskApi(LockedExternalTask task) {
    engineRule.getExternalTaskService().handleBpmnError(task.getId(), "workerId", "errorCode");
  }

  @Override
  public void assertExternalTaskResults() {
    Assert.assertEquals(0, engineRule.getExternalTaskService().createExternalTaskQuery().count());
  }
}

