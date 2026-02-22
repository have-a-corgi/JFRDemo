package org.jfrdemo;

import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jfrdemo.excel.CustomRow;
import org.jfrdemo.excel.EventExample;
import org.jfrdemo.excel.FromHowTo;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HSSFTest {

    @Test
    void oldFormat() throws IOException {
        // create a new file input stream with the input file specified
        // at the command line
        FileInputStream fin = new FileInputStream("/home/dmitry/zebra/zebra3.xls");
        // create a new org.apache.poi.poifs.filesystem.Filesystem
        POIFSFileSystem poifs = new POIFSFileSystem(fin);
       // get the Workbook (excel part) stream in a InputStream
        InputStream din = poifs.createDocumentInputStream("Workbook");
        // construct out HSSFRequest object
        HSSFRequest req = new HSSFRequest();
        // lazy listen for ALL records with the listener shown above
        EventExample eventExample = new EventExample();
        req.addListenerForAllRecords(eventExample);
        // create our event factory
        HSSFEventFactory factory = new HSSFEventFactory();
        // process our events based on the document input stream
        factory.processEvents(req, din);
        // once all the events are processed close our file input stream
        fin.close();
        // and our document input stream (don't want to leak these!)
        din.close();
        System.out.println("done.");

        Collection<CustomRow> values = eventExample.getRows().values();
        System.out.println(values);
    }



    @Test
    void index() {

        assertEquals(1,getIdx("B1"));
        assertEquals(24,getIdx("Y1111"));
        assertEquals(26,getIdx("AA1"));
        assertEquals(51,getIdx("AZ12345"));

    }

    private int getIdx(String value) {
        int first = value.charAt(0) - 65;
        int second = value.charAt(1) - 65;
        return (second >= 0 && second < 26) ? (first+1)*26+second : first;

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
