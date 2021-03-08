package world.ucode.pixelizator.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilenamesHelperTest {

    @Test
    void addPrefixToFileName_1() {
        // Given
        var fileName = "asdf.ddd";

        // When
        var result = FilenamesHelper.addSuffixToFileName(fileName, "_pixelized");

        // Then
        assertEquals("asdf_pixelized.ddd", result);
    }

    @Test
    void addPrefixToFileName_2() {
        // Given
        var fileName = ".ddd";

        // When
        var result = FilenamesHelper.addSuffixToFileName(fileName, "_pixelized");

        // Then
        assertEquals("_pixelized.ddd", result);
    }

    @Test
    void addPrefixToFileName_3() {
        // Given
        var fileName = "asdf.";

        // When
        var result = FilenamesHelper.addSuffixToFileName(fileName, "_pixelized");

        // Then
        assertEquals("asdf_pixelized.", result);
    }

    @Test
    void addPrefixToFileName_4() {
        // Given
        var fileName = ".";

        // When
        var result = FilenamesHelper.addSuffixToFileName(fileName, "_pixelized");

        // Then
        assertEquals("_pixelized.", result);
    }

    @Test
    void addPrefixToFileName_5() {
        // Given
        var fileName = "";

        // When
        var result = FilenamesHelper.addSuffixToFileName(fileName, "_pixelized");

        // Then
        assertEquals("_pixelized", result);
    }

    @Test
    void addPrefixToFileName_6() {
        // Given
        var fileName = "zxcv";

        // When
        var result = FilenamesHelper.addSuffixToFileName(fileName, "_pixelized");

        // Then
        assertEquals("zxcv_pixelized", result);
    }

    @Test
    void addPrefixToFileName_7() {
        // Given
        var fileName = "asdf.wqer.png";

        // When
        var result = FilenamesHelper.addSuffixToFileName(fileName, "_pixelized");

        // Then
        assertEquals("asdf.wqer_pixelized.png", result);
    }

    @Test
    void addPrefixToFileName_8() {
        // Given
        var fileName = ".asdf.wqer.png";

        // When
        var result = FilenamesHelper.addSuffixToFileName(fileName, "_pixelized");

        // Then
        assertEquals(".asdf.wqer_pixelized.png", result);
    }

    @Test
    void addPrefixToFileName_9() {
        // Given
        var fileName = ".asdf.wqer.png.";

        // When
        var result = FilenamesHelper.addSuffixToFileName(fileName, "_pixelized");

        // Then
        assertEquals(".asdf.wqer.png_pixelized.", result);
    }

    // CDcQrE4UsAIyhGB.jpg
    // kiss-sexy-couple-hot-boy-and-girl-Favim.com-623834.jpg
}