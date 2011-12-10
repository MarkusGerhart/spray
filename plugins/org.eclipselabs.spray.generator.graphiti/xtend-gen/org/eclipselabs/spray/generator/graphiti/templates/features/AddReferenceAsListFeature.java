package org.eclipselabs.spray.generator.graphiti.templates.features;

import com.google.inject.Inject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipselabs.spray.generator.graphiti.templates.FileGenerator;
import org.eclipselabs.spray.generator.graphiti.templates.JavaGenFile;
import org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil;
import org.eclipselabs.spray.generator.graphiti.util.NamingExtensions;
import org.eclipselabs.spray.mm.spray.Container;
import org.eclipselabs.spray.mm.spray.Diagram;
import org.eclipselabs.spray.mm.spray.MetaClass;
import org.eclipselabs.spray.mm.spray.MetaReference;

@SuppressWarnings("all")
public class AddReferenceAsListFeature extends FileGenerator {
  @Inject
  private NamingExtensions _namingExtensions;
  
  public CharSequence generateBaseFile(final EObject modelElement) {
    JavaGenFile _javaGenFile = this.getJavaGenFile();
    String _baseClassName = _javaGenFile.getBaseClassName();
    CharSequence _mainFile = this.mainFile(((MetaReference) modelElement), _baseClassName);
    return _mainFile;
  }
  
  public CharSequence generateExtensionFile(final EObject modelElement) {
    JavaGenFile _javaGenFile = this.getJavaGenFile();
    String _className = _javaGenFile.getClassName();
    CharSequence _mainExtensionPointFile = this.mainExtensionPointFile(((MetaReference) modelElement), _className);
    return _mainExtensionPointFile;
  }
  
  public CharSequence mainExtensionPointFile(final MetaReference metaReference, final String className) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _extensionHeader = this.extensionHeader(this);
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
  
  public CharSequence mainFile(final MetaReference reference, final String className) {
    StringConcatenation _builder = new StringConcatenation();
    EObject _eContainer = reference.eContainer();
    MetaClass _represents = ((Container) _eContainer).getRepresents();
    final MetaClass metaClass = _represents;
    _builder.newLineIfNotEmpty();
    EReference _target = reference.getTarget();
    final EReference target = _target;
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    Diagram _diagram = metaClass.getDiagram();
    String _name = _diagram.getName();
    final String diagramName = _name;
    _builder.append("  ");
    _builder.newLineIfNotEmpty();
    CharSequence _header = this.header(this);
    _builder.append(_header, "");
    _builder.newLineIfNotEmpty();
    _builder.append("package ");
    String _feature_package = GeneratorUtil.feature_package();
    _builder.append(_feature_package, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("import java.util.ArrayList;");
    _builder.newLine();
    _builder.append("import org.eclipse.emf.ecore.EObject;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.datatypes.IDimension;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.IFeatureProvider;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.context.IAddContext;");
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
    _builder.append("import org.eclipse.graphiti.services.IGaService;");
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
    _builder.append("import org.eclipselabs.spray.runtime.graphiti.features.AbstractAddFeature;");
    _builder.newLine();
    _builder.append("import static org.eclipselabs.spray.runtime.graphiti.ISprayConstants.PROPERTY_MODEL_TYPE;");
    _builder.newLine();
    _builder.append("import static org.eclipselabs.spray.runtime.graphiti.ISprayConstants.PROPERTY_STATIC;");
    _builder.newLine();
    _builder.append("// MARKER_IMPORT");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    _builder.append(className, "");
    _builder.append(" extends AbstractAddFeature {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("private static final ArrayList<org.eclipse.graphiti.mm.Property> EMPTY_PROPERTIES_LIST = new ArrayList<org.eclipse.graphiti.mm.Property>(0);");
    _builder.newLine();
    _builder.append("    ");
    CharSequence _generate_additionalFields = this.generate_additionalFields(reference);
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
    Diagram _diagram_1 = metaClass.getDiagram();
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
    CharSequence _generate_canAdd = this.generate_canAdd(reference);
    _builder.append(_generate_canAdd, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    CharSequence _generate_add = this.generate_add(reference);
    _builder.append(_generate_add, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    CharSequence _generate_createShape = this.generate_createShape(reference);
    _builder.append(_generate_createShape, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    CharSequence _generate_getText = this.generate_getText(reference);
    _builder.append(_generate_getText, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    CharSequence _generate_additionalFields_1 = this.generate_additionalFields(reference);
    _builder.append(_generate_additionalFields_1, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence generate_canAdd(final MetaReference reference) {
    StringConcatenation _builder = new StringConcatenation();
    EReference _target = reference.getTarget();
    final EReference target = _target;
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    EObject _eContainer = reference.eContainer();
    MetaClass _represents = ((Container) _eContainer).getRepresents();
    final MetaClass metaClass = _represents;
    _builder.newLineIfNotEmpty();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* {@inheritDoc}");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("public boolean canAdd(IAddContext context) {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("final Object newObject = context.getNewObject();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("if (newObject instanceof ");
    EClass _eReferenceType = target.getEReferenceType();
    String _name = _eReferenceType.getName();
    _builder.append(_name, "    ");
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("// check if user wants to add to a diagram");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("Shape target = context.getTargetContainer();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("EObject domainObject = getBusinessObjectForPictogramElement(target);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("if (domainObject instanceof ");
    String _javaInterfaceName = this._namingExtensions.getJavaInterfaceName(metaClass);
    String _shortName = this._namingExtensions.shortName(_javaInterfaceName);
    _builder.append(_shortName, "        ");
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("            ");
    _builder.append("return true;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("return false;");
    _builder.newLine();
    _builder.append("}    ");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence generate_add(final MetaReference reference) {
    StringConcatenation _builder = new StringConcatenation();
    EReference _target = reference.getTarget();
    final EReference target = _target;
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* {@inheritDoc}");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* This method very much depends on the structure of the standard rectangle shape.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("public PictogramElement add(IAddContext context) {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("final ");
    EClass _eReferenceType = target.getEReferenceType();
    String _javaInterfaceName = this._namingExtensions.getJavaInterfaceName(_eReferenceType);
    String _shortName = this._namingExtensions.shortName(_javaInterfaceName);
    _builder.append(_shortName, "    ");
    _builder.append(" addedModelElement = (");
    EClass _eReferenceType_1 = target.getEReferenceType();
    String _name = _eReferenceType_1.getName();
    _builder.append(_name, "    ");
    _builder.append(") context.getNewObject();");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("final ContainerShape containerShape= (ContainerShape) context.getTargetContainer();");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("// CONTAINER SHAPE WITH ROUNDED RECTANGLE");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("Shape found = null;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("int index = 0; ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("int i = 0;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("ContainerShape textbox = SprayContainerService.findTextShape(containerShape);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("for (Shape shape : textbox.getChildren()) {");
    _builder.newLine();
    _builder.append("       ");
    _builder.append("GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("IDimension size = gaService.calculateSize(graphicsAlgorithm, true);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("gaService.setLocation(graphicsAlgorithm, 0, 0);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("String modelType = peService.getPropertyValue(shape, PROPERTY_MODEL_TYPE);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("if( modelType != null && modelType.equals(");
    EClass _eReferenceType_2 = target.getEReferenceType();
    String _literalConstant = this._namingExtensions.getLiteralConstant(_eReferenceType_2);
    _builder.append(_literalConstant, "        ");
    _builder.append(".getName()) ){");
    _builder.newLineIfNotEmpty();
    _builder.append("            ");
    _builder.append("found = shape; index = i; ");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("i++;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("// LIST of PROPERTIES");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("Shape newShape = createShape(textbox, index);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("peService.setPropertyValue(newShape, PROPERTY_STATIC, Boolean.TRUE.toString());");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("peService.setPropertyValue(newShape, PROPERTY_MODEL_TYPE, ");
    EClass _eReferenceType_3 = target.getEReferenceType();
    String _literalConstant_1 = this._namingExtensions.getLiteralConstant(_eReferenceType_3);
    _builder.append(_literalConstant_1, "    ");
    _builder.append(".getName());");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("peService.setPropertyValue(newShape, ISprayContainer.CONCEPT_SHAPE_KEY, ISprayContainer.TEXT);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("// TODO Name attribute should not be default");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("Text text = gaService.createDefaultText(getDiagram(), newShape, getText(context, addedModelElement)/*, addedModelElement.get");
    String _labelPropertyName = this._namingExtensions.getLabelPropertyName(reference);
    String _firstUpper = StringExtensions.toFirstUpper(_labelPropertyName);
    _builder.append(_firstUpper, "    ");
    _builder.append("()*/);");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("// TODO find the right text color");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("text.setForeground(manageColor(ISprayColorConstants.CLASS_TEXT_FOREGROUND));");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("text.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("text.setVerticalAlignment(Orientation.ALIGNMENT_TOP);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("gaService.setLocationAndSize(text, 0, 0, 0, 0);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("// create link and wire it");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("link(newShape, addedModelElement);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("layoutPictogramElement(containerShape);");
    _builder.newLine();
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("setDoneChanges(true);");
    _builder.newLine();
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("return containerShape;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence generate_createShape(final MetaReference reference) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("protected Shape createShape(final ContainerShape containerShape, int index) {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("Shape newShape  = PictogramsFactory.eINSTANCE.createShape();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("newShape.getProperties().addAll(EMPTY_PROPERTIES_LIST);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("newShape.setVisible(true);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("newShape.setActive(true);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("containerShape.getChildren().add(index, newShape);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("return newShape;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence generate_getText(final MetaReference reference) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Computes the displayed text. Clients may override this method.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("protected String getText (IAddContext context, ");
    EReference _target = reference.getTarget();
    EClass _eReferenceType = _target.getEReferenceType();
    String _name = _eReferenceType.getName();
    _builder.append(_name, "");
    _builder.append(" bo) {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("return bo.get");
    String _labelPropertyName = this._namingExtensions.getLabelPropertyName(reference);
    String _firstUpper = StringExtensions.toFirstUpper(_labelPropertyName);
    _builder.append(_firstUpper, "    ");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
