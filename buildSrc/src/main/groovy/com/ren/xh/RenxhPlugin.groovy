package com.ren.xh

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ResolvedArtifact

import java.util.zip.ZipEntry
import java.util.zip.ZipFile


class RenxhPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        project.afterEvaluate {
            checkso(project)
        }
    }

    private static void checkso(Project project){
        new CheckSo().createTask(project)
        new CheckManifest().createManifestTask(project)
    }



}