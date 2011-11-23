package org.eclipselabs.spray.generator.graphiti.templates.features;

import com.google.inject.Inject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xtend2.lib.StringConcatenation;
import org.eclipselabs.spray.generator.graphiti.templates.FileGenerator;
import org.eclipselabs.spray.generator.graphiti.templates.JavaGenFile;
import org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil;
import org.eclipselabs.spray.generator.graphiti.util.MetaModel;
import org.eclipselabs.spray.generator.graphiti.util.NamingExtensions;
import org.eclipselabs.spray.generator.graphiti.util.mm.DiagramExtensions;
import org.eclipselabs.spray.mm.spray.Connection;
import org.eclipselabs.spray.mm.spray.Diagram;
import org.eclipselabs.spray.mm.spray.MetaClass;
import org.eclipselabs.spray.mm.spray.Text;

@SuppressWarnings("all")
public class UpdateConnectionFeature extends FileGenerator {
  @Inject
  private NamingExtensions _namingExtensions;
  
  @Inject
  private DiagramExtensions _diagramExtensions;
  
  public StringConcatenation generateBaseFile(final EObject modelElement) {
    JavaGenFile _javaGenFile = this.getJavaGenFile();
    String _baseClassName = _javaGenFile.getBaseClassName();
    StringConcatenation _mainFile = this.mainFile(((Connection) modelElement), _baseClassName);
    return _mainFile;
  }
  
  public StringConcatenation generateExtensionFile(final EObject modelElement) {
    JavaGenFile _javaGenFile = this.getJavaGenFile();
    String _className = _javaGenFile.getClassName();
    StringConcatenation _mainExtensionPointFile = this.mainExtensionPointFile(((Connection) modelElement), _className);
    return _mainExtensionPointFile;
  }
  
  public StringConcatenation mainExtensionPointFile(final Connection connection, final String className) {
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
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public StringConcatenation mainFile(final Connection connection, final String className) {
    StringConcatenation _builder = new StringConcatenation();
    MetaClass _represents = connection.getRepresents();
    Diagram _diagram = _represents.getDiagram();
    String _name = _diagram.getName();
    final String diagramName = _name;
    _builder.newLineIfNotEmpty();
    MetaClass _represents_1 = connection.getRepresents();
    String _name_1 = this._namingExtensions.getName(_represents_1);
    final String metaClassName = _name_1;
    _builder.newLineIfNotEmpty();
    MetaClass _represents_2 = connection.getRepresents();
    EClass _type = _represents_2.getType();
    EPackage _ePackage = _type.getEPackage();
    String _name_2 = _ePackage.getName();
    final String pack = _name_2;
    _builder.newLineIfNotEmpty();
    MetaClass _represents_3 = connection.getRepresents();
    EClass _type_1 = _represents_3.getType();
    String _fullPackageName = MetaModel.fullPackageName(_type_1);
    final String fullPackage = _fullPackageName;
    _builder.newLineIfNotEmpty();
    final String labelName = "name";
    _builder.newLineIfNotEmpty();
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
    _builder.append("import org.eclipse.graphiti.features.IFeatureProvider;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.IReason;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.context.IUpdateContext;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.impl.Reason;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.algorithms.Text;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.Diagram;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.PictogramElement;");
    _builder.newLine();
    _builder.append("import org.eclipselabs.spray.runtime.graphiti.ISprayConstants;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.services.IGaService;");
    _builder.newLine();
    _builder.append("import org.eclipse.xtext.xbase.lib.ObjectExtensions;");
    _builder.newLine();
    _builder.append("import org.eclipselabs.spray.runtime.graphiti.features.AbstractUpdateFeature;");
    _builder.newLine();
    _builder.append("import ");
    MetaClass _represents_4 = connection.getRepresents();
    String _javaInterfaceName = this._namingExtensions.getJavaInterfaceName(_represents_4);
    _builder.append(_javaInterfaceName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("// MARKER_IMPORT");
    _builder.newLine();
    _builder.append("        ");
    _builder.newLine();
    _builder.append("public class ");
    _builder.append(className, "");
    _builder.append(" extends AbstractUpdateFeature {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    StringConcatenation _generate_additionalFields = this.generate_additionalFields(connection);
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
    _builder.append("        ");
    _builder.append("gaService = ");
    Diagram _diagram_1 = this._diagramExtensions.getDiagram(connection);
    String _activatorClassName = this._namingExtensions.getActivatorClassName(_diagram_1);
    String _shortName = this._namingExtensions.shortName(_activatorClassName);
    _builder.append(_shortName, "        ");
    _builder.append(".get(IGaService.class);");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    StringConcatenation _generate_canUpdate = this.generate_canUpdate(connection);
    _builder.append(_generate_canUpdate, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    StringConcatenation _generate_updateNeeded = this.generate_updateNeeded(connection);
    _builder.append(_generate_updateNeeded, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    StringConcatenation _generate_update = this.generate_update(connection);
    _builder.append(_generate_update, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    StringConcatenation _generate_getValue = this.generate_getValue(connection);
    _builder.append(_generate_getValue, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    StringConcatenation _generate_additionalFields_1 = this.generate_additionalFields(connection);
    _builder.append(_generate_additionalFields_1, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public StringConcatenation generate_canUpdate(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    MetaClass _represents = connection.getRepresents();
    String _name = this._namingExtensions.getName(_represents);
    final String metaClassName = _name;
    _builder.newLineIfNotEmpty();
    StringConcatenation _overrideHeader = this.overrideHeader();
    _builder.append(_overrideHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.append("public boolean canUpdate(IUpdateContext context) {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("// return true, if linked business object is a EClass");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("EObject bo = getBusinessObjectForPictogramElement(context.getPictogramElement());");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("PictogramElement pictogramElement = context.getPictogramElement();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("return (bo instanceof ");
    _builder.append(metaClassName, "    ");
    _builder.append(") && (!(pictogramElement instanceof Diagram));");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public StringConcatenation generate_updateNeeded(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    MetaClass _represents = connection.getRepresents();
    String _name = this._namingExtensions.getName(_represents);
    final String metaClassName = _name;
    _builder.newLineIfNotEmpty();
    StringConcatenation _overrideHeader = this.overrideHeader();
    _builder.append(_overrideHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.append("public IReason updateNeeded(IUpdateContext context) {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("PictogramElement pictogramElement = context.getPictogramElement();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("EObject bo = getBusinessObjectForPictogramElement(pictogramElement);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("if ( ! (bo instanceof ");
    _builder.append(metaClassName, "    ");
    _builder.append(")) {");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("return Reason.createFalseReason();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append(metaClassName, "    ");
    _builder.append(" eClass = (");
    _builder.append(metaClassName, "    ");
    _builder.append(") bo;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("if (pictogramElement instanceof FreeFormConnection) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("FreeFormConnection free = (FreeFormConnection) pictogramElement;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("for (ConnectionDecorator decorator : free.getConnectionDecorators()) {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("String type = peService.getPropertyValue(decorator, ISprayConstants.PROPERTY_MODEL_TYPE);");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("String value = getValue(type, eClass);");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("Text text = (Text) decorator.getGraphicsAlgorithm();");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("String current = text.getValue();");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("if (ObjectExtensions.operator_notEquals(current, value)) {");
    _builder.newLine();
    _builder.append("                ");
    _builder.append("return Reason.createTrueReason(type + \" is changed\");");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("return Reason.createFalseReason();");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public StringConcatenation generate_update(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    MetaClass _represents = connection.getRepresents();
    String _name = this._namingExtensions.getName(_represents);
    final String metaClassName = _name;
    _builder.newLineIfNotEmpty();
    StringConcatenation _overrideHeader = this.overrideHeader();
    _builder.append(_overrideHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.append("public boolean update(IUpdateContext context) {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("PictogramElement pictogramElement = context.getPictogramElement();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append(metaClassName, "    ");
    _builder.append(" eClass = (");
    _builder.append(metaClassName, "    ");
    _builder.append(") getBusinessObjectForPictogramElement(pictogramElement);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("FreeFormConnection free = (FreeFormConnection) pictogramElement;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("for (ConnectionDecorator decorator : free.getConnectionDecorators()) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("String type = peService.getPropertyValue(decorator, ISprayConstants.PROPERTY_MODEL_TYPE);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("String value = getValue(type, eClass);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("Text text = (Text) decorator.getGraphicsAlgorithm();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("text.setValue(value);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("return true;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public StringConcatenation generate_getValue(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("protected String getValue(String type, ");
    MetaClass _represents = connection.getRepresents();
    String _name = this._namingExtensions.getName(_represents);
    _builder.append(_name, "");
    _builder.append(" eClass) {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("String result = \"\";");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("if( \"FROM_LABEL\".equals(type) ){");
    _builder.newLine();
    _builder.append("        ");
    Text _fromLabel = connection.getFromLabel();
    Text fromLabel = _fromLabel;
    _builder.newLineIfNotEmpty();
    {
      boolean _operator_notEquals = ObjectExtensions.operator_notEquals(fromLabel, null);
      if (_operator_notEquals) {
        _builder.append("        ");
        Text _fromLabel_1 = connection.getFromLabel();
        StringConcatenation _valueGenerator = this.valueGenerator(_fromLabel_1, "eClass");
        _builder.append(_valueGenerator, "        ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("if( \"TO_LABEL\".equals(type) ){");
    _builder.newLine();
    {
      Text _label = connection.getToLabel();
      boolean _operator_notEquals_1 = ObjectExtensions.operator_notEquals(_label, null);
      if (_operator_notEquals_1) {
        _builder.append("        ");
        Text _label_1 = connection.getToLabel();
        StringConcatenation _valueGenerator_1 = this.valueGenerator(_label_1, "eClass");
        _builder.append(_valueGenerator_1, "        ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("if( \"CONNECTION_LABEL\".equals(type) ){");
    _builder.newLine();
    {
      Text _connectionLabel = connection.getConnectionLabel();
      boolean _operator_notEquals_2 = ObjectExtensions.operator_notEquals(_connectionLabel, null);
      if (_operator_notEquals_2) {
        _builder.append("        ");
        Text _connectionLabel_1 = connection.getConnectionLabel();
        StringConcatenation _valueGenerator_2 = this.valueGenerator(_connectionLabel_1, "eClass");
        _builder.append(_valueGenerator_2, "        ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("return result;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
