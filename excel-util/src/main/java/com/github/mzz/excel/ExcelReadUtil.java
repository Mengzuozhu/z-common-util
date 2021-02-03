package com.github.mzz.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * excel工具类
 *
 * @author mengzz
 **/
public class ExcelReadUtil {

    /**
     * Read excel.
     *
     * @param path        the path
     * @param rowConsumer the rowConsumer
     */
    public static void readExcel(String path, Consumer<Map<Integer, String>> rowConsumer) {
        readExcel(path, 0, -1, null, rowConsumer);
    }

    /**
     * Read excel.
     *
     * @param path        the path
     * @param sheetNo     the sheet no
     * @param rowConsumer the row consumer
     */
    public static void readExcel(String path, int sheetNo, Consumer<Map<Integer, String>> rowConsumer) {
        readExcel(path, sheetNo, -1, null, rowConsumer);
    }

    /**
     * Read excel.
     *
     * @param path        the path
     * @param sheetNo     the sheet no
     * @param limitRow    the limit row
     * @param rowConsumer the row consumer
     */
    public static void readExcel(String path, int sheetNo, int limitRow, Consumer<Map<Integer, String>> rowConsumer) {
        readExcel(path, sheetNo, limitRow, null, rowConsumer);
    }

    /**
     * Read excel.
     *
     * @param path         the path
     * @param sheetNo      the sheet no
     * @param headConsumer the head consumer
     * @param rowConsumer  the rowConsumer
     */
    public static void readExcel(String path, int sheetNo, int limitRow, Consumer<Map<Integer, String>> headConsumer,
                                 Consumer<Map<Integer, String>> rowConsumer) {
        EasyExcel.read(path, new AnalysisEventListener<Map<Integer, String>>() {
            @Override
            public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                if (headConsumer != null) {
                    headConsumer.accept(headMap);
                }
            }

            @Override
            public boolean hasNext(AnalysisContext context) {
                if (limitRow > 0 && context.readRowHolder().getRowIndex() >= limitRow) {
                    return false;
                }
                return super.hasNext(context);
            }

            @Override
            public void invoke(Map<Integer, String> data, AnalysisContext context) {
                if (rowConsumer != null) {
                    rowConsumer.accept(data);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet(sheetNo).doRead();
    }

    /**
     * Read sync list.
     *
     * @param path    the path
     * @param sheetNo the sheet no
     * @return the list
     */
    public static List<Map<Integer, String>> readSync(String path, int sheetNo) {
        return EasyExcel.read(path).sheet(sheetNo).doReadSync();
    }

    /**
     * Read list list.
     *
     * @param path    the path
     * @param sheetNo the sheet no
     * @return the list
     */
    public static List<List<String>> readList(String path, int sheetNo) {
        List<List<String>> list = new ArrayList<>();
        readExcel(path, sheetNo, row -> {
            List<String> strings = new ArrayList<>();
            for (Map.Entry<Integer, String> entry : row.entrySet()) {
                strings.add(entry.getKey(), entry.getValue());
            }
            list.add(strings);
        });
        return list;
    }

    /**
     * Read excel head list.
     *
     * @param path      the path
     * @param readCount the read count
     * @return the list
     */
    public static List<Map<Integer, String>> readExcelHead(String path, int readCount) {
        List<Map<Integer, String>> heads = new ArrayList<>();
        readExcelHead(path, 0, readCount, heads::add);
        return heads;
    }

    /**
     * Read excel head.
     *
     * @param path         the path
     * @param sheetNo      the sheet no
     * @param limitRow     the limit row
     * @param headConsumer the head consumer
     */
    public static void readExcelHead(String path, int sheetNo, int limitRow,
                                     Consumer<Map<Integer, String>> headConsumer) {
        Objects.requireNonNull(headConsumer);
        readExcel(path, sheetNo, limitRow, headConsumer, null);
    }

}
