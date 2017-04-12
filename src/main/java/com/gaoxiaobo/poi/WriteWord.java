package com.gaoxiaobo.poi;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class WriteWord
{
    public static void main(String[] args) throws IOException {
        String readPath = "/Users/gaoxiaobo/Desktop/workspace/git/edu/code/edu-cloud-school/business-school/exam-work/src/main/java/com/hanboard/examwork/controller/ExamWorkController.java";
        String startDir = "/Users/gaoxiaobo/Desktop/workspace/git/edu/code/edu-cloud-school";

        String fileName = "校园管理.docx";
        Path src = Paths.get(readPath);
        Path dst = Paths.get("school.txt");
        BufferedReader reader = Files.newBufferedReader(src, StandardCharsets.UTF_8);
        BufferedWriter writer = Files.newBufferedWriter(dst, StandardCharsets.UTF_8);
        List<String> pathList = new ArrayList<>() ;

        try(Stream<Path> paths = Files.walk(Paths.get(startDir))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath) &&
                        FilenameUtils.getExtension(filePath.toString()).equals("java") ) {
                    pathList.add(filePath.toString());
                }
            });
        }

        String line;
       for(String p :pathList){
           reader = Files.newBufferedReader(Paths.get(p), StandardCharsets.UTF_8);
           while ((line = reader.readLine()) != null ) {
               writer.write(line);
               // must do this: .readLine() will have stripped line endings
               writer.newLine();
           }
       }

        writer.close();

        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        ArrayList<XWPFRun> runs = new ArrayList<XWPFRun>();
        XWPFRun run;

        String HW1 = "SimSum";
        // college ruled => 12pt = 12*20 = 240
        int fontSize = 9;

        run = paragraph.createRun();
        run.setFontSize(fontSize);
        run.setFontFamily(HW1);
        run.setText("hello");

        try {
            FileOutputStream output = new FileOutputStream(fileName);
            document.write(output);
            output.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("created successfully.");
    }
}
