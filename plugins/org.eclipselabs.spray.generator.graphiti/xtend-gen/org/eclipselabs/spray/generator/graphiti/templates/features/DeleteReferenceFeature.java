package org.eclipselabs.spray.generator.graphiti.templates.features;

import com.google.inject.Inject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xtend2.lib.StringConcatenation;
import org.eclipselabs.spray.generator.graphiti.templates.FileGenerator;
import org.eclipselabs.spray.generator.graphiti.templates.JavaGenFile;
import org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil;
import org.eclipselabs.spray.generator.graphiti.util.NamingExtensions;
import org.eclipselabs.spray.mm.spray.MetaClass;
import org.eclipselabs.spray.mm.spray.MetaReference;

@SuppressWarnings("all")
public class DeleteReferenceFeature extends FileGenerator {
  @Inject
  private NamingExtensions _namingExtensions;
  
  public StringConcatenation generateBaseFile(final EObject modelElement) {
    JavaGenFile _javaGenFile = this.getJavaGenFile();
    String _baseClassName = _javaGenFile.getBaseClassName();
    StringConcatenation _mainFile = this.mainFile(((MetaReference) modelElement), _baseClassName);
    return _mainFile;
  }
  
  public StringConcatenation generateExtensionFile(final EObject modelElement) {
    JavaGenFile _javaGenFile = this.getJavaGenFile();
    String _className = _javaGenFile.getClassName();
    StringConcatenation _mainExtensionPointFile = this.mainExtensionPointFile(((MetaReference) modelElement), _className);
    return _mainExtensionPointFile;
  }
  
  public StringConcatenation mainExtensionPointFile(final MetaReference metaReference, final String className) {
    StringConcatenation _builder = new StringConcatenation();
    StringConcatenation _extensionHeader = this.extensionHeader(this);
    _builder.append(_extensionHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.append("package ");
    String _feature_package = GeneratorUtil.feature_package();
    _builder.append(_feature_package, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.IFeatureProvider;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    _builder.append(className, "");
    _builder.append(" extends ");
    _builder.append(className, "");
    _builder.append("Base {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("public ");
    _builder.append(className, "    ");
    _builder.append("(IFeatureProvider fp) {");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("super(fp);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public StringConcatenation mainFile(final MetaReference reference, final String className) {
    StringConcatenation _builder = new StringConcatenation();
    StringConcatenation _header = this.header(this);
    _builder.append(_header, "");
    _builder.newLineIfNotEmpty();
    _builder.append("package ");
    String _feature_package = GeneratorUtil.feature_package();
    _builder.append(_feature_package, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.eclipse.emf.ecore.EObject;");
    _builder.newLine();
    _builder.append("import org.eclipse.emf.ecore.util.EcoreUtil;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.IFeatureProvider;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.IRemoveFeature;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.context.IDeleteContext;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.context.IRemoveContext;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.context.impl.RemoveContext;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.AnchorContainer;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.Connection;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.PictogramElement;");
    _builder.newLine();
    _builder.append("import org.eclipselabs.spray.runtime.graphiti.features.DefaultDeleteFeature;");
    _builder.newLine();
    _builder.append("// MARKER_IMPORT");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    _builder.append(className, "");
    _builder.append(" extends DefaultDeleteFeature {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    StringConcatenation _generate_additionalFields = this.generate_additionalFields(reference);
    _builder.append(_generate_additionalFields, "    ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public ");
    _builder.append(className, "    ");
    _builder.append("(IFeatureProvider fp) {");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("super(fp);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    StringConcatenation _generate_delete = this.generate_delete(reference);
    _builder.append(_generate_delete, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    StringConcatenation _generate_deleteReferences = this.generate_deleteReferences(reference);
    _builder.append(_generate_deleteReferences, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    StringConcatenation _generate_deleteReference = this.generate_deleteReference(reference);
    _builder.append(_generate_deleteReference, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    StringConcatenation _generate_additionalFields_1 = this.generate_additionalFields(reference);
    _builder.append(_generate_additionalFields_1, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public StringConcatenation generate_delete(final MetaReference reference) {
    StringConcatenation _builder = new StringConcatenation();
    StringConcatenation _overrideHeader = this.overrideHeader();
    _builder.append(_overrideHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.append("public void delete(IDeleteContext context) {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("PictogramElement pe = context.getPictogramElement();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("String reference = peService.getPropertyValue(pe, \"REFERENCE\");");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("String element   = peService.getPropertyValue(pe, \"TARGETOBJECT\");");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("EObject[] businessObjectsForPictogramElement = getAllBusinessObjectsForPictogramElement(pe);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("if (businessObjectsForPictogramElement != null && businessObjectsForPictogramElement.length > 0) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("if (!getUserDecision()) {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("return;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("preDelete(context);");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("// TRY");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("if( pe instanceof Connection) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("Connection line = (Connection)pe;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("AnchorContainer parent = line.getStart().getParent();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("// EObject start = getBusinessObjectForPictogramElement(parent);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("AnchorContainer child = line.getEnd().getParent();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("// EObject end = getBusinessObjectForPictogramElement(child);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("//END TRY");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("IRemoveContext rc = new RemoveContext(pe);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("IFeatureProvider featureProvider = getFeatureProvider();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("IRemoveFeature removeFeature = featureProvider.getRemoveFeature(rc);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("if (removeFeature != null) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("removeFeature.remove(rc);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("deleteReferences(businessObjectsForPictogramElement, reference, element);");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("postDelete(context);");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public StringConcatenation generate_deleteReferences(final MetaReference reference) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Delete business objects.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* ");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* @param businessObjects");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*            the business objects");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("protected void deleteReferences(EObject[] businessObjects, String reference, String element) {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("if (businessObjects != null) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("for (EObject bo : businessObjects) {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("deleteReference(bo, reference, element);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public StringConcatenation generate_deleteReference(final MetaReference reference) {
    StringConcatenation _builder = new StringConcatenation();
    EReference _target = reference.getTarget();
    final EReference target = _target;
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Delete business object.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* ");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* @param bo");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*            the bo");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("protected void deleteReference(EObject bo, String reference, String element) {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("if( reference == null){");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("EcoreUtil.delete(bo, true);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("} else {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("if( bo instanceof ");
    MetaClass _metaClass = reference.getMetaClass();
    String _javaInterfaceName = this._namingExtensions.getJavaInterfaceName(_metaClass);
    String _shortName = this.shortName(_javaInterfaceName);
    _builder.append(_shortName, "        ");
    _builder.append(" ){");
    _builder.newLineIfNotEmpty();
    _builder.append("            ");
    MetaClass _metaClass_1 = reference.getMetaClass();
    String _name = this._namingExtensions.getName(_metaClass_1);
    _builder.append(_name, "            ");
    _builder.append(" object = (");
    MetaClass _metaClass_2 = reference.getMetaClass();
    String _name_1 = this._namingExtensions.getName(_metaClass_2);
    _builder.append(_name_1, "            ");
    _builder.append(" ) bo;");
    _builder.newLineIfNotEmpty();
    _builder.append("            ");
    _builder.newLine();
    {
      int _upperBound = target.getUpperBound();
      boolean _operator_notEquals = ObjectExtensions.operator_notEquals(((Integer)_upperBound), ((Integer)1));
      if (_operator_notEquals) {
        _builder.append("    ");
        EClass _eReferenceType = target.getEReferenceType();
        String _javaInterfaceName_1 = this._namingExtensions.getJavaInterfaceName(_eReferenceType);
        String _shortName_1 = this.shortName(_javaInterfaceName_1);
        _builder.append(_shortName_1, "    ");
        _builder.append(" toBeRemoved = null;");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("for (");
        EClass _eReferenceType_1 = target.getEReferenceType();
        String _name_2 = _eReferenceType_1.getName();
        _builder.append(_name_2, "    ");
        _builder.append(" rule : object.get");
        String _name_3 = target.getName();
        String _firstUpper = StringExtensions.toFirstUpper(_name_3);
        _builder.append(_firstUpper, "    ");
        _builder.append("()) {");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("if( rule.getName().equals(element)){");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("        ");
        _builder.append("toBeRemoved = rule;");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("}");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("}    ");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("if( toBeRemoved != null ){");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("object.get");
        String _name_4 = target.getName();
        String _firstUpper_1 = StringExtensions.toFirstUpper(_name_4);
        _builder.append(_firstUpper_1, "        ");
        _builder.append("().remove(toBeRemoved);");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("// TODO Must remove toBeRemoved if it is a containment reference !");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("}");
        _builder.newLine();
      } else {
        _builder.append("    ");
        _builder.append("object.set");
        String _name_5 = this._namingExtensions.getName(reference);
        String _firstUpper_2 = StringExtensions.toFirstUpper(_name_5);
        _builder.append(_firstUpper_2, "    ");
        _builder.append("(null);");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("        ");
    _builder.append("} else {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("System.out.println(\"DELETE OBJECT \" + bo);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
