package com.github.mzz.common.bean;

import org.junit.jupiter.api.Test;
import org.objenesis.instantiator.ObjectInstantiator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mengzz
 **/
class BeanCreatorTest {

    @Test
    void testNewInstance() {
        SourceClass result = BeanCreator.newInstance(SourceClass.class);
        assertNotNull(result);
    }

    @Test
    void testGetInstantiatorOf() {
        ObjectInstantiator<SourceClass> result = BeanCreator.getInstantiatorOf(SourceClass.class);
        assertNotNull(result.newInstance());
    }
}
