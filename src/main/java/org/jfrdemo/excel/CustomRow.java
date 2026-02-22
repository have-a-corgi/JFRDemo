package org.jfrdemo.excel;

import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomRow {

    private int rowIndex;
    Map<Integer, CustomCell> cells = new HashMap<>();

    public CustomCell getCell(int cellIndex) {
        if (cells == null) {
            cells = new HashMap<>();
        }
        return cells.computeIfAbsent(cellIndex, key->CustomCell.builder()
                .rowIndex(rowIndex).columnIndex(cellIndex).build());
    }

    @Override
    public String toString() {
        return cells.values().stream().map(CustomCell::getValue).collect(Collectors.joining(";"));
    }

}
