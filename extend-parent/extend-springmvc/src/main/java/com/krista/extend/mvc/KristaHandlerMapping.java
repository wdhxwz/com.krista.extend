package com.krista.extend.mvc;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/12 16:53
 * @Description: 默认将方法名作为URL的一部分
 */
public class KristaHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = null;
        RequestMapping methodAnnotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        if (methodAnnotation != null) {
            RequestCondition<?> methodCondition = getCustomMethodCondition(method);
            info = createRequestMappingInfo(methodAnnotation, methodCondition, method);
            RequestMapping typeAnnotation = AnnotationUtils.findAnnotation(handlerType, RequestMapping.class);
            if (typeAnnotation != null) {
                RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
                info = createRequestMappingInfo(typeAnnotation, typeCondition, null).combine(info);
            }
        }

        return info;
    }

    protected RequestMappingInfo createRequestMappingInfo(RequestMapping annotation, RequestCondition<?> customCondition, Method method) {
        String[] patterns;
        if (method != null && annotation.value().length == 0) {
            patterns = new String[]{createPattern(method.getName())};
        } else {
            patterns = resolveEmbeddedValuesInPatterns(createPatterns(annotation.value()));
        }
        return new RequestMappingInfo(new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), false, this.useTrailingSlashMatch(), this.getFileExtensions()),
                new RequestMethodsRequestCondition(annotation.method()), new ParamsRequestCondition(annotation.params()), new HeadersRequestCondition(annotation.headers()),
                new ConsumesRequestCondition(annotation.consumes(), annotation.headers()), new ProducesRequestCondition(annotation.produces(), annotation.headers(), getContentNegotiationManager()),
                customCondition);
    }

    protected String createPattern(String methodName) {
        return methodName + ".do";
    }

    protected String[] createPatterns(String[] annotationValus) {
        String[] array = new String[annotationValus.length];
        for(int index = 0;index < annotationValus.length;index ++){
            if("/".equals(annotationValus[index])){
                array[index] = annotationValus[index];
            }else{
                array[index] = createPattern(annotationValus[index]);
            }
        }

        return  array;
    }
}