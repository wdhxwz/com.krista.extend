package com.krista.extend.mybatis.generator;

import com.krista.extend.utils.TypeUtil;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.util.Date;
import java.util.List;

/**
 * KristaCommentGenerator
 *
 * @author dw_wangdonghong
 * @version V1.0
 * @since 2018/12/21 16:10
 */
public class KristaCommentGenerator extends DefaultCommentGenerator {
    private String currentDateStr = TypeUtil.dateToString(new Date());

    public KristaCommentGenerator() {
        super();
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (StringUtils.isNotEmpty(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
            field.addJavaDocLine(" */");
        }
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (StringUtils.isNotEmpty(introspectedColumn.getRemarks())) {
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * get " + introspectedColumn.getRemarks());
            method.addJavaDocLine(" */");
        }
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (StringUtils.isNotEmpty(introspectedColumn.getRemarks())) {
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * set " + introspectedColumn.getRemarks());
//            Parameter param = method.getParameters().get(0);
//            method.addJavaDocLine(" * @param " + param.getName());
            method.addJavaDocLine(" */");
        }
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        compilationUnit.addFileCommentLine("/** ");
        compilationUnit.addFileCommentLine(" * ");
        compilationUnit.addFileCommentLine(" */ ");
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass,
                                     IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("/**");
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        topLevelClass.addJavaDocLine(" * " + tableName);
        topLevelClass.addJavaDocLine(" * ");
        topLevelClass.addJavaDocLine(" * @author krista");
        topLevelClass.addJavaDocLine(" * @version V1.0");
        topLevelClass.addJavaDocLine(" * @since " + currentDateStr);
        topLevelClass.addJavaDocLine(" */");
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * " + method.getName());
        method.addJavaDocLine(" * ");
        List<Parameter> params = method.getParameters();
        params.forEach(param -> method.addJavaDocLine(" * @param " + param.getName()));
        method.addJavaDocLine(" * @return " + method.getReturnType().getFullyQualifiedName());
        method.addJavaDocLine(" * @author krista");
        method.addJavaDocLine(" * @date " + currentDateStr);
        method.addJavaDocLine(" */");
    }

    @Override
    public void addComment(XmlElement xmlElement) {

    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        super.addClassComment(innerClass, introspectedTable);
    }
}
