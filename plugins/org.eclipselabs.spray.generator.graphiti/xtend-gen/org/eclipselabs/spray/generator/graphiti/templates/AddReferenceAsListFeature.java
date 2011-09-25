package org.eclipselabs.spray.generator.graphiti.templates;

import com.google.inject.Inject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xtend2.lib.StringConcatenation;
import org.eclipselabs.spray.generator.graphiti.templates.FileGenerator;
import org.eclipselabs.spray.generator.graphiti.templates.JavaGenFile;
import org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil;
import org.eclipselabs.spray.generator.graphiti.util.ImportUtil;
import org.eclipselabs.spray.generator.graphiti.util.NamingExtensions;
import org.eclipselabs.spray.mm.spray.Container;
import org.eclipselabs.spray.mm.spray.Diagram;
import org.eclipselabs.spray.mm.spray.MetaClass;
import org.eclipselabs.spray.mm.spray.MetaReference;
import org.eclipselabs.spray.mm.spray.extensions.SprayExtensions;

@SuppressWarnings("all")
public class AddReferenceAsListFeature extends FileGenerator {
  
  @Inject
  private ImportUtil importUtil;
  
  @Inject
  private SprayExtensions e1;
  
  @Inject
  private NamingExtensions naming;
  
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
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public StringConcatenation mainFile(final MetaReference reference, final String className) {
    StringConcatenation _builder = new StringConcatenation();
    String _feature_package = GeneratorUtil.feature_package();
    this.importUtil.initImports(_feature_package);
    _builder.newLineIfNotEmpty();
    StringConcatenation _header = this.header(this);
    _builder.append(_header, "");
    _builder.newLineIfNotEmpty();
    _builder.append("package ");
    String _feature_package_1 = GeneratorUtil.feature_package();
    _builder.append(_feature_package_1, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    StringConcatenation _mainFileBody = this.mainFileBody(reference, className);
    final StringConcatenation body = _mainFileBody;
    _builder.newLineIfNotEmpty();
    _builder.append("import java.util.ArrayList;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.datatypes.IDimension;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.IFeatureProvider;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.context.IAddContext;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.context.IContext;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.ContainerShape;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.algorithms.styles.Orientation;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.PictogramElement;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.PictogramsFactory;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.Shape;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.algorithms.Text;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.services.Graphiti;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.services.IGaService;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.services.IPeCreateService;");
    _builder.newLine();
    _builder.append("import ");
    String _util_package = GeneratorUtil.util_package();
    _builder.append(_util_package, "");
    _builder.append(".ISprayContainer;");
    _builder.newLineIfNotEmpty();
    _builder.append("import ");
    String _util_package_1 = GeneratorUtil.util_package();
    _builder.append(_util_package_1, "");
    _builder.append(".SprayContainerService;");
    _builder.newLineIfNotEmpty();
    _builder.append("import ");
    String _util_package_2 = GeneratorUtil.util_package();
    _builder.append(_util_package_2, "");
    _builder.append(".ISprayColorConstants;");
    _builder.newLineIfNotEmpty();
    String _printImports = this.importUtil.printImports();
    _builder.append(_printImports, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append(body, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public StringConcatenation mainFileBody(final MetaReference reference, final String className) {
    StringConcatenation _builder = new StringConcatenation();
    EObject _eContainer = reference.eContainer();
    MetaClass _represents = ((Container) _eContainer).getRepresents();
    MetaClass metaClass = _represents;
    _builder.newLineIfNotEmpty();
    EReference _reference = reference.getReference();
    EReference target = _reference;
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    Diagram _diagram = metaClass.getDiagram();
    String _name = _diagram.getName();
    String diagramName = _name;
    _builder.append("  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public class ");
    _builder.append(className, "");
    _builder.append(" extends AbstractAddShapeFeature {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("private static final ArrayList<org.eclipse.graphiti.mm.Property> EMPTY_PROPERTIES_LIST = new ArrayList<org.eclipse.graphiti.mm.Property>(0);");
    _builder.newLine();
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
    _builder.append(" ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("/* This method very much depends on the sturtcure of the stnadard rectangle shape.");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public PictogramElement add(IAddContext context) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("final ");
    EClass _eReferenceType = target.getEReferenceType();
    String _javaInterfaceName = this.naming.getJavaInterfaceName(_eReferenceType);
    String _shortName = this.importUtil.shortName(_javaInterfaceName);
    _builder.append(_shortName, "		");
    _builder.append(" addedModelElement = (");
    EClass _eReferenceType_1 = target.getEReferenceType();
    String _name_1 = _eReferenceType_1.getName();
    _builder.append(_name_1, "		");
    _builder.append(") context.getNewObject();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("final ContainerShape containerShape= (ContainerShape) context.getTargetContainer();");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// CONTAINER SHAPE WITH ROUNDED RECTANGLE");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("IGaService gaService = Graphiti.getGaService();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("Shape found = null;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("int index = 0; ");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("int i = 0;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("ContainerShape textbox = SprayContainerService.findTextShape(containerShape);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("for (Shape shape : textbox.getChildren()) {");
    _builder.newLine();
    _builder.append("           ");
    _builder.append("GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("IDimension size = gaService.calculateSize(graphicsAlgorithm, true);");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("gaService.setLocation(graphicsAlgorithm, 0, 0);");
    _builder.newLine();
    _builder.append("\t        ");
    _builder.append("String modelType = Graphiti.getPeService().getPropertyValue(shape, \"MODEL_TYPE\");");
    _builder.newLine();
    _builder.append("\t        ");
    _builder.append("if( modelType != null && modelType.equals(\"");
    EClass _eReferenceType_2 = target.getEReferenceType();
    String _name_2 = _eReferenceType_2.getName();
    _builder.append(_name_2, "	        ");
    _builder.append("\") ){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t        \t");
    _builder.append("found = shape; index = i; ");
    _builder.newLine();
    _builder.append("\t        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("i++;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("// LIST of PROPERTIES");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("Shape newShape = createShape(textbox, index);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("Graphiti.getPeService().setPropertyValue(newShape, \"STATIC\", \"true\");");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("Graphiti.getPeService().setPropertyValue(newShape, \"MODEL_TYPE\", \"");
    EClass _eReferenceType_3 = target.getEReferenceType();
    String _name_3 = _eReferenceType_3.getName();
    _builder.append(_name_3, "	    ");
    _builder.append("\");");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("Graphiti.getPeService().setPropertyValue(newShape, ISprayContainer.CONCEPT_SHAPE_KEY, ISprayContainer.TEXT);");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("// TODO Name attribute should not be default");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("Text text = gaService.createDefaultText(getDiagram(), newShape, addedModelElement.get");
    String _labelPropertyName = this.e1.getLabelPropertyName(reference);
    String _firstUpper = StringExtensions.toFirstUpper(_labelPropertyName);
    _builder.append(_firstUpper, "		");
    _builder.append("());");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("// TODO find the right text color");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("text.setForeground(manageColor(ISprayColorConstants.CLASS_TEXT_FOREGROUND));");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("text.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("text.setVerticalAlignment(Orientation.ALIGNMENT_TOP);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("gaService.setLocationAndSize(text, 0, 0, 0, 0);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// create link and wire it");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("link(newShape, addedModelElement);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("layoutPictogramElement(containerShape);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return containerShape;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("protected Shape createShape(final ContainerShape containerShape, int index) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("Shape newShape  = PictogramsFactory.eINSTANCE.createShape();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("newShape.getProperties().addAll(EMPTY_PROPERTIES_LIST);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("newShape.setVisible(true);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("newShape.setActive(true);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("containerShape.getChildren().add(index, newShape);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return newShape;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public boolean canAdd(IAddContext context) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("final Object newObject = context.getNewObject();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if (newObject instanceof ");
    EClass _eReferenceType_4 = target.getEReferenceType();
    String _name_4 = _eReferenceType_4.getName();
    _builder.append(_name_4, "		");
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("// check if user wants to add to a diagram");
    _builder.newLine();
    _builder.append("\t    \t");
    _builder.append("Shape target = context.getTargetContainer();");
    _builder.newLine();
    _builder.append("\t    \t");
    _builder.append("Object domainObject = getBusinessObjectForPictogramElement(target);");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("if (domainObject instanceof ");
    String _javaInterfaceName_1 = this.naming.getJavaInterfaceName(metaClass);
    String _shortName_1 = this.importUtil.shortName(_javaInterfaceName_1);
    _builder.append(_shortName_1, "			");
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t");
    _builder.append("return true;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return false;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}    ");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public boolean hasDoneChanges() {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("return false;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public boolean canUndo(IContext context) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("return false;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}