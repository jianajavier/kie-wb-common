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
package org.kie.workbench.common.stunner.bpmn.client.shape.def;

import java.util.HashMap;
import java.util.Map;

import org.kie.workbench.common.stunner.bpmn.client.resources.BPMNImageResources;
import org.kie.workbench.common.stunner.bpmn.client.resources.BPMNSVGViewFactory;
import org.kie.workbench.common.stunner.bpmn.definition.BaseIntermediateEvent;
import org.kie.workbench.common.stunner.bpmn.definition.IntermediateTimerEvent;
import org.kie.workbench.common.stunner.core.client.shape.SvgDataUriGlyph;
import org.kie.workbench.common.stunner.core.client.shape.view.HasTitle;
import org.kie.workbench.common.stunner.core.definition.shape.Glyph;
import org.kie.workbench.common.stunner.svg.client.shape.view.SVGShapeView;

public class IntermediateEventShapeDef
        implements BPMNSvgShapeDef<BaseIntermediateEvent> {

    public final static Map<Class<? extends BaseIntermediateEvent>, String> VIEWS = new HashMap<Class<? extends BaseIntermediateEvent>, String>(1) {{
        put(IntermediateTimerEvent.class,
            BPMNSVGViewFactory.VIEW_EVENT_TIMER);
    }};

    private static final SvgDataUriGlyph.Builder GLYPH_BUILDER =
            SvgDataUriGlyph.Builder.create()
                    .setUri(BPMNImageResources.INSTANCE.eventIntermediate().getSafeUri())
                    .addUri(BPMNSVGViewFactory.VIEW_EVENT_TIMER,
                            BPMNImageResources.INSTANCE.eventTimer().getSafeUri());

    @Override
    public double getAlpha(final BaseIntermediateEvent element) {
        return 1d;
    }

    @Override
    public String getBackgroundColor(final BaseIntermediateEvent element) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public double getBackgroundAlpha(final BaseIntermediateEvent element) {
        return 1;
    }

    @Override
    public String getBorderColor(final BaseIntermediateEvent element) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize(final BaseIntermediateEvent element) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public double getBorderAlpha(final BaseIntermediateEvent element) {
        return 1;
    }

    @Override
    public String getFontFamily(final BaseIntermediateEvent element) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor(final BaseIntermediateEvent element) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public String getFontBorderColor(final BaseIntermediateEvent element) {
        return element.getFontSet().getFontBorderColor().getValue();
    }

    @Override
    public double getFontSize(final BaseIntermediateEvent element) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public double getFontBorderSize(final BaseIntermediateEvent element) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public HasTitle.Position getFontPosition(final BaseIntermediateEvent element) {
        return HasTitle.Position.BOTTOM;
    }

    @Override
    public double getFontRotation(final BaseIntermediateEvent element) {
        return 0;
    }

    @Override
    public double getWidth(final BaseIntermediateEvent element) {
        return element.getDimensionsSet().getRadius().getValue() * 2;
    }

    @Override
    public double getHeight(final BaseIntermediateEvent element) {
        return element.getDimensionsSet().getRadius().getValue() * 2;
    }

    @Override
    public boolean isSVGViewVisible(final String viewName,
                                    final BaseIntermediateEvent element) {
        return viewName.equals(VIEWS.get(element.getClass()));
    }

    @Override
    public SVGShapeView<?> newViewInstance(final BPMNSVGViewFactory factory,
                                           final BaseIntermediateEvent intermediateTimerEvent) {
        return factory.eventIntermediate(getWidth(intermediateTimerEvent),
                                         getHeight(intermediateTimerEvent),
                                         false);
    }

    @Override
    public Class<BPMNSVGViewFactory> getViewFactoryType() {
        return BPMNSVGViewFactory.class;
    }

    @Override
    public Glyph getGlyph(final Class<? extends BaseIntermediateEvent> type) {
        return GLYPH_BUILDER.build(VIEWS.get(type));
    }
}
