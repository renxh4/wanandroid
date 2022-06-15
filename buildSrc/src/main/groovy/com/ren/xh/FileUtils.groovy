package com.ren.xh

class FileUtils {
    static String getFileContent(File file) {
        def reader = new FileReader(file)
        def sb = new StringBuffer()
        def line
        while ((line = reader.readLine()) != null) {
            sb.append(line)
        }
        reader.close()

        return sb.toString()
    }

    static void toFileContent(File file, String json) {
        def fw = new FileWriter(file)
        fw.write(json)
        fw.close()
    }
}