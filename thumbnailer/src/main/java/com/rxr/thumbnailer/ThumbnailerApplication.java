package com.rxr.thumbnailer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;

@SpringBootApplication
public class ThumbnailerApplication {

	public static void main(String[] args) throws IOException {

		try {
			InputStream docFile = new FileInputStream(new File("C:/Soumyak/DMS/file-sample_100kB.docx"));
			XWPFDocument doc = new XWPFDocument(docFile);
			PdfOptions pdfOptions = PdfOptions.create();
			OutputStream out = new FileOutputStream(new File("C:/Soumyak/DMS/file-sample_100kB.pdf"));
			PdfConverter.getInstance().convert(doc, out, pdfOptions);
			doc.close();
			out.close();
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// reference
		// https://stackoverflow.com/questions/23326562/convert-pdf-files-to-images-with-pdfbox

		PDDocument pd = PDDocument.load(new File("C:/Soumyak/DMS/file-sample_100kB.pdf"));
		PDFRenderer pr = new PDFRenderer(pd);
		BufferedImage bi = pr.renderImageWithDPI(0, 300);
		ImageIO.write(bi, "JPEG", new File("C:/Soumyak/DMS/file-sample_100kB.jpeg"));

		SpringApplication.run(ThumbnailerApplication.class, args);
	}

	static ByteArrayOutputStream copy(InputStream source) throws IOException {
		try (InputStream inputStream = new ByteArrayInputStream(source.toString().getBytes());
		         ByteArrayOutputStream targetStream = new ByteArrayOutputStream()) {
		        IOUtils.copy(inputStream, targetStream);
		        
		        return targetStream;
		 
		    }
	}
}
