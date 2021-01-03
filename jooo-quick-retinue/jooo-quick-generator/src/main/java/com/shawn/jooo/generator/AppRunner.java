package com.shawn.jooo.generator;

import com.shawn.jooo.generator.api.ConfigurationParser;
import com.shawn.jooo.generator.api.CodeGenarator;
import com.shawn.jooo.generator.config.Configuration;
import com.shawn.jooo.generator.exception.XMLParserException;
import com.shawn.jooo.generator.utils.ZipUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 *
 * @author shawn
 */
public class AppRunner {

    public static void main(String[] args) {

        try {
            String configFile= "generator.xml";
            InputStream in  = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);
            ConfigurationParser cp = new ConfigurationParser();
            Configuration configuration = cp.parse(in);
            CodeGenarator generator = new CodeGenarator(configuration);
            generator.initPath();
            generator.generate();
            if(configuration.getProperties().getProperty("zip").equals("true")){
                generator.zip();
            }

        } catch (XMLParserException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
