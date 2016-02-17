/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camunda.bpm.engine.impl.migration.instance;

import org.camunda.bpm.engine.impl.bpmn.parser.EventSubscriptionDeclaration;
import org.camunda.bpm.engine.impl.jobexecutor.TimerDeclarationImpl;
import org.camunda.bpm.engine.impl.persistence.entity.EventSubscriptionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.JobEntity;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;

public class MigratingTimerJobInstance implements MigratingInstance, RemovingInstance, EmergingInstance {

  protected JobEntity jobEntity;
  protected ScopeImpl targetScope;

  protected TimerDeclarationImpl timerDeclaration;

  public MigratingTimerJobInstance(JobEntity jobEntity, ScopeImpl targetScope) {
    this.jobEntity = jobEntity;
    this.targetScope = targetScope;
  }

  public MigratingTimerJobInstance(JobEntity jobEntity) {
    this(jobEntity, null);
  }

  public MigratingTimerJobInstance(TimerDeclarationImpl timerDeclaration) {
    this.timerDeclaration = timerDeclaration;
  }

  public void detachState() {
    jobEntity.setExecution(null);
  }

  public void attachState(ExecutionEntity newScopeExecution) {
    jobEntity.setExecution(newScopeExecution);
  }

  public void migrateState() {
    jobEntity.setActivityId(targetScope.getId());
  }

  public void migrateDependentEntities() {
    // do nothing
  }

  public void create(ExecutionEntity representativeExecution) {
    timerDeclaration.createTimer(representativeExecution);
  }

  public void remove() {
    jobEntity.delete();
  }
}
