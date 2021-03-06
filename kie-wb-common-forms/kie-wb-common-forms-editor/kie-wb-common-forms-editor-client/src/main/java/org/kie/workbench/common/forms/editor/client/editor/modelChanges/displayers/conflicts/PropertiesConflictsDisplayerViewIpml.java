/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

package org.kie.workbench.common.forms.editor.client.editor.modelChanges.displayers.conflicts;

import javax.inject.Inject;

import org.jboss.errai.common.client.dom.DOMUtil;
import org.jboss.errai.common.client.dom.UnorderedList;
import org.jboss.errai.ui.client.local.api.IsElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.kie.workbench.common.forms.editor.client.editor.modelChanges.displayers.conflicts.elements.ConflictElement;

@Templated
public class PropertiesConflictsDisplayerViewIpml implements PropertiesConflictsDisplayerView,
                                                             IsElement {

    private Presenter presenter;

    @Inject
    @DataField
    private UnorderedList conflicts;

    @Override
    public void init(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showConflict(ConflictElement conflictElement) {
        conflicts.appendChild(conflictElement.getElement());
    }

    @Override
    public void clear() {
        DOMUtil.removeAllChildren(conflicts);
    }
}
