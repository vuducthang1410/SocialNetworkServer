package wint.webchat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import wint.webchat.google.GoogleDriveFileServiceImpl;

@Controller
@RequiredArgsConstructor
public class GoogleDriveController {
    private final GoogleDriveFileServiceImpl googleDriveFileServiceImpl;
    @PostMapping(value = "/upload/file",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload,
                                   @RequestParam("filePath") String pathFile,
                                   @RequestParam("shared") String shared) {

        System.out.println(pathFile);
        if (pathFile.equals("")) {
            pathFile = "Root"; // Save to default folder if the user does not select a folder to save - you can change it
        }
        System.out.println(pathFile);
        googleDriveFileServiceImpl.uploadFile(fileUpload, pathFile, Boolean.parseBoolean(shared));
        return new ModelAndView("redirect:" + "/");
    }

    @GetMapping("/test")
    public String index() {
        return "test";
    }
}
