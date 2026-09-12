package com.EcommerceWeb.Foodzy.Controllers;

import com.EcommerceWeb.Foodzy.ServicesInterface.ImageFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")

@Tag(
        name = "Image Module",
        description = "APIs for Image Download and Viewing"
)
public class ImageController {

    @Autowired
    private ImageFileService imageFileService;


    @Value("${project.image}")
    private String path;

    @Operation(
            summary = "Download Image",
            description = "Returns an image file using its image name."
    )
    @GetMapping(value = "/{imageName}")
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        System.out.println("Image name received: " + imageName);

        InputStream resource = this.imageFileService.getResources(path, imageName);

        String contentType = Files.probeContentType(Paths.get(path, imageName));

        response.setContentType(contentType);

        StreamUtils.copy(resource, response.getOutputStream());
    }

}
