package org.jfrdemo;

import org.jfrdemo.excel.FromHowTo;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HSSFTest {

    @Test
    void index() {

        String s1 = "D12454";
        String s2 = "AA555";
        String s3 = "BA4444";


        System.out.println(getIdx(s1));
        System.out.println(getIdx(s2));
        System.out.println(getIdx(s3));

    }

    private int getIdx(String value) {
        int first = value.charAt(0) - 65;
        int second = value.charAt(1) - 65;
        boolean radix = false;

        int index = 0;

        if (second >= 0 && second <= 25) { //A-Z
            radix = true;
        }

        if (radix) {
            index = (first+1)*26+second;
        } else {
            index = first;
        }

        return index;
    }

    @Test
    void zebraDir() throws IOException {
        File file = new File("/home/dmitry/zebra/z1.txt");
        if (!file.exists()) {
            file.createNewFile();

            try(FileOutputStream stream = new FileOutputStream(file);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("ABC".getBytes(StandardCharsets.UTF_8));) {
                byteArrayInputStream.transferTo(stream);
            }
        } else {
            file.delete();
        }

    }

    /*@Test
    void sheet() throws FileNotFoundException {
        InputStream is = new FileInputStream(new File("/path/to/workbook.xlsx"));
        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(100) // number of rows to keep in memory (defaults to 10)
                .bufferSize(4096)
                .open(is);
        Sheet sheet = workbook.getSheetAt(0);
        for(Row row : sheet) {

        }
    }*/

    @Test
    void sheet2() throws Exception {
        FromHowTo fromHowTo = new FromHowTo();
        fromHowTo.processFirstSheet("/home/dmitry/zebra/zebra2.xlsx");
    }

}
