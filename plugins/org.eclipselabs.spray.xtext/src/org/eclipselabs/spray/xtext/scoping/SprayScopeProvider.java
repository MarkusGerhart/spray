/*
 * generated by Xtext
 */
package org.eclipselabs.spray.xtext.scoping;

import static org.eclipselabs.spray.mm.spray.SprayPackage.Literals.COMPARTMENT_BEHAVIOR__COMPARTMENT_REFERENCE;
import static org.eclipselabs.spray.mm.spray.SprayPackage.Literals.CONNECTION_IN_SPRAY;
import static org.eclipselabs.spray.mm.spray.SprayPackage.Literals.CONNECTION_IN_SPRAY__FROM;
import static org.eclipselabs.spray.mm.spray.SprayPackage.Literals.CONNECTION_IN_SPRAY__TO;
import static org.eclipselabs.spray.mm.spray.SprayPackage.Literals.CREATE_BEHAVIOR;
import static org.eclipselabs.spray.mm.spray.SprayPackage.Literals.CREATE_BEHAVIOR__ASK_FOR;
import static org.eclipselabs.spray.mm.spray.SprayPackage.Literals.CREATE_BEHAVIOR__CONTAINMENT_REFERENCE;
import static org.eclipselabs.spray.mm.spray.SprayPackage.Literals.DIAGRAM__MODEL_TYPE;
import static org.eclipselabs.spray.mm.spray.SprayPackage.Literals.META_CLASS;
import static org.eclipselabs.spray.mm.spray.SprayPackage.Literals.META_CLASS__TYPE;
import static org.eclipselabs.spray.mm.spray.SprayPackage.Literals.META_REFERENCE;
import static org.eclipselabs.spray.mm.spray.SprayPackage.Literals.META_REFERENCE__TARGET;
import static org.eclipselabs.spray.mm.spray.SprayPackage.Literals.SHAPE_COMPARTMENT_ASSIGNMENT__REFERENCE;
import static org.eclipselabs.spray.mm.spray.SprayPackage.Literals.SHAPE_PROPERTY_ASSIGNMENT__ATTRIBUTE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.common.types.JvmEnumerationLiteral;
import org.eclipse.xtext.common.types.JvmEnumerationType;
import org.eclipse.xtext.common.types.JvmField;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmMember;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.common.types.TypesPackage;
import org.eclipse.xtext.common.types.access.IJvmTypeProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.FilteringScope;
import org.eclipse.xtext.scoping.impl.MapBasedScope;
import org.eclipse.xtext.scoping.impl.SingletonScope;
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations;
import org.eclipse.xtext.xbase.scoping.LocalVariableScopeContext;
import org.eclipse.xtext.xbase.scoping.XbaseScopeProvider;
import org.eclipselabs.spray.mm.spray.ConnectionInSpray;
import org.eclipselabs.spray.mm.spray.ConnectionReference;
import org.eclipselabs.spray.mm.spray.CreateBehavior;
import org.eclipselabs.spray.mm.spray.Diagram;
import org.eclipselabs.spray.mm.spray.Import;
import org.eclipselabs.spray.mm.spray.MetaClass;
import org.eclipselabs.spray.mm.spray.MetaReference;
import org.eclipselabs.spray.mm.spray.ShapeFromDsl;
import org.eclipselabs.spray.mm.spray.ShapeReference;
import org.eclipselabs.spray.mm.spray.SprayPackage;
import org.eclipselabs.spray.mm.spray.SprayStyleRef;
import org.eclipselabs.spray.runtime.graphiti.IColorConstantTypeProvider;
import org.eclipselabs.spray.shapes.scoping.ConnectionScopeRestrictor;
import org.eclipselabs.spray.shapes.scoping.ShapeScopeRestrictor;
import org.eclipselabs.spray.styles.scoping.StyleScopeRestrictor;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * This class contains custom scoping description.
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping on
 * how and when to use it
 */
public class SprayScopeProvider extends XbaseScopeProvider {
    @Inject
    private IJvmModelAssociations      associations;
    @Inject
    private IJvmTypeProvider.Factory   typeProviderFactory;
    @Inject(optional = true)
    private IColorConstantTypeProvider colorConstantTypeProvider;
    @Inject
    private IQualifiedNameProvider     qnProvider;
    @Inject
    private PackageSelector            packageSelector;

    private static final Logger        LOGGER = Logger.getLogger(SprayScopeProvider.class);

    @Override
    public IScope getScope(EObject context, EReference reference) {
        IScope scope = IScope.NULLSCOPE;
        if (reference == DIAGRAM__MODEL_TYPE) {
            scope = scope_Diagram_ModelType(context, reference);
        } else if (reference == META_CLASS__TYPE) {
            scope = scope_MetaClass_Type(context, reference);
        } else if (reference == CREATE_BEHAVIOR__CONTAINMENT_REFERENCE) {
            scope = scope_CreateBehavior_ContainmentReference(context, reference);
        } else if (reference == COMPARTMENT_BEHAVIOR__COMPARTMENT_REFERENCE) {
            scope = scope_CreateBehavior_CompartmentReference(context, reference);
        } else if (context.eClass() == CONNECTION_IN_SPRAY && reference == CONNECTION_IN_SPRAY__FROM) {
            scope = scope_Connection_from(context);
        } else if (context.eClass() == CONNECTION_IN_SPRAY && reference == CONNECTION_IN_SPRAY__TO) {
            scope = scope_Connection_to(context);
        } else if ((context.eClass() == META_REFERENCE || context.eClass() == META_CLASS) && reference == META_REFERENCE__TARGET) {
            scope = scope_MetaReference_target(context, reference);
        } else if (context.eClass() == CREATE_BEHAVIOR && reference == CREATE_BEHAVIOR__ASK_FOR) {
            scope = scope_CreateBehavior_askFor(context);
        } else if (reference == TypesPackage.Literals.JVM_PARAMETERIZED_TYPE_REFERENCE__TYPE) {
            scope = scope_JvmParametrizedTypeReference_type(context, reference);
        } else if (reference == SHAPE_PROPERTY_ASSIGNMENT__ATTRIBUTE) {
            return scope_ShapePropertyAssignment_attribute(context);
        } else if (reference == SHAPE_COMPARTMENT_ASSIGNMENT__REFERENCE) {
            return scope_ShapeCompartmentAssignment_reference(context);
        } else if (reference == SprayPackage.Literals.SHAPE_DSL_KEY__JVM_KEY) {
            scope = scope_ShapePropertyAssignment_Key(context, reference);
        } else if (reference == SprayPackage.Literals.SHAPE_COMPARTMENT_ASSIGNMENT__SHAPE_DSL_KEY) {
            scope = scope_ShapeCompartmentAssignment_Key(context, reference);
        } else {
            // not handled specially, delegate to super
            scope = super.getScope(context, reference);
        }
        return scope;
    }

    protected IScope scope_ShapePropertyAssignment_attribute(EObject context) {
        MetaClass metaClass = EcoreUtil2.getContainerOfType(context, MetaClass.class);
        if (metaClass != null) {
            Predicate<EObject> filterPredicate = new Predicate<EObject>() {
                @Override
                public boolean apply(EObject input) {
                    if (input instanceof EAttribute) {
                        if (((EAttribute) input).getEType().getName().equals("EString")) {
                            return true;
                        }
                    }
                    return false;
                }
            };
            return MapBasedScope.createScope(IScope.NULLSCOPE, Scopes.scopedElementsFor(Iterables.filter(metaClass.getType().getEAllAttributes(), filterPredicate)));
        } else {
            return IScope.NULLSCOPE;
        }
    }

    protected IScope scope_ShapeCompartmentAssignment_reference(EObject context) {
        MetaClass metaClass = EcoreUtil2.getContainerOfType(context, MetaClass.class);
        if (metaClass != null) {
            Predicate<EObject> filterPredicate = new Predicate<EObject>() {
                @Override
                public boolean apply(EObject input) {
                    if (input instanceof EReference) {
                        //                        if (((EReference) input).getEType().getName().equals("EString")) {
                        return true;
                        //                        }
                    }
                    return false;
                }
            };
            return MapBasedScope.createScope(IScope.NULLSCOPE, Scopes.scopedElementsFor(Iterables.filter(metaClass.getType().getEAllReferences(), filterPredicate)));
        } else {
            return IScope.NULLSCOPE;
        }
    }

    protected IScope scope_JvmParametrizedTypeReference_type(EObject context, EReference reference) {
        SprayStyleRef style = EcoreUtil2.getContainerOfType(context, SprayStyleRef.class);
        if (style != null) {
            return scope_ShapeStyleRefScope(style, context, reference);
        }
        ShapeFromDsl shape = EcoreUtil2.getContainerOfType(context, ShapeFromDsl.class);
        if (shape != null) {
            return scope_ShapeShapeFromDslScope(shape, context, reference);
        }
        ConnectionInSpray connection = EcoreUtil2.getContainerOfType(context, ConnectionInSpray.class);
        if (connection != null) {
            return scope_ShapeConnectionInSprayScope(connection, context, reference);
        }
        return IScope.NULLSCOPE;
    }

    protected IScope scope_CreateBehavior_askFor(EObject context) {
        CreateBehavior createBehavior = (CreateBehavior) context;
        EReference ref = createBehavior.getContainmentReference();
        if (ref == null)
            return IScope.NULLSCOPE;
        if (ref.eIsProxy()) {
            ref = (EReference) EcoreUtil.resolve(ref, context);
            if (ref.eIsProxy()) {
                // still a proxy?
                return IScope.NULLSCOPE;
            }
        }
        Iterable<EAttribute> simpleAttributes = Iterables.filter(createBehavior.getContainmentReference().getEReferenceType().getEAllAttributes(), new Predicate<EAttribute>() {
            @Override
            public boolean apply(EAttribute input) {
                return input.getEType() instanceof EDataType;
            }
        });
        final IScope result = MapBasedScope.createScope(IScope.NULLSCOPE, Scopes.scopedElementsFor(simpleAttributes));
        return result;
    }

    protected IScope scope_MetaReference_labelProperty(EObject context) {
        MetaReference metaRef = (MetaReference) context;
        EReference ref = metaRef.getTarget();
        if (ref.eIsProxy()) {
            ref = (EReference) EcoreUtil.resolve(ref, context);
            if (ref.eIsProxy()) {
                // still a proxy?
                return IScope.NULLSCOPE;
            }
        }
        final IScope result = MapBasedScope.createScope(IScope.NULLSCOPE, Scopes.scopedElementsFor(metaRef.getTarget().getEReferenceType().getEAllAttributes()));
        return result;
    }

    protected IScope scope_MetaReference_target(final EObject context, final EReference reference) {
        final MetaClass metaClass = EcoreUtil2.getContainerOfType(context, MetaClass.class);
        IScope result = IScope.NULLSCOPE;
        if (metaClass != null && metaClass.getType() != null) {
            final Iterable<EReference> nonContainmentReferences = Iterables.filter(metaClass.getType().getEAllReferences(), new Predicate<EReference>() {
                @Override
                public boolean apply(EReference input) {
                    return !input.isContainment();
                }
            });
            result = MapBasedScope.createScope(IScope.NULLSCOPE, Scopes.scopedElementsFor(nonContainmentReferences));
        }
        return result;
    }

    protected IScope scope_Connection_to(EObject context) {
        final ConnectionInSpray connection = (ConnectionInSpray) context;
        IScope result = IScope.NULLSCOPE;
        final MetaClass metaClass = EcoreUtil2.getContainerOfType(context, MetaClass.class);
        // filter derived and 'from' from the possible references
        if (metaClass != null && metaClass.getType() != null && metaClass.getType().getEAllReferences() != null) {
            Iterable<EReference> targetReferences = Iterables.filter(metaClass.getType().getEAllReferences(), new Predicate<EReference>() {
                @Override
                public boolean apply(EReference input) {
                    return input != connection.getFrom() && !input.isDerived();
                }
            });
            result = MapBasedScope.createScope(IScope.NULLSCOPE, Scopes.scopedElementsFor(targetReferences));
        }
        return result;
    }

    protected IScope scope_Connection_from(EObject context) {
        IScope result = null;
        final MetaClass metaClass = EcoreUtil2.getContainerOfType(context, MetaClass.class);
        if (metaClass != null && metaClass.getType() != null && metaClass.getType().getEAllReferences() != null) {
            // filter derived references
            Iterable<EReference> targetReferences = Iterables.filter(metaClass.getType().getEAllReferences(), new Predicate<EReference>() {
                @Override
                public boolean apply(EReference input) {
                    return !input.isDerived();
                }
            });
            result = MapBasedScope.createScope(IScope.NULLSCOPE, Scopes.scopedElementsFor(targetReferences));
        } else {
            result = IScope.NULLSCOPE;
        }
        return result;
    }

    /**
     * Restrict the scope of shapes to the Shapes implementing ISprayShape
     * 
     * @param style
     * @param context
     * @param reference
     * @return
     */
    protected IScope scope_ShapeShapeFromDslScope(ShapeFromDsl style, EObject context, EReference reference) {
        IScope typesScope = delegateGetScope(style, TypesPackage.Literals.JVM_PARAMETERIZED_TYPE_REFERENCE__TYPE);
        Predicate<IEObjectDescription> stylesFilter = new ShapeScopeRestrictor();
        IScope result = new FilteringScope(typesScope, stylesFilter);
        return result;
    }

    /**
     * Restrict the scope of connections to the Shapes implementing ISprayConnection
     * 
     * @param style
     * @param context
     * @param reference
     * @return
     */
    protected IScope scope_ShapeConnectionInSprayScope(ConnectionInSpray style, EObject context, EReference reference) {
        IScope typesScope = delegateGetScope(style, TypesPackage.Literals.JVM_PARAMETERIZED_TYPE_REFERENCE__TYPE);
        Predicate<IEObjectDescription> stylesFilter = new ConnectionScopeRestrictor();
        IScope result = new FilteringScope(typesScope, stylesFilter);
        return result;
    }

    /**
     * Restrict the scope of styles to the Styles implementing ISprayStyle
     * 
     * @param style
     * @param context
     * @param reference
     * @return
     */
    protected IScope scope_ShapeStyleRefScope(SprayStyleRef style, EObject context, EReference reference) {
        IScope typesScope = delegateGetScope(style, TypesPackage.Literals.JVM_PARAMETERIZED_TYPE_REFERENCE__TYPE);
        Predicate<IEObjectDescription> stylesFilter = new StyleScopeRestrictor();
        IScope result = new FilteringScope(typesScope, stylesFilter);
        return result;
    }

    /**
     * Restrict the scope of keys to the simple names of parameters
     * 
     * @param context
     * @param reference
     * @return
     */
    protected IScope scope_ShapePropertyAssignment_Key(EObject context, EReference reference) {
        JvmType jvmType = null;
        final String className = "TextIds";
        final ShapeFromDsl shape = EcoreUtil2.getContainerOfType(context, ShapeFromDsl.class);
        final ConnectionInSpray connection = EcoreUtil2.getContainerOfType(context, ConnectionInSpray.class);
        if (shape != null && shape.getShape() != null && shape.getShape().getJvmShape() != null) {
            jvmType = shape.getShape().getJvmShape().getType();
        } else if (connection != null && connection.getConnection() != null && connection.getConnection().getJvmConnection() != null) {
            jvmType = connection.getConnection().getJvmConnection().getType();
        }
        if (jvmType != null && jvmType instanceof JvmGenericType) {
            return getEnumerationLiteralsScopeForShape((JvmGenericType) jvmType, className);
        } else {
            // done via proposal provider
            return IScope.NULLSCOPE;
        }
    }

    protected IScope scope_ShapeCompartmentAssignment_Key(EObject context, EReference reference) {
        JvmType jvmType = null;
        final String className = "TextIds";
        final ShapeFromDsl shape = EcoreUtil2.getContainerOfType(context, ShapeFromDsl.class);
        final ConnectionInSpray connection = EcoreUtil2.getContainerOfType(context, ConnectionInSpray.class);
        if (shape != null && shape.getShape() != null && shape.getShape().getJvmShape() != null) {
            jvmType = shape.getShape().getJvmShape().getType();
        } else if (connection != null && connection.getConnection() != null && connection.getConnection().getJvmConnection() != null) {
            jvmType = connection.getConnection().getJvmConnection().getType();
        }
        if (jvmType != null && jvmType instanceof JvmGenericType) {
            return getEnumerationLiteralsScopeForShape((JvmGenericType) jvmType, className);
        } else {
            // done via proposal provider
            return IScope.NULLSCOPE;
        }
    }

    protected boolean isJvm(EObject object) {
        if (object instanceof ShapeReference) {
            ShapeReference shaperef = (ShapeReference) object;
            return shaperef.getJvmShape() != null;
        } else if (object instanceof ConnectionReference) {
            ConnectionReference conref = (ConnectionReference) object;
            return conref.getJvmConnection() != null;
        }
        return false;
    }

    private IScope getEnumerationLiteralsScopeForShape(JvmGenericType type, String className) {
        JvmEnumerationType enumType = null;
        for (JvmMember member : type.getMembers()) {
            if (member.getSimpleName().equals(className)) {
                enumType = (JvmEnumerationType) member;
            }
        }
        List<IEObjectDescription> descrList = new ArrayList<IEObjectDescription>();
        if (enumType != null) {
            for (JvmEnumerationLiteral literal : enumType.getLiterals()) {
                IEObjectDescription description = EObjectDescription.create(literal.getSimpleName(), literal, null);
                descrList.add(description);
            }
        }
        return MapBasedScope.createScope(IScope.NULLSCOPE, descrList);
    }

    protected IScope scope_CreateBehavior_ContainmentReference(EObject context, EReference reference) {
        final MetaClass mc = EcoreUtil2.getContainerOfType(context, MetaClass.class);
        if (mc == null) {
            return IScope.NULLSCOPE;
        }
        Diagram diagram = mc.getDiagram();
        final EClass diagramModelType = diagram.getModelType();
        Predicate<EReference> filter = new Predicate<EReference>() {
            @Override
            public boolean apply(EReference input) {
                boolean superType = false;
                if (mc != null && mc.getType() != null) {
                    superType = isSuperType(input.getEReferenceType(), mc.getType());
                }
                return superType;
            }
        };
        // get all containments of EClass contained in this package
        List<EReference> containmentReferences = new ArrayList<EReference>();
        containmentReferences.addAll(diagramModelType.getEAllContainments());
        // if the MetaClass is a connection take also the containment dependencies of the source type
        if (mc.getRepresentedBy() instanceof ConnectionInSpray) {
            EClass sourceType = (EClass) ((ConnectionInSpray) mc.getRepresentedBy()).getFrom().getEType();
            if (sourceType != null) {
                containmentReferences.addAll(sourceType.getEAllContainments());
            }
        }
        return Scopes.scopeFor(Iterables.filter(containmentReferences, filter));
    }

    protected IScope scope_CreateBehavior_CompartmentReference(EObject context, EReference reference) {
        Diagram diagram = EcoreUtil2.getContainerOfType(context, Diagram.class);
        MetaClass metaClass = EcoreUtil2.getContainerOfType(context, MetaClass.class);
        if (diagram == null || metaClass == null) {
            return IScope.NULLSCOPE;
        }
        // all eClasses that are direct containments of context's diagram model type
        final EClass diagramModelType = diagram.getModelType();
        if (diagramModelType == null || diagramModelType.getEPackage() == null) {
            return IScope.NULLSCOPE;
        }
        List<EReference> containmentReferences = new ArrayList<EReference>();
        for (EClassifier eClassifiers : diagramModelType.getEPackage().getEClassifiers()) {
            if (eClassifiers instanceof EClass) {
                for (EReference ref : ((EClass) eClassifiers).getEAllContainments()) {
                    if (ref.getEType() instanceof EClass && isSuperType((EClass) ref.getEType(), metaClass.getType())) {
                        containmentReferences.add(ref);
                    }
                }
            }
        }
        return Scopes.scopeFor(containmentReferences);
    }

    /**
     * @param equals
     * @return
     */
    private boolean isSuperType(EClass eClass1, EClass eClass2) {
        return isSuperType(eClass1, eClass2, new ArrayList<EClass>());
    }

    private boolean isSuperType(EClass eClass1, EClass eClass2, List<EClass> visited) {
        boolean superType = eClass1.isSuperTypeOf(eClass2);
        if (!superType) {
            if (eClass1.getName().equals(eClass2.getName())) {
                superType = true;
            } else {
                EList<EClass> superTypes = eClass2.getESuperTypes();
                superTypes.removeAll(visited);
                visited.addAll(superTypes);
                for (EClass st : superTypes) {
                    superType = isSuperType(eClass1, st, visited);
                    if (superType) {
                        return superType;
                    }
                }
            }
        }
        return superType;
    }

    @SuppressWarnings("unchecked")
    protected IScope scope_MetaClass_Type(EObject context, EReference reference) {
        Diagram diagram = EcoreUtil2.getContainerOfType(context, Diagram.class);
        if (diagram == null) {
            return IScope.NULLSCOPE;
        }
        // all eClasses that are direct containments of context's diagram model type
        final EClass diagramModelType = diagram.getModelType();
        if (diagramModelType == null || diagramModelType.getEPackage() == null) {
            return IScope.NULLSCOPE;
        }
        final Predicate<EClassifier> filter = new Predicate<EClassifier>() {

            @Override
            public boolean apply(EClassifier input) {
                return input instanceof EClass; // && !((EClass) input).isAbstract() && isAContainmentReferenceType(diagramModelType, (EClass) input);
            }

        };
        return scopeEClasses(context, filter);
    }

    private boolean isAContainmentReferenceType(EClass diagramModelType, EClass input) {
        for (EReference containment : diagramModelType.getEAllContainments()) {
            if (input != null && containment.getEReferenceType() != null && isSuperType(containment.getEReferenceType(), input)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    protected IScope scope_Diagram_ModelType(final EObject context, EReference reference) {

        final Predicate<EClassifier> filter = new Predicate<EClassifier>() {
            @Override
            public boolean apply(EClassifier input) {
                return input instanceof EClass && ((EClass) input).getEAllReferences().size() > 0 && matchesOneOfTheImportedPackages(context, (EClass) input);
            }
        };
        return scopeEClasses(context, filter);
    }

    private boolean matchesOneOfTheImportedPackages(EObject context, EClass eClass) {
        String packageName = eClass.getEPackage() != null ? getFullQualifiedPackageName(eClass.getEPackage()) : null;
        if (packageName != null) {
            Import[] imports = ((Diagram) context).getImports();
            for (Import imp : imports) {
                String ns = imp.getImportedNamespace();
                ns = ns.replace(".*", "");
                if (packageName.startsWith(ns)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param ePackage
     * @return
     */
    private String getFullQualifiedPackageName(EPackage ePackage) {
        String name = null;
        EPackage superPackage = ePackage.getESuperPackage();
        if (superPackage != null) {
            name = getFullQualifiedPackageName(superPackage) + "." + ePackage.getName();
        } else {
            name = ePackage.getName();
        }
        return name;
    }

    private IScope scopeEClasses(EObject context, final Predicate<EClassifier>... filters) {
        List<EPackage> ePackages = packageSelector.getFilteredEPackages(context);
        List<EPackage> importedEPackages = getImportedPackages(context);
        Set<EClassifier> eClassifiers = new HashSet<EClassifier>();
        for (EPackage pack : ePackages) {
            iteratePackage(pack, eClassifiers);
        }
        return createScope(eClassifiers, importedEPackages, filters);
    }

    private List<EPackage> getImportedPackages(EObject context) {
        List<EPackage> ePackages = packageSelector.getFilteredEPackages(context);
        List<EPackage> importedEPackages = new ArrayList<EPackage>();
        List<String> alreadyImported = packageSelector.getAlreadyImportedForElement(context);
        String name;
        for (EPackage pack : ePackages) {
            name = pack.getName() + ".*";
            if (alreadyImported.contains(name)) {
                importedEPackages.add(pack);
            }
        }
        return importedEPackages;
    }

    private IScope createScope(final Set<EClassifier> eClassifiers, final List<EPackage> ePackages, final Predicate<EClassifier>... filters) {
        Iterable<EClassifier> filtered = getFiltered(eClassifiers, filters);
        List<IEObjectDescription> global = new ArrayList<IEObjectDescription>();
        // getEObjectDescriptionsForPackages(ePackages, global);
        // Iterable<IEObjectDescription> qnNamedEObjects = getEObjectDescriptionsForQualifiedNames(global, filtered);
        Iterable<IEObjectDescription> simpleNamedEObjects = getEObjectDescriptionsForSimpleNames(ePackages, filtered);
        final IScope packageScope = MapBasedScope.createScope(IScope.NULLSCOPE, global);
        return MapBasedScope.createScope(packageScope, simpleNamedEObjects);
    }

    private Iterable<EClassifier> getFiltered(final Set<EClassifier> eClassifiers, final Predicate<EClassifier>... filters) {
        final Predicate<EClassifier> filter = new Predicate<EClassifier>() {
            @Override
            public boolean apply(EClassifier input) {
                boolean filter = true;
                for (Predicate<EClassifier> f : filters) {
                    filter = filter && f.apply(input);
                }
                return filter;
            }
        };
        return Iterables.filter(eClassifiers, filter);
    }

    private Iterable<IEObjectDescription> getEObjectDescriptionsForPackages(final List<EPackage> ePackages, List<IEObjectDescription> global) {
        Function<EPackage, IEObjectDescription> packageToObjDesc = new Function<EPackage, IEObjectDescription>() {
            @Override
            public IEObjectDescription apply(EPackage from) {
                return EObjectDescription.create(from.getName(), from);
            }
        };
        Iterable<IEObjectDescription> packages = Iterables.transform(ePackages, packageToObjDesc);
        for (IEObjectDescription pack : packages) {
            global.add(pack);
        }
        return packages;
    }

    private Iterable<IEObjectDescription> getEObjectDescriptionsForQualifiedNames(List<IEObjectDescription> global, Iterable<EClassifier> filtered) {
        Function<EClassifier, IEObjectDescription> qnClassToObjDesc = new Function<EClassifier, IEObjectDescription>() {
            @Override
            public IEObjectDescription apply(EClassifier from) {
                if (from.getEPackage() != null && from.getEPackage().getName() != null) {
                    return EObjectDescription.create(QualifiedName.create(from.getEPackage().getName(), from.getName()), from);
                } else {
                    return EObjectDescription.create(from.getName(), from);
                }
            }
        };
        Iterable<IEObjectDescription> qnClassifiers = Iterables.transform(filtered, qnClassToObjDesc);
        for (IEObjectDescription qnClassifier : qnClassifiers) {
            global.add(qnClassifier);
        }
        return qnClassifiers;
    }

    private Iterable<IEObjectDescription> getEObjectDescriptionsForSimpleNames(final List<EPackage> ePackages, Iterable<EClassifier> filtered) {
        Function<EClassifier, IEObjectDescription> classToObjDesc = new Function<EClassifier, IEObjectDescription>() {
            @Override
            public IEObjectDescription apply(EClassifier from) {
                if (containsPackage(ePackages, from.getEPackage())) {
                    return EObjectDescription.create(from.getName(), from);
                } else {
                    if (from.getEPackage() != null && from.getEPackage().getName() != null) {
                        return EObjectDescription.create(QualifiedName.create(from.getEPackage().getName(), from.getName()), from);
                    } else {
                        return EObjectDescription.create(from.getName(), from);
                    }
                }
            }

            private boolean containsPackage(List<EPackage> ePackages, EPackage ePackage) {
                for (EPackage p : ePackages) {
                    if (p.getName() != null && p.getName().equals(ePackage.getName()) && p.getNsURI() != null && p.getNsURI().equals(ePackage.getNsURI())) {
                        return true;
                    }
                }
                return false;
            }
        };
        return Iterables.transform(filtered, classToObjDesc);
    }

    public void iteratePackage(EPackage pack, Set<EClassifier> eClassifiers) {
        List<EObject> contents = pack.eContents();
        EClass eclass;
        for (EObject o : contents) {
            if (o instanceof EClass) {
                eclass = (EClass) o;
                eClassifiers.add(eclass);
            } else if (o instanceof EPackage) {
                iteratePackage((EPackage) o, eClassifiers);
            }
        }
    }

    /**
     * Create the local variable scope for expressions.
     * The method will bind a variable 'this' which refers to the JvmType of the EClass associated with the current MetaClass.
     */
    @Override
    protected IScope createLocalVarScope(IScope parentScope, LocalVariableScopeContext scopeContext) {
        // bind the EClass of the context's MetaClass, only if the context is for a Spray DSL element.
        // all others context objects (like expressions) should be handled by default behavior
        if (scopeContext.getContext().eClass().getEPackage() == SprayPackage.eINSTANCE) {
            // Look up the containment hierarchy of the current object to find the MetaClass
            MetaClass mc = EcoreUtil2.getContainerOfType(scopeContext.getContext(), MetaClass.class);
            if (mc != null) {
                // get the JvmType for MetaClass. It is inferred by the SprayJvmModelInferrer
                JvmGenericType jvmType = (JvmGenericType) getJvmType(mc);
                if (jvmType == null || jvmType.getMembers().isEmpty()) {
                    // should not happen!
                    return IScope.NULLSCOPE;
                }
                // the JvmType has a field named 'ecoreClass'
                JvmField eClassField = (JvmField) jvmType.getMembers().get(0);
                Assert.isTrue(eClassField.getSimpleName().equals("ecoreClass"));
                // get the JvmType of the associated EClass
                JvmType jvmTypeOfEcoreClass = eClassField.getType().getType();
                // bind the EClass' JvmType as variable 'this'
                IScope result = new SingletonScope(EObjectDescription.create(XbaseScopeProvider.THIS, jvmTypeOfEcoreClass), super.createLocalVarScope(parentScope, scopeContext));
                return result;
            }
        }
        return super.createLocalVarScope(parentScope, scopeContext);
    }

    protected JvmType getJvmType(EObject context) {
        Iterable<JvmType> jvmTypes = Iterables.filter(associations.getJvmElements(context), JvmType.class);
        Iterator<JvmType> it = jvmTypes.iterator();
        JvmType result = it.hasNext() ? it.next() : null;
        return result;
    }

}
