//package com.goodall.controllers;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.goodall.entities.Photo;
//import com.goodall.serializers.EventSerializer;
//import com.goodall.serializers.RootSerializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//
//
//import java.util.Map;
//
//@CrossOrigin("*")
//@RestController
//public class PhotosController {
//    RootSerializer rootSerializer = new RootSerializer();
//    EventSerializer eventSerializer = new EventSerializer();
//
//    @Value("${cloud.aws.s3.bucket}")
//    String bucket;
//
//    @Autowired
//    AmazonS3Client s3;
//
//    public Map<String, Object> uploadPhoto(@RequestParam("photo")){
//        //Creating a new PhotoPost Entity
//        Photo photo = new Photo();
//        //Set properties other than the file
//        photo.setCaption(caption);
//        photo.setPhotoUrl("Https://s3.amazonaws.com/" + bucket + "/" + file.getOriginalFilename());
//
//        PutObjectRequest s3Req = new PutObjectRequest(bucket, file.getOriginalFilename(),
//                file.getInputStream(), new ObjectMetadata());
//
//        //Save the object to s3
//        s3.putObject(s3Req);
//
//        return rootSerializer.serializeOne("/photo/" + photo.getId())
//
//
//
//    }
//}
