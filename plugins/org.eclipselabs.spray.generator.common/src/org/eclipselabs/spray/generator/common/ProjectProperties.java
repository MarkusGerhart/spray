package org.eclipselabs.spray.generator.common;

/*******************************************************************************
 * Copyright (c) 2009 Ordina and committers to Mod4j
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ordina - initial implementation
 *******************************************************************************/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

/**
 * Properties:
 * <table>
 * <tr>
 * <th>Key</th>
 * <th>Type</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>headerTimestamp</td>
 * <td>boolean</td>
 * <td>If <tt>true</tt> a timestamp will be generated to file headers. Default is <tt>true</tt>.</td> </td>
 * </table>
 * To be continued
 */
public class ProjectProperties {
    private static final Log LOG = LogFactory.getLog(ProjectProperties.class);

    public static void setModelUri(URI uri) {
        if ((!uri.lastSegment().endsWith(".spray")) && (!uri.lastSegment().endsWith(".style")) && (!uri.lastSegment().endsWith(".shape"))) {
            return;
        }
        //        URI propertiesUri = uri.trimSegments(1).appendSegment(uri.lastSegment().replace(".spray", ".properties"));

        URI propertiesUri = uri.trimSegments(1).appendSegment("spray.properties");
        if (propertiesUri.isFile()) {
            propertyFile = propertiesUri.toFileString();
        } else if (propertiesUri.isPlatformResource()) {
            try {
                propertyFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(propertiesUri.devicePath().replace("/resource", ""))).getLocation().toFile().getCanonicalPath();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        readAllProperties();
    }

    private static void readAllProperties() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(propertyFile));
        } catch (FileNotFoundException e) {
            System.err.println("PropjectProperties: cannot find properties file [" + propertyFile + "]");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("PropjectProperties: cannot read properties file [" + propertyFile + "]");
            e.printStackTrace();
        }
        // overload with System properties
        for (Entry<Object, Object> entry : System.getProperties().entrySet()) {
            if (properties.containsKey(entry.getKey())) {
                LOG.info("Overriding configured property " + entry.getKey() + " by system property, value" + entry.getValue());
                properties.put(entry.getKey(), entry.getValue());
            }
        }

        mainPackage = properties.getProperty("mainPackage", mainPackage);
        diagramPackage = properties.getProperty("diagramPackage", diagramPackage);
        featurePackage = properties.getProperty("featurePackage", featurePackage);
        propertyPackage = properties.getProperty("propertyPackage", propertyPackage);
        stylesPackage = properties.getProperty("stylesPackage", stylesPackage);
        gradientsPackage = properties.getProperty("gradientsPackage", gradientsPackage);
        shapesPackage = properties.getProperty("shapesPackage", shapesPackage);
        utilPackage = properties.getProperty("utilPackage", utilPackage);
        srcGenPath = properties.getProperty("srcGenPath", srcGenPath);
        resourceGenPath = properties.getProperty("resourceGenPath", resourceGenPath);
        srcManPath = properties.getProperty("srcManPath", srcManPath);
        resourceManPath = properties.getProperty("resourceManPath", resourceManPath);
        projectPath = properties.getProperty("projectPath", projectPath);
        headerTimestamp = Boolean.valueOf(properties.getProperty("headerTimestamp", "true"));
        fileExtension = properties.getProperty("modelFileExtension");
    }

    private static String       projectPath                   = "/";

    private static String       propertyFile                  = "DEFAULT";

    private static String       applicationVersion            = "DEFAULT";

    private static String       applicationName               = "DEFAULT";

    private static String       applicationPath               = "DEFAULT";

    private static String       dslModelsModuleName           = "dslModels";

    private static final String SRC_MODEL_PATH                = "src/model";

    private static String       businessModuleName            = "DEFAULT";

    private static String       domainModuleName              = "org.sample.domain";

    private static String       rootPackage                   = "org.sample.domain";

    private static String       businessRootPackage           = "DEFAULT";

    private static String       pluginId                      = "spray";
    private static String       mainPackage                   = pluginId;
    private static String       diagramPackage                = "diagrams";
    private static String       featurePackage                = "features";
    private static String       propertyPackage               = "property";
    private static String       stylesPackage                 = "styles";
    private static String       gradientsPackage              = "gradients";
    private static String       shapesPackage                 = "shapes";
    private static String       utilPackage                   = "org.eclipselabs.spray.runtime.containers";

    private static String       srcGenPath                    = "src-gen";

    private static String       resourceGenPath               = "resource-gen";

    private static String       srcManPath                    = "src";

    private static String       resourceManPath               = "resource";

    private static final String environmentPropertiesFileName = "environment.properties";

    private static String       fileEncoding                  = "UTF-8";

    public static final String  IMPLBASE_SUFFIX               = "ImplBase";

    private static String       workDir                       = "/";

    private static boolean      headerTimestamp               = true;

    private static String       fileExtension;

    public static void setWorkDir(String dir) {
        workDir = dir;
    }

    public static String getApplicationName() {
        return applicationName;
    }

    public static String getApplicationVersion() {
        return applicationVersion;
    }

    public static String getApplicationPath() {
        if (applicationPath != null) {
            if (applicationPath.startsWith("..")) {
                if (applicationPath.equals("..")) {
                    int last = workDir.lastIndexOf("/");
                    if (last == -1) {
                        last = workDir.lastIndexOf("\\");
                    }
                    if (last > -1) {
                        return workDir.substring(0, last);
                    }
                } else {
                    int last = workDir.lastIndexOf("/");
                    return workDir.substring(0, last) + applicationPath.substring(2);
                }
            }
        }
        return workDir + "/" + applicationPath;
    }

    public static String getDslModelsModulePath() {
        return getApplicationPath() + "/" + getDslModelsModuleName();
    }

    public static String getDslModelsModuleName() {
        return dslModelsModuleName;
    }

    public static String getDomainModulePath() {
        return getApplicationPath() + "/" + getDomainModuleName();
    }

    public static String getDomainModuleName() {
        return domainModuleName;
    }

    public static String getBusinessModuleName() {
        return businessModuleName;
    }

    public static String getBusinessModulePath() {
        return getApplicationPath() + "/" + getBusinessModuleName();
    }

    public static String getRootPackage() {
        return rootPackage;
    }

    public static String getBusinessRootPackage() {
        return businessRootPackage;
    }

    public static String getBusinessRootPackageAsPath() {
        return getBusinessRootPackage().replaceAll("\\.", "/");
    }

    public static String getProjectPath() {
        return projectPath;
    }

    public static String getMainPackage() {
        return mainPackage;
    }

    public static String getPluginId() {
        return pluginId;
    }

    public static String getDiagramPackage() {
        return diagramPackage;
    }

    public static String getFeaturePackage() {
        return featurePackage;
    }

    public static String getPropertyPackage() {
        return propertyPackage;
    }

    public static String getStylesPackage() {
        return stylesPackage;
    }

    public static String getGradientsPackage() {
        return gradientsPackage;
    }

    public static String getShapesPackage() {
        return shapesPackage;
    }

    public static String getUtilPackage() {
        return utilPackage;
    }

    public static String getDomainRootPackageAsPath() {
        return getDiagramPackage().replaceAll("\\.", "/");
    }

    public static String toPath(String packageName) {
        return packageName.replaceAll("\\.", "/") + "/";
    }

    public static String getSrcModelPath() {
        return SRC_MODEL_PATH;
    }

    public static String getSrcGenPath() {
        return srcGenPath;
    }

    public static String getResourceGenPath() {
        return resourceGenPath;
    }

    public static String getSrcManPath() {
        return srcManPath;
    }

    public static String getResourceManPath() {
        return resourceManPath;
    }

    public static String getEnvPropFileName() {
        return environmentPropertiesFileName;
    }

    public static String getFileEncoding() {
        return fileEncoding;
    }

    public static boolean getHeaderTimestamp() {
        return headerTimestamp;
    }

    public static String getModelFileExtension() {
        return fileExtension;
    }

    private static String project = "/";

    public static String getProject() {
        return project;
    }

    public static String getProjectForEObject(EObject object) {
        if (project.equals("/")) {
            URI uri = object.eResource().getURI();
            return uri.segment(1);
        } else {
            return project;
        }
    }

    public static void setProject(String project) {
        ProjectProperties.project = project;
    }

}
