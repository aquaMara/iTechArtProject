package org.aquam.learnrest.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
class ImageUploaderImplTest {

    @Mock
    private MultipartFile multipartFile;

    @Test
    @DisplayName("uploadImage")
    void uploadImageShouldThrowIOException() throws IOException {
        StringBuilder filename = new StringBuilder();
        given(multipartFile.getOriginalFilename()).willReturn("fg");
        filename.append(multipartFile.getOriginalFilename());
        Path filenameAndPath = Paths.get("uploadDirectory", multipartFile.getOriginalFilename());
        given(multipartFile.getBytes()).willThrow(IOException.class);
        assertThrows(IOException.class,
                () -> Files.write(filenameAndPath, multipartFile.getBytes()));

    }
    /*
    PowerMockito.mockStatic(Calendar.class);
    Calendar calendar = mock(Calendar.class);
    when(calendar.get(eq(Calendar.HOUR_OF_DAY))).thenReturn(3);
     */
/*
try (MockedStatic<WelcomeUtil> theMock = Mockito.mockStatic(WelcomeUtil.class)) {
            theMock.when(() -> WelcomeUtil.generateWelcome("John"))
                   .thenReturn("Guten Tag John");

            assertEquals("Guten Tag John", WelcomeUtil.generateWelcome("John"));
        }

        assertEquals("Welcome John", WelcomeUtil.generateWelcome("John"));
 */
    @Test
    @DisplayName("uploadImage")
    void uploadImageShouldReturnFilename() throws IOException {
        StringBuilder filename = new StringBuilder();
        given(multipartFile.getOriginalFilename()).willReturn("original_filename");
        filename.append(multipartFile.getOriginalFilename());
        Path filenameAndPath = Paths.get("uploadDirectory", multipartFile.getOriginalFilename());
        //byte[] arrayOfBytes = new byte[20];
        given(multipartFile.getBytes()).willReturn(new byte[20]);
        Path finalFilenameAndPath = filenameAndPath;
        try(MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.write(finalFilenameAndPath, multipartFile.getBytes())).thenReturn(finalFilenameAndPath);
            assertEquals(finalFilenameAndPath, Files.write(finalFilenameAndPath, multipartFile.getBytes()));
        }
        //assertEquals(finalFilenameAndPath, Files.write(finalFilenameAndPath, multipartFile.getBytes()));

    }

    @Test
    @DisplayName("uploadImage")
    void uploadImageShouldReturnFilename2() throws IOException {
        StringBuilder filename = new StringBuilder();
        given(multipartFile.getOriginalFilename()).willReturn("original_filename");
        filename.append(multipartFile.getOriginalFilename());
        Path filenameAndPath = Paths.get("uploadDirectory", multipartFile.getOriginalFilename());
        //byte[] arrayOfBytes = new byte[20];
        given(multipartFile.getBytes()).willReturn(new byte[20]);
        Path finalFilenameAndPath = filenameAndPath;
        try(MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.write(Path.of(""), multipartFile.getBytes()))
                    .thenReturn(filename);
            assertThrows(NoSuchFileException.class,
                    () -> Files.write(finalFilenameAndPath, multipartFile.getBytes()));
            // assertEquals(finalFilenameAndPath, Files.write(finalFilenameAndPath, multipartFile.getBytes()));
        }


    }
}