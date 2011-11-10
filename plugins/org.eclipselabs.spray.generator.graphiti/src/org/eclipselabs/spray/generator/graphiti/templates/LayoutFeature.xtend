package org.eclipselabs.spray.generator.graphiti.templates

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.xtend2.lib.StringConcatenation
import org.eclipselabs.spray.generator.graphiti.util.NamingExtensions
import org.eclipselabs.spray.mm.spray.Container
import org.eclipselabs.spray.mm.spray.extensions.SprayExtensions

import static org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil.*
import static org.eclipselabs.spray.generator.graphiti.util.MetaModel.*


class LayoutFeature extends FileGenerator {
    @Inject extension SprayExtensions
    @Inject extension NamingExtensions
    
    override StringConcatenation generateBaseFile(EObject modelElement) {
        mainFile( modelElement as Container, javaGenFile.baseClassName)
    }

    override StringConcatenation generateExtensionFile(EObject modelElement) {
        mainExtensionPointFile( modelElement as Container, javaGenFile.className)
    }
    
    def mainExtensionPointFile(Container container, String className) '''
        «extensionHeader(this)»
        package «feature_package()»;
        
        import org.eclipse.graphiti.features.IFeatureProvider;
        
        public class «className» extends «className»Base {
            public «className»(IFeatureProvider fp) {
                super(fp);
            }
        
        }
    '''

    def mainFile(Container container, String className) '''
        «var diagramName = container.represents.diagram.name »
        «var pack = container.represents.type.EPackage.name »
        «var fullPackage = fullPackageName(container.represents.type) »
        «var containerType = constainerClass(container)»
        «header(this)»
        package «feature_package()»;
        
        import org.eclipse.emf.common.util.EList;
        import org.eclipse.emf.ecore.EObject;
        import org.eclipse.graphiti.features.IFeatureProvider;
        import org.eclipse.graphiti.features.context.ILayoutContext;
        import org.eclipse.graphiti.features.context.IContext;
        import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
        import org.eclipse.graphiti.mm.pictograms.ContainerShape;
        import org.eclipse.graphiti.mm.pictograms.PictogramElement;
        import «util_package()».«containerType»;
        // MARKER_IMPORT
        
        public class «className» extends  AbstractLayoutFeature {
        
            private static final int MIN_HEIGHT = 30;
            private static final int MIN_WIDTH = 50;
            protected «containerType» container = null;
        
            public «className»(IFeatureProvider fp) {
                super(fp);
                container = new «containerType»();
            «IF container.layout.figure.matches(".*Concept.*")»
                container.setConcept(true);
            «ENDIF»
            }
         
            public boolean canLayout(ILayoutContext context) {
               PictogramElement pe = context.getPictogramElement();
               if (!(pe instanceof ContainerShape)) {
                   return false;
               }
               EList<EObject> businessObjects = pe.getLink().getBusinessObjects();
               return (businessObjects.size() == 1) && (businessObjects.get(0) instanceof «container.represents.type.javaInterfaceName.shortName»);
            }
         
            public boolean layout(ILayoutContext context) {
                return container.layoutContainer(context);
            }
        
            @Override
            public boolean hasDoneChanges() {
                return false;
            }
        
            @Override
            public boolean canUndo(IContext context) {
                return false;
            }
        
        }
        '''
}