package org.eclipselabs.spray.generator.graphiti.templates

import com.google.inject.Inject
import org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil
import org.eclipselabs.spray.generator.graphiti.util.NamingExtensions
import org.eclipselabs.spray.generator.graphiti.util.XtendProperties
import org.eclipselabs.spray.mm.spray.ContainerInSpray
import org.eclipselabs.spray.mm.spray.Diagram
import org.eclipselabs.spray.mm.spray.MetaReference

class Plugin extends TemplateUtil {
    @Inject extension NamingExtensions
    
    def generate(Diagram diagram) '''
        «val diagramName = diagram.name»
<?xml version="1.0" encoding="UTF-8"?>
        <?eclipse version="3.0"?>
        «pluginHeader(this)»
        <plugin>
           <!-- Potentially redefine the EDITOR EXTENSION TO ALLOW FOR OUR OWN EDITOR SUBCLASS TO BE USED. 
           <extension
                 point="org.eclipse.ui.editors">
              <editor
                  class="«diagram.extensionFactoryClassName»:«diagram.diagramEditorClassName»"
                  contributorClass="org.eclipse.graphiti.ui.editor.DiagramEditorActionBarContributor"
                  default="true"
                  extensions="diagram"
                  icon="icons/diagram.gif"
                  id="«diagram.diagramEditorClassName»"
                  matchingStrategy="org.eclipse.graphiti.ui.editor.DiagramEditorFactory$DiagramEditorMatchingStrategy"
                  name="%_diagram_editor_name">
               <contentTypeBinding
                     contentTypeId="org.eclipse.graphiti.content.diagram">
               </contentTypeBinding>
              </editor>
           </extension>
           -->
        
          <extension
              point="org.eclipse.graphiti.ui.diagramTypes">
            <diagramType
              description="This is the diagram type for the «diagramName» diagram type"
              id="«GeneratorUtil::diagram_package()».«diagramName.toFirstUpper»DiagramType"
              name="«diagramName» Graphiti Diagram Type"
              type="«diagramName»">
            </diagramType>
          </extension>
        
          <extension
              point="org.eclipse.graphiti.ui.diagramTypeProviders">
            <diagramTypeProvider
              class="«diagram.extensionFactoryClassName»:«diagram.diagramTypeProviderClassName»"
              description="This is my editor for the «diagramName» diagram type"
              id="«diagram.diagramTypeProviderClassName»"
              name="«diagramName» editor">
              <diagramType
                    id="«GeneratorUtil::diagram_package()».«diagramName.toFirstUpper»DiagramType">
              </diagramType>
                <imageProvider
                       id="«diagram.imageProviderClassName»">
                 </imageProvider>
            </diagramTypeProvider>
          </extension>
        
           <extension
                 point="org.eclipse.graphiti.ui.imageProviders">
              <imageProvider
                    class="«diagram.extensionFactoryClassName»:«diagram.imageProviderClassName»"
                       id="«diagram.imageProviderClassName»">
              </imageProvider>
           </extension>
        
          <extension
              point="org.eclipse.ui.views.properties.tabbed.propertyContributor">
              <propertyContributor contributorId="«diagramName».PropertyContributor">
                  <propertyCategory category="«diagramName»Category">
                  </propertyCategory>
              </propertyContributor>
          </extension>
        
          <extension
              point="org.eclipse.ui.views.properties.tabbed.propertyTabs">
              <propertyTabs contributorId="«diagramName».PropertyContributor">
                  <propertyTab label="Main" category="«diagramName»Category"
                      id="«diagramName».main.tab">
                  </propertyTab>
              </propertyTabs>
          </extension>
        «FOR cls : diagram.metaClasses »
          «XtendProperties::setValue("PreviousSection", null)»
          <extension
              point="org.eclipse.ui.views.properties.tabbed.propertySections">
              <propertySections contributorId="«diagramName».PropertyContributor">
            «FOR property : cls.type.EAllAttributes»
                  <propertySection tab="«diagramName».main.tab"
                   class="«diagram.extensionFactoryClassName»:«cls.type.getPropertySectionClassName(property)»"
                   filter="«cls.filterClassName»"
                 «IF XtendProperties::getValue("PreviousSection") != null»
                   afterSection="«XtendProperties::getValue("PreviousSection")»"
                 «ENDIF»
                   «XtendProperties::setValue("PreviousSection", diagramName + ".main.tab." + cls.getName + "." + property.name)»
                   id="«XtendProperties::getValue("PreviousSection")»">
                  </propertySection>
            «ENDFOR»
              </propertySections>
          </extension>
        «ENDFOR»
    
    
        // Find all clases that are shown as lists in the compartments
        «FOR cls :  diagram.metaClasses »
            «IF cls.representedBy instanceof ContainerInSpray»
                «var container = (cls.representedBy as ContainerInSpray) »
                «FOR ref :  container.parts.filter(typeof(MetaReference)) »  
                    «XtendProperties::setValue("refName", ref.name)» 
                    «val references = cls.type.EAllReferences» 
                    «val target = ref.target»
                      «XtendProperties::setValue("PreviousSection", null)»
                    <extension
                      point="org.eclipse.ui.views.properties.tabbed.propertySections">
                      <propertySections contributorId="«diagramName».PropertyContributor">
                    «FOR attribute : target.EReferenceType.EAllAttributes»
                          <propertySection tab="«diagramName».main.tab"           
                           class="«diagram.extensionFactoryClassName»:«GeneratorUtil::property_package()».«target.EReferenceType.name»«attribute.name.toFirstUpper»Section"
                           filter="«GeneratorUtil::property_package()».«target.EReferenceType.name»Filter"
                         «IF XtendProperties::getValue("PreviousSection") != null»
                          afterSection="«XtendProperties::getValue("PreviousSection")»"
                         «ENDIF»
                           «XtendProperties::setValue("PreviousSection", diagramName + ".main.tab." + target.EReferenceType.name + "." + attribute.name)»
                           id="«XtendProperties::getValue("PreviousSection")»">
                          </propertySection>
                    «ENDFOR»
                      </propertySections>
                  </extension>
                «ENDFOR»
            «ENDIF»
        «ENDFOR»
        </plugin>
    '''
}
