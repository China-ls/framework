package test.infinite;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class TubanMifReader {

    public static void main(String[] args) {
        String mif = "/home/hx/dev/workspace/web_android/LandResourceAS/app/src/main/assets/Export.mif";
        String mid = "/home/hx/dev/workspace/web_android/LandResourceAS/app/src/main/assets/Export.mid";

        File mifFile = new File(mif);
        File midFile = new File(mid);

        Scanner scMif = null;
        FileInputStream fisMid = null;
        InputStreamReader isrMid = null;
        BufferedReader brMid = null;
        try {

            scMif = new Scanner(mifFile);
            fisMid = new FileInputStream(midFile);
            isrMid = new InputStreamReader(fisMid, "GBK");
            brMid = new BufferedReader(isrMid);

            String propertyLine = null;
            String pointLine = null;

            StringBuilder pointSb = new StringBuilder();
            int count = 0;
            while (scMif.hasNextLine()) {
                pointLine = scMif.nextLine();
                if (pointLine.startsWith("REGION")) {
                    pointSb.setLength(0);
                    count = Integer.parseInt(scMif.nextLine());
                    propertyLine = brMid.readLine();
                } else if (count > 0) {
                    pointSb.append(pointLine);
                    count --;
                } else if (pointLine.startsWith("BRUSH")) {
                    System.out.println( "point:" + pointSb.toString() + "  --- " + propertyLine );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuietly(scMif);
            closeQuietly(fisMid);
            closeQuietly(isrMid);
            closeQuietly(brMid);
        }
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
