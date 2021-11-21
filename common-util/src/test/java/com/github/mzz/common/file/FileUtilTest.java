package com.github.mzz.common.file;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
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

    @Test
    public void zip() throws IOException {
        String jsonFile = "src/test/resources/demo.json";
        String jsonFile2 = "src/test/resources/demo2.json";
        String outDir = "src/test/resources/out";
        FileUtils.forceMkdir(new File(outDir));
        String outFile = outDir + "/test.zip";

        FileUtil.zip(Lists.newArrayList(jsonFile, jsonFile2), outFile);
        File result = new File(outFile);
        assertTrue(result.exists());
        assertTrue(result.delete());
    }
}
