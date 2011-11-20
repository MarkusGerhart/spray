package org.eclipselabs.spray.generator.graphiti.templates.features;

import com.google.inject.Inject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.xtend2.lib.StringConcatenation;
import org.eclipselabs.spray.generator.graphiti.templates.FileGenerator;
import org.eclipselabs.spray.generator.graphiti.templates.JavaGenFile;
import org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil;
import org.eclipselabs.spray.generator.graphiti.util.MetaModel;
import org.eclipselabs.spray.generator.graphiti.util.NamingExtensions;
import org.eclipselabs.spray.mm.spray.Container;
import org.eclipselabs.spray.mm.spray.Diagram;
import org.eclipselabs.spray.mm.spray.Layout;
import org.eclipselabs.spray.mm.spray.MetaClass;

@SuppressWarnings("all")
public class LayoutFeature extends FileGenerator {
  @Inject
  private NamingExtensions _namingExtensions;
  
  public StringConcatenation generateBaseFile(final EObject modelElement) {
    JavaGenFile _javaGenFile = this.getJavaGenFile();
    String _baseClassName = _javaGenFile.getBaseClassName();
    StringConcatenation _mainFile = this.mainFile(((Container) modelElement), _baseClassName);
    return _mainFile;
  }
  
  public StringConcatenation generateExtensionFile(final EObject modelElement) {
    JavaGenFile _javaGenFile = this.getJavaGenFile();
    String _className = _javaGenFile.getClassName();
    StringConcatenation _mainExtensionPointFile = this.mainExtensionPointFile(((Container) modelElement), _className);
    return _mainExtensionPointFile;
  }
  
  public StringConcatenation mainExtensionPointFile(final Container container, final String className) {
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
  
  public StringConcatenation mainFile(final Container container, final String className) {
    StringConcatenation _builder = new StringConcatenation();
    MetaClass _represents = container.getRepresents();
    Diagram _diagram = _represents.getDiagram();
    String _name = _diagram.getName();
    String diagramName = _name;
    _builder.newLineIfNotEmpty();
    MetaClass _represents_1 = container.getRepresents();
    EClass _type = _represents_1.getType();
    EPackage _ePackage = _type.getEPackage();
    String _name_1 = _ePackage.getName();
    String pack = _name_1;
    _builder.newLineIfNotEmpty();
    MetaClass _represents_2 = container.getRepresents();
    EClass _type_1 = _represents_2.getType();
    String _fullPackageName = MetaModel.fullPackageName(_type_1);
    String fullPackage = _fullPackageName;
    _builder.newLineIfNotEmpty();
    String _constainerClass = GeneratorUtil.constainerClass(container);
    String containerType = _constainerClass;
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
    _builder.append("import org.eclipse.emf.common.util.EList;");
    _builder.newLine();
    _builder.append("import org.eclipse.emf.ecore.EObject;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.IFeatureProvider;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.context.ILayoutContext;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.ContainerShape;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.PictogramElement;");
    _builder.newLine();
    _builder.append("import org.eclipselabs.spray.runtime.graphiti.features.AbstractLayoutFeature;");
    _builder.newLine();
    _builder.append("import ");
    String _util_package = GeneratorUtil.util_package();
    _builder.append(_util_package, "");
    _builder.append(".");
    _builder.append(containerType, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("// MARKER_IMPORT");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    _builder.append(className, "");
    _builder.append(" extends AbstractLayoutFeature {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("protected static final int MIN_HEIGHT = 30;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("protected static final int MIN_WIDTH = 50;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("protected ");
    _builder.append(containerType, "    ");
    _builder.append(" container = null;");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    StringConcatenation _generate_additionalFields = this.generate_additionalFields(container);
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
    _builder.append("container = new ");
    _builder.append(containerType, "        ");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    {
      Layout _layout = container.getLayout();
      String _figure = _layout.getFigure();
      boolean _matches = _figure.matches(".*Concept.*");
      if (_matches) {
        _builder.append("    ");
        _builder.append("container.setConcept(true);");
        _builder.newLine();
      }
    }
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append(" ");
    _builder.newLine();
    _builder.append("    ");
    StringConcatenation _generate_canLayout = this.generate_canLayout(container);
    _builder.append(_generate_canLayout, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    StringConcatenation _generate_layout = this.generate_layout(container);
    _builder.append(_generate_layout, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    StringConcatenation _generate_additionalFields_1 = this.generate_additionalFields(container);
    _builder.append(_generate_additionalFields_1, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public StringConcatenation generate_canLayout(final Container container) {
    StringConcatenation _builder = new StringConcatenation();
    StringConcatenation _overrideHeader = this.overrideHeader();
    _builder.append(_overrideHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.append("public boolean canLayout(ILayoutContext context) {");
    _builder.newLine();
    _builder.append("   ");
    _builder.append("PictogramElement pe = context.getPictogramElement();");
    _builder.newLine();
    _builder.append("   ");
    _builder.append("if (!(pe instanceof ContainerShape)) {");
    _builder.newLine();
    _builder.append("       ");
    _builder.append("return false;");
    _builder.newLine();
    _builder.append("   ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("   ");
    _builder.append("EList<EObject> businessObjects = pe.getLink().getBusinessObjects();");
    _builder.newLine();
    _builder.append("   ");
    _builder.append("return (businessObjects.size() == 1) && (businessObjects.get(0) instanceof ");
    MetaClass _represents = container.getRepresents();
    EClass _type = _represents.getType();
    String _javaInterfaceName = this._namingExtensions.getJavaInterfaceName(_type);
    String _shortName = this.shortName(_javaInterfaceName);
    _builder.append(_shortName, "   ");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public StringConcatenation generate_layout(final Container container) {
    StringConcatenation _builder = new StringConcatenation();
    StringConcatenation _overrideHeader = this.overrideHeader();
    _builder.append(_overrideHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.append("public boolean layout(ILayoutContext context) {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("return container.layoutContainer(context);");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
