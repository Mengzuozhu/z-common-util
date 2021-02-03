package com.github.mzz.web;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * Http file util.
 *
 * @author mengzz
 */
public class HttpFileUtil {

    private static final String CONTENT_TYPE = "multipart/form-data";
    private static final String CONTENT_DISPOSITION = "Content-Disposition";

    /**
     * 下载文件，注：需自己释放关闭inputStream
     *
     * @param inputStream the input stream
     * @param fileName    the file name
     * @param response    the response
     * @throws IOException the io exception
     */
    public static void downloadFile(InputStream inputStream, String fileName, HttpServletResponse response) throws IOException {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {
            response.reset();
            response.setContentType(CONTENT_TYPE);
            String encodeName = getEncodeName(fileName);
            response.setHeader(CONTENT_DISPOSITION, "attachment;fileName=" + encodeName);
            IOUtils.copy(inputStream, outputStream);
        }
    }

    /**
     * Download file.
     *
     * @param filePath the file path
     * @param response the response
     * @throws IOException the io exception
     */
    public static void downloadFile(String filePath, HttpServletResponse response) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            downloadFile(inputStream, FilenameUtils.getName(filePath), response);
        }
    }

    private static String getEncodeName(String fileName) throws UnsupportedEncodingException {
        String encodeName = URLEncoder.encode(fileName, Charset.defaultCharset().name());
        return encodeName.replaceAll("\\+", "%20");
    }
}
