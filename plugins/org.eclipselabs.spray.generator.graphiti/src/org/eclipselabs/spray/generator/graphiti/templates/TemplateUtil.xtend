package org.eclipselabs.spray.generator.graphiti.templates

import com.google.inject.Inject
import org.eclipse.xtext.xbase.compiler.ImportManager
import org.eclipselabs.spray.generator.graphiti.util.SprayCompiler
import org.eclipselabs.spray.mm.spray.TextInSpray

import static org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil.*
import org.eclipse.xtend2.lib.StringConcatenation
import org.eclipse.emf.ecore.EReference

class TemplateUtil extends Object {
    @Inject SprayCompiler compiler
    
    def header(Object templateClass) '''
        /*************************************************************************************
         *
         * Generated «timestamp()»by Spray «templateClass.getClass().simpleName».xtend
         *
         * This file contains generated and should not be changed.
         * Use the extension point class (the direct subclass of this class) to add manual code
         *
         *************************************************************************************/
    '''

    def extensionHeader(Object templateClass) '''
        /*************************************************************************************
         *
         * Generated «timestamp()»by Spray «templateClass.getClass().simpleName».xtend
         * 
         * This file is an extension point: copy to "src" folder to manually add code to this
         * extension point.
         *
         *************************************************************************************/
    '''

    def pluginHeader(Object templateClass) '''
        <!--
         Generated «timestamp()»by Spray «templateClass.getClass().simpleName».xtend
         This file will be generated oinly once if it need to be regenerated, remove this file.
         
        -->
    '''

    /**
     * Generate the full expression to calculate the value of a Text, existing of string literals and navigation expressions
     */
    def CharSequence valueGenerator(TextInSpray text, String metaClassVariable)    {
        try {
            compiler.metaClassVariable = metaClassVariable
            val body = compiler.compile(text.value, new ImportManager(false))
            val result = new StringConcatenation()
            result.append(body)
            return result
        } finally {
            compiler.metaClassVariable = null
        }
    }
    
    /**
     * Generate a unique key for the full expression to be used in map storage
     */
    def keyGenerator(TextInSpray text) {
        return text.value.toString
    }
    
    /**
     * Generate an Override annotation and Javadoc inheritance 
     */
    def overrideHeader () '''
        /**
         * {@inheritDoc}
         */
        @Override
    '''
    
    def addOrSetReference(EReference ref, String containerName, String elementName)'''
        «IF ref.many»
            «containerName».get«ref.name.toFirstUpper»().add(«elementName»);
        «ELSE»
            «containerName».set«ref.name.toFirstUpper»(«elementName»);
        «ENDIF»
    '''
}