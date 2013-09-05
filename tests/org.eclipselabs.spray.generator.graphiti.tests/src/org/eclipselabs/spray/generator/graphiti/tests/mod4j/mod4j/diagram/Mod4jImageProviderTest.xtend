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
package org.eclipselabs.spray.generator.graphiti.tests.mod4j.mod4j.diagram

import org.eclipse.xtext.junit4.InjectWith
import org.eclipselabs.spray.generator.graphiti.tests.AbstractSprayGeneratorTest
import org.eclipselabs.spray.generator.graphiti.tests.SprayTestsInjectorProvider
import org.eclipselabs.xtext.utils.unittesting.XtextRunner2
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(typeof(XtextRunner2))
@InjectWith(typeof(SprayTestsInjectorProvider))
class Mod4jImageProviderTest extends AbstractSprayGeneratorTest {

    @Test
    def void test() {
       val fsa = triggerGenerator("mod4j/mod4j-busmod.spray")
       val keyClass = "DEFAULT_OUTPUTorg/eclipselabs/spray/examples/mod4j/diagram/Mod4jImageProvider.java"
       assertTrue(keyClass + " should have been generated", fsa.files.containsKey(keyClass))
       assertEquals(keyClass + " should have the expected content", fsa.files.get(keyClass).toString, expectedContent.toString)
    }

    def private expectedContent() '''
        /*************************************************************************************
         *
         * Generated by Spray ImageProvider.xtend
         * 
         * This file is an extension point: copy to "src" folder to manually add code to this
         * extension point.
         *
         *************************************************************************************/
        package org.eclipselabs.spray.examples.mod4j.diagram;
        
        public class Mod4jImageProvider extends Mod4jImageProviderBase {
            //    /**
            //     * The image identifier for ...
            //     */
            //    public static final String MOD4J__MYIMAGEID     = PREFIX + "MYIMAGEID";
            //
            //    /**
            //     * {@inheritDoc}
            //     */
            //    @Override
            //    protected void addAvailableImages() {
            //        super.addAvailableImages();
            //        // register the path for each image identifier
            //       addImageFilePath(MOD4J__MYIMAGEID, "icons/...");
            //    }
        }
    '''
}
