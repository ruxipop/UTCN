package com.bianca.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.*;

public class WriteReceiptToPDF {

    public WriteReceiptToPDF(String text) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("Receipt.pdf"));
        document.open();

//        boolean result;
//        File file = new File("Factura.pdf");
//        try
//        {
//            result = file.createNewFile();  //creates a new file
//            if(result)      // test if successfully created a new file
//            {
//                System.out.println("file created "+ file.getCanonicalPath()); //returns the path string
//            }
//            else
//            {
//                System.out.println("File already exist at location: "+ file.getCanonicalPath());
//            }
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();    //prints exception if any
//        }
//
//
//        if(file.exists()){
//            try{
//                PdfFileSpecification fileSpecification = PdfFileSpecification.fileEmbedded(pdfWriter, file.getAbsolutePath(), file.getName(), null);
//                pdfWriter.addFileAttachment("Attachment", fileSpecification);
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }

        Paragraph paragraph = new Paragraph();
        paragraph.add(text);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        document.add(paragraph);
        document.close();
    }



}
