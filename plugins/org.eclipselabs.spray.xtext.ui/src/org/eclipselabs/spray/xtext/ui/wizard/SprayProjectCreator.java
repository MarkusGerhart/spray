package org.eclipselabs.spray.xtext.ui.wizard;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.xtext.builder.EclipseResourceFileSystemAccess;
import org.eclipse.xtext.ui.wizard.AbstractPluginProjectCreator;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipselabs.spray.generator.common.ProjectProperties;
import org.eclipselabs.spray.xtext.ui.internal.SprayActivator;
import org.eclipselabs.spray.xtext.ui.wizard.codegen.NewProjectGenerator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class SprayProjectCreator extends AbstractPluginProjectCreator {

    protected static final String           DSL_GENERATOR_PROJECT_NAME = "org.eclipselabs.spray.generator.graphiti";

    protected static final String           SRC_ROOT                   = "src";
    protected static final String           SRC_GEN_ROOT               = "src-gen";
    protected static final String           MODEL_ROOT                 = "model";
    protected final List<String>            SRC_FOLDER_LIST            = ImmutableList.of(SRC_ROOT, SRC_GEN_ROOT, MODEL_ROOT);
    protected final String                  rootpath                   = "/resources/newProjectFiles";

    @Inject
    private EclipseResourceFileSystemAccess fileSystemAccess;
    @Inject
    private NewProjectGenerator             newProjectGenerator;
    @Inject
    private IWorkspaceRoot                  root;

    @Override
    protected SprayProjectInfo getProjectInfo() {
        return (SprayProjectInfo) super.getProjectInfo();
    }

    protected String getModelFolderName() {
        return MODEL_ROOT;
    }

    @Override
    protected List<String> getAllFolders() {
        SprayProjectInfo info = getProjectInfo();
        return Lists.newArrayList(info.getJavaMainSrcDir(), info.getJavaGenSrcDir(), info.getSprayModelDir());
    }

    protected void enhanceProject(final IProject project, final IProgressMonitor monitor) throws CoreException {
        createRootFiles(project, monitor);
        project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
        IFile generatedPluginXml = project.getFile(getProjectInfo().getJavaGenSrcDir() + "/plugin.xml");
        if (generatedPluginXml.exists()) {
            generatedPluginXml.copy(project.getFullPath().append("/plugin.xml"), true, new NullProgressMonitor());
        }
    }

    protected void createRootFiles(final IProject project, final IProgressMonitor monitor) throws CoreException {
        copyRootFiles(project, monitor, rootpath);
        generateFiles(project, monitor);
    }

    protected void copyRootFiles(final IProject project, final IProgressMonitor monitor, String basepath) throws CoreException {
        Enumeration<String> entries = SprayActivator.getInstance().getBundle().getEntryPaths(basepath);
        while (entries.hasMoreElements()) {
            String entry = entries.nextElement();

            URL url = SprayActivator.getInstance().getBundle().getResource(entry);
            String targetPath = entry.substring(rootpath.length());
            if (entry.endsWith("/")) {
                IFolder folder = project.getFolder(targetPath);
                if (!folder.exists()) {
                    folder.create(true, true, monitor);
                }
                copyRootFiles(project, monitor, entry);
            } else {
                try {
                    IFile targetFile = project.getFile(targetPath);
                    if (targetFile.exists())
                        targetFile.delete(true, monitor);
                    targetFile.create(url.openStream(), true, monitor);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    protected void generateFiles(final IProject project, final IProgressMonitor monitor) throws CoreException {
        // Register the names of all projects as outlet names
        for (IProject p : root.getProjects()) {
            fileSystemAccess.setOutputPath(p.getName(), p.getName());
        }
        fileSystemAccess.setNewFileAcceptor(new IAcceptor<String>() {
            @Override
            public void accept(String t) {
                IFile file = root.getFile(new Path(t));
                try {
                    file.setDerived(false, monitor);
                } catch (CoreException e) {
                    ; // can be ignored
                }
            }
        });
        newProjectGenerator.doGenerate(getProjectInfo(), fileSystemAccess);
    }

    @Override
    protected IFile getModelFile(IProject project) throws CoreException {
        return project.getFile(getProjectInfo().getSprayModelDir() + "/" + getProjectInfo().getDiagramTypeName() + ProjectProperties.SPRAY_FILE_EXTENSION);
    }

    @Override
    protected String getActivatorClassName() {
        return getProjectInfo().getBasePackage() + ".Activator";
    }

    @Override
    protected List<String> getImportedPackages() {
        return Lists.newArrayList("org.apache.log4j", "org.apache.commons.logging", "org.eclipse.xtext.xbase.lib", "org.eclipse.xtext.service", "org.eclipse.xtext.ui.editor", "org.eclipse.xtext.ui.guice", "org.eclipse.xtext.ui.resource", "org.eclipse.xtext.util", "org.eclipselabs.spray.styles.generator.util");
    }

    @Override
    protected List<String> getRequiredBundles() {
        List<String> result = Lists.newArrayList("org.eclipse.ui", "org.eclipse.core.runtime", "org.eclipse.emf.ecore", "org.eclipse.graphiti;bundle-version=\"0.8.0\"", "org.eclipse.graphiti.mm;bundle-version=\"0.8.0\"", "org.eclipse.graphiti.pattern;bundle-version=\"0.8.0\"", "org.eclipse.graphiti.ui;bundle-version=\"0.8.0\"", "org.eclipse.graphiti.ui.capabilities;bundle-version=\"0.8.0\"", "org.eclipselabs.spray.runtime.graphiti", "org.eclipselabs.spray.shapes", "org.eclipselabs.spray.styles", "org.eclipse.ui.views.properties.tabbed;bundle-version=\"3.5.200\"", "org.eclipse.emf;bundle-version=\"2.6.0\"", "org.eclipse.emf.transaction;bundle-version=\"1.4.0\"", "com.google.guava;bundle-version=\"10.0.1\"", "com.google.inject;bundle-version=\"3.0.0\"", "javax.inject;bundle-version=\"1.0.0\"");
        String mmBundle = getProjectInfo().getMetamodelBundleName();
        if (mmBundle != null && !result.contains(mmBundle)) {
            result.add(mmBundle);
        }
        return result;
    }
}
