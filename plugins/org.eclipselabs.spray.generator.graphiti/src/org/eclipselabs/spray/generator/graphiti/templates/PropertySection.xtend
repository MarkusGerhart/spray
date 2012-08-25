package org.eclipselabs.spray.generator.graphiti.templates

import com.google.inject.Inject
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EEnum
import org.eclipselabs.spray.generator.graphiti.util.NamingExtensions
import org.eclipselabs.spray.mm.spray.Diagram

import static org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil.*
import static org.eclipselabs.spray.generator.graphiti.util.MetaModel.*


class PropertySection extends FileGenerator<EAttribute>  {
    @Inject extension NamingExtensions
    
    Diagram diagram
    
    def setDiagram(Diagram d){
        diagram = d
    }
    
    override CharSequence generateBaseFile(EAttribute modelElement) {
        mainFile( modelElement, javaGenFile.baseClassName)
    }

    override CharSequence generateExtensionFile(EAttribute modelElement) {
        mainExtensionPointFile( modelElement, javaGenFile.className)
    }
    
    def mainExtensionPointFile(EAttribute eAttribute, String className) '''    
        «extensionHeader(this)»
        package «property_package()»;
        
        public class «className» extends «className»Base {
            
        }
        
    '''

    def mainFile(EAttribute eAttribute, String className) '''
        «val eClass = eAttribute.EContainingClass»
        «val propertyName = eAttribute.name» 
        «val isEnum = eAttribute.EAttributeType instanceof EEnum »
        «val isBoolean = eAttribute.EAttributeType.name == "EBoolean" »
        «header(this)»
        package «property_package()»;
        
        import org.eclipse.emf.ecore.EObject;
        import org.eclipse.emf.transaction.RecordingCommand;
        import org.eclipse.emf.transaction.TransactionalEditingDomain;
        import org.eclipse.graphiti.mm.pictograms.PictogramElement;
        import org.eclipse.graphiti.services.Graphiti;
        import org.eclipse.graphiti.ui.platform.GFPropertySection;
        import org.eclipse.graphiti.ui.internal.services.GraphitiUiInternal;
        import org.eclipse.jface.action.IStatusLineManager;
        import org.eclipse.swt.SWT;
        import org.eclipse.swt.custom.CLabel;
        import org.eclipse.swt.events.FocusEvent;
        import org.eclipse.swt.events.FocusListener;
        import org.eclipse.swt.events.ModifyEvent;
        import org.eclipse.swt.events.ModifyListener;
        import org.eclipse.swt.events.SelectionAdapter;
        import org.eclipse.swt.events.SelectionEvent;
        import org.eclipse.swt.events.SelectionListener;
        import org.eclipse.swt.layout.FormAttachment;
        import org.eclipse.swt.layout.FormData;
        import org.eclipse.swt.widgets.Composite;
        import org.eclipse.swt.widgets.Text;
        import org.eclipse.swt.custom.CCombo;
        import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
        import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
        import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;
        import java.util.List;
        import «eClass.javaInterfaceName»;
        «IF isEnum»
        import «eAttribute.EAttributeType.javaInterfaceName»;
        «ENDIF»
        // MARKER_IMPORT
        
        public abstract class «className» extends GFPropertySection implements ITabbedPropertyConstants {
         
            protected «eClass.itfName» bc = null;
            «IF isEnum || isBoolean»        
            protected CCombo «propertyName»Widget;
            «ELSE»
            protected Text «propertyName»Widget;
            «ENDIF»
            «generate_additionalFields(eAttribute)»
         
            @Override
            public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
                super.createControls(parent, tabbedPropertySheetPage);
         
                final TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
                final Composite composite = factory.createFlatFormComposite(parent);
                FormData data;
        
            «IF isEnum || isBoolean»        
                «propertyName»Widget = factory.createCCombo(composite);
            «ELSE»
                «propertyName»Widget = factory.createText(composite, "");
            «ENDIF»
                data = new FormData();
                data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
                data.right = new FormAttachment(100, 0);
                data.top = new FormAttachment(0, VSPACE);
                «propertyName»Widget.setLayoutData(data);
         
                CLabel valueLabel = factory.createCLabel(composite, "«propertyName.toFirstUpper»");
                data = new FormData();
                data.left = new FormAttachment(0, 0);
                data.right = new FormAttachment(«propertyName»Widget, -HSPACE);
                data.top = new FormAttachment(«propertyName»Widget, 0, SWT.CENTER);
                valueLabel.setLayoutData(data);
        }
         
        «IF ! (isEnum || isBoolean)»
            @Override
            public void refresh() {
                «propertyName»Widget.removeModifyListener(nameListener);

                final PictogramElement pe = getSelectedPictogramElement();
                if (pe != null) {
                    final EObject bo = (EObject) Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
                   // the filter assured, that it is a «eClass.name»
                    if (bo == null)
                        return;
                    bc = («eClass.itfName»)bo;
                    String value = "";
            «IF eAttribute.EAttributeType.name == "EString"»
                value = bc.get«propertyName.toFirstUpper»();
            «ELSEIF eAttribute.EAttributeType.name == "EInt"»
                value = Integer.toString( bc.get«propertyName.toFirstUpper»() );
            «ELSE»
                value = "unknown";
            «ENDIF»
                    «propertyName»Widget.setText(value == null ? "" : value);
                    «propertyName»Widget.addModifyListener(nameListener);
                }
            }

            private ModifyListener nameListener = new ModifyListener() {
                public void modifyText(ModifyEvent arg0) {
                    TransactionalEditingDomain editingDomain = getDiagramEditor().getEditingDomain();
                    editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
                        @Override
                        protected void doExecute() {
                            changePropertyValue();
                        }
                    });
                }
            };
        «ENDIF»
        
        «IF ! (isEnum || isBoolean)»
            protected void changePropertyValue(){
                String newValue = «propertyName»Widget.getText();
            «IF ( ! eAttribute.derived) && (eAttribute.changeable)»
                «IF eAttribute.EAttributeType.name == "EBoolean"»
                    String oldValue = ( bc.is«propertyName.toFirstUpper»() ? "true" : "false" );
                    if( ! newValue.equals(oldValue) ) { 
                        bc.set«propertyName.toFirstUpper»(! bc.is«propertyName.toFirstUpper»() );
                    }
                «ELSEIF eAttribute.EAttributeType.name == "EString"»
                    if( ! newValue.equals(bc.get«propertyName.toFirstUpper»() ) ) { 
                        bc.set«propertyName.toFirstUpper»(newValue);
                    }
                «ELSEIF eAttribute.EAttributeType.name == "EInt"»
                    try {
                        int newIntValue = Integer.parseInt(newValue);
                        bc.set«propertyName.toFirstUpper»( newIntValue );
                    } catch(Exception e) {
                           IStatusLineManager mgr = GraphitiUiInternal.getWorkbenchService().getActiveStatusLineManager();
                        mgr.setErrorMessage(e.getMessage() + " should be a number");
                    }
                «ENDIF»
            «ENDIF»
            }
        «ENDIF»

        «IF isEnum || isBoolean»
            @Override
            public void refresh() {
                «propertyName»Widget.removeSelectionListener(nameListener);
                «propertyName»Widget.setItems(getEnumerationFeatureValues());
                «propertyName»Widget.setText(getFeatureAsText());
                «propertyName»Widget.addSelectionListener(nameListener);
            }
            
           /**
             * 
             * @return An Array of all the String representations of Multiplicity enumeration values
             */
            protected String[] getEnumerationFeatureValues() {
            «IF isEnum»
                List<«eAttribute.EAttributeType.name»> values = «eAttribute.EAttributeType.name».VALUES;
                String[] ret = new String[values.size()];
                for (int i = 0; i < values.size(); i++) {
                    ret[i] = ((«eAttribute.EAttributeType.name») values.get(i)).getName();
                }
            «ELSEIF eAttribute.EAttributeType.name == "EBoolean"»
                String[] ret = new String[] {"false", "true"};
            «ENDIF»    
                return ret;
            }
        //        value = ( bc.is«propertyName.toFirstUpper»() ? "true" : "false" );
        
            /**
             * 
             * @return The string representation of the current value of 'sourceMultiplicity' of the selected Association
             */
            protected String getFeatureAsText() {
                final PictogramElement pe = getSelectedPictogramElement();
                if (pe != null) {
                    final EObject bo = (EObject) Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
                    // the filter assured, that it is a «eClass.name»
                    if (bo == null) {
                        return "unknown ";
                    }
                    bc = («eClass.itfName») bo;
            «IF isEnum»
                    if( bc.get«eAttribute.name.toFirstUpper»() == null ){
                        return "Null value for «eAttribute.name»";
                    }
                    return bc.get«eAttribute.name.toFirstUpper»().getName();
            «ELSEIF isBoolean»
                       «val name =  if( eAttribute.name.startsWith("is") ) eAttribute.name else  ("is" + eAttribute.name.toFirstUpper) »
                    return ( bc.«name»() ? "true" : "false" );
            «ENDIF»
                }
                return "unknown ";
            }
        
        
             
            private SelectionListener nameListener = new SelectionAdapter() {
                public void widgetSelected(SelectionEvent event) {
                    TransactionalEditingDomain editingDomain = getDiagramEditor().getEditingDomain();
                    editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
                        @Override
                        protected void doExecute() {
                            changePropertyValue();
                        }
                    });
                }
            };
        
            protected void changePropertyValue(){
                int index = «propertyName»Widget.getSelectionIndex();
            «IF ( ! eAttribute.derived) && (eAttribute.changeable)»
                «IF isEnum»
                    «eAttribute.EAttributeType.name» value = «eAttribute.EAttributeType.name».VALUES.get(index);
                    bc.set«eAttribute.name.toFirstUpper»(value);
                «ELSEIF isBoolean»
                       boolean newValue = (index == 0 ? false : true);
                       «val name =  if( eAttribute.name.startsWith("is") ) eAttribute.name else  ("is" + eAttribute.name.toFirstUpper)»
                    if( newValue != bc.«name»() ) { 
                        bc.set«eAttribute.name.toFirstUpper»(newValue );
                    }
                «ENDIF»
            «ENDIF»
            }
            «ENDIF»
        }
        «generate_additionalMethods(eAttribute)»
    '''
}