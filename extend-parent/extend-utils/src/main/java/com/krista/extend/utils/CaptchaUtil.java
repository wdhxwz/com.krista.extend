package com.krista.extend.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * CaptchaUtil 验证码工具类
 *
 * @author dw_wangdonghong
 * @version V1.0
 * @since 2018/12/20 19:49
 */
public class CaptchaUtil {
    public CaptchaUtil(int width, int height, int lineNum, int strNum) {
        this.width = width;
        this.height = height;
        this.lineNum = lineNum;
        this.strNum = strNum;
    }

    public CaptchaUtil() {

    }

    /**
     * 随机产生的字符串
     */
    private static final String RANDOM_STRS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String FONT_NAME = "Fixedsys";
    private static final int FONT_SIZE = 18;
    private Random random = new Random();

    /**
     * 图片宽
     */
    private int width = 80;
    /**
     * 图片高
     */
    private int height = 33;
    /**
     * 干扰线数量
     */
    private int lineNum = 50;
    /**
     * 随机产生字符数量
     */
    private int strNum = 4;
    /**
     * x轴坐标
     */
    private int x = 13;
    /**
     * y轴坐标
     */
    private int y = 20;

    public CaptchaUtil setX(int x) {
        this.x = x;
        return this;
    }

    public CaptchaUtil setY(int y) {
        this.y = y;

        return this;
    }

    /**
     * 生成随机图片
     */
    public BufferedImage genRandomCodeImage(StringBuffer randomCode) {
        // BufferedImage类是具有缓冲区的Image类
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        // 获取Graphics对象,便于对图像进行各种绘制操作
        Graphics g = image.getGraphics();
        // 设置背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        // 设置干扰线的颜色
        g.setColor(getRandColor(110, 120));

        // 绘制干扰线
        for (int i = 0; i <= lineNum; i++) {
            drawLine(g);
        }
        // 绘制随机字符
        g.setFont(new Font(FONT_NAME, Font.ROMAN_BASELINE, FONT_SIZE));
        for (int i = 1; i <= strNum; i++) {
            randomCode.append(drawString(g, i));
        }
        g.dispose();

        return image;
    }

    /**
     * 给定范围获得随机颜色
     */
    private Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 绘制字符串
     */
    private String drawString(Graphics g, int i) {
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                .nextInt(121)));
        String rand = getRandomString(random.nextInt(RANDOM_STRS
                .length()));
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, x * i, y);

        return rand;
    }

    /**
     * 绘制干扰线
     */
    private void drawLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int x0 = random.nextInt(16);
        int y0 = random.nextInt(16);
        g.drawLine(x, y, x + x0, y + y0);
    }

    /**
     * 获取随机的字符
     */
    private String getRandomString(int num) {
        return String.valueOf(RANDOM_STRS.charAt(num));
    }

    public static void main(String[] args) {
        CaptchaUtil tool = new CaptchaUtil();
        StringBuffer code = new StringBuffer();
        BufferedImage image = tool.genRandomCodeImage(code);
        System.out.println("random code = " + code);
        try {
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "JPEG", new FileOutputStream(new File(
                    "C:\\Users\\Administrator\\Desktop\\random-code.jpg")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
