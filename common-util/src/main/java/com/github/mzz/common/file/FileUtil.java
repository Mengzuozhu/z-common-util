package com.github.mzz.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The type File util.
 *
 * @author mengzz
 */
public class FileUtil {

    /**
     * Foreach files.
     *
     * @param dirPath  the dir path
     * @param consumer the consumer
     */
    public static void foreachFiles(String dirPath, Consumer<? super File> consumer) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile()) {
                consumer.accept(file);
            }
        }
    }

    /**
     * Zip.
     *
     * @param srcFiles    the src files
     * @param outFilePath the out file path
     * @throws IOException the io exception
     */
    public static void zip(List<String> srcFiles, String outFilePath) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(outFilePath))) {
            for (String srcFile : srcFiles) {
                File fileToZip = new File(srcFile);
                try (FileInputStream fis = new FileInputStream(fileToZip)) {
                    zipOut.putNextEntry(new ZipEntry(fileToZip.getName()));
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) != -1) {
                        zipOut.write(buffer, 0, length);
                    }
                    zipOut.closeEntry();
                }
            }
        }
    }
}
