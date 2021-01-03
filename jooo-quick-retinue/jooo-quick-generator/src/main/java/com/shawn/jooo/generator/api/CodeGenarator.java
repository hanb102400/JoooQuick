package com.shawn.jooo.generator.api;

import com.shawn.jooo.generator.config.Configuration;
import com.shawn.jooo.generator.config.JdbcConnectionConfiguration;
import com.shawn.jooo.generator.config.TableConfiguration;
import com.shawn.jooo.generator.config.TemplateConfiguration;
import com.shawn.jooo.generator.table.DatabaseIntrospector;
import com.shawn.jooo.generator.table.IntrospectedTable;
import com.shawn.jooo.generator.table.JdbcConnectionFactory;
import com.shawn.jooo.generator.utils.MsgFmt;
import com.shawn.jooo.generator.utils.PathUtils;
import com.shawn.jooo.generator.utils.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shawn
 */
public class CodeGenarator {

    private Configuration configuration;
    private JdbcConnectionConfiguration jdbcConnectionConfiguration;
    private JdbcConnectionFactory connectionFactory;
    private List<String> warnings;

    private String classesPath;
    File templatePath;
    File outPath;

    public CodeGenarator(Configuration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("configuration cant be null");
        } else {
            this.configuration = configuration;
            jdbcConnectionConfiguration = configuration.getJdbcConnectionConfiguration();
            if (jdbcConnectionConfiguration == null) {
                throw new IllegalArgumentException("jdbcConnectionConfiguration cant be null");
            }
            System.out.println(MsgFmt.getString("[start] 开始连接数据库：{0}", jdbcConnectionConfiguration.getUrl()));
            this.connectionFactory = new JdbcConnectionFactory(this.jdbcConnectionConfiguration);
            if (warnings == null) {
                this.warnings = new ArrayList();
            }
        }
    }

    public void initPath(){

        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        try {
            classesPath = new URI(path).getPath();
            classesPath = new File(classesPath).getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String targetProject = configuration.getProperties().getProperty("targetProject");
        if (PathUtils.isAbsolutePath(targetProject)) {
            outPath = Paths.get(targetProject).toFile();
        } else {
            outPath = Paths.get(classesPath, "../../", targetProject).toFile();
        }
        if (!outPath.exists()) {
            outPath.mkdirs();
            System.err.print(MsgFmt.getString("\t\t[error!] 当前配置的工程目录不存在:{0}\n", outPath.getAbsolutePath()));
        }


        /**
         *  templateFile
         */
        String templateDirName = configuration.getProperties().getProperty("templateDir");
        templatePath = Paths.get(classesPath, templateDirName).toFile();

    }

    public void generate() throws SQLException, IOException {
        List<TableConfiguration> tableConfigurations = configuration.getTableConfigurations();
        List<IntrospectedTable> introspectedTables = new ArrayList<>();
        for (TableConfiguration tableConfiguration : tableConfigurations) {
            Connection connection = connectionFactory.getConnection();
            DatabaseIntrospector databaseIntrospector = new DatabaseIntrospector(connection, tableConfiguration);
            IntrospectedTable table = databaseIntrospector.introspectTable();
            if (table != null && table.getClassName()!= null) {
                introspectedTables.add(table);
            }
            connectionFactory.closeConnection(connection);
        }
        for (IntrospectedTable table : introspectedTables) {
            TemplateExecutor templateExecutor = new TemplateExecutor(configuration, table);
            templateExecutor.generate(templatePath,outPath);
        }

        System.out.println(MsgFmt.getString("[success] 文件生成完成！！"));
    }

    public void zip() throws IOException {
        ZipUtils.zipCompress(outPath.getPath() , outPath.getName() + "." + "zip");
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
}
