package com.ls.jsp;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

public class AddJspPrefix {

    public static void main(String[] args) {
        String baseDir = args[0];

        File baseFile = new File(baseDir);
        Stack<File> fileStack = new Stack<File>();
        fileStack.add(baseFile);
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[2048];
        int len = -1;
        do {
            File file = fileStack.pop();
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                if (null != children) {
                    fileStack.addAll(Arrays.asList(children));
                }
            } else {
                String name = file.getName();
                if (name.endsWith(".jsp")) {
                    sb.setLength(0);

                    FileInputStream fis = null;
                    FileOutputStream fos = null;
                    boolean shouldAdd = true;
                    try {
                        fis = new FileInputStream(file);
                        len = -1;
                        while ((len = fis.read(buffer)) > 0) {
                            String line = new String(buffer, 0, len);
                            sb.append(line);
                            if (line.contains("<%@ page")) {
                                shouldAdd = false;
                                break;
                            }
                        }

                        if (shouldAdd) {
                            sb.insert(0, "<%@ page language=\"java\" pageEncoding=\"utf-8\" %>\n" +
                                    "<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jstl/core\" %>\n" +
                                    "<%@ taglib prefix=\"fmt\" uri=\"http://java.sun.com/jsp/jstl/fmt\" %>\n" +
                                    "<%@ taglib prefix=\"s\" uri=\"http://www.springframework.org/tags\" %>\n" +
                                    "<%@ taglib prefix=\"sform\" uri=\"http://www.springframework.org/tags/form\" %>\n\n");
                            fos = new FileOutputStream(file, false);
                            fos.write(sb.toString().getBytes());

                            System.out.println( file.getAbsolutePath() );
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        closeQuietly(fis);
                        closeQuietly(fos);
                    }
                }
            }
        } while (!fileStack.isEmpty());
    }

    private static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

}
