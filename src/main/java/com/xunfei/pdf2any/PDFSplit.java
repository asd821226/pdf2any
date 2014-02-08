package com.xunfei.pdf2any;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdfwriter.COSWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.Splitter;

public class PDFSplit {
	private static PDFSplit split=null;

	private PDFSplit() {
	}

	public static PDFSplit getInstance(){
		if(split==null){
			split=new PDFSplit();
		}
		return split;
	}
	
	public void splitToMulti(String file,String splitMulti){
		String[] splitArray=splitMulti.split(";");
		for (int i = 0; i < splitArray.length; i++) {
			split(file, splitArray[i]);
		}
	}
	
	public void split(String file,String splitStr){
		String[] splitArray=splitStr.split("-");
		try{
			split(file,splitArray[0],splitArray[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void split(String pdfFile, String startPage, String endPage)
			throws Exception {
		PDDocument document = null;
		List<PDDocument> documents = null;
		Splitter splitter = new Splitter();
		ZipOutputStream zos=null;
		COSWriter writer = null;
		try {
			document = PDDocument.load(pdfFile);
			document.isEncrypted();
			splitter.setStartPage(Integer.parseInt(startPage));
			splitter.setEndPage(Integer.parseInt(endPage));
			splitter.setSplitAtPage(Integer.parseInt(endPage));

			documents = splitter.split(document);		
			String zipFileName=pdfFile.substring(0, pdfFile.length() - 4)+".zip";
			zos = new ZipOutputStream(new FileOutputStream(zipFileName), Charset.forName("GBK"));
			for (int i = 0; i < documents.size(); i++) {
				String fileName = pdfFile.substring(pdfFile.lastIndexOf("/")+1, pdfFile.length() - 4)
						+ "-" + i + ".pdf";
				ZipEntry entry = new ZipEntry(fileName);
				PDDocument doc = documents.get(i);
				zos.putNextEntry(entry);
				writer = new COSWriter(zos);
				writer.write(doc);
				doc.close();
			}
		} finally {
			if (document != null) {
				document.close();
			}
			if(zos!=null){
				zos.close();
			}
			if (writer != null) {
				writer.close();
			}
			for (int i = 0; documents != null && i < documents.size(); i++) {
				PDDocument doc = (PDDocument) documents.get(i);
				doc.close();
			}
		}
	}
}
