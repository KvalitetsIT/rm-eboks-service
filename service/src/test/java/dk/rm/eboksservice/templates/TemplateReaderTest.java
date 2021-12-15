package dk.rm.eboksservice.templates;

import dk.rm.eboksservice.service.EboksServiceException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TemplateReaderTest {

    @Test
    public void testReadTemplatesWithInvalidPath() {
        try {
            TemplateReader.readTemplates("asdf/asdf.json");
            fail("readTemplates should fail");
        } catch (EboksServiceException e) {
            assertEquals("Failed to read templates from file: asdf/asdf.json", e.getMessage());
            assertTrue(e.getCause() instanceof FileNotFoundException);
        }
    }

    @Test
    public void testReadTemplatesWithMissingName() {
        String templatesPath = this.getClass().getResource("/template-missing-name.json").getPath();
        try {
            TemplateReader.readTemplates(templatesPath);
            fail("readTemplates should fail");
        } catch (EboksServiceException e) {
            assertTrue(e.getMessage(), e.getMessage().contains("Failed to read templates from file"));
            assertTrue(e.getMessage(), e.getCause().getMessage().contains("Missing required creator property 'name'"));
        }
    }

    @Test
    public void testReadTemplatesWithMissingDescription() {
        String templatesPath = this.getClass().getResource("/template-missing-description.json").getPath();
        try {
            TemplateReader.readTemplates(templatesPath);
            fail("readTemplates should fail");
        } catch (EboksServiceException e) {
            assertTrue(e.getMessage(), e.getMessage().contains("Failed to read templates from file"));
            assertTrue(e.getMessage(), e.getCause().getMessage().contains("Missing required creator property 'description'"));
        }
    }

    @Test
    public void testReadTemplatesWithMissingBody() {
        String templatesPath = this.getClass().getResource("/template-missing-body.json").getPath();
        try {
            TemplateReader.readTemplates(templatesPath);
            fail("readTemplates should fail");
        } catch (EboksServiceException e) {
            assertTrue(e.getMessage(), e.getMessage().contains("Failed to read templates from file"));
            assertTrue(e.getCause().getMessage(), e.getCause().getMessage().contains("Missing required creator property 'templateBody'"));
        }
    }

    @Test
    public void testReadTemplatesWithDuplicateName() {
        String templatesPath = this.getClass().getResource("/templates-with-duplicates.json").getPath();
        try {
            TemplateReader.readTemplates(templatesPath);
            fail("readTemplates should fail");
        } catch (EboksServiceException e) {
            assertTrue(e.getMessage(), e.getMessage().contains("Failed to read templates from file"));
            assertTrue(e.getCause().getMessage(), e.getCause().getMessage().contains("Templates contain duplicate name: test"));
        }
    }

    @Test
    public void testReadTemplates() throws EboksServiceException {
        String templatesPath = this.getClass().getResource("/templates.json").getPath();
        Map<String, Template> templates = TemplateReader.readTemplates(templatesPath);

        assertEquals(2, templates.size());
        validateTemplate(templates, "test", "test template", "this is a template");
        validateTemplate(templates, "test2", "another test template", "this is another template");
    }

    private void validateTemplate(Map<String, Template> templates, String name, String description, String templateBody) {
        Template template = templates.get(name);
        assertNotNull(template);
        assertEquals(name, template.getName());
        assertEquals(description, template.getDescription());
        assertEquals(templateBody, template.getTemplateBody());
    }
}