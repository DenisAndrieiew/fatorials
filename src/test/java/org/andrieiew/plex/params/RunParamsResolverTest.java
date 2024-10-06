package org.andrieiew.plex.params;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RunParamsResolverTest {
    @Test
    public void testFParam() {
        AppParams appParams = RunParamsResolver.resolve(new String[]{"-f", "test.txt"});
        assertEquals("test.txt", appParams.getFileName());
    }

    @Test
    public void testFileParam() {
        AppParams appParams = RunParamsResolver.resolve(new String[]{"--file", "test.txt"});
        assertEquals("test.txt", appParams.getFileName());
    }

    @Test
    public void testEmptyParams() {
        AppParams appParams = RunParamsResolver.resolve(new String[]{});
        assertNull(appParams.getFileName());
    }

    @Test
    public void testEmptyFileName() {
        AppParams appParams = RunParamsResolver.resolve(new String[]{"-f"});
        assertNull(appParams.getFileName());
    }

    @Test
    public void testEmptyFileName2() {
        AppParams appParams = RunParamsResolver.resolve(new String[]{"--file"});
        assertNull(appParams.getFileName());
    }

    @Test
    public void testRandomParams() {
        AppParams appParams = RunParamsResolver.resolve(new String[]{"-d", "dddd", "--file", "test2.txt"});
        assertEquals("test2.txt", appParams.getFileName());
    }

    @Test
    public void testThreadsParam() {
        AppParams appParams = RunParamsResolver.resolve(new String[]{"-t", "5"});
        assertEquals(5, appParams.getThreadsCount());
    }

    @Test
    public void testCacheParam() {
        AppParams appParams = RunParamsResolver.resolve(new String[]{"-c", "FULL"});
        assertEquals(AppParams.CacheType.FULL, appParams.getCacheType());
        appParams = RunParamsResolver.resolve(new String[]{"-c", "none"});
        assertEquals(AppParams.CacheType.NONE, appParams.getCacheType());
    }

    @Test
    public void testCacheParam2() {
        AppParams appParams = RunParamsResolver.resolve(new String[]{"--cache", "STEPS"});
        assertEquals(AppParams.CacheType.STEPS, appParams.getCacheType());
        appParams = RunParamsResolver.resolve(new String[]{"--cache", "steps"});
        assertEquals(AppParams.CacheType.STEPS, appParams.getCacheType());
    }

    @Test
    public void testDefaultCacheParam() {
        AppParams appParams = RunParamsResolver.resolve(new String[]{"--cache", "steps"});
        assertEquals(AppParams.CacheType.STEPS, appParams.getCacheType());
        appParams = RunParamsResolver.resolve(new String[]{});
        assertEquals(AppParams.CacheType.NONE, appParams.getCacheType());
        appParams = RunParamsResolver.resolve(new String[]{"kdkd", "-f", "test.txt", "-t", "5"});
        assertEquals(AppParams.CacheType.NONE, appParams.getCacheType());
    }


}