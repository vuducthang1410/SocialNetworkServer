package wint.webchat.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaveVietNamProvinces {
    private final ResourceLoader resourceLoader;

    public List<String> readProvincesFromFile(String path){
        Resource resource=resourceLoader.getResource(path);
        try(BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(resource.getInputStream()))){
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
