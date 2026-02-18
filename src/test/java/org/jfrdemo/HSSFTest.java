package org.jfrdemo;

/*import com.github.pjfanning.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;*/
import org.jfrdemo.excel.FromHowTo;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HSSFTest {

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
