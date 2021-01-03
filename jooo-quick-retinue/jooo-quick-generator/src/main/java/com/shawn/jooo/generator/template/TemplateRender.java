package com.shawn.jooo.generator.template;

import java.io.File;
import java.util.Map;

/**
 *
 * @author shawn
 */
public interface TemplateRender {

    void config(File projectDir, File templateDir);
    void render(Map<String, Object> context, String templateFile, String outputFile, boolean override);
}
