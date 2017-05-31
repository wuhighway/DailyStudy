package com.highway.study.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * @author JH
 * @date 2017/5/31
 */


public class QRCodeHelper {
    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xffffffff;
    private static final int DEFAULT_QRCODE_WIDTH = 500;
    private static final String CHARACTER_SET = "UTF-8";

    /**
     * 生成二维码(二维码默认大小为500*500，颜色为黑色)
     *
     * @param content 二维码内容
     * @see #createQRCode(String, int)
     */
    public static Bitmap createQRCode(String content) {
        return createQRCode(content, BLACK);
    }


    /**
     * 生成二维码(二维码默认颜色为黑色)
     *
     * @param content 二维码内容
     * @param color   二维码颜色
     * @see #createQRCode(String, int, int)
     */
    public static Bitmap createQRCode(String content, int color) {
        return createQRCode(content, DEFAULT_QRCODE_WIDTH, WHITE, color);
    }

    /**
     * 生成二维码(二维码默认颜色为黑色)
     *
     * @param content 二维码内容
     * @param bgColor  二维码背景颜色
     * @param color   二维码颜色
     * @see #createQRCode(String, int, int)
     */
    public static Bitmap createQRCode(String content, int bgColor, int color) {
        return createQRCode(content, DEFAULT_QRCODE_WIDTH, bgColor, color);
    }

    /**
     * 生成二维码
     *
     * @param content 二维码内容
     * @param size    二维码内容大小
     * @param color   二维码颜色
     */
    public static Bitmap createQRCode(String content, int size,int bgColor, int color) {
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, CHARACTER_SET);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);
            int[] pixels = new int[size * size];
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * size + x] = color;
                    } else {
                        pixels[y * size + x] = bgColor;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);
            return bitmap;
        } catch (WriterException e) {
            return null;
        }
    }

    /**
     * 生成带Logo的二维码(二维码默认大小为500*500，颜色为黑色)
     *
     * @param content 二维码内容
     * @param bitmap  二维码Logo
     * @see #createQRCodeWithLogo(String, int, Bitmap)
     */
    public static Bitmap createQRCodeWithLogo(String content, Bitmap bitmap) {
        return createQRCodeWithLogo(content, DEFAULT_QRCODE_WIDTH, bitmap);
    }

    /**
     * 生成带Logo的二维码(二维码默认颜色为黑色)
     *
     * @param content 二维码内容
     * @param size    二维码内容大小
     * @param bitmap  二维码Logo
     * @see #createQRCodeWithLogo(String, int, Bitmap, int)
     */
    public static Bitmap createQRCodeWithLogo(String content, int size, Bitmap bitmap) {
        return createQRCodeWithLogo(content, size, bitmap, BLACK);
    }

    /**
     * 生成带Logo的二维码(二维码默认颜色为黑色)
     *
     * @param content 二维码内容
     * @param bitmap  二维码Logo
     * @param color   二维码颜色
     * @see #createQRCodeWithLogo(String, int, Bitmap, int)
     */
    public static Bitmap createQRCodeWithLogo(String content, Bitmap bitmap, int color) {
        return createQRCodeWithLogo(content, DEFAULT_QRCODE_WIDTH, bitmap, color);
    }

    /**
     * 生成带Logo的二维码
     *
     * @param content 二维码内容
     * @param size    二维码内容大小
     * @param bitmap  二维码Logo
     * @param color   二维码颜色
     */
    public static Bitmap createQRCodeWithLogo(String content, int size, Bitmap bitmap, int color) {
        try {
            int logoHalfWidth = size / 10;
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, CHARACTER_SET);
            // 设置容错级别，默认为ErrorCorrectionLevel.L
            // 因为中间加入logo所以建议你把容错级别调至H,否则可能会出现识别不了
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.MARGIN, 2); // default is 4
//            hints.put(EncodeHintType.MAX_SIZE, 350);
//            hints.put(EncodeHintType.MIN_SIZE, 100);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);
            // 将logo图片按martix设置的信息缩放
            bitmap = Bitmap.createScaledBitmap(bitmap, size, size, false);
            int width = bitMatrix.getWidth(); // 矩阵高度
            int height = bitMatrix.getHeight(); // 矩阵宽度
            int halfW = width / 2;
            int halfH = height / 2;
            Matrix matrix = new Matrix();
            float sx = (float) 2 * logoHalfWidth / bitmap.getWidth();
            float sy = (float) 2 * logoHalfWidth / bitmap.getHeight();
            matrix.setScale(sx, sy);
            // 设置缩放信息
            // 将logo图片按martix设置的信息缩放
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
            int[] pixels = new int[size * size];
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (x > halfW - logoHalfWidth
                            && x < halfW + logoHalfWidth
                            && y > halfH - logoHalfWidth
                            && y < halfH + logoHalfWidth) {
                        //该位置用于存放图片信息
                        //记录图片每个像素信息
                        pixels[y * width + x] = bitmap.getPixel(x - halfW + logoHalfWidth, y - halfH + logoHalfWidth);
                    } else {
                        if (bitMatrix.get(x, y)) {
                            pixels[y * size + x] = color;
                        } else {
                            pixels[y * size + x] = WHITE;
                        }
                    }
                }
            }
            Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            // 通过像素数组生成bitmap
            result.setPixels(pixels, 0, size, 0, 0, size, size);
            return result;
        } catch (WriterException e) {
            return null;
        }
    }
}
