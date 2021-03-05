package com.zhrenjie04.alex.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BasicEntity {
    Integer pageNo;
    Integer pageSize;
    Integer total;
    Integer totalPages;
    String gid;
}
