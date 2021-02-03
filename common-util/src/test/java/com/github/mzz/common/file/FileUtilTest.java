package com.github.mzz.common.file;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type File util test.
 *
 * @author mengzz
 */
public class FileUtilTest {
    private String jsonFile = "src/test/resources/demo.json";
    private String jsonFile2 = "src/test/resources/demo2.json";
    private String outFile = "src/test/resources/out/test.zip";

    @Test
    public void zip() throws IOException {
        FileUtil.zip(Lists.newArrayList(jsonFile, jsonFile2), outFile);
        assertTrue(new File(outFile).exists());
    }
}
