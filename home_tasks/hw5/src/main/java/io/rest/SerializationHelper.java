package io.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.*;

public class SerializationHelper<T extends Serializable> {

    Class<T> serialazationType;

    public SerializationHelper(Class<T> serialazationType) {
        this.serialazationType = serialazationType;
    }

    private final Logger log = Logger.getLogger(getClass());

    ObjectMapper mapper = new ObjectMapper();


    /*
      Необходимо десереализовать объект из файла по указанному пути
    */
    public T loadFromFile(String path) {
        try {
            return mapper.readValue(new File(path), serialazationType);
        } catch (IOException e) {
            log.error(String.format("Cannot deserialize content from file %s", path));
            return null;
        }
    }

    public boolean saveToFile(String path, T toSave) {
        /* Функция сохранения файла по заданному пути */
        try {
            File file = new File(path);
            if (!file.exists()) {
                boolean fileCreated = file.createNewFile();
                if (!fileCreated) {
                    throw new IOException("Unable to create file at specified path. It already exists");
                }
            }
            mapper.writeValue(file, toSave);
            return true;
        } catch (IOException e) {
            log.error(String.format("Cannot save class %s to file %s", toSave, path));
            return false;
        } catch (SecurityException e) {
            log.error(String.format("Cannot save class %s to file %s instead ", toSave, path));
            return false;
        }
    }

    public String convertToJsonString(T toConvert) {
        try {
            return mapper.writeValueAsString(toConvert);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeJsonToStream(OutputStream output, T toWrite) {
        try {
            mapper.writeValue(output, toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T parseJson(String json) {
        try {
            return mapper.readValue(json, serialazationType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
