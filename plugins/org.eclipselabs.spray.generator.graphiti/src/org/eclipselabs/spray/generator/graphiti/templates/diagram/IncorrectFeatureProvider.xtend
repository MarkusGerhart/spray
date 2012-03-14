package org.eclipselabs.spray.generator.graphiti.templates.diagram

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import org.eclipselabs.spray.generator.graphiti.util.XtendProperties

class IncorrectFeatureProvider {
    
    def finalError(EPackage pack, String className) '''
        «FOR cls : pack.EClassifiers.filter(typeof(EClass))»
            «XtendProperties::setValue("className", cls.name)»
            «FOR attribute : cls.EAllAttributes»
                «XtendProperties::setValue("attName", attribute.name)»
«««                «var target = cls.EAllReferences.findFirst(e| XtendProperties::getValue("className") == XtendProperties::getValue("attName") ) » 
            «ENDFOR»
        «ENDFOR»
        }
    '''
    
    def String eClassName(EClass cls) {
        return cls.name
    }
}