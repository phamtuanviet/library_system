package com.jetbrains.librarysystem.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class QrImage {
    /** Tạo qr từ đường link lấy từ dữ liệu document. */
    public static Image generateQRCodeImage(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 400, 400);

        Path tempFile = Files.createTempFile("QRCode", ".png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", tempFile);

        byte[] imageBytes = Files.readAllBytes(tempFile);
        return new Image(new ByteArrayInputStream(imageBytes));
    }
}
