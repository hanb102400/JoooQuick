package com.shawn.jooo.generator.types;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 *
 * @author shawn
 */
@XStreamAlias("convert")
public class Convert {

    private List<Type> types;

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }
}
