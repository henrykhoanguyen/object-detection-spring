package com.take.home.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.take.home.model.Image;
import com.take.home.model.ImageRequest;
import com.take.home.repository.ImaggaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageService {

    @Autowired
    private ImaggaRepository imageRepository;

    public List<Image> getAllImages(){
        return imageRepository.findAll();
    }

    public Image getImage(String imageId){
        return imageRepository.findById(imageId);
    }

    public List<Image> getImagesWithObjects(String[] objects){
        return imageRepository.findImagesByObjects(objects);
    }

    public Image getImageInfo(ImageRequest imageRequest){
        String imageUrl = imageRequest.getImageUrl();
        String imageLabel = imageRequest.getLabel();
        boolean enableObjectDetection = imageRequest.isEnableObjectDetection();
        List<String> detectedObjects = null;

        if (enableObjectDetection) {

        }

        return new Image();
    }

    public String extractMetadataFromImageUrl(String imageUrl) throws IOException, ImageProcessingException {
        Map<String, String> metadataMap = new HashMap<>();

        // Fetch image bytes from URL
        byte[] imageBytes = new RestTemplate().getForObject(imageUrl, byte[].class);

        // Read metadata from image bytes
        Metadata metadata = ImageMetadataReader.readMetadata(new ByteArrayInputStream(imageBytes));

        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                metadataMap.put(tag.getTagName(), tag.getDescription());
            }
        }

        return metadataMap.toString();
    }
}

//        System.out.println(extractMetadataFromUrl("https://upload.wikimedia.org/wikipedia/commons/d/d3/16-04-04-Felsendom-Tempelberg-Jerusalem-RalfR-WAT_6385.jpg"));
//        System.out.println(extractMetadataFromUrl("https://upload.wikimedia.org/wikipedia/commons/a/ae/Olympic_flag.jpg"));
//        System.out.println(extractMetadataFromUrl("https://upload.wikimedia.org/wikipedia/commons/b/b6/Felis_catus-cat_on_snow.jpg"));
//        System.out.println(extractMetadataFromUrl("https://upload.wikimedia.org/wikipedia/commons/6/6e/Paris_-_Eiffelturm_und_Marsfeld2.jpg"));
