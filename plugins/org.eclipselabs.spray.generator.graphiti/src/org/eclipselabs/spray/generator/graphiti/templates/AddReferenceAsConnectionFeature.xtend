package org.eclipselabs.spray.generator.graphiti.templates

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.xtend2.lib.StringConcatenation
import org.eclipselabs.spray.generator.graphiti.util.LayoutExtensions
import org.eclipselabs.spray.mm.spray.MetaReference
import org.eclipselabs.spray.mm.spray.extensions.SprayExtensions
import org.eclipselabs.spray.xtext.util.GenModelHelper

import static org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil.*


class AddReferenceAsConnectionFeature extends FileGenerator  {
    @Inject extension SprayExtensions
    @Inject extension LayoutExtensions
    @Inject extension GenModelHelper
    
    override StringConcatenation generateBaseFile(EObject modelElement) {
        mainFile( modelElement as MetaReference, javaGenFile.baseClassName)
    }

    override StringConcatenation generateExtensionFile(EObject modelElement) {
        mainExtensionPointFile( modelElement as MetaReference, javaGenFile.className)
    }
    
    def mainExtensionPointFile(MetaReference metaReference, String className) '''    
        «extensionHeader(this)»
        package «feature_package()»;
        
        import org.eclipse.graphiti.features.IFeatureProvider;
        
        public class «className» extends «className»Base {
            public «className»(IFeatureProvider fp) {
                super(fp);
            }
        }
    '''
    
    def mainFile(MetaReference reference, String className) '''
        «val target=reference.reference»
        «header(this)»
        package «feature_package()»;
        import org.eclipse.emf.ecore.EObject;
        import org.eclipse.graphiti.features.IFeatureProvider;
        import org.eclipse.graphiti.features.context.IAddConnectionContext;
        import org.eclipse.graphiti.features.context.IAddContext;
        import org.eclipse.graphiti.features.context.IContext;
        import org.eclipse.graphiti.features.impl.AbstractAddFeature;
        import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
        import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
        import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
        import org.eclipse.graphiti.mm.pictograms.Connection;
        import org.eclipse.graphiti.mm.pictograms.PictogramElement;
        import org.eclipse.graphiti.mm.algorithms.Polyline;
        import org.eclipse.graphiti.services.Graphiti;
        import org.eclipse.graphiti.services.IGaService;
        import org.eclipse.graphiti.services.IPeCreateService;
        // MARKER_IMPORT
        
        public class «className» extends  AbstractAddFeature {
            public «className»(IFeatureProvider fp) {
                super(fp);
            }
         
            public PictogramElement add(IAddContext context) {
                IAddConnectionContext addConContext = (IAddConnectionContext) context;
                «reference.metaClass.type.javaInterfaceName.shortName» addedDomainObject = («reference.metaClass.name») context.getNewObject();
            «IF target.upperBound == 1»
                removeExisting(context);
            «ENDIF»        
                IPeCreateService peCreateService = Graphiti.getPeCreateService();
              
                // CONNECTION WITH POLYLINE
                Connection connection = peCreateService.createFreeFormConnection(getDiagram());
                connection.setStart(addConContext.getSourceAnchor());
                connection.setEnd(addConContext.getTargetAnchor());
         
                // TRY
                AnchorContainer parent = connection.getStart().getParent();
                Object start = getBusinessObjectForPictogramElement(parent);
                AnchorContainer child = connection.getEnd().getParent();
                Object end = getBusinessObjectForPictogramElement(child);
                //END TRY
        
                IGaService gaService = Graphiti.getGaService();
                Polyline polyline = gaService.createPolyline(connection);
                polyline.setLineWidth(«reference.representedBy.layout.lineWidth»);
                polyline.setForeground(manageColor(«reference.lineColor»));
                 
                // create link and wire it
                Graphiti.getPeService().setPropertyValue(connection, "MODEL_TYPE", "«reference.metaClass.name».«target.name»");
                Graphiti.getPeService().setPropertyValue(connection, "REFERENCE", (String)context.getProperty("REFERENCE"));
                Graphiti.getPeService().setPropertyValue(connection, "TARGETOBJECT", (String)context.getProperty("TARGETOBJECT"));
         //       link(connection, addedDomainObject);
        
                // add static graphical decorator
                ConnectionDecorator cd;
                cd = peCreateService.createConnectionDecorator(connection, false, 1.0, true);
        //      No arraows
        //        createArrow(cd);
        
                return connection;
            }
        
            public boolean canAdd(IAddContext context) {
                // return true if given business object is an «reference.metaClass.name»
                // note, that the context must be an instance of IAddConnectionContext
                if (context instanceof IAddConnectionContext
                    && context.getNewObject() instanceof «reference.metaClass.name») {
                    return true;
                }
                return false;
            }
            
            protected void removeExisting(IAddContext context) {
                IAddConnectionContext addConContext = (IAddConnectionContext) context;
                «reference.metaClass.name» addedDomainObject = («reference.metaClass.name») context.getNewObject();
                Object[] pictogramElements = Graphiti.getPeService().getLinkedPictogramElements(new EObject[] { addedDomainObject }, getDiagram());
                for (Object pict : pictogramElements) {
                    if( pict instanceof PictogramElement){
                        PictogramElement p = (PictogramElement)pict;
                        String reference = Graphiti.getPeService().getPropertyValue(p, "REFERENCE");
                        if( "«reference.name»".equals(reference)){
                            Graphiti.getPeService().deletePictogramElement(p) ;
                        }
                    }
                }
            }
        
            private Polyline createArrow(GraphicsAlgorithmContainer gaContainer) {
                Polyline polyline = Graphiti.getGaCreateService().createPolyline(gaContainer, new int[] { -15, 10, 0, 0, -15, -10 });
        //        polyline.setStyle(StyleUtil.getStyleForEClass(getDiagram()));
                polyline.setLineWidth(«reference.representedBy.layout.lineWidth»);
                polyline.setForeground(manageColor(«reference.lineColor»));
                return polyline;
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