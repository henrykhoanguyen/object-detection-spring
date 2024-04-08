package com.take.home.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.take.home.model.Image;
import com.take.home.model.ImageRequest;
import com.take.home.model.Object;
import com.take.home.repository.ImageRepository;
import com.take.home.repository.ObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ObjectRepository objectRepository;

    private final DetectionService detectionService;

    public List<Image> getAllImages(){
        return imageRepository.findAll();
    }

    public Image getImage(Long imageId){
        Image image = null;
        try {
            image = imageRepository.findById(imageId).get();
        } catch (Exception e){
            throw new NoSuchElementException(e);
        }

        return image;
    }

    public List<Image> getImagesWithObjects(List<String> objects){
        Set<Image> images = new HashSet<>();
        for (String name: objects){
            images.addAll(objectRepository.findByName(name).getImages());
        }
        return List.copyOf(images);
    }

    public Image getImageInfo(ImageRequest imageRequest) throws IOException, ImageProcessingException {
        String imageUrl = imageRequest.getImageUrl();
        String imageLabel = imageRequest.getLabel();
        boolean enableObjectDetection = imageRequest.isEnableObjectDetection();
        List<Object> detectedObjects = new ArrayList<>();

        if (enableObjectDetection) {
            // identify objects using Imagga
            detectionService.getObjectsDetected(imageUrl)
                    .stream()
                    .limit(10)
                    .forEach(objectName -> {
                        // Retrieve objects if exist in db
                        // create new if not
                        Object tempObject = objectRepository.findByName(objectName);
                        if (tempObject == null){
                            tempObject = Object.builder().name(objectName).build();
                        }
                        detectedObjects.add(tempObject);
                    });
        }
        if (imageLabel.isEmpty() || imageLabel.isBlank()){
            imageLabel = detectedObjects.get(0).getName();
        }

        String imageMetadata = extractMetadataFromImageUrl(imageUrl);

        Image image = Image.builder()
                .label(imageLabel)
                .imageUrl(imageUrl)
                .metadata(imageMetadata)
                .objects(new HashSet<>(detectedObjects))
                .build();

        // Push Image into MongoDB
        imageRepository.save(image);

        return image;
    }

    private String extractMetadataFromImageUrl(String imageUrl) throws IOException, ImageProcessingException {
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
// TODO: populate db, inject image info into db, test

//        https://upload.wikimedia.org/wikipedia/commons/d/d3/16-04-04-Felsendom-Tempelberg-Jerusalem-RalfR-WAT_6385.jpg
//        https://upload.wikimedia.org/wikipedia/commons/a/ae/Olympic_flag.jpg
//        https://upload.wikimedia.org/wikipedia/commons/b/b6/Felis_catus-cat_on_snow.jpg
//        https://upload.wikimedia.org/wikipedia/commons/6/6e/Paris_-_Eiffelturm_und_Marsfeld2.jpg
