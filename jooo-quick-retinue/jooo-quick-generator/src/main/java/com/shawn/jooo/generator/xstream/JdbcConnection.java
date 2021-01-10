package com.shawn.jooo.generator.xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("JdbcConnection")
public class JdbcConnection {

    private String driverClassName;

    private String url;

    private String username;

    private String password;
}
