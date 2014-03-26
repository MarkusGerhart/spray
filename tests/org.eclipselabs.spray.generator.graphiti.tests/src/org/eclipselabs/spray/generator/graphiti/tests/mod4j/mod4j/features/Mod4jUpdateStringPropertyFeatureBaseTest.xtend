/** ****************************************************************************
 * Copyright (c)  The Spray Project.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Spray Dev Team - initial API and implementation
 **************************************************************************** */
package org.eclipselabs.spray.generator.graphiti.tests.mod4j.mod4j.features

import org.eclipse.xtext.junit4.InjectWith
import org.eclipselabs.spray.generator.graphiti.tests.AbstractSprayGeneratorTest
import org.eclipselabs.spray.generator.graphiti.tests.SprayTestsInjectorProvider
import org.eclipselabs.xtext.utils.unittesting.XtextRunner2
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(typeof(XtextRunner2))
@InjectWith(typeof(SprayTestsInjectorProvider))
class Mod4jUpdateStringPropertyFeatureBaseTest extends AbstractSprayGeneratorTest {

    @Test
    def void test() {
       val fsa = triggerGenerator("mod4j/mod4j-busmod.spray")
       val keyClass = "DEFAULT_OUTPUTorg/eclipselabs/spray/examples/mod4j/features/Mod4jUpdateStringPropertyFeatureBase.java"
       assertTrue(keyClass + " should have been generated", fsa.files.containsKey(keyClass))
       assertEquals(keyClass + " should have the expected content", fsa.files.get(keyClass).toString, expectedContent.toString)
    }

    def private expectedContent() '''
        /*************************************************************************************
         *
         * Generated by Spray UpdateShapeFromDslFeature.xtend
         *
         * This file contains generated and should not be changed.
         * Use the extension point class (the direct subclass of this class) to add manual code
         *
         *************************************************************************************/
        package org.eclipselabs.spray.examples.mod4j.features;
        
        import org.eclipse.graphiti.features.IFeatureProvider;
        import org.eclipse.graphiti.features.IReason;
        import org.eclipse.graphiti.features.context.IUpdateContext;
        import org.eclipse.graphiti.features.impl.Reason;
        import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
        import org.eclipse.graphiti.mm.algorithms.AbstractText;
        import org.eclipse.graphiti.mm.pictograms.ContainerShape;
        import org.eclipse.graphiti.mm.pictograms.Shape;
        import org.eclipse.graphiti.mm.pictograms.Diagram;
        import org.eclipse.graphiti.mm.pictograms.PictogramElement;
        import org.eclipse.graphiti.services.IGaService;
        import org.eclipselabs.spray.runtime.graphiti.features.AbstractUpdateFeature;
        import org.eclipselabs.spray.runtime.graphiti.layout.SprayLayoutService;
        
        import samplepackage.StringProperty;
        import com.google.common.base.Function;
        import org.eclipselabs.spray.examples.mod4j.Activator;
        import samplepackage.StringProperty;
        
        public abstract class Mod4jUpdateStringPropertyFeatureBase extends AbstractUpdateFeature {
            public Mod4jUpdateStringPropertyFeatureBase(final IFeatureProvider fp) {
                super(fp);
                gaService = Activator.get(IGaService.class);
            }
        
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean canUpdate(final IUpdateContext context) {
                // return true, if linked business object is a StringProperty
                final PictogramElement pictogramElement = context.getPictogramElement();
                final Object bo = getBusinessObjectForPictogramElement(pictogramElement);
                return (bo instanceof StringProperty) && (!(pictogramElement instanceof Diagram));
            }
        
            /**
             * {@inheritDoc}
             */
            @Override
            public IReason updateNeeded(final IUpdateContext context) {
                final PictogramElement pictogramElement = context.getPictogramElement();
                final Object bo = getBusinessObjectForPictogramElement(pictogramElement);
                if (!(bo instanceof StringProperty)) {
                    return Reason.createFalseReason();
                }
                if (pictogramElement instanceof Shape) {
                    Shape shape = (Shape) pictogramElement;
                    StringProperty eClass = (StringProperty) bo;
                    if (checkUpdateNeededRecursively(shape, eClass)) {
                        return Reason.createTrueReason();
                    }
                    if (shape instanceof ContainerShape) {
                        for (Shape childShape : ((ContainerShape) shape).getChildren()) {
                            if (checkUpdateNeededRecursively(childShape, eClass)) {
                                return Reason.createTrueReason();
                            }
                        }
                    }
                }
                return Reason.createFalseReason();
            }
        
            protected boolean checkUpdateNeededRecursively(Shape shape, final StringProperty eClass) {
                GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();
                if (graphicsAlgorithm instanceof AbstractText) {
                    AbstractText text = (AbstractText) graphicsAlgorithm;
                    String id = peService.getPropertyValue(graphicsAlgorithm, TEXT_ID);
                    if (id != null) {
                        if (id.equals("textId2")) {
                            String eClassValue = new Function<StringProperty, String>() {
                                public String apply(StringProperty modelElement) {
                                    // generation exception: java.lang.NullPointerException
                                }
                            }.apply(eClass);
                            String gAlgorithmValue = text.getValue();
                            if (eClassValue != null) {
                                if (!eClassValue.equals(gAlgorithmValue)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                if (shape instanceof ContainerShape) {
                    for (Shape child : ((ContainerShape) shape).getChildren()) {
                        if (checkUpdateNeededRecursively(child, eClass)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean update(final IUpdateContext context) {
                final PictogramElement pictogramElement = context.getPictogramElement();
                final StringProperty eClass = (StringProperty) getBusinessObjectForPictogramElement(pictogramElement);
                if (pictogramElement instanceof Shape) {
                    Shape shape = (Shape) pictogramElement;
                    updateChildrenRecursively(shape, eClass);
                    Shape top = findTopShape(shape);
                    SprayLayoutService.getLayoutManager(top).layout();
                }
                return true;
        
            }
        
            protected void updateChildrenRecursively(Shape shape, final StringProperty eClass) {
                GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();
                if (graphicsAlgorithm instanceof AbstractText) {
                    AbstractText text = (AbstractText) graphicsAlgorithm;
                    String id = peService.getPropertyValue(graphicsAlgorithm, TEXT_ID);
                    if (id != null) {
                        if (id.equals("textId2")) {
                            String value = new Function<StringProperty, String>() {
                                public String apply(StringProperty modelElement) {
                                    // generation exception: java.lang.NullPointerException
                                }
                            }.apply(eClass);
                            text.setValue(value);
                        }
                    }
                }
        
                if (shape instanceof ContainerShape) {
                    for (Shape child : ((ContainerShape) shape).getChildren()) {
                        updateChildrenRecursively(child, eClass);
                    }
                }
            }
        }
    '''
}
