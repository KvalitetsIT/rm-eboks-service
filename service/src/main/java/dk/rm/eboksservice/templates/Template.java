package dk.rm.eboksservice.templates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Template {
    private String name, description, templateBody;

    @JsonCreator
    public Template(@JsonProperty(value = "name", required = true) String name,
                    @JsonProperty(value = "description", required = true) String description,
                    @JsonProperty(value = "templateBody", required = true) String templateBody) {
        this.name = name;
        this.description = description;
        this.templateBody = templateBody;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemplateBody() {
        return templateBody;
    }

    public void setTemplateBody(String templateBody) {
        this.templateBody = templateBody;
    }
}
