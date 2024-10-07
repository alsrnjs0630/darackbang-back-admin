package com.lab.darackbang.dto.common;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoDTO {
    private String type;
    private Integer sortNum;
    private String fileName;
}
