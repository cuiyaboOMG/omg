package com.omg.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

/**
 * Created by gp-0096 on 2019/4/16.
 */
public class PdfUtil {
    public void tranPdf(){
        // 模板路径
        String templatePath = "C:\\Users\\gp-0096\\Desktop\\审批流程1.pdf";
        // 生成的新文件路径
        String newPDFPath = "C:\\Users\\gp-0096\\Desktop\\审批流程2.pdf";
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;

        try {
            /*PdfDocument pdfDoc = new PdfDocument(new PdfReader("C:\\Users\\gp-0096\\Desktop\\test.pdf"), new PdfWriter("C:\\Users\\gp-0096\\Desktop\\omg.pdf"));
            PdfAcroForm pdfAcroForm = PdfAcroForm.getAcroForm(pdfDoc, true);
            pdfAcroForm.getField("title").setValue("小编");
            pdfAcroForm.flattenFields();
            pdfDoc.close();*/
            out = new FileOutputStream(newPDFPath);// 输出流
            reader = new PdfReader(templatePath);// 读取pdf模板
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader,bos);
            AcroFields form = stamper.getAcroFields();
            Set<String> strings = form.getFields().keySet();
            boolean channelName = strings.contains("channelName");
            System.out.println(channelName);
            form.setField("channelName","123");
            form.setField("startTime","2019-03-01");
            for (int i =1;i<=3;i++) {
                form.setField("setp"+i,String.valueOf(i));
            }
            stamper.setFormFlattening(true);
            stamper.close();
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            int count = reader.getNumberOfPages();//原PDF总页数
            for(int j=1;j<=count;j++){
                PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), j);
                copy.addPage(importPage);
            }

            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PdfUtil pdfUtil = new PdfUtil();
        pdfUtil.tranPdf();
    }
}
