package com.mylearning.utils;

import com.mylearning.constants.SimulatorConstants;
import com.mylearning.exception.InValidDataException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

public class FileLoaderUtil {

    private FileLoaderUtil(){}

    /**
     * readFileLinesAsStreams
     * @param fileName
     * @return
     * @throws InValidDataException
     */
    public static Stream<String> readFileLinesAsStreams(String fileName) throws InValidDataException {
        fileName = fileExtensionLookUp(fileName);
        URL url = FileLoaderUtil.class.getClassLoader().getResource(fileName);
        Path path = url == null ? Paths.get(fileName) : Paths.get(url.getPath());

        try {
            return Files.lines(path);
        } catch (IOException e) {
            String message = StaticDataLoaderUtil.getConfigValueAsString(SimulatorConstants.ErrorCode_E1007) + fileName;
            throw new InValidDataException(SimulatorConstants.ErrorCode_E1007, message);
        }
    }

    /**
     * Simulator can accept files with or with extensions using fileExtensionLookUp
     * @param fileName
     * @return
     */
    public static String fileExtensionLookUp(String fileName){
        fileName = fileName.matches(SimulatorConstants.FILEEXTENSION_REGEX) ? fileName : fileName + SimulatorConstants.TEXTFILE_EXTENSION;
        return fileName;
    }


    /**
     * Populates the configuration values (at the start of the APP)
     */
    public static void populateProperties(String fileName, Properties prop) throws InValidDataException {
        InputStream input = StaticDataLoaderUtil.class.getClassLoader().getResourceAsStream(fileName);
        // load a properties file
        try {
            prop.load(input);
        } catch (IOException e) {
            String message = StaticDataLoaderUtil.getConfigValueAsString(SimulatorConstants.ErrorCode_E1006) + e.getLocalizedMessage();
            throw new InValidDataException(SimulatorConstants.ErrorCode_E1006, message);
        }

    }
}
