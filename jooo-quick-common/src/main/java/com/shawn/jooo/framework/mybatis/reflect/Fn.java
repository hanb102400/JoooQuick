package com.shawn.jooo.framework.mybatis.reflect;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Fn函数
 *
 * @author shawn
 */
public interface Fn<T, R> extends Function<T, R>, Serializable {
}
