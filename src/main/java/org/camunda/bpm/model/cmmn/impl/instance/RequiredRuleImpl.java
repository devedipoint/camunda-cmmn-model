/* Licensed under the Apache License, Version 2.0 (the "License");
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
package org.camunda.bpm.model.cmmn.impl.instance;

import static org.camunda.bpm.model.cmmn.impl.CmmnModelConstants.CMMN10_NS;
import static org.camunda.bpm.model.cmmn.impl.CmmnModelConstants.CMMN_ATTRIBUTE_CONTEXT_REF;
import static org.camunda.bpm.model.cmmn.impl.CmmnModelConstants.CMMN_ATTRIBUTE_NAME;
import static org.camunda.bpm.model.cmmn.impl.CmmnModelConstants.CMMN_ELEMENT_REQUIRED_RULE;

import org.camunda.bpm.model.cmmn.instance.CaseFileItem;
import org.camunda.bpm.model.cmmn.instance.CmmnElement;
import org.camunda.bpm.model.cmmn.instance.RequiredRule;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder.ModelTypeInstanceProvider;
import org.camunda.bpm.model.xml.type.attribute.Attribute;
import org.camunda.bpm.model.xml.type.child.ChildElement;
import org.camunda.bpm.model.xml.type.child.SequenceBuilder;
import org.camunda.bpm.model.xml.type.reference.AttributeReference;

/**
 * @author Roman Smirnov
 *
 */
public class RequiredRuleImpl extends CmmnElementImpl implements RequiredRule {

  protected static Attribute<String> nameAttribute;
  protected static AttributeReference<CaseFileItem> contextRefAttribute;
  protected static ChildElement<ConditionExpression> conditionChild;

  public RequiredRuleImpl(ModelTypeInstanceContext instanceContext) {
    super(instanceContext);
  }

  public String getName() {
    return nameAttribute.getValue(this);
  }

  public void setName(String name) {
    nameAttribute.setValue(this, name);
  }

  public CaseFileItem getContext() {
    return contextRefAttribute.getReferenceTargetElement(this);
  }

  public void setContext(CaseFileItem caseFileItem) {
    contextRefAttribute.setReferenceTargetElement(this, caseFileItem);
  }

  public ConditionExpression getCondition() {
    return conditionChild.getChild(this);
  }

  public void setCondition(ConditionExpression condition) {
    conditionChild.setChild(this, condition);
  }

  public static void registerType(ModelBuilder modelBuilder) {
    ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(RequiredRule.class, CMMN_ELEMENT_REQUIRED_RULE)
        .namespaceUri(CMMN10_NS)
        .extendsType(CmmnElement.class)
        .instanceProvider(new ModelTypeInstanceProvider<RequiredRule>() {
          public RequiredRule newInstance(ModelTypeInstanceContext instanceContext) {
            return new RequiredRuleImpl(instanceContext);
          }
        });

    nameAttribute = typeBuilder.stringAttribute(CMMN_ATTRIBUTE_NAME)
        .build();

    contextRefAttribute = typeBuilder.stringAttribute(CMMN_ATTRIBUTE_CONTEXT_REF)
        .idAttributeReference(CaseFileItem.class)
        .build();

    SequenceBuilder sequenceBuilder = typeBuilder.sequence();

    conditionChild = sequenceBuilder.element(ConditionExpression.class)
        .build();

    typeBuilder.build();
  }

}
