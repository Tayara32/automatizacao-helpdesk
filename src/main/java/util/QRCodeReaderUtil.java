package util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class QRCodeReaderUtil {

    public static String lerQRCode(String caminhoImagem) {
        try {
            BufferedImage imagem = ImageIO.read(new File(caminhoImagem));
            LuminanceSource source = new BufferedImageLuminanceSource(imagem);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result resultado = new MultiFormatReader().decode(bitmap);
            return resultado.getText();

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao ler QR Code.";
        }
    }
}
