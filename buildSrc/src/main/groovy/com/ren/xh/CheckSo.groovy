package com.ren.xh

import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ResolvedArtifact

import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class CheckSo{

    public static final String ARTIFACT_TYPE_AAR = 'aar'

    public static final String ARTIFACT_TYPE_JAR = 'jar'

    void createTask(Project project){
        project.task("checkso").doFirst {
            Configuration bb= project.configurations.getByName("implementation")
            bb.canBeResolved=true
            project.gradle.addListener(new EmbedResolutionListener(project, bb))
            def set  = resolveArtifacts(bb)
            processArtifacts(set)
        }
    }


    private static Collection<ResolvedArtifact> resolveArtifacts(Configuration configuration) {
        //这个方法的主要作用是，拿到embed引用的依赖，并判断他是aar或者jar包，然后放入集合
        def set = new ArrayList()
        if (configuration != null) {
            configuration.canBeResolved=true
            configuration.resolvedConfiguration.resolvedArtifacts.each { artifact ->
                if (ARTIFACT_TYPE_AAR == artifact.type || ARTIFACT_TYPE_JAR == artifact.type) {
                    //
                } else {
                    throw new ProjectConfigurationException('Only support embed aar and jar dependencies!', null)
                }
                //这里的 artifact 指的就是  如 ： okhttp3-integration-4.11.0.aar
                set.add(artifact)
            }
        }
        return set
    }


    private static void processArtifacts(Collection<ResolvedArtifact> artifacts) {
        ArrayList<String>  v8a =  new ArrayList<String>()
        ArrayList<String>  arm =  new ArrayList<String>()
        HashMap<String,String>  armMap = new HashMap<String,String>()
        HashMap<String,String>  v8aMap = new HashMap<String,String>()
        for (final ResolvedArtifact artifact in artifacts) {
            String gp = artifact.getModuleVersion().getId().getGroup()
            String na = artifact.getModuleVersion().getId().getName()
            Utils.printDebugmm("---------------[依赖产物开始] group=${gp}:${na}----------------")
            ZipFile zipFile =  new ZipFile(artifact.file)
            Enumeration<?> entries =  zipFile.entries()

            while (entries.hasMoreElements()){
                ZipEntry entry = (ZipEntry) entries.nextElement();

                if (entry.isDirectory()){

                }else {
                    def lastIndex = entry.name.lastIndexOf("/") + 1
                    def name =  entry.name.substring(lastIndex)
                    if (name.endsWith(".so")){
                        Utils.printDebugmm("so文件 = "+ entry.name+"   size = "+(entry.getCompressedSize()/1024/1024))
                        if (entry.name.contains("arm64-v8a")){
                            v8a.add(name)
                            v8aMap.put(name,"group=${gp}:${na}"+"   size = "+(entry.getCompressedSize()/1024/1024))
                        }
                        if (entry.name.contains("armeabi")){
                            arm.add(name)
                            armMap.put(name,"group=${gp}:${na}"+"   size = "+(entry.getCompressedSize()/1024/1024))
                        }
                    }
                }
            }
            Utils.printDebugmm("---------------[依赖产物结束] group=${gp}:${na}----------------")
        }

        v8a.each {
            if (!arm.contains(it)){
                Utils.printDebugmm("arm不包含 = "+ it+"   "+ armMap.get(it))
            }
        }

        arm.each {
            if (!v8a.contains(it)){
                Utils.printDebugmm("v8a不包含 = "+ it+"   "+armMap.get(it))
            }
        }
    }


}