package com.yyds.billshare.Model.ClearModels;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClearItemsPK implements Serializable {

    private Integer clearInfo;
    private Integer bid;
}
