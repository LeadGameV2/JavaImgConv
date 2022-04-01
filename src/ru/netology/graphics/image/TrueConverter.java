package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TrueConverter implements TextGraphicsConverter {

    private int maxWidth = 0;
    private int newWidth = 0;
    private int maxHeight = 0;
    private int newHeight = 0;
    private double maxRatio = 0;
    private TextColorSchema schema;


    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        BufferedImage img = ImageIO.read(new URL(url));
        int imgW = img.getWidth();
        int imgH = img.getHeight();

        int currentRatio = (imgH > imgW) ? imgH / imgW : imgW / imgH;
        if (currentRatio > maxRatio) {
            throw new BadImageSizeException(currentRatio, maxRatio);
        }
        if (imgW > imgH && imgW > maxWidth) {
            newWidth = maxWidth;
            newHeight = imgH / (imgW / maxWidth);
        } else if (imgH > imgW && imgH > maxHeight) {
            newHeight = maxHeight;
            newWidth = imgW / (imgH / maxHeight);
        } else if (imgH == imgW) {
            newWidth = imgW;
            newHeight = imgH;
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);

        ImageIO.write(bwImg, "png", new File("BRUH.png"));

        WritableRaster bwRaster = bwImg.getRaster();

        if (schema == null) {
            ColorSchema preset = new ColorSchema();
            schema = preset;
        }

        StringBuilder textPictue = new StringBuilder();

        for (int j = 0; j < newHeight; j++) {
            for (int i = 0; i < newWidth; i++) {
                int color = bwRaster.getPixel(i, j, new int[3])[0];
                char c = schema.convert(color);
                textPictue.append(c);
                textPictue.append(c);
            }
            textPictue.append("\n");
        }
        return textPictue.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}
