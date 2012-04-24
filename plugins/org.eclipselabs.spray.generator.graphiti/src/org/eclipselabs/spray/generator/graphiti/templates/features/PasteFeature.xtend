package org.eclipselabs.spray.generator.graphiti.templates.features

import com.google.inject.Inject
import static org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil.*
import org.eclipselabs.spray.generator.graphiti.templates.FileGenerator
import org.eclipselabs.spray.generator.graphiti.util.NamingExtensions
import org.eclipselabs.spray.mm.spray.Diagram

class PasteFeature extends FileGenerator<Diagram>{
	
	@Inject extension NamingExtensions
        
    override generateExtensionFile(Diagram modelElement) {
		modelElement.mainExtensionPointFile(javaGenFile.className);
	}
	
	override generateBaseFile(Diagram modelElement) {
		modelElement.mainFile(javaGenFile.baseClassName)
	}
 	
 	def mainExtensionPointFile(Diagram metaClass, String className) '''    
        «extensionHeader(this)»
        package «feature_package()»;
        
        import org.eclipse.graphiti.features.IFeatureProvider;
        import org.eclipse.graphiti.features.context.ICustomContext;
        
        public class «className» extends «className»Base {
            public «className»(IFeatureProvider fp) {
                super(fp);
            }
        }
    '''

    def mainFile(Diagram metaclass, String className) '''
        «header(this)» 
        package «feature_package()»;
        
        import org.eclipse.graphiti.features.IFeatureProvider;
        import org.eclipse.graphiti.features.context.IPasteContext;
        import org.eclipse.graphiti.features.context.impl.AddContext;
        import org.eclipse.graphiti.mm.Property;
        import org.eclipse.graphiti.mm.pictograms.PictogramElement;
        import org.eclipse.graphiti.mm.pictograms.Diagram;
        import org.eclipse.graphiti.services.Graphiti;
        import org.eclipse.graphiti.services.IPeService;
        import org.eclipse.graphiti.ui.features.AbstractPasteFeature;
        import «metaclass.modelType.javaInterfaceName»;
        // MARKER_IMPORT
        
        public abstract class «className» extends AbstractPasteFeature  {
            «generate_additionalFields(metaclass)»
            
            public «className»(IFeatureProvider fp) {
                super(fp);
            }
            
            «generate_canPaste(metaclass)»
            «generate_paste(metaclass)»
        }
    '''

	def generate_canPaste(Diagram metaclass)'''
		«overrideHeader»
		public boolean canPaste(IPasteContext context) {
		    // TODO: only support pasting directly in the diagram
		    PictogramElement[] pes = context.getPictogramElements();
		    if (pes.length != 1 || !(pes[0] instanceof Diagram)) {
		        return false;
		    }
		    // can paste, if all objects on the clipboard are PictogramElements with link on subclasses of «metaclass.modelType.itfName»
		    Object[] fromClipboard = getFromClipboard();
		    if (fromClipboard == null || fromClipboard.length == 0) {
		        return false;
		    }
		    for (Object object : fromClipboard) {
		        if (!(object instanceof PictogramElement)) {
		            return false;
		        } else if(!(getBusinessObjectForPictogramElement((PictogramElement) object) instanceof «metaclass.modelType.itfName»)) {
		            return false;
		        }
		    }
		    return true;
		}
    '''
    
    def generate_paste(Diagram metaclass)'''
		«overrideHeader»
		public void paste(IPasteContext context) {
			// already verified, that pasting is allowed just directly in the diagram
			PictogramElement[] pes = context.getPictogramElements();
			Diagram diagram = (Diagram) pes[0];
			
			// get the PictogramElements from the clipboard and the linked business object. 
			Object[] objects = getFromClipboard();
			for (Object object : objects) {
			    PictogramElement pictogramElement = (PictogramElement) object;
			    Object bo = getBusinessObjectForPictogramElement(pictogramElement);
			    // create a new AddContext for the creation of a new shape.
			    AddContext ac = new AddContext(new AddContext(), bo);
			    ac.setLocation(0, 0); // for simplicity paste at (0, 0)
			    ac.setTargetContainer(diagram); // paste on diagram
			    // copy all properties from the shape (e.g. ALIAS etc.)
			    for(Property prop : pictogramElement.getProperties()) {
			        ac.putProperty(prop.getKey(), prop.getValue());
			    }
			    getFeatureProvider().addIfPossible(ac);   
			}
		}
    '''
}