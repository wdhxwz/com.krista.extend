package com.krista.extend.mvc.views;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.Map;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/13 14:57
 * @Description: z自定义的抽象视图
 */
public abstract class AbstractView extends ModelAndView {

    private AbstractUrlBasedView view = new AbstractUrlBasedView() {

        @Override
        protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
            String body = null;
            try {
                body = AbstractView.this.getBody(request, response);
            }
            catch (Exception e) {
                request.getServletContext().log(e.getMessage());
                body = e.getMessage();
            }
            if (body == null) {
                return;
            }
            response.setContentType(AbstractView.this.getContentType());
            response.setContentLength(body.getBytes().length);

            Writer out = response.getWriter();
            out.write(body);
            out.flush();
        }
    };

    public AbstractView(){
        super.setView(view);
    }

    public abstract String getContentType();

    public abstract String getBody(HttpServletRequest request, HttpServletResponse response);
}