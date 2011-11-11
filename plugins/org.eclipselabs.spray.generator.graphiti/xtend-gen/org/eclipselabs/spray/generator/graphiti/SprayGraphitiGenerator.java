package org.eclipselabs.spray.generator.graphiti;

import com.google.inject.Inject;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.builder.EclipseResourceFileSystemAccess2;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.xbase.lib.BooleanExtensions;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xtend2.lib.StringConcatenation;
import org.eclipselabs.spray.generator.graphiti.templates.ExecutableExtensionFactory;
import org.eclipselabs.spray.generator.graphiti.templates.Filter;
import org.eclipselabs.spray.generator.graphiti.templates.GuiceModule;
import org.eclipselabs.spray.generator.graphiti.templates.JavaGenFile;
import org.eclipselabs.spray.generator.graphiti.templates.Plugin;
import org.eclipselabs.spray.generator.graphiti.templates.PluginActivator;
import org.eclipselabs.spray.generator.graphiti.templates.PropertySection;
import org.eclipselabs.spray.generator.graphiti.templates.diagram.DiagramTypeProvider;
import org.eclipselabs.spray.generator.graphiti.templates.diagram.FeatureProvider;
import org.eclipselabs.spray.generator.graphiti.templates.diagram.ImageProvider;
import org.eclipselabs.spray.generator.graphiti.templates.diagram.ToolBehaviourProvider;
import org.eclipselabs.spray.generator.graphiti.templates.features.AddConnectionFeature;
import org.eclipselabs.spray.generator.graphiti.templates.features.AddReferenceAsConnectionFeature;
import org.eclipselabs.spray.generator.graphiti.templates.features.AddReferenceAsListFeature;
import org.eclipselabs.spray.generator.graphiti.templates.features.AddShapeFeature;
import org.eclipselabs.spray.generator.graphiti.templates.features.CreateConnectionFeature;
import org.eclipselabs.spray.generator.graphiti.templates.features.CreateReferenceAsConnectionFeature;
import org.eclipselabs.spray.generator.graphiti.templates.features.CreateReferenceAsListFeature;
import org.eclipselabs.spray.generator.graphiti.templates.features.CreateShapeFeature;
import org.eclipselabs.spray.generator.graphiti.templates.features.CustomFeature;
import org.eclipselabs.spray.generator.graphiti.templates.features.DeleteReferenceFeature;
import org.eclipselabs.spray.generator.graphiti.templates.features.LayoutFeature;
import org.eclipselabs.spray.generator.graphiti.templates.features.UpdateConnectionFeature;
import org.eclipselabs.spray.generator.graphiti.templates.features.UpdateReferenceAsListFeature;
import org.eclipselabs.spray.generator.graphiti.templates.features.UpdateShapeFeature;
import org.eclipselabs.spray.generator.graphiti.util.EclipseHelpers;
import org.eclipselabs.spray.generator.graphiti.util.MetaModel;
import org.eclipselabs.spray.generator.graphiti.util.NamingExtensions;
import org.eclipselabs.spray.generator.graphiti.util.ProjectProperties;
import org.eclipselabs.spray.generator.graphiti.util.StringHelpers;
import org.eclipselabs.spray.mm.spray.Behaviour;
import org.eclipselabs.spray.mm.spray.Connection;
import org.eclipselabs.spray.mm.spray.Container;
import org.eclipselabs.spray.mm.spray.Diagram;
import org.eclipselabs.spray.mm.spray.MetaClass;
import org.eclipselabs.spray.mm.spray.MetaReference;
import org.eclipselabs.spray.mm.spray.Shape;
import org.eclipselabs.spray.mm.spray.SprayElement;

@SuppressWarnings("all")
public class SprayGraphitiGenerator implements IGenerator {
  @Inject
  private NamingExtensions naming;
  
  @Inject
  private PluginActivator pluginActivator;
  
  @Inject
  private ExecutableExtensionFactory executableExtensionFactory;
  
  @Inject
  private GuiceModule guiceModule;
  
  @Inject
  private Plugin plugin;
  
  @Inject
  private DiagramTypeProvider dtp;
  
  @Inject
  private FeatureProvider fp;
  
  @Inject
  private AddShapeFeature addShapeFeature;
  
  @Inject
  private AddConnectionFeature addConnectionFeature;
  
  @Inject
  private AddReferenceAsConnectionFeature addReferenceAsConnectionFeature;
  
  @Inject
  private AddReferenceAsListFeature addReferenceAsListFeature;
  
  @Inject
  private CreateConnectionFeature createConnectionFeature;
  
  @Inject
  private CreateShapeFeature createShapeFeature;
  
  @Inject
  private CreateReferenceAsListFeature createReferenceAsListFeature;
  
  @Inject
  private CreateReferenceAsConnectionFeature createReferenceAsConnectionFeature;
  
  @Inject
  private UpdateConnectionFeature updateConnectionFeature;
  
  @Inject
  private LayoutFeature layoutFeature;
  
  @Inject
  private UpdateShapeFeature updateShapeFeature;
  
  @Inject
  private UpdateReferenceAsListFeature updateReferenceAsListFeature;
  
  @Inject
  private DeleteReferenceFeature deleteReferenceFeature;
  
  @Inject
  private ImageProvider imageProvider;
  
  @Inject
  private ToolBehaviourProvider toolBehaviourProvider;
  
  @Inject
  private PropertySection propertySection;
  
  @Inject
  private Filter filter;
  
  @Inject
  private Filter filter2;
  
  @Inject
  private CustomFeature customFeature;
  
  /**
   * This method is a long sequence of calling all templates for the code generation
   */
  public void doGenerate(final Resource resource, final IFileSystemAccess fsa) {
      JavaIoFileSystemAccess javaFsa = null;
      EclipseResourceFileSystemAccess2 eclipseFsa = null;
      URI _uRI = resource.getURI();
      String _devicePath = _uRI.devicePath();
      String modelPath = _devicePath;
      String _replaceLastSubstring = StringHelpers.replaceLastSubstring(modelPath, "spray", "properties");
      String propertiesPath = _replaceLastSubstring;
      URI _uRI_1 = resource.getURI();
      ProjectProperties.setModelUri(_uRI_1);
      String _projectPath = ProjectProperties.getProjectPath();
      String _operator_plus = StringExtensions.operator_plus(_projectPath, "/");
      String _srcGenPath = ProjectProperties.getSrcGenPath();
      String _operator_plus_1 = StringExtensions.operator_plus(_operator_plus, _srcGenPath);
      String genOutputPath = _operator_plus_1;
      String _projectPath_1 = ProjectProperties.getProjectPath();
      String _operator_plus_2 = StringExtensions.operator_plus(_projectPath_1, "/");
      String _srcManPath = ProjectProperties.getSrcManPath();
      String _operator_plus_3 = StringExtensions.operator_plus(_operator_plus_2, _srcManPath);
      String manOutputPath = _operator_plus_3;
      if ((fsa instanceof JavaIoFileSystemAccess)) {
        javaFsa = ((JavaIoFileSystemAccess) fsa);
      }
      if ((fsa instanceof EclipseResourceFileSystemAccess2)) {
        {
          InputOutput.<String>println("EclipseResourceFileSystemAccess: WARNING: dos not work yet");
          eclipseFsa = ((EclipseResourceFileSystemAccess2) fsa);
        }
      }
      EList<EObject> _contents = resource.getContents();
      EObject _head = IterableExtensions.<EObject>head(_contents);
      Diagram diagram = ((Diagram) _head);
      StringConcatenation _generate = this.plugin.generate(diagram);
      fsa.generateFile("plugin.xml", _generate);
      JavaGenFile java = null;
      boolean _operator_notEquals = ObjectExtensions.operator_notEquals(javaFsa, null);
      if (_operator_notEquals) {
        {
          JavaGenFile _javaGenFile = new JavaGenFile(javaFsa);
          java = _javaGenFile;
          java.setGenOutputPath(genOutputPath);
          java.setManOutputPath(manOutputPath);
        }
      } else {
        {
          JavaGenFile _javaGenFile_1 = new JavaGenFile(eclipseFsa);
          java = _javaGenFile_1;
          IResource _eclipseResource = EclipseHelpers.toEclipseResource(resource);
          IProject _project = _eclipseResource.getProject();
          IProject project = _project;
          String _srcGenPath_1 = ProjectProperties.getSrcGenPath();
          java.setGenOutputPath(_srcGenPath_1);
          String _srcManPath_1 = ProjectProperties.getSrcManPath();
          java.setManOutputPath(_srcManPath_1);
        }
      }
      java.hasExtensionPoint = false;
      String _activatorClassName = this.naming.getActivatorClassName(diagram);
      java.setPackageAndClass(_activatorClassName);
      this.pluginActivator.generate(diagram, java);
      String _extensionFactoryClassName = this.naming.getExtensionFactoryClassName(diagram);
      java.setPackageAndClass(_extensionFactoryClassName);
      this.executableExtensionFactory.generate(diagram, java);
      java.hasExtensionPoint = true;
      String _guiceModuleClassName = this.naming.getGuiceModuleClassName(diagram);
      java.setPackageAndClass(_guiceModuleClassName);
      this.guiceModule.generate(diagram, java);
      String _diagramTypeProviderClassName = this.naming.getDiagramTypeProviderClassName(diagram);
      java.setPackageAndClass(_diagramTypeProviderClassName);
      this.dtp.generate(diagram, java);
      String _featureProviderClassName = this.naming.getFeatureProviderClassName(diagram);
      java.setPackageAndClass(_featureProviderClassName);
      this.fp.generate(diagram, java);
      MetaClass[] _metaClasses = diagram.getMetaClasses();
      final Function1<MetaClass,Boolean> _function = new Function1<MetaClass,Boolean>() {
          public Boolean apply(final MetaClass m) {
            Shape _representedBy = m.getRepresentedBy();
            return ((Boolean)(_representedBy instanceof Container));
          }
        };
      Iterable<MetaClass> _filter = IterableExtensions.<MetaClass>filter(((Iterable<MetaClass>)Conversions.doWrapArray(_metaClasses)), _function);
      for (final MetaClass metaClass : _filter) {
        {
          Shape _representedBy = metaClass.getRepresentedBy();
          Container container = ((Container) _representedBy);
          String _addFeatureClassName = this.naming.getAddFeatureClassName(metaClass);
          java.setPackageAndClass(_addFeatureClassName);
          this.addShapeFeature.generate(container, java);
        }
      }
      MetaClass[] _metaClasses_1 = diagram.getMetaClasses();
      final Function1<MetaClass,Boolean> _function_1 = new Function1<MetaClass,Boolean>() {
          public Boolean apply(final MetaClass m) {
            Shape _representedBy = m.getRepresentedBy();
            return ((Boolean)(_representedBy instanceof Connection));
          }
        };
      Iterable<MetaClass> _filter_1 = IterableExtensions.<MetaClass>filter(((Iterable<MetaClass>)Conversions.doWrapArray(_metaClasses_1)), _function_1);
      for (final MetaClass metaClass_1 : _filter_1) {
        {
          Shape _representedBy_1 = metaClass_1.getRepresentedBy();
          Connection connection = ((Connection) _representedBy_1);
          String _addFeatureClassName_1 = this.naming.getAddFeatureClassName(metaClass_1);
          java.setPackageAndClass(_addFeatureClassName_1);
          this.addConnectionFeature.generate(metaClass_1, java);
        }
      }
      MetaClass[] _metaClasses_2 = diagram.getMetaClasses();
      for (final MetaClass metaClass_2 : _metaClasses_2) {
        MetaReference[] _references = metaClass_2.getReferences();
        final Function1<MetaReference,Boolean> _function_2 = new Function1<MetaReference,Boolean>() {
            public Boolean apply(final MetaReference ref) {
              Connection _representedBy = ref.getRepresentedBy();
              boolean _operator_notEquals = ObjectExtensions.operator_notEquals(_representedBy, null);
              return ((Boolean)_operator_notEquals);
            }
          };
        Iterable<MetaReference> _filter_2 = IterableExtensions.<MetaReference>filter(((Iterable<MetaReference>)Conversions.doWrapArray(_references)), _function_2);
        for (final MetaReference reference : _filter_2) {
          {
            String _addReferenceAsConnectionFeatureClassName = this.naming.getAddReferenceAsConnectionFeatureClassName(reference);
            java.setPackageAndClass(_addReferenceAsConnectionFeatureClassName);
            this.addReferenceAsConnectionFeature.generate(reference, java);
          }
        }
      }
      MetaClass[] _metaClasses_3 = diagram.getMetaClasses();
      for (final MetaClass metaClass_3 : _metaClasses_3) {
        Shape _representedBy_2 = metaClass_3.getRepresentedBy();
        if ((_representedBy_2 instanceof Container)) {
          {
            Shape _representedBy_3 = metaClass_3.getRepresentedBy();
            Container container_1 = ((Container) _representedBy_3);
            SprayElement[] _parts = container_1.getParts();
            Iterable<MetaReference> _filter_3 = IterableExtensions.<MetaReference>filter(((Iterable<? extends Object>)Conversions.doWrapArray(_parts)), org.eclipselabs.spray.mm.spray.MetaReference.class);
            for (final MetaReference metaRef : _filter_3) {
              {
                String _addReferenceAsListFeatureClassName = this.naming.getAddReferenceAsListFeatureClassName(metaRef);
                java.setPackageAndClass(_addReferenceAsListFeatureClassName);
                this.addReferenceAsListFeature.generate(metaRef, java);
              }
            }
          }
        }
      }
      MetaClass[] _metaClasses_4 = diagram.getMetaClasses();
      for (final MetaClass metaClass_4 : _metaClasses_4) {
        Shape _representedBy_4 = metaClass_4.getRepresentedBy();
        if ((_representedBy_4 instanceof Connection)) {
          {
            String _createFeatureClassName = this.naming.getCreateFeatureClassName(metaClass_4);
            java.setPackageAndClass(_createFeatureClassName);
            this.createConnectionFeature.generate(metaClass_4, java);
          }
        } else {
          {
            String _createFeatureClassName_1 = this.naming.getCreateFeatureClassName(metaClass_4);
            java.setPackageAndClass(_createFeatureClassName_1);
            this.createShapeFeature.generate(metaClass_4, java);
          }
        }
      }
      MetaClass[] _metaClasses_5 = diagram.getMetaClasses();
      final Function1<MetaClass,Boolean> _function_3 = new Function1<MetaClass,Boolean>() {
          public Boolean apply(final MetaClass m) {
            Shape _representedBy = m.getRepresentedBy();
            boolean _operator_notEquals = ObjectExtensions.operator_notEquals(_representedBy, null);
            return ((Boolean)_operator_notEquals);
          }
        };
      Iterable<MetaClass> _filter_4 = IterableExtensions.<MetaClass>filter(((Iterable<MetaClass>)Conversions.doWrapArray(_metaClasses_5)), _function_3);
      final Function1<MetaClass,Shape> _function_4 = new Function1<MetaClass,Shape>() {
          public Shape apply(final MetaClass m) {
            Shape _representedBy = m.getRepresentedBy();
            return _representedBy;
          }
        };
      Iterable<Shape> _map = IterableExtensions.<MetaClass, Shape>map(_filter_4, _function_4);
      Iterable<Container> _filter_5 = IterableExtensions.<Container>filter(_map, org.eclipselabs.spray.mm.spray.Container.class);
      final Function1<Container,Iterable<MetaReference>> _function_5 = new Function1<Container,Iterable<MetaReference>>() {
          public Iterable<MetaReference> apply(final Container c) {
            SprayElement[] _parts = ((Container) c).getParts();
            Iterable<MetaReference> _filter = IterableExtensions.<MetaReference>filter(((Iterable<? extends Object>)Conversions.doWrapArray(_parts)), org.eclipselabs.spray.mm.spray.MetaReference.class);
            return _filter;
          }
        };
      Iterable<Iterable<MetaReference>> _map_1 = IterableExtensions.<Container, Iterable<MetaReference>>map(_filter_5, _function_5);
      Iterable<MetaReference> _flatten = IterableExtensions.<MetaReference>flatten(_map_1);
      for (final MetaReference reference_1 : _flatten) {
        {
          String _name = this.naming.getName(reference_1);
          final String referenceName = _name;
          EObject _eContainer = reference_1.eContainer();
          MetaClass _represents = ((Container) _eContainer).getRepresents();
          MetaClass metaClass_5 = _represents;
          EClass _type = metaClass_5.getType();
          EList<EReference> _eAllReferences = _type.getEAllReferences();
          final Function1<EReference,Boolean> _function_6 = new Function1<EReference,Boolean>() {
              public Boolean apply(final EReference e) {
                String _name = e.getName();
                boolean _operator_equals = ObjectExtensions.operator_equals(_name, referenceName);
                return ((Boolean)_operator_equals);
              }
            };
          EReference _findFirst = IterableExtensions.<EReference>findFirst(_eAllReferences, _function_6);
          EReference target = _findFirst;
          EClass _eReferenceType = target.getEReferenceType();
          EClass targetType = _eReferenceType;
          boolean _isAbstract = targetType.isAbstract();
          boolean _operator_not = BooleanExtensions.operator_not(_isAbstract);
          if (_operator_not) {
            {
              String _name_1 = targetType.getName();
              String _operator_plus_4 = StringExtensions.operator_plus("NOT ABSTRACT: ", _name_1);
              InputOutput.<String>println(_operator_plus_4);
              String _createReferenceAsListFeatureClassName = this.naming.getCreateReferenceAsListFeatureClassName(reference_1);
              java.setPackageAndClass(_createReferenceAsListFeatureClassName);
              this.createReferenceAsListFeature.setTarget(targetType);
              this.createReferenceAsListFeature.generate(reference_1, java);
            }
          } else {
            String _name_2 = targetType.getName();
            String _operator_plus_5 = StringExtensions.operator_plus("ABSTRACT: ", _name_2);
            InputOutput.<String>println(_operator_plus_5);
          }
          List<EClass> _subclasses = MetaModel.getSubclasses(targetType);
          for (final EClass subclass : _subclasses) {
            boolean _isAbstract_1 = subclass.isAbstract();
            boolean _operator_not_1 = BooleanExtensions.operator_not(_isAbstract_1);
            if (_operator_not_1) {
              {
                String _name_3 = subclass.getName();
                String _operator_plus_6 = StringExtensions.operator_plus("NOT ABSTRACT subclass: ", _name_3);
                InputOutput.<String>println(_operator_plus_6);
                String _createReferenceAsListFeatureClassName_1 = this.naming.getCreateReferenceAsListFeatureClassName(reference_1, subclass);
                java.setPackageAndClass(_createReferenceAsListFeatureClassName_1);
                this.createReferenceAsListFeature.setTarget(subclass);
                this.createReferenceAsListFeature.generate(reference_1, java);
              }
            } else {
              {
                String _name_4 = subclass.getName();
                String _operator_plus_7 = StringExtensions.operator_plus("ABSTRACT subclass: ", _name_4);
                InputOutput.<String>println(_operator_plus_7);
                String _createReferenceAsListFeatureClassName_2 = this.naming.getCreateReferenceAsListFeatureClassName(reference_1, subclass);
                java.setPackageAndClass(_createReferenceAsListFeatureClassName_2);
                this.createReferenceAsListFeature.setTarget(subclass);
                this.createReferenceAsListFeature.generate(reference_1, java);
              }
            }
          }
        }
      }
      MetaClass[] _metaClasses_6 = diagram.getMetaClasses();
      for (final MetaClass metaClass_6 : _metaClasses_6) {
        MetaReference[] _references_1 = metaClass_6.getReferences();
        final Function1<MetaReference,Boolean> _function_7 = new Function1<MetaReference,Boolean>() {
            public Boolean apply(final MetaReference ref) {
              Connection _representedBy = ref.getRepresentedBy();
              boolean _operator_notEquals = ObjectExtensions.operator_notEquals(_representedBy, null);
              return ((Boolean)_operator_notEquals);
            }
          };
        Iterable<MetaReference> _filter_6 = IterableExtensions.<MetaReference>filter(((Iterable<MetaReference>)Conversions.doWrapArray(_references_1)), _function_7);
        for (final MetaReference reference_2 : _filter_6) {
          {
            String _createReferenceAsConnectionFeatureClassName = this.naming.getCreateReferenceAsConnectionFeatureClassName(reference_2);
            java.setPackageAndClass(_createReferenceAsConnectionFeatureClassName);
            this.createReferenceAsConnectionFeature.generate(reference_2, java);
          }
        }
      }
      MetaClass[] _metaClasses_7 = diagram.getMetaClasses();
      for (final MetaClass metaClass_7 : _metaClasses_7) {
        Shape _representedBy_5 = metaClass_7.getRepresentedBy();
        if ((_representedBy_5 instanceof Connection)) {
          {
            String _updateFeatureClassName = this.naming.getUpdateFeatureClassName(metaClass_7);
            java.setPackageAndClass(_updateFeatureClassName);
            Shape _representedBy_6 = metaClass_7.getRepresentedBy();
            this.updateConnectionFeature.generate(_representedBy_6, java);
          }
        } else {
          Shape _representedBy_7 = metaClass_7.getRepresentedBy();
          if ((_representedBy_7 instanceof Container)) {
            {
              String _layoutFeatureClassName = this.naming.getLayoutFeatureClassName(metaClass_7);
              java.setPackageAndClass(_layoutFeatureClassName);
              Shape _representedBy_8 = metaClass_7.getRepresentedBy();
              this.layoutFeature.generate(_representedBy_8, java);
              String _updateFeatureClassName_1 = this.naming.getUpdateFeatureClassName(metaClass_7);
              java.setPackageAndClass(_updateFeatureClassName_1);
              Shape _representedBy_9 = metaClass_7.getRepresentedBy();
              this.updateShapeFeature.generate(_representedBy_9, java);
              Shape _representedBy_10 = metaClass_7.getRepresentedBy();
              Container container_2 = ((Container) _representedBy_10);
              SprayElement[] _parts_1 = container_2.getParts();
              final Function1<SprayElement,Boolean> _function_8 = new Function1<SprayElement,Boolean>() {
                  public Boolean apply(final SprayElement p) {
                    return ((Boolean)(p instanceof MetaReference));
                  }
                };
              Iterable<SprayElement> _filter_7 = IterableExtensions.<SprayElement>filter(((Iterable<SprayElement>)Conversions.doWrapArray(_parts_1)), _function_8);
              final Function1<SprayElement,MetaReference> _function_9 = new Function1<SprayElement,MetaReference>() {
                  public MetaReference apply(final SprayElement p) {
                    return ((MetaReference) p);
                  }
                };
              Iterable<MetaReference> _map_2 = IterableExtensions.<SprayElement, MetaReference>map(_filter_7, _function_9);
              for (final MetaReference reference_3 : _map_2) {
                {
                  String _name_5 = this.naming.getName(reference_3);
                  final String referenceName_1 = _name_5;
                  EClass _type_1 = metaClass_7.getType();
                  EList<EReference> _eAllReferences_1 = _type_1.getEAllReferences();
                  final Function1<EReference,Boolean> _function_10 = new Function1<EReference,Boolean>() {
                      public Boolean apply(final EReference e) {
                        String _name = e.getName();
                        boolean _operator_equals = ObjectExtensions.operator_equals(_name, referenceName_1);
                        return ((Boolean)_operator_equals);
                      }
                    };
                  EReference _findFirst_1 = IterableExtensions.<EReference>findFirst(_eAllReferences_1, _function_10);
                  EClass _eReferenceType_1 = _findFirst_1.getEReferenceType();
                  EClass eClass = _eReferenceType_1;
                  this.updateReferenceAsListFeature.setTarget(eClass);
                  String _updateReferenceAsListFeatureClassName = this.naming.getUpdateReferenceAsListFeatureClassName(reference_3);
                  java.setPackageAndClass(_updateReferenceAsListFeatureClassName);
                  this.updateReferenceAsListFeature.generate(reference_3, java);
                }
              }
            }
          }
        }
      }
      MetaClass[] _metaClasses_8 = diagram.getMetaClasses();
      for (final MetaClass metaClass_8 : _metaClasses_8) {
        MetaReference[] _references_2 = metaClass_8.getReferences();
        for (final MetaReference reference_4 : _references_2) {
          {
            String _deleteReferenceFeatureClassName = this.naming.getDeleteReferenceFeatureClassName(reference_4);
            java.setPackageAndClass(_deleteReferenceFeatureClassName);
            this.deleteReferenceFeature.generate(reference_4, java);
          }
        }
      }
      String _imageProviderClassName = this.naming.getImageProviderClassName(diagram);
      java.setPackageAndClass(_imageProviderClassName);
      this.imageProvider.generate(diagram, java);
      String _olBehaviourProviderClassName = this.naming.getToolBehaviourProviderClassName(diagram);
      java.setPackageAndClass(_olBehaviourProviderClassName);
      this.toolBehaviourProvider.generate(diagram, java);
      MetaClass[] _metaClasses_9 = diagram.getMetaClasses();
      for (final MetaClass metaClass_9 : _metaClasses_9) {
        {
          EClass _type_2 = metaClass_9.getType();
          final EClass eClass1 = _type_2;
          EList<EAttribute> _eAllAttributes = eClass1.getEAllAttributes();
          for (final EAttribute attribute : _eAllAttributes) {
            {
              String _propertySectionClassName = this.naming.getPropertySectionClassName(eClass1, attribute);
              java.setPackageAndClass(_propertySectionClassName);
              this.propertySection.setDiagram(diagram);
              this.propertySection.generate(attribute, java);
            }
          }
          Shape _representedBy_11 = metaClass_9.getRepresentedBy();
          if ((_representedBy_11 instanceof Container)) {
            {
              Shape _representedBy_12 = metaClass_9.getRepresentedBy();
              final Container container_3 = ((Container) _representedBy_12);
              SprayElement[] _parts_2 = container_3.getParts();
              final Function1<SprayElement,Boolean> _function_11 = new Function1<SprayElement,Boolean>() {
                  public Boolean apply(final SprayElement p) {
                    return ((Boolean)(p instanceof MetaReference));
                  }
                };
              Iterable<SprayElement> _filter_8 = IterableExtensions.<SprayElement>filter(((Iterable<SprayElement>)Conversions.doWrapArray(_parts_2)), _function_11);
              final Function1<SprayElement,MetaReference> _function_12 = new Function1<SprayElement,MetaReference>() {
                  public MetaReference apply(final SprayElement p) {
                    return ((MetaReference) p);
                  }
                };
              Iterable<MetaReference> _map_3 = IterableExtensions.<SprayElement, MetaReference>map(_filter_8, _function_12);
              for (final MetaReference reference_5 : _map_3) {
                {
                  String _name_6 = this.naming.getName(reference_5);
                  final String referenceName_2 = _name_6;
                  EClass _type_3 = metaClass_9.getType();
                  EList<EReference> _eAllReferences_2 = _type_3.getEAllReferences();
                  final Function1<EReference,Boolean> _function_13 = new Function1<EReference,Boolean>() {
                      public Boolean apply(final EReference r) {
                        String _name = r.getName();
                        boolean _operator_equals = ObjectExtensions.operator_equals(_name, referenceName_2);
                        return ((Boolean)_operator_equals);
                      }
                    };
                  EReference _findFirst_2 = IterableExtensions.<EReference>findFirst(_eAllReferences_2, _function_13);
                  EClass _eReferenceType_2 = _findFirst_2.getEReferenceType();
                  EClass eClass_1 = _eReferenceType_2;
                  EList<EAttribute> _eAllAttributes_1 = eClass_1.getEAllAttributes();
                  for (final EAttribute attribute_1 : _eAllAttributes_1) {
                    {
                      String _propertySectionClassName_1 = this.naming.getPropertySectionClassName(eClass_1, attribute_1);
                      java.setPackageAndClass(_propertySectionClassName_1);
                      this.propertySection.setDiagram(diagram);
                      this.propertySection.generate(attribute_1, java);
                    }
                  }
                }
              }
            }
          }
        }
      }
      MetaClass[] _metaClasses_10 = diagram.getMetaClasses();
      for (final MetaClass metaClass_10 : _metaClasses_10) {
        {
          this.filter.setDiagram(diagram);
          String _filterClassName = this.naming.getFilterClassName(metaClass_10);
          java.setPackageAndClass(_filterClassName);
          EClass _type_4 = metaClass_10.getType();
          this.filter.generate(_type_4, java);
          Shape _representedBy_13 = metaClass_10.getRepresentedBy();
          if ((_representedBy_13 instanceof Container)) {
            {
              Shape _representedBy_14 = metaClass_10.getRepresentedBy();
              final Container container_4 = ((Container) _representedBy_14);
              SprayElement[] _parts_3 = container_4.getParts();
              final Function1<SprayElement,Boolean> _function_14 = new Function1<SprayElement,Boolean>() {
                  public Boolean apply(final SprayElement p) {
                    return ((Boolean)(p instanceof MetaReference));
                  }
                };
              Iterable<SprayElement> _filter_9 = IterableExtensions.<SprayElement>filter(((Iterable<SprayElement>)Conversions.doWrapArray(_parts_3)), _function_14);
              final Function1<SprayElement,MetaReference> _function_15 = new Function1<SprayElement,MetaReference>() {
                  public MetaReference apply(final SprayElement p) {
                    return ((MetaReference) p);
                  }
                };
              Iterable<MetaReference> _map_4 = IterableExtensions.<SprayElement, MetaReference>map(_filter_9, _function_15);
              for (final MetaReference reference_6 : _map_4) {
                {
                  String _name_7 = this.naming.getName(reference_6);
                  final String referenceName_3 = _name_7;
                  EClass _type_5 = metaClass_10.getType();
                  EList<EReference> _eAllReferences_3 = _type_5.getEAllReferences();
                  final Function1<EReference,Boolean> _function_16 = new Function1<EReference,Boolean>() {
                      public Boolean apply(final EReference ref) {
                        String _name = ref.getName();
                        boolean _operator_equals = ObjectExtensions.operator_equals(_name, referenceName_3);
                        return ((Boolean)_operator_equals);
                      }
                    };
                  EReference _findFirst_3 = IterableExtensions.<EReference>findFirst(_eAllReferences_3, _function_16);
                  EClass _eReferenceType_3 = _findFirst_3.getEReferenceType();
                  final EClass eClass_2 = _eReferenceType_3;
                  this.filter2.setDiagram(diagram);
                  String _filterClassName_1 = this.naming.getFilterClassName(eClass_2);
                  java.setPackageAndClass(_filterClassName_1);
                  this.filter2.generate(eClass_2, java);
                }
              }
            }
          }
        }
      }
      MetaClass[] _metaClasses_11 = diagram.getMetaClasses();
      for (final MetaClass metaClass_11 : _metaClasses_11) {
        Behaviour[] _behaviours = metaClass_11.getBehaviours();
        for (final Behaviour behaviour : _behaviours) {
          {
            String _customFeatureClassName = this.naming.getCustomFeatureClassName(behaviour);
            java.setPackageAndClass(_customFeatureClassName);
            this.customFeature.generate(behaviour, java);
          }
        }
      }
  }
}
