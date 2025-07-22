package util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Classe responsável pela geração de QR Codes.
 * <p>
 * Utiliza a biblioteca ZXing para gerar imagens de QR Code a partir de um texto.
 * </p>
 */

public class QRCodeGenerator {


    /**
     * Gera um QR Code com o texto especificado.
     *
     * @param texto   Conteúdo a ser codificado no QR Code.
     * @param largura Largura da imagem em pixels.
     * @param altura  Altura da imagem em pixels.
     * @return Uma imagem {@link Image} contendo o QR Code gerado.
     * @throws WriterException caso ocorra um erro na geração do QR Code.
     */

    public static Image gerarQRCode(String texto, int largura, int altura) throws WriterException {
        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix matrix = qrWriter.encode(texto, BarcodeFormat.QR_CODE, largura, altura);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
        return image;
    }
}
