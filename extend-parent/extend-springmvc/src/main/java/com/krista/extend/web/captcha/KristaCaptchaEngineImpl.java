package com.krista.extend.web.captcha;

import com.jhlabs.image.PinchFilter;
import com.jhlabs.math.ImageFunction2D;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByBufferedImageOp;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.GlyphsPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.GlyphsVisitors;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.OverlapGlyphsUsingShapeVisitor;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.TranslateAllToRandomPointVisitor;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.TranslateGlyphsVerticalRandomVisitor;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ImageCaptchaEngine;
import com.octo.captcha.image.ImageCaptchaFactory;
import com.octo.captcha.image.gimpy.GimpyFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/27 15:37
 * @Description:
 */
public class KristaCaptchaEngineImpl extends ImageCaptchaEngine implements KristaCaptchaEngine {
    private int width;
    private int height;
    private static final String words = "abcdefghijklmnopqrstuvwsyz1234567890";

    @Override
    public void initialFactories() {
        WordGenerator dictionnaryWords = new RandomWordGenerator(words);
        TextPaster randomPaster = new GlyphsPaster(4, 4, new RandomListColorGenerator(
                new Color[]{
                        new Color(23, 170, 27),
                        new Color(220, 34, 11),
                        new Color(23, 67, 172)
                }),

                new GlyphsVisitors[]{
                        new TranslateGlyphsVerticalRandomVisitor(1),

                        new OverlapGlyphsUsingShapeVisitor(3),
                        new TranslateAllToRandomPointVisitor()
                });

        BackgroundGenerator back = new UniColorBackgroundGenerator(width, height, Color.white);
        FontGenerator shearedFont = new RandomFontGenerator(50, 50,
                new Font[]{new Font("nyala", Font.BOLD, 50),
                        new Font("Bell MT", Font.PLAIN, 50),
                        new Font("Credit valley", Font.BOLD, 50)
                }, false);

        PinchFilter pinch = new PinchFilter();

        pinch.setAmount(-.5f);
        pinch.setRadius(30);
        pinch.setAngle((float) (Math.PI / 16));
        pinch.setCentreX(0.5f);
        pinch.setCentreY(-0.01f);
        pinch.setEdgeAction(ImageFunction2D.CLAMP);

        PinchFilter pinch2 = new PinchFilter();
        pinch2.setAmount(-.6f);
        pinch2.setRadius(70);
        pinch2.setAngle((float) (Math.PI / 16));
        pinch2.setCentreX(0.3f);
        pinch2.setCentreY(1.01f);
        pinch2.setEdgeAction(ImageFunction2D.CLAMP);

        PinchFilter pinch3 = new PinchFilter();
        pinch3.setAmount(-.6f);
        pinch3.setRadius(70);
        pinch3.setAngle((float) (Math.PI / 16));
        pinch3.setCentreX(0.8f);
        pinch3.setCentreY(-0.01f);
        pinch3.setEdgeAction(ImageFunction2D.CLAMP);

        List<ImageDeformation> textDef = new ArrayList<ImageDeformation>();
        textDef.add(new ImageDeformationByBufferedImageOp(pinch));
        textDef.add(new ImageDeformationByBufferedImageOp(pinch2));
        textDef.add(new ImageDeformationByBufferedImageOp(pinch3));

        // word2image 1
        WordToImage word2image = new DeformedComposedWordToImage(false, shearedFont, back, randomPaster, new ArrayList<ImageDeformation>(), new ArrayList<ImageDeformation>(), textDef

        );

        this.addFactory(new GimpyFactory(dictionnaryWords, word2image, false));
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    public boolean addFactory(ImageCaptchaFactory factory) {
        return factory != null && this.factories.add(factory);
    }
}
