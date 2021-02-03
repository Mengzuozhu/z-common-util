package com.github.mzz.excel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mengzz
 **/
class ExcelReadUtilTest {

    private String fileName = "src/test/resources/simple.xlsx";

    @Test
    public void readExcelHead() {
        List<Map<Integer, String>> heads = ExcelReadUtil.readExcelHead(fileName, 0);
        Assertions.assertEquals(1, heads.size());
    }

    @Test
    public void readExcel() {
        List<Map<Integer, String>> rows = new ArrayList<>();
        List<Map<Integer, String>> heads = new ArrayList<>();
        int limitRow = 2;
        ExcelReadUtil.readExcel(fileName, 0, limitRow, heads::add, rows::add);
        Assertions.assertEquals(1, heads.size());
        Assertions.assertEquals(limitRow, rows.size());
        System.out.println(heads);
    }

    @Test
    public void readSync() {
        List<Map<Integer, String>> rows = ExcelReadUtil.readSync(fileName, 0);
        Assertions.assertEquals(10, rows.size());
    }

    @Test
    public void readList() {
        List<List<String>> rows = ExcelReadUtil.readList(fileName, 0);
        Assertions.assertEquals(10, rows.size());
    }
}
