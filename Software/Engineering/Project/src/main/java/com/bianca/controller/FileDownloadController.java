package com.bianca.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.io.*;

@Controller
public class FileDownloadController {

    String folderPath = "E:\\1.UTCN\\Anul_3\\Sem_I\\IS\\apache-tomcat-9.0.54\\bin\\";

    @RequestMapping("/process-pdf")
    @ResponseBody
    public void showFile(@RequestParam("fileName") String fileName, HttpServletResponse response){
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setHeader("Content-Transfer-Encoding", "binary");
        System.out.println(fileName);
        try{
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(folderPath + fileName);
            int len;
            byte[] buf = new byte[1024];
            while((len = fis.read(buf)) > 0){
                bos.write(buf, 0, len);
            }
            bos.close();
            response.flushBuffer();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
