package dk.rm.eboksservice.templates;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.rm.eboksservice.service.EboksServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateReader {
    private static final Logger logger = LoggerFactory.getLogger(TemplateReader.class);

    public static Map<String, Template> readTemplates(String templatesPath) throws EboksServiceException {
        try {
            Template[] templates = new ObjectMapper().readValue(new File(templatesPath), Template[].class);
            return Arrays.stream(templates).collect(Collectors.toMap(Template::getName, t -> t, TemplateReader::failOnDuplicates));
        } catch (IOException | DuplicateException e) {
            String message = "Failed to read templates from file: " + templatesPath;
            logger.error(message, e);
            throw new EboksServiceException(message, e);
        }
    }

    private static Template failOnDuplicates(Template t1, Template t2) {
        throw new DuplicateException("Templates contain duplicate name: " + t1.getName());
    }

    private static class DuplicateException extends RuntimeException {
        public DuplicateException(String message) {
            super(message);
        }
    }
}
