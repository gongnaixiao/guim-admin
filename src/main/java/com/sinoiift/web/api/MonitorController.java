package com.sinoiift.web.api;

import com.sinoiift.domain.result.Rest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by xg on 2018/3/22.
 */
@RestController
public class MonitorController {
    @PostMapping("/monitor")
    public Rest login(@RequestBody String post) {
        try (
                FileWriter writer = new FileWriter("./my.txt", true);
                PrintWriter pw = new PrintWriter(writer);
        ) {
            pw.write(post + "\r\n");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Rest.ok();
    }
}
