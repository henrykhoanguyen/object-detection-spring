package com.take.home.service;

import com.take.home.exception.ResourceNotFoundException;
import com.take.home.model.Color;
import com.take.home.model.Image;
import com.take.home.model.ImageRequest;
import com.take.home.model.Object;
import com.take.home.repository.ColorRepository;
import com.take.home.repository.ImageRepository;
import com.take.home.repository.ObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private ColorRepository colorRepository;

    private final DetectionService detectionService;

    public List<Image> getAllImages(){
        List<Image> images = imageRepository.findAll();
        if(images.isEmpty()){
            throw new ResourceNotFoundException("No Images Found");
        }
        return images;
    }

    public Image getImage(Long imageId){
        Image image = null;
        if(imageRepository.existsById(imageId)){
            throw new ResourceNotFoundException("Not found image with id = " + imageId);
        }
        image = imageRepository.findById(imageId).get();

        return image;
    }

    public List<Image> getImagesWithObjects(List<String> objects){
        Set<Image> images = new HashSet<>();

        for (String name: objects){
            System.out.println(objectRepository.findImagesByName(name));
//            List<Image> foundImages = objectRepository.findImagesByName(name);
//            if (!foundImages.isEmpty()){
//                foundImages.forEach(foundImage -> {
//                    images.add(Image.builder().imageUrl(foundImage.getImageUrl()).build());
//                });
//            }
        }

        if(images.isEmpty()){
            throw new ResourceNotFoundException("No Images Found");
        }

        return List.copyOf(images);
    }

    public Image getImageInfo(ImageRequest imageRequest)  {
        String imageUrl = imageRequest.getImageUrl();
        String imageLabel = imageRequest.getLabel();
        boolean enableObjectDetection = imageRequest.isEnableObjectDetection();
        List<Object> detectedObjects = new ArrayList<>();

        try {
            if (enableObjectDetection) {
                // identify objects using Imagga
                detectionService.getDetectedObjects(imageUrl)
                        .stream()
                        .limit(10)
                        .forEach(objectName -> {
                            // Retrieve objects if exist in db
                            // create new if not
                            Object objectBuilder = objectRepository.findByName(objectName);
                            if (objectBuilder == null){
                                objectBuilder = Object.builder().name(objectName).build();
                            }
                            detectedObjects.add(objectBuilder);
                        });
            }
            if (imageLabel.isEmpty() || imageLabel.isBlank()){
                imageLabel = detectionService.getLabel(imageUrl);
            }
        } catch (Exception e){
            throw new ResourceNotFoundException("Image Info Not Found with Error " + e);
        }


        List<Color> imageColors = detectionService.getColors(imageUrl);

        Image imageBuilder = Image.builder()
                .label(imageLabel)
                .imageUrl(imageUrl)
                .colors(imageColors)
                .objects(new HashSet<>(detectedObjects))
                .build();


        // Push Image into MongoDB
        Image savedImage = imageRepository.save(imageBuilder);

        // Save objects info
        detectedObjects.stream().map(object -> {
            object.getImages().add(savedImage);
            return objectRepository.save(object);
        });

        // Save color info
        imageColors.stream().map(color -> {
            color.setImage(savedImage);
            color.getImage().setId(savedImage.getId());
            return colorRepository.save(color);
        });

        return imageBuilder;
    }

}

//        https://imagga.com/static/images/tagging/wind-farm-538576_640.jpg
//        https://upload.wikimedia.org/wikipedia/commons/a/ae/Olympic_flag.jpg
//        https://upload.wikimedia.org/wikipedia/commons/b/b6/Felis_catus-cat_on_snow.jpg
//        https://upload.wikimedia.org/wikipedia/commons/6/6e/Paris_-_Eiffelturm_und_Marsfeld2.jpg
