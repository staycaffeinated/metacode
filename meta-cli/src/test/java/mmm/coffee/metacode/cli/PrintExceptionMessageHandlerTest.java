/*
 * Copyright 2020 Jon Caulfield
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmm.coffee.metacode.cli;

import mmm.coffee.metacode.common.exception.CreateEndpointUnsupportedException;
import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit test of the PrintExceptionMessageHandler
 */
class PrintExceptionMessageHandlerTest {

    PrintExceptionMessageHandler handlerUnderTest = new PrintExceptionMessageHandler();

    CommandLine mockCommandLine = Mockito.mock(CommandLine.class);

    CommandLine.IExitCodeExceptionMapper mockExitCodeExceptionMapper = Mockito.mock(CommandLine.IExitCodeExceptionMapper.class);

    CommandLine.ParseResult mockParseResult = Mockito.mock(CommandLine.ParseResult.class);

    CommandLine.Help.ColorScheme mockColorScheme = Mockito.mock(CommandLine.Help.ColorScheme.class);

    CommandLine.Model.CommandSpec mockCommandSpec = Mockito.mock(CommandLine.Model.CommandSpec.class);

    StringWriter stringWriter;
    PrintWriter printWriter;

    @BeforeEach
    public void setUpMocks() {
        // Start with clean Writer's each time
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);

        when(mockCommandLine.getErr()).thenReturn(printWriter);
        when(mockCommandLine.getExitCodeExceptionMapper()).thenReturn(mockExitCodeExceptionMapper);
        when(mockCommandLine.getColorScheme()).thenReturn(mockColorScheme);
        when(mockCommandLine.getCommandSpec()).thenReturn(mockCommandSpec);

        when(mockExitCodeExceptionMapper.getExitCode(any(Exception.class))).thenReturn(1);

    }

    @Test
    void test_handleCreateEndpointUnsupportedException() throws Throwable {
        CreateEndpointUnsupportedException ex = new CreateEndpointUnsupportedException();
        handlerUnderTest.handleExecutionException(ex, mockCommandLine, mockParseResult);

        String errorText = stringWriter.toString();
        assertThat(errorText).isNotEmpty();
        // We don't want too be to rigid on the exact verbiage of the error message,
        // but we want some assurance the verbiage for this specific exception is rendered,
        // so we'll check for some things we'll expect to see.
        assertThat(errorText).contains("spring-webflux");
        assertThat(errorText).contains("spring-webmvc");
    }

    @Test
    void test_handleRuntimeApplicationError() throws Throwable {
        RuntimeApplicationError ex = new RuntimeApplicationError();
        handlerUnderTest.handleExecutionException(ex, mockCommandLine, mockParseResult);

        // Since we're mocking all the Picocli classes, we don't have any concrete verbiage to verify.
        // We'll get sonarqube errors if we don't assert on something, so this is it.
        assertThat(stringWriter.toString()).isNotEmpty();
    }

    /**
     * This test handles the scenario where the ExitCodeExceptionMapper is null
     */
    @Test
    void whenCommandLineExitCodeExceptionMapperIsNull() throws Throwable {
        when(mockCommandLine.getExitCodeExceptionMapper()).thenReturn(null);

        RuntimeApplicationError ex = new RuntimeApplicationError();
        handlerUnderTest.handleExecutionException(ex, mockCommandLine, mockParseResult);

        // Since we're mocking all the Picocli classes, we don't have any concrete verbiage to verify.
        // We'll get sonarqube errors if we don't assert on something, so this is it.
        assertThat(stringWriter.toString()).isNotEmpty();
    }

}
