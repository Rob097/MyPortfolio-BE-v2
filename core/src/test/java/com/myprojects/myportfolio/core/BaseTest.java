package com.myprojects.myportfolio.core;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

@Slf4j
public class BaseTest {

    @BeforeAll
    public static void beforeAll(TestInfo testInfo) {
        String methodName = testInfo.getTestClass().orElseThrow().getName();
        log.info("\n\n========== BEGIN " + methodName + " ==========\n");
    }

    @AfterAll
    public static void afterAll(TestInfo testInfo) {
        String methodName = testInfo.getTestClass().orElseThrow().getName();
        log.info("\n\n========== END " + methodName + " ==========\n");
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        String methodName = testInfo.getTestMethod().orElseThrow().getName();
        log.info("\n\n========== BEGIN " + methodName + " ==========\n");
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        String methodName = testInfo.getTestMethod().orElseThrow().getName();
        log.info("\n\n========== END " + methodName + " ==========\n");
    }

}
