package org.jfrdemo.excel;

import lombok.Getter;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.record.*;

import java.util.HashMap;
import java.util.Map;

@Getter
public class EventExample implements HSSFListener {

    private SSTRecord sstrec;
    private Map<Integer, CustomRow> rows = new HashMap<>();

    @Override
    public void processRecord(Record record) {

        switch (record.getSid()) {
            // the BOFRecord can represent either the beginning of a sheet or the workbook
            case BOFRecord.sid:
                BOFRecord bof = (BOFRecord) record;
                if (bof.getType() == bof.TYPE_WORKBOOK) {
                    System.out.println("Encountered workbook");
                    // assigned to the class level member
                } else if (bof.getType() == bof.TYPE_WORKSHEET) {
                    System.out.println("Encountered sheet reference");
                }
                break;
            case BoundSheetRecord.sid:
                BoundSheetRecord bsr = (BoundSheetRecord) record;
                System.out.println("New sheet named: " + bsr.getSheetname());
                break;
            case RowRecord.sid:
                RowRecord rowrec = (RowRecord) record;
                CustomRow row = CustomRow.builder().rowIndex(rowrec.getRowNumber()).build();
                rows.put(row.getRowIndex(), row);
                break;
            case NumberRecord.sid:
                NumberRecord numrec = (NumberRecord) record;
                CustomCell cell = rows.get(numrec.getRow()).getCell(numrec.getColumn());
                cell.setValue(String.valueOf(numrec.getValue()));
                break;
            // SSTRecords store an array of unique strings used in Excel.
            case SSTRecord.sid:
                sstrec = (SSTRecord) record;
                for (int k = 0; k < sstrec.getNumUniqueStrings(); k++)
                {
                    System.out.println("String table value " + k + " = " + sstrec.getString(k));
                }
                break;
            case LabelSSTRecord.sid:
                LabelSSTRecord lrec = (LabelSSTRecord) record;
                CustomCell cell2 = rows.get(lrec.getRow()).getCell(lrec.getColumn());
                cell2.setValue(sstrec.getString(lrec.getSSTIndex()).getString());
                break;
        }

    }
}
