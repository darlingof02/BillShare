package com.yyds.billshare.Controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class UserInfo{
    public String username;
    public String password;
    MultipartFile avatar;
}

@Data
class BillCreateForm{
    private int amount;
    private String comment;
    private MultipartFile receipt;
}

@Slf4j
@Controller
@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true", allowedHeaders = "*")
public class TestAnythingController {

    @GetMapping("/test/string")
    @ResponseBody
    public String tt() throws IOException {
        log.info("shit");
        String path = "/Users/yuning/Documents/GitHub/BillShare/src/main/resources/image/test.png";
        return path;
    }

    @GetMapping("/test/test_download")
    @ResponseBody
    public ResponseEntity<byte[]> testDownload() throws IOException {
        log.info("shit");
        String path = "/Users/yuning/Documents/GitHub/BillShare/src/main/resources/image/test.png";
        InputStream is = new FileInputStream(path);
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        MultiValueMap<String,String> headers = new HttpHeaders();
        headers.add("Content-Disposition","attachment;filename=1.jpg");
        HttpStatus status = HttpStatus.OK;
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes,headers,status);
        return responseEntity;
    }
    @PostMapping("/test/test_up")
    @ResponseBody
    public String testUpload(@RequestParam(value = "username") String username,
                             @RequestParam(value = "avatar") MultipartFile avatar) throws IOException {
        String originalFilename = avatar.getOriginalFilename();
        String savePath = "/Users/yuning/Documents/GitHub/BillShare/src/main/resources/static/image";
        String saveFilePath = savePath + File.separator + originalFilename;

        avatar.transferTo(new File(saveFilePath));
        System.out.println(username);
//        System.out.println(password);

        return "shit, success!";
    }

    @PostMapping("/test/create_bill")
    @ResponseBody
    public String createBill(BillCreateForm form){
        log.info(form.toString());
        if(form.getReceipt().getSize()==0)
            log.info("wocaonima");

        return "shit";
    }

}
