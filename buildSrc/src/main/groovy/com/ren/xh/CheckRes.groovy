package com.ren.xh

import com.google.gson.Gson
import org.gradle.api.Project

class CheckRes {

    static void createResTask(Project project) {
        TinifyFormt tinifyFormt = new TinifyFormt()
        tinifyFormt.init()
        project.task("checkres").doFirst {
            def path = project.getRootDir().getAbsolutePath() + "/app/src/main/res/"
            def outPath = project.getRootDir().getAbsolutePath() + "/app/build/compress"
            def outPathFile = project.getRootDir().getAbsolutePath() + "/compress.json"
            def json = new File(outPathFile)
            CompressBean compressBean
            if (!json.exists()) {
                json.createNewFile()
                compressBean = new CompressBean()
            } else {
                def jsoncon = FileUtils.getFileContent(json)
                compressBean = new Gson().fromJson(jsoncon, CompressBean.class)
            }
            def out = new File(outPath)
            if (!out.exists()) {
                out.mkdirs()
            }
            File file = new File(path)
            if (compressBean == null) {
                compressBean = new CompressBean()
            }
            if (compressBean.data==null){
                compressBean.data = new ArrayList<String>()
            }
            eachDictory(file, tinifyFormt, outPath, compressBean.data)

            def jsonResult = new Gson().toJson(compressBean)
            FileUtils.toFileContent(json, jsonResult)

            Utils.printDebugmm("此次共压缩 {$TinifyFormt.compressSize}")
        }
    }

    static void eachDictory(File file, TinifyFormt tinifyFormt, String outPath, ArrayList<String> list) {
        if (file.isDirectory()) {
            file.listFiles().each {
                eachDictory(it, tinifyFormt, outPath, list)
            }
        } else {
            if (file.name.endsWith(".png")) {
                int size = file.size() / 1024
                if (size > 100) {
                    if (list.contains(file.absolutePath)) {
                        Utils.printDebugmm(file.absolutePath+"  已经压缩过了")
                    } else {
                        Utils.printDebugmm(file.absolutePath + "  size = " + size + "KB")
                        tinifyFormt.commpress(file.absolutePath, outPath, file.name, list)
                    }
                }
            }
        }
    }
}