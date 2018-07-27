package com.krista.extend.mvc.views;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/27 14:57
 * @Description:
 */
public class DownloadView extends ModelAndView {
    public DownloadView(final String filename, final String name) {
        AbstractUrlBasedView view = new AbstractUrlBasedView() {
            @Override
            protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
                String name2 = URLEncoder.encode(name, "UTF-8");
                response.setContentType("application/force-download");
                response.addHeader("Content-Disposition", "attachment;filename=" + name2);

                File file = new File(filename);

                InputStream inputStream = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                byte[] bytes = new byte[1024];
                ServletOutputStream out = response.getOutputStream();
                int readLength = 0;
                while ((readLength = bis.read(bytes)) != -1) {
                    out.write(bytes, 0, readLength);
                }
                inputStream.close();
                bis.close();
                out.flush();
                out.close();
            }
        };
        super.setView(view);
    }

    public DownloadView(final InputStream input, final String name) {
        AbstractUrlBasedView view = new AbstractUrlBasedView() {
            @Override
            protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
                String name2 = URLEncoder.encode(name, "UTF-8");
                response.setContentType("application/force-download");
                response.addHeader("Content-Disposition", "attachment;filename=" + name2);
                byte[] bytes = new byte[1024];
                ServletOutputStream out = response.getOutputStream();
                int readLength = 0;
                while ((readLength = input.read(bytes)) != -1) {
                    out.write(bytes, 0, readLength);
                }

                input.close();
                out.flush();
                out.close();
            }
        };
        super.setView(view);
    }
}
