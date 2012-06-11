package org.eclipselabs.spray.styles.scoping;

import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipselabs.spray.runtime.graphiti.styles.ISprayStyle;

import com.google.common.base.Predicate;

public class StyleScopeRestrictor implements Predicate<IEObjectDescription> {

    private final String STYLE_INTERFACE = ISprayStyle.class.getName();

    @Override
    public boolean apply(IEObjectDescription input) {
        if (input.getEObjectOrProxy() instanceof JvmGenericType) {
            return isStyle((JvmGenericType) input.getEObjectOrProxy());
        } else {
            return false;
        }
    }

    private boolean isStyle(JvmGenericType type) {
        for (JvmTypeReference itfRef : type.getExtendedInterfaces()) {
            if (isStyle(itfRef)) {
                return true;
            }
        }
        for (JvmTypeReference superTypeRef : type.getSuperTypes()) {
            if (isStyle(superTypeRef)) {
                return true;
            }
        }
        return false;
    }

    private boolean isStyle(JvmTypeReference typeRef) {
        if (STYLE_INTERFACE.equals(typeRef.getIdentifier())) {
            return true;
        }
        JvmGenericType type = (JvmGenericType) typeRef.getType();
        for (JvmTypeReference itfRef : type.getExtendedInterfaces()) {
            if (STYLE_INTERFACE.equals(itfRef.getIdentifier())) {
                return true;
            }
        }
        for (JvmTypeReference superTypeRef : type.getSuperTypes()) {
            if (isStyle(superTypeRef)) {
                return true;
            }
        }
        return false;
    }

}
