package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.services.CloudinaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "user0", userDetailsServiceBeanName = "userDetailsService")
class FileTransferRestControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

    public static final String TEST_URL = "testUrl.com";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FileTransferRestController fileTransferRestController;

    @MockBean
    private CloudinaryService cloudinaryService;

    @Test
    void upload() throws Exception {
        // arrange
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!" .getBytes()
        );

        when(cloudinaryService.uploadFile(any(), anyString(), any())).thenReturn(TEST_URL);

        // act & assert
        mockMvc.perform(multipart("/api/file/upload").file(file).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url", is(TEST_URL)));
    }

    @Test
    void uploadError() throws Exception {
        // arrange
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!" .getBytes()
        );

        when(cloudinaryService.uploadFile(any(), anyString(), any()))
                .thenThrow(new MaxUploadSizeExceededException(500L) );

        // act & assert
        mockMvc.perform(multipart("/api/file/upload").file(file).with(csrf()))
                .andExpect(status().isPayloadTooLarge())
                .andExpect(jsonPath("$.message", containsString("File too large!")));
    }

    @Test
    void uploadValidationError() throws Exception {
        // arrange
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getAllErrors())
                .thenReturn(List.of(new FieldError("file", "fileName", "should not be empty")));

        // act
        Map<String,String> errors = fileTransferRestController.handleValidationExceptions(
                new MethodArgumentNotValidException(
                        new MethodParameter(FileTransferRestController.class.getMethod("upload", MultipartFile.class),0),
                        bindingResult));

        // assert
        assertTrue(errors.containsKey("fileName"));
        assertEquals("should not be empty", errors.get("fileName"));
    }
}