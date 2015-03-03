/**
 * Copyright (c)  The Spray Project.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Spray Dev Team - initial API and implementation
 */
package org.eclipselabs.spray.generator.graphiti.templates.diagram;

import javax.inject.Inject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipselabs.spray.generator.common.GeneratorUtil;
import org.eclipselabs.spray.generator.graphiti.util.NamingExtensions;
import org.eclipselabs.spray.mm.spray.Diagram;
import org.eclipselabs.spray.xtext.generator.FileGenerator;
import org.eclipselabs.spray.xtext.generator.filesystem.JavaGenFile;

@SuppressWarnings("all")
public class ModelService extends FileGenerator<Diagram> {
  @Inject
  @Extension
  private NamingExtensions _namingExtensions;
  
  @Override
  public CharSequence generateExtensionFile(final Diagram modelElement) {
    JavaGenFile _javaGenFile = this.getJavaGenFile();
    String _className = _javaGenFile.getClassName();
    return this.mainExtensionPointFile(modelElement, _className);
  }
  
  public CharSequence mainExtensionPointFile(final Diagram diagram, final String className) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _extensionHeader = this.extensionHeader(this);
    _builder.append(_extensionHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.append("package ");
    String _diagram_package = GeneratorUtil.diagram_package();
    _builder.append(_diagram_package, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.newLine();
    _builder.append("public class ");
    _builder.append(className, "");
    _builder.append(" extends ");
    _builder.append(className, "");
    _builder.append("Base {");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  @Override
  public CharSequence generateBaseFile(final Diagram modelElement) {
    JavaGenFile _javaGenFile = this.getJavaGenFile();
    String _className = _javaGenFile.getClassName();
    return this.mainFile(modelElement, _className);
  }
  
  public CharSequence mainFile(final Diagram diagram, final String className) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _header = this.header(this);
    _builder.append(_header, "");
    _builder.newLineIfNotEmpty();
    EClass _modelType = diagram.getModelType();
    final String modelClassName = this._namingExtensions.getItfName(_modelType);
    _builder.newLineIfNotEmpty();
    _builder.append("package ");
    String _diagram_package = GeneratorUtil.diagram_package();
    _builder.append(_diagram_package, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import java.io.IOException;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import org.eclipse.core.resources.IResource;");
    _builder.newLine();
    _builder.append("import org.eclipse.core.resources.IWorkspaceRoot;");
    _builder.newLine();
    _builder.append("import org.eclipse.core.resources.ResourcesPlugin;");
    _builder.newLine();
    _builder.append("import org.eclipse.core.runtime.CoreException;");
    _builder.newLine();
    _builder.append("import org.eclipse.emf.ecore.EObject;");
    _builder.newLine();
    _builder.append("import org.eclipse.emf.ecore.InternalEObject;");
    _builder.newLine();
    _builder.append("import org.eclipse.emf.common.util.URI;");
    _builder.newLine();
    _builder.append("import org.eclipse.emf.ecore.resource.Resource;");
    _builder.newLine();
    _builder.append("import org.eclipse.emf.ecore.resource.ResourceSet;");
    _builder.newLine();
    _builder.append("import org.eclipse.emf.ecore.util.EcoreUtil;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.dt.IDiagramTypeProvider;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.Diagram;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.PictogramElement;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.services.Graphiti;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.services.IPeService;");
    _builder.newLine();
    _builder.append("import org.eclipselabs.spray.runtime.graphiti.ISprayConstants;");
    _builder.newLine();
    _builder.append("// MARKER_IMPORT");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* This class gives access to the domain model root element for a diagram.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* On first access, a new resource will be created to which the model");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* is added.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("public class ");
    _builder.append(className, "");
    _builder.append(" implements ISprayConstants {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("public static final String FILE_EXTENSION = \"");
    String _modelFileExtension = this._namingExtensions.getModelFileExtension(diagram);
    _builder.append(_modelFileExtension, "    ");
    _builder.append("\";");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("protected IPeService peService;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("protected IDiagramTypeProvider dtp;");
    _builder.newLine();
    _builder.append("    ");
    CharSequence _generate_additionalFields = this.generate_additionalFields(diagram);
    _builder.append(_generate_additionalFields, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("static protected ");
    _builder.append(className, "    ");
    _builder.append(" modelService = null;");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* return the model service, create one if it does not exist yet.");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("static public ");
    _builder.append(className, "    ");
    _builder.append(" getModelService(IDiagramTypeProvider dtp){");
    _builder.newLineIfNotEmpty();
    _builder.append("   \t    ");
    _builder.append("modelService = new ");
    _builder.append(className, "   \t    ");
    _builder.append("(dtp);");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("return modelService;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* return the model service.");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* returns null if there is no model service.");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("static public ");
    _builder.append(className, "    ");
    _builder.append(" getModelService(){");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("return modelService;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("protected ");
    _builder.append(className, "    ");
    _builder.append(" (IDiagramTypeProvider dtp) {");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("this.dtp = dtp;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("this.peService = Graphiti.getPeService();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public ");
    _builder.append(modelClassName, "    ");
    _builder.append(" getModel () {");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("final Diagram diagram = dtp.getDiagram();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("Resource r = diagram.eResource();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("ResourceSet set = r.getResourceSet();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("EObject bo = (EObject) dtp.getFeatureProvider().getBusinessObjectForPictogramElement(diagram);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append(modelClassName, "        ");
    _builder.append(" model = null;");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("if (bo != null) {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("// If its a proxy, resolve it");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("if (bo.eIsProxy()) {");
    _builder.newLine();
    _builder.append("                ");
    _builder.append("if (bo instanceof InternalEObject) {");
    _builder.newLine();
    _builder.append("                    ");
    _builder.append("model = (");
    _builder.append(modelClassName, "                    ");
    _builder.append(")set.getEObject(((InternalEObject) bo).eProxyURI(), true);");
    _builder.newLineIfNotEmpty();
    _builder.append("                ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("} else {");
    _builder.newLine();
    _builder.append("                ");
    _builder.append("if( bo instanceof ");
    _builder.append(modelClassName, "                ");
    _builder.append("){");
    _builder.newLineIfNotEmpty();
    _builder.append("                    ");
    _builder.append("model = (");
    _builder.append(modelClassName, "                    ");
    _builder.append(")bo;");
    _builder.newLineIfNotEmpty();
    _builder.append("                ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("        ");
    _builder.append("if (model == null) {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("model = createModel();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("return model;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public Object getBusinessObject(PictogramElement pe){");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return dtp.getFeatureProvider().getBusinessObjectForPictogramElement(dtp.getDiagram());");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* Creates the domain model element and store it in the file. Overwrite to set required properties on creation.");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("protected ");
    _builder.append(modelClassName, "    ");
    _builder.append(" createModel () {");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("final Diagram diagram = dtp.getDiagram();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("try {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append(modelClassName, "            ");
    _builder.append(" model = ");
    EClass _modelType_1 = diagram.getModelType();
    String _eFactoryInterfaceName = this._namingExtensions.getEFactoryInterfaceName(_modelType_1);
    String _shortName = this._namingExtensions.shortName(_eFactoryInterfaceName);
    _builder.append(_shortName, "            ");
    _builder.append(".eINSTANCE.create");
    EClass _modelType_2 = diagram.getModelType();
    String _name = _modelType_2.getName();
    _builder.append(_name, "            ");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("            ");
    _builder.append("createModelResourceAndAddModel (model);");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("peService.setPropertyValue(diagram, PROPERTY_URI, EcoreUtil.getURI(model).toString());");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("// link the diagram with the model element");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("dtp.getFeatureProvider().link(diagram, model);");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("return model;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("} catch (CoreException e) {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("e.printStackTrace();");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("return null;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("} catch (IOException e) {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("e.printStackTrace();");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("return null;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("protected void createModelResourceAndAddModel (final ");
    _builder.append(modelClassName, "    ");
    _builder.append(" model) throws CoreException, IOException {");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("final Diagram diagram = dtp.getDiagram();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("URI uri = diagram.eResource().getURI();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("uri = uri.trimFragment();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("uri = uri.trimFileExtension();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("uri = uri.appendFileExtension(FILE_EXTENSION);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("ResourceSet rSet = diagram.eResource().getResourceSet();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("IResource file = workspaceRoot.findMember(uri.toPlatformString(true));");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("if (file == null || !file.exists()) {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("Resource resource = rSet.createResource(uri);");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("resource.setTrackingModification(true);");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("resource.getContents().add(model);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("} else {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("final Resource resource = rSet.getResource(uri, true);");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("resource.getContents().add(model);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    CharSequence _generate_additionalFields_1 = this.generate_additionalFields(diagram);
    _builder.append(_generate_additionalFields_1, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
