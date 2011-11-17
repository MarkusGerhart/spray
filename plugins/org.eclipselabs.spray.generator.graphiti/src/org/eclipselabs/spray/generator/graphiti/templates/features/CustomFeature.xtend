package org.eclipselabs.spray.generator.graphiti.templates.features

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.xtend2.lib.StringConcatenation
import org.eclipselabs.spray.mm.spray.CustomBehavior
import org.eclipselabs.spray.generator.graphiti.templates.FileGenerator

import static org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil.*


class CustomFeature extends FileGenerator  {
    
    override StringConcatenation generateBaseFile(EObject modelElement) {
        mainFile( modelElement as CustomBehavior, javaGenFile.baseClassName)
    }

    override StringConcatenation generateExtensionFile(EObject modelElement) {
        mainExtensionPointFile( modelElement as CustomBehavior, javaGenFile.className)
    }
    
    def mainExtensionPointFile(CustomBehavior metaClass, String className) '''    
        «extensionHeader(this)»
        package «feature_package()»;
        
        import org.eclipse.emf.ecore.EObject;
        import org.eclipse.graphiti.features.IFeatureProvider;
        import org.eclipse.graphiti.features.context.ICustomContext;
        
        public class «className» extends «className»Base {
            public «className»(IFeatureProvider fp) {
                super(fp);
            }
        
            @Override
            public void execute(ICustomContext context, EObject object) {
                // TODO add code here.
            }
        
        }
    '''

    def mainFile(CustomBehavior behavior, String className) '''
        «var diagramName = behavior.metaClass.diagram.name »
        «header(this)»
        package «feature_package()»;
        
        import org.eclipse.emf.ecore.EObject;
        import org.eclipse.graphiti.features.IFeatureProvider;
        import org.eclipse.graphiti.features.context.ICustomContext;
        import org.eclipse.graphiti.mm.pictograms.PictogramElement;
        import org.eclipselabs.spray.runtime.graphiti.features.AbstractCustomFeature;
        
        public abstract class «className» extends AbstractCustomFeature {
            «generate_additionalFields(behavior)»
        
            public «className»(IFeatureProvider fp) {
                super(fp);
            }
        
            @Override
            public String getName() {
                return "«behavior.label»"; //$NON-NLS-1$
            }
        
            @Override
            public String getDescription() {
                return "«behavior.label» description"; //$NON-NLS-1$
            }
        
            «generate_canExecute(behavior)»
            «generate_execute(behavior)»
            «generate_additionalFields(behavior)»
        }
    '''
    
    def generate_canExecute (CustomBehavior behavior) '''
        «overrideHeader»
        public boolean canExecute(ICustomContext context) {
            // allow rename if exactly one pictogram element
            // representing an EClass is selected
            boolean ret = true;
            PictogramElement[] pes = context.getPictogramElements();
            if (pes != null && pes.length == 1) {
                EObject bo = (EObject) getBusinessObjectForPictogramElement(pes[0]);
                ret = canExecute (context, bo);
            }
            return ret;
        } 

        protected boolean canExecute(ICustomContext context, EObject bo) {
            return true;
        }
    '''
    
    def generate_execute (CustomBehavior behavior) '''
        «overrideHeader»
        public void execute(ICustomContext context) {
            PictogramElement[] pes = context.getPictogramElements();
            if (pes != null && pes.length == 1) {
                EObject bo = (EObject) getBusinessObjectForPictogramElement(pes[0]);
                execute(context, bo);
            }
        }
        
        public abstract void execute(ICustomContext context, EObject object);
    '''
}