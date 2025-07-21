package util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class QRCodeGenerator {
    public static Image gerarQRCode(String texto, int largura, int altura) throws WriterException {
        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix matrix = qrWriter.encode(texto, BarcodeFormat.QR_CODE, largura, altura);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
        return image;
    }
}
