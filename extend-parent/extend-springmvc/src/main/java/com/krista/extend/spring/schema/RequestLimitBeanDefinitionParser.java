package com.krista.extend.spring.schema;

import com.krista.extend.mvc.limit.AbstractLimitService;
import com.krista.extend.mvc.limit.LimitConfig;
import com.krista.extend.utils.TypeUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/8/7 09:51
 * @Description: 访问限制配置解析器
 */
public class RequestLimitBeanDefinitionParser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        NodeList nodeList = element.getElementsByTagName("limit");
        if(nodeList == null || nodeList.getLength() == 0){
            return null;
        }

        int nodeSize = nodeList.getLength();
        for(int index = 0 ;index < nodeSize ; index ++){
            Node node = nodeList.item(index);
            List<LimitConfig> limitConfigs = parseLimitConfig((Element)node);
            AbstractLimitService.setLimitConfig(limitConfigs);
        }

        return null;
    }

    protected List<LimitConfig> parseLimitConfig(Element element){
        List<LimitConfig> list = new ArrayList<>();

        NodeList nodeList = element.getElementsByTagName("url");
        if(nodeList == null || nodeList.getLength() == 0){
            return list;
        }

        Integer frequency = TypeUtil.obj2Int(element.getAttribute("frequency"));
        Integer minutes = TypeUtil.obj2Int(element.getAttribute("minutes"));
        for(int index = 0;index < nodeList.getLength();index ++){
            LimitConfig limitConfig = new LimitConfig();
            limitConfig.setFrequency(frequency);
            limitConfig.setMinutes(minutes);
            limitConfig.setUrl(nodeList.item(index).getFirstChild().getNodeValue());

            list.add(limitConfig);
        }

        return list;
    }
}