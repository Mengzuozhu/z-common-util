package com.github.mzz.common.bean;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mengzz
 **/
class BeanCopyUtilTest {

    private static final long ID = 1L;

    @Test
    void testCopyClass() {
        SourceClass source = getSourceClass();
        TargetClass result = BeanCopyUtil.copy(source, TargetClass.class);
        compare(source, result);
    }

    private void compare(SourceClass source, TargetClass result) {
        assertEquals(source.getId(), result.getId());
        assertEquals(source.getAge(), result.getAge());
        assertEquals(source.getName(), result.getName());

        assertNotEquals(source.getEmail(), result.getMyEmail());
        assertNotEquals(source.getNum(), result.getNum());
    }

    @Test
    void testCopyObject() {
        SourceClass source = getSourceClass();
        TargetClass result = BeanCopyUtil.copy(source, new TargetClass());
        compare(source, result);
    }

    @Test
    void testCopyList() {
        SourceClass source = getSourceClass();
        List<TargetClass> result = BeanCopyUtil.copyList(Lists.newArrayList(source), TargetClass.class);
        compare(source, result.get(0));
    }

    @Test
    void testMapToBean() {
        Map<String, Object> map = new HashMap<>();
        map.put(TargetClass.Fields.id, ID);
        TargetClass result = BeanCopyUtil.mapToBean(map, TargetClass.class);
        assertEquals(map.get(TargetClass.Fields.id), result.getId());
    }

    @Test
    void testBeanToMap() {
        SourceClass source = getSourceClass();
        Map<String, Object> result = BeanCopyUtil.beanToMap(source);
        assertEquals(source.getId(), result.get(SourceClass.Fields.id));
    }

    private SourceClass getSourceClass() {
        return SourceClass.builder()
                .id(ID)
                .name("test")
                .age(20)
                .email("email@xxx")
                .num(12)
                .build();
    }
}
