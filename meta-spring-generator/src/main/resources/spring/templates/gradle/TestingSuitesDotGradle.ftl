/*
 * This follows Gradle's idiom for defining
 * new test suites, based on Gradle's jvm-test-suite
 * plugin.  In this file, two kinds of test suites
 * are defined: integrationTest and performanceTest.
 * Java integration tests are found in src/integrationTest/java.
 * Define your own src/performanceTest/[language] folder
 * for your performance tests (since I don't know if you'll
 * use JMeter or something else).
 */


testing {
    suites {
        test {
            useJUnitJupiter()
        }
        /*
         * Define the integration test suite
         */
        integrationTest(JvmTestSuite) {
            testType.set(TestSuiteType.INTEGRATION_TEST)
            dependencies {
                implementation project()
            }
            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(test)
                    }
                }
            }
            configurations {
                integrationTestImplementation {
                    extendsFrom testImplementation
                }
            }
        }
        /*
         * Define the performance test suite
         */
        performanceTest(JvmTestSuite) {
            testType.set(TestSuiteType.PERFORMANCE_TEST)
            dependencies {
                implementation project()
            }
            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(test)
                    }
                }
            }
            configurations {
                performanceTestImplementation {
                    extendsFrom testImplementation
                }
            }
        }
    }
}
/*
 * The 'check' task will trigger integration tests.
 */
tasks.named('check') {
    dependsOn(testing.suites.integrationTest)
}
