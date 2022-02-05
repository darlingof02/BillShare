package com.yyds.billshare.Model.Form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class BillCreateForm {

    @NotNull
    @Min(1)
    private Integer amount;
    private Date createTime;

    @Size(max = 30)
    private String type;
    private MultipartFile receipt;
    private String comment;

    @NotNull
    @NotEmpty
    private List<DebtorInfo> debtorInfos;

}
