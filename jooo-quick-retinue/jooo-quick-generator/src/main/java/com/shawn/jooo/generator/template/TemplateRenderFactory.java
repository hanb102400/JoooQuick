package com.shawn.jooo.generator.template;

/**
 *
 * @author shawn
 */
public class TemplateRenderFactory {

    public static TemplateRender createInstance(String type){
        if("freemarker".equals(type)){
            return new FreemarkerRender();
        }else{
            throw new RuntimeException("with the type["+type+"] cant be init Instance！");
        }
    }

}
