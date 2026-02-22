package org.jfrdemo.excel;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomCell {

    private int rowIndex;
    private int columnIndex;
    private String value;

}
