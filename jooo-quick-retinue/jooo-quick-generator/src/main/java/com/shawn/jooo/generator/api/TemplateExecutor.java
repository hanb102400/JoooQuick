package com.shawn.jooo.generator.api;

import com.shawn.jooo.generator.template.TemplateRender;
import com.shawn.jooo.generator.template.TemplateRenderFactory;
import com.shawn.jooo.generator.config.Configuration;
import com.shawn.jooo.generator.config.ConstantConfig;
import com.shawn.jooo.generator.config.ModuleConfiguration;
import com.shawn.jooo.generator.config.TemplateConfiguration;
import com.shawn.jooo.generator.table.IntrospectedTable;
import com.shawn.jooo.generator.utils.DateUtils;
import com.shawn.jooo.generator.utils.ObjectFactory;
import com.shawn.jooo.generator.utils.PathUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 *
 * @author shawn
 */
public class TemplateExecutor {

    private final String USER_DIR = System.getProperty("user.dir");

    private Configuration configuration;
    IntrospectedTable introspectedTable;

    private File templatePath;
    private File outPath;

    TemplateExecutor(Configuration configuration, IntrospectedTable introspectedTable) {
        this.configuration = configuration;
        this.introspectedTable = introspectedTable;

    }

    public void generate(File templatePath, File outPath) throws IOException {

        this.templatePath=templatePath;
        this.outPath = outPath;
        List<ModuleConfiguration> moduleConfiguration = configuration.getModuleConfigurations();
        for (ModuleConfiguration module : moduleConfiguration) {
            List<TemplateConfiguration> moduleTemplates = getModuleTemplates(module);
            for (TemplateConfiguration template : moduleTemplates) {
                generateFile(module, template, introspectedTable);
            }
        }
    }

    private List<TemplateConfiguration> getModuleTemplates(ModuleConfiguration module) {
        String templates = module.getTemplates();
        List<TemplateConfiguration> templateList = new ArrayList<TemplateConfiguration>();
        List<TemplateConfiguration> templateConfigurations = configuration.getTemplateConfigurations();
        String[] arr = templates.split(",");
        for (String tpl : arr) {
            if ("*".equals(tpl)) {
                templateList.addAll(templateConfigurations);
            } else {
                for (TemplateConfiguration template : templateConfigurations) {
                    if (template.getName().trim().equals(tpl))
                        templateList.add(template);
                }
            }
        }
        return templateList;
    }


    public void generateFile(ModuleConfiguration module, TemplateConfiguration template, IntrospectedTable introspectedTable) {


        Map context = initContext();

        //templateFile
        String templateFile = template.getFileName();

        //outputFile
        List<String> pathList = new ArrayList<String>();
        String moduleDir = module.getModuleDir();
        if (StringUtils.isNotBlank(moduleDir)) {
            pathList.add(moduleDir);
        }

        if (ConstantConfig.TYPE_XML.equalsIgnoreCase(template.getType()) ||
                ConstantConfig.TYPE_XMLMAPPER.equalsIgnoreCase(template.getType())) {
            String resource = module.getResources();
            if (StringUtils.isNotBlank(resource)) {
                pathList.add(resource);
            }
        } else {
            String source = module.getSources();
            if (StringUtils.isNotBlank(source)) {
                pathList.add(source);
            }
        }

        String packageName = template.getPackageName();
        if (StringUtils.isNotBlank(packageName)) {
            packageName = PathUtils.resolveFileName(packageName, context);
            packageName = PathUtils.getPathOfPackage(packageName);
            pathList.add(packageName);
        }

        String outFileName = template.getFileName();
        if (StringUtils.isNotBlank(outFileName)) {
            outFileName = PathUtils.resolveFileName(outFileName, context);
            pathList.add(outFileName);
        }

        String[] arr = new String[pathList.size()];
        String[] pathArr = pathList.toArray(arr);
        String outputFile = Paths.get("", pathArr).toFile().getPath();

        String overrideStr = configuration.getProperties().getProperty("override");
        overrideStr = StringUtils.defaultIfBlank(overrideStr, "false");
        boolean override = Boolean.valueOf(overrideStr);

        String render = configuration.getProperties().getProperty("render");
        render = StringUtils.defaultIfBlank(render, "freemarker");
        TemplateRender templateRender = TemplateRenderFactory.createInstance(render);
        templateRender.config(outPath, templatePath);
        templateRender.render(context, templateFile, outputFile, override);


    }

    private Map initContext() {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("table", introspectedTable);
        context.put("tableName", introspectedTable.getTableName());
        context.put("className", introspectedTable.getClassName());
        context.put("subPackageName", introspectedTable.getSubPackageName());
        context.put("catalog", introspectedTable.getCatalog());
        context.put("schema", introspectedTable.getSchema());
        context.put("entityName", StringUtils.uncapitalize(introspectedTable.getClassName()));
        String author = configuration.getProperties().getProperty("author");
        context.put("author", StringUtils.defaultIfBlank(author, System.getProperty("user.name")));
        context.put("now", DateUtils.now());


        //add @prop
        Properties properties = configuration.getProperties();
        Map prop = new HashMap();
        for (Object keyObj : properties.keySet()) {
            String key = (String) keyObj;
            if (StringUtils.isNotBlank(key)) {
                prop.put(key.trim(), properties.getProperty(key));
            }

        }
        context.put("@prop", prop);

        //add @mod
        List<ModuleConfiguration> moduleConfiguration = configuration.getModuleConfigurations();
        Map mod = new HashMap();
        for (ModuleConfiguration module : moduleConfiguration) {
            if (StringUtils.isNotBlank(module.getName())) {
                mod.put(module.getName().trim(), ObjectFactory.resolveParams(module, context));
            }
        }
        context.put("@mod", mod);

        //add @tpl
        Map tpl = new HashMap();
        List<TemplateConfiguration> templateConfigurations = configuration.getTemplateConfigurations();
        for (TemplateConfiguration template : templateConfigurations) {
            if (StringUtils.isNotBlank(template.getName())) {
                tpl.put(template.getName().trim(), ObjectFactory.resolveParams(template, context));
            }
        }
        context.put("@tpl", tpl);
        return context;
    }
}
