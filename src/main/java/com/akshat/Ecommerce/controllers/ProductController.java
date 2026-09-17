package com.akshat.Ecommerce.controllers;

import com.akshat.Ecommerce.model.Product;
import com.akshat.Ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(service.getProducts(), HttpStatus.OK) ;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") int id){
        Product product = service.getById(id);
        if(product!=null){
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("product")
    public ResponseEntity<?> addProduct(@RequestPart("product") Product product, @RequestPart("image") MultipartFile image)
     {
         System.out.println("POST /product HIT");

         Product savedProduct = null;
        try {
            savedProduct = service.addProduct(product,image);
            return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);
        } catch (IOException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int productId){
        Product product = service.getById(productId);
        if(product!=null)
            return new ResponseEntity<>(product.getImageData(),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @PutMapping("/product/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable("productId") int productId,@RequestPart Product product,@RequestPart MultipartFile image){
        try{
            Product updatedProduct = service.updateProduct(productId,product,image);
            if(updatedProduct!=null){
                return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e){
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId){
        Product product = service.getById(productId);
        if(product!=null){
            service.deleteById(productId);
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchByKeyword(@RequestParam String keyword){
        List<Product> products = service.findBykeyword(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);

    }



}
