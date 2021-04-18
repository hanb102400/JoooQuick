package com.shawn.jooo.framework.mybatis.mapper;

import com.shawn.jooo.framework.mybatis.annotation.LogicDelete;
import com.shawn.jooo.framework.mybatis.reflect.BeanReflections;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Mybatis的sql处理器
 *
 * @author shawn
 */
public abstract class MybatisProvider {

    private static Logger logger = LoggerFactory.getLogger(MybatisProvider.class);


    public static abstract class CacheProvider {

        private static ConcurrentHashMap<String, String> dynamicSQLMap = new ConcurrentHashMap<>();

        public String staticSQL(ProviderContext context) {
            return getCacheSQL(context, () -> dynamicSQL(context));
        }

        public static String getCacheSQL(ProviderContext context, Supplier<String> supplier) {
            String key = context.getMapperType().getName() + "." + context.getMapperMethod().getName();
            if (dynamicSQLMap.containsKey(key)) {
                return dynamicSQLMap.get(key);
            } else {
                String value = supplier.get();
                dynamicSQLMap.put(key, value);
                return value;
            }
        }

        abstract String dynamicSQL(ProviderContext context);
    }


    public static class CountByExampleProvider extends CacheProvider {

        @Override
        public String dynamicSQL(ProviderContext context) {
            Class mapperType = context.getMapperType();
            Class<?> entityType = BeanReflections.getEntityType(mapperType);
            String[] script = {
                    "<script>",
                    "select count(*) from  " + TABLE_NAME_SQL(entityType),
                    EXAMPLE_WHERE_CLAUSE_SQL(),
                    "</script>",
            };
            return String.join("\n", script);

        }
    }


    public static class InsertProvider extends CacheProvider {

        @Override
        public String dynamicSQL(ProviderContext context) {
            Class mapperType = context.getMapperType();
            Class<?> entityType = BeanReflections.getEntityType(mapperType);
            String[] script = {
                    "<script>",
                    "insert into " + TABLE_NAME_SQL(entityType),
                    "(" + COLUMN_NAME_SQL(entityType) + ")",
                    "values",
                    "(" + COLUMN_PARAM_SQL(entityType) + ")",
                    "</script>",
            };
            return String.join("\n", script);
        }
    }

    public static class InsertSelectiveProvider extends CacheProvider {

        @Override
        public String dynamicSQL(ProviderContext context) {

            Class mapperType = context.getMapperType();
            Class<?> entityType = BeanReflections.getEntityType(mapperType);
            String[] script = {
                    "<script>",
                    "insert into " + TABLE_NAME_SQL(entityType),
                    "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
                    COLUMN_NAME_TEST_SQL(entityType),
                    "</trim>",
                    "<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">",
                    COLUMN_PARAM_TEST_SQL(entityType),
                    "</trim>",
                    "</script>",
            };
            return String.join("\n", script);
        }
    }


    public static class DeleteByExampleProvider extends CacheProvider {

        @Override
        public String dynamicSQL(ProviderContext context) {
            Class mapperType = context.getMapperType();
            Class<?> entityType = BeanReflections.getEntityType(mapperType);
            String[] script = {
                    "<script>",
                    PHYSICAL_OR_LOGIC_DELETE_SQL(entityType),
                    EXAMPLE_WHERE_CLAUSE_SQL(),
                    "</script>",
            };
            return String.join("\n", script);
        }
    }

    public static class DeleteByPrimaryKeyProvider extends CacheProvider {

        @Override
        public String dynamicSQL(ProviderContext context) {
            Class mapperType = context.getMapperType();
            Class<?> entityType = BeanReflections.getEntityType(mapperType);
            String[] script = {
                    "<script>",
                    PHYSICAL_OR_LOGIC_DELETE_SQL(entityType),
                    "where " + ID_PARAM_SQL(entityType),
                    "</script>",
            };
            return String.join("\n", script);
        }
    }

    public static class SelectByExampleProvider extends CacheProvider {

        @Override
        public String dynamicSQL(ProviderContext context) {
            Class mapperType = context.getMapperType();
            Class<?> entityType = BeanReflections.getEntityType(mapperType);
            String[] script = {
                    "<script>",
                    "select",
                    "<if test=\"distinct\"> distinct </if>",
                    COLUMN_NAME_SQL(entityType),
                    "from  " + TABLE_NAME_SQL(entityType),
                    EXAMPLE_WHERE_CLAUSE_SQL(),
                    "<if test=\"orderByClause != null\">",
                    "   order by ${orderByClause}",
                    "</if>",
                    "</script>",
            };
            return String.join("\n", script);
        }
    }

    public static class selectPageByExampleProvider extends CacheProvider {

        @Override
        public String dynamicSQL(ProviderContext context) {
            Class mapperType = context.getMapperType();
            Class<?> entityType = BeanReflections.getEntityType(mapperType);
            String[] script = {
                    "<script>",
                    "select",
                    "<if test=\"example.distinct\"> distinct </if>",
                    COLUMN_NAME_SQL(entityType),
                    "from  " + TABLE_NAME_SQL(entityType),
                    UPDATE_EXAMPLE_WHERE_CLAUSE_SQL(),
                    "<if test=\"example.orderByClause != null\">",
                    "   order by ${example.orderByClause}",
                    "</if>",
                    "</script>",
            };
            return String.join("\n", script);

        }
    }

    public static class SelectByPrimaryKeyProvider extends CacheProvider {

        @Override
        public String dynamicSQL(ProviderContext context) {
            Class mapperType = context.getMapperType();
            Class<?> entityType = BeanReflections.getEntityType(mapperType);

            String[] script = {
                    "<script>",
                    "select",
                    COLUMN_NAME_SQL(entityType),
                    "from  " + TABLE_NAME_SQL(entityType),
                    "where " + ID_PARAM_SQL(entityType),
                    "</script>",
            };
            return String.join("\n", script);
        }
    }

    public static class UpdateByExampleSelectiveProvider extends CacheProvider {

        @Override
        public String dynamicSQL(ProviderContext context) {
            Class mapperType = context.getMapperType();
            Class<?> entityType = BeanReflections.getEntityType(mapperType);
            String[] script = {
                    "<script>",
                    "update " + TABLE_NAME_SQL(entityType),
                    "<set>",
                    UPDATE_COLUMN_PARAM_TEST_SQL(entityType),
                    "</set>",
                    UPDATE_EXAMPLE_WHERE_CLAUSE_SQL(),
                    "</script>",
            };
            return String.join("\n", script);
        }
    }


    public static class UpdateByExampleProvider extends CacheProvider {

        @Override
        public String dynamicSQL(ProviderContext context) {
            Class mapperType = context.getMapperType();
            Class<?> entityType = BeanReflections.getEntityType(mapperType);

            String[] script = {
                    "<script>",
                    "update " + TABLE_NAME_SQL(entityType),
                    "set " + UPDATE_COLUMN_PARAM_SQL(entityType),
                    UPDATE_EXAMPLE_WHERE_CLAUSE_SQL(),
                    "</script>",
            };
            return String.join("\n", script);
        }
    }

    public static class UpdateByPrimaryKeySelectiveProvider extends CacheProvider {

        @Override
        public String dynamicSQL(ProviderContext context) {
            Class mapperType = context.getMapperType();
            Class<?> entityType = BeanReflections.getEntityType(mapperType);

            String[] script = {
                    "<script>",
                    "update " + TABLE_NAME_SQL(entityType),
                    "<set>",
                    UPDATE_COLUMN_PK_TEST_SQL(entityType),
                    "</set>",
                    "where " + ID_PARAM_SQL(entityType),
                    "</script>",
            };
            return String.join("\n", script);
        }
    }

    public static class UpdateByPrimaryKeyProvider extends CacheProvider {

        @Override
        public String dynamicSQL(ProviderContext context) {
            Class mapperType = context.getMapperType();
            Class<?> entityType = BeanReflections.getEntityType(mapperType);

            String[] script = {
                    "<script>",
                    "update " + TABLE_NAME_SQL(entityType),
                    "set " + UPDATE_COLUMN_PK_SQL(entityType),
                    "where " + ID_PARAM_SQL(entityType),
                    "</script>",
            };
            return String.join("\n", script);
        }
    }

    public static class InsertInBatchProvider extends CacheProvider {

        @Override
        public String dynamicSQL(ProviderContext context) {
            Class mapperType = context.getMapperType();
            Class<?> entityType = BeanReflections.getEntityType(mapperType);

            String[] script = {
                    "<script>",
                    "insert into " + TABLE_NAME_SQL(entityType),
                    "(" + COLUMN_NAME_SQL(entityType) + ")",
                    "values",
                    "<foreach collection=\"list\" item=\"item\" separator=\",\">",
                    "(" + COLUMN_ITEM_SQL(entityType) + ")",
                    "</foreach>",
                    "</script>",
            };
            return String.join("\n", script);
        }
    }

    /**
     * 返回所有表名，例如: tbl_user
     *
     * @param entityType
     * @return
     */
    private static String TABLE_NAME_SQL(Class<?> entityType) {
        return BeanReflections.getTableName(entityType);
    }


    /**
     * 返回所有ID类型，例如: java.lang.Integer
     *
     * @param entityType
     * @return
     */
    private static String ID_TYPE_SQL(Class<?> entityType) {
        List<String> columns = new ArrayList();
        List<Field> fields = BeanReflections.getFields(entityType);
        for (Field field : fields) {
            if (BeanReflections.isId(field)) {
                String fieldType = BeanReflections.getFieldTypeName(field);
                columns.add(fieldType);
            }
        }
        return String.join(",", columns);

    }

    /**
     * 返回ID条件，例如 id = #{ id }
     *
     * @param entityType
     * @return
     */
    private static String ID_PARAM_SQL(Class<?> entityType) {
        List<String> idAssigns = new ArrayList<>(32);
        List<Field> fields = BeanReflections.getFields(entityType);
        for (Field field : fields) {
            if (BeanReflections.isId(field)) {

                String columnName = BeanReflections.getColumnName(field);
                String columnType = BeanReflections.getColumnTypeName(field);
                String fieldName = BeanReflections.getFieldName(field);

                if (columnName != null) {
                    idAssigns.add(columnName + " = " + "#{" + fieldName + ",jdbcType=" + columnType + "}");
                }
            }

        }
        return String.join(",", idAssigns);
    }


    /**
     * 返回所有列名，例如: id,name
     *
     * @param entityType
     * @return
     */
    private static String COLUMN_NAME_SQL(Class<?> entityType) {
        List<String> columns = new ArrayList<>(32);
        List<Field> fields = BeanReflections.getFields(entityType);
        for (Field field : fields) {
            if (BeanReflections.isColumn(field)) {
                String columnName = BeanReflections.getColumnName(field);
                if (columnName != null) {
                    columns.add(columnName);
                }
            }
        }
        return String.join(",", columns);

    }

    /**
     * 返回所有列参数，例如: #{ id,jdbcType=INTEGER }，#{ name,jdbcType=VARCHAR }
     *
     * @param entityType
     * @return
     */
    private static String COLUMN_PARAM_SQL(Class<?> entityType) {
        List<String> columns = new ArrayList<>(32);
        List<Field> fields = BeanReflections.getFields(entityType);
        for (Field field : fields) {
            if (BeanReflections.isColumn(field)) {
                String columnName = BeanReflections.getColumnName(field);
                String columnType = BeanReflections.getColumnTypeName(field);
                String fieldName = BeanReflections.getFieldName(field);

                if (columnName != null) {
                    columns.add("#{" + fieldName + ",jdbcType=" + columnType + "}");
                }
            }
        }
        return String.join(",", columns);
    }


    /**
     * 返回所有列参数，例如: #{ item.id,jdbcType=INTEGER }，#{ item.name,jdbcType=VARCHAR }
     *
     * @param entityType
     * @return
     */
    private static String COLUMN_ITEM_SQL(Class<?> entityType) {
        List<String> columns = new ArrayList<>(32);
        List<Field> fields = BeanReflections.getFields(entityType);
        for (Field field : fields) {
            if (BeanReflections.isColumn(field)) {
                String columnName = BeanReflections.getColumnName(field);
                String columnType = BeanReflections.getColumnTypeName(field);
                String fieldName = BeanReflections.getFieldName(field);

                if (columnName != null) {
                    columns.add("#{ item." + fieldName + ",jdbcType=" + columnType + "}");
                }
            }
        }
        return String.join(",", columns);
    }

    /**
     * 返回所有列名，判空，例如:
     * <if test=" id != null"> id, </if> <if test=" username != null"> username, </if>
     *
     * @param entityType
     * @return
     */
    private static String COLUMN_NAME_TEST_SQL(Class<?> entityType) {
        List<String> columns = new ArrayList<>(32);
        List<Field> fields = BeanReflections.getFields(entityType);
        for (Field field : fields) {
            if (BeanReflections.isColumn(field)) {
                String fieldName = BeanReflections.getFieldName(field);
                String columnName = BeanReflections.getColumnName(field);
                if (columnName != null) {
                    columns.add("<if test=\"" + fieldName + " != null\">\n" +
                            "   " + columnName + ",\n" +
                            "</if>");
                }
            }
        }
        return String.join("\n", columns);

    }


    /**
     * 返回所有列参数，判空，例如:
     * <if test=" id != null"> id, </if> <if test=" username != null"> username, </if>
     *
     * @param entityType
     * @return
     */
    private static String COLUMN_PARAM_TEST_SQL(Class<?> entityType) {
        List<String> columns = new ArrayList<>(32);
        List<Field> fields = BeanReflections.getFields(entityType);
        for (Field field : fields) {
            if (BeanReflections.isColumn(field)) {
                String columnName = BeanReflections.getColumnName(field);
                String columnType = BeanReflections.getColumnTypeName(field);
                String fieldName = BeanReflections.getFieldName(field);

                if (columnName != null) {
                    columns.add("<if test=\"" + fieldName + " != null\">\n" +
                            "        #{" + fieldName + ",jdbcType=" + columnType + "},\n" +
                            "</if>");
                }
            }
        }
        return String.join("\n", columns);
    }

    /**
     * 返回所有非主键列条件，判空，例如:
     * </if> <if test="username != null"> username, </if>
     *
     * @param entityType
     * @return
     */
    private static String UPDATE_COLUMN_PARAM_SQL(Class<?> entityType) {
        List<String> columns = new ArrayList<>(32);
        List<Field> fields = BeanReflections.getFields(entityType);
        for (Field field : fields) {
            if (BeanReflections.isColumn(field)) {
                String columnName = BeanReflections.getColumnName(field);
                String columnType = BeanReflections.getColumnTypeName(field);
                String fieldName = BeanReflections.getFieldName(field);

                String record = "record.";
                if (columnName != null) {
                    columns.add(columnName + " = #{ " + record + fieldName + ",jdbcType=" + columnType + "}");
                }
            }
        }
        return String.join(",\n", columns);
    }


    /**
     * 返回所有非主键列条件，判空，例如:
     * </if> <if test="record.username != null"> username, </if>
     *
     * @param entityType
     * @return
     */
    private static String UPDATE_COLUMN_PK_SQL(Class<?> entityType) {
        List<String> columns = new ArrayList<>(32);
        List<Field> fields = BeanReflections.getFields(entityType);
        for (Field field : fields) {
            if (BeanReflections.isColumn(field)) {
                if (BeanReflections.isId(field)) {
                    continue;
                }
                String columnName = BeanReflections.getColumnName(field);
                String columnType = BeanReflections.getColumnTypeName(field);
                String fieldName = BeanReflections.getFieldName(field);

                if (columnName != null) {
                    columns.add(columnName + " = #{ " + fieldName + ",jdbcType=" + columnType + "}");
                }
            }


        }
        return String.join(",\n", columns);
    }


    private static String UPDATE_COLUMN_PARAM_TEST_SQL(Class<?> entityType) {
        List<String> columns = new ArrayList<>(32);
        List<Field> fields = BeanReflections.getFields(entityType);
        for (Field field : fields) {
            if (BeanReflections.isColumn(field)) {

                String columnName = BeanReflections.getColumnName(field);
                String columnType = BeanReflections.getColumnTypeName(field);
                String fieldName = BeanReflections.getFieldName(field);

                String record = "record.";
                if (columnName != null) {
                    columns.add("<if test=\"" + record + fieldName + " != null\">\n" +
                            "        " + columnName + "= #{ " + record + fieldName + ",jdbcType=" + columnType + "},\n" +
                            "</if>");
                }
            }

        }
        return String.join("\n", columns);
    }

    private static String UPDATE_COLUMN_PK_TEST_SQL(Class<?> entityType) {
        List<String> columns = new ArrayList<>(32);
        List<Field> fields = BeanReflections.getFields(entityType);
        for (Field field : fields) {
            if (BeanReflections.isColumn(field)) {
                if (BeanReflections.isId(field)) {
                    continue;
                }
                String columnName = BeanReflections.getColumnName(field);
                String columnType = BeanReflections.getColumnTypeName(field);
                String fieldName = BeanReflections.getFieldName(field);

                if (columnName != null) {
                    columns.add(
                            "<if test=\"" + fieldName + " != null\">\n" +
                                    "        " + columnName + "= #{ " + fieldName + ",jdbcType=" + columnType + "},\n" +
                                    "</if>");
                }
            }

        }
        return String.join("\n", columns);
    }

    private static String PHYSICAL_OR_LOGIC_DELETE_SQL(Class<?> entityType) {

        List<Field> logicFieldList = BeanReflections.getFieldsWithAnnotation(entityType, LogicDelete.class);
        if (logicFieldList.size() > 1) {
            throw new IllegalArgumentException(entityType.getName() + " logicDelete annotation muast unique");
        } else if (logicFieldList.size() == 1) {
            Field logicField = logicFieldList.get(0);
            LogicDelete logicDelete = logicField.getAnnotation(LogicDelete.class);
            String columnName = BeanReflections.getColumnName(logicField);
            int logicDeleteFlag = logicDelete.value();
            return "update " + TABLE_NAME_SQL(entityType) + " set " + columnName + " = " + logicDeleteFlag;
        }
        return "delete from " + TABLE_NAME_SQL(entityType);
    }


    /**
     * 返回where example 条件
     *
     * @return
     */
    private static String EXAMPLE_WHERE_CLAUSE_SQL() {
        String[] script = {
                "<if test=\"_parameter != null\">",
                "   <where>",
                "       <foreach collection=\"oredCriteria\" item=\"criteria\" separator=\"or\">",
                "           <if test=\"criteria.valid\">",
                "               <trim prefix=\"(\" prefixOverrides=\"and\" suffix=\")\">",
                "                   <foreach collection=\"criteria.criteria\" item=\"criterion\">",
                "                       <choose>",
                "                           <when test=\"criterion.noValue\">",
                "                               and ${criterion.condition}",
                "                           </when>",
                "                           <when test=\"criterion.singleValue\">",
                "                               and ${criterion.condition} #{criterion.value}",
                "                           </when>",
                "                           <when test=\"criterion.betweenValue\">",
                "                               and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}",
                "                           </when>",
                "                           <when test=\"criterion.listValue\">",
                "                               and ${criterion.condition}",
                "                               <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">",
                "                                   #{listItem}",
                "                               </foreach>",
                "                           </when>",
                "                       </choose>",
                "                   </foreach>",
                "               </trim>",
                "           </if>",
                "       </foreach>",
                "   </where>",
                "</if>",
        };
        return String.join("\n", script);
    }

    private static String UPDATE_EXAMPLE_WHERE_CLAUSE_SQL() {
        String[] script = {
                "<if test=\"_parameter != null\">",
                "   <where>",
                "       <foreach collection=\"example.oredCriteria\" item=\"criteria\" separator=\"or\">",
                "           <if test=\"criteria.valid\">",
                "               <trim prefix=\"(\" prefixOverrides=\"and\" suffix=\")\">",
                "                   <foreach collection=\"criteria.criteria\" item=\"criterion\">",
                "                       <choose>",
                "                           <when test=\"criterion.noValue\">",
                "                               and ${criterion.condition}",
                "                           </when>",
                "                           <when test=\"criterion.singleValue\">",
                "                               and ${criterion.condition} #{criterion.value}",
                "                           </when>",
                "                           <when test=\"criterion.betweenValue\">",
                "                               and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}",
                "                           </when>",
                "                           <when test=\"criterion.listValue\">",
                "                               and ${criterion.condition}",
                "                               <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">",
                "                                   #{listItem}",
                "                               </foreach>",
                "                           </when>",
                "                       </choose>",
                "                   </foreach>",
                "               </trim>",
                "           </if>",
                "       </foreach>",
                "   </where>",
                "</if>",
        };
        return String.join("\n", script);
    }
}
