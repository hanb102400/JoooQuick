package com.shawn.jooo.generator.xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

import java.util.List;
import java.util.Properties;

@Data
@XStreamAlias("Configuration")
public class Configuration {

    private Properties properties;

    private Properties out;

    @XStreamAsAttribute
    private JdbcConnection jdbcConnection;

    @XStreamAsAttribute
    private List<Template> templates;
}
