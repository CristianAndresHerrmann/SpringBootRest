 package com.application.rest.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.rest.dto.ProductDTO;
import com.application.rest.entities.Product;
import com.application.rest.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Product> productOpt = productService.findById(id);
        if(productOpt.isPresent()){
            Product product = productOpt.get();

            ProductDTO productDTO = ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build();
            return ResponseEntity.ok(productDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){

        List<ProductDTO> productList = productService.findAll()
                        .stream()
                        .map(product -> ProductDTO.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .price(product.getPrice())
                            .maker(product.getMaker())
                            .build())
                        .toList();

        return ResponseEntity.ok(productList);
    }

 
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductDTO produtDTO) throws URISyntaxException{
        if(produtDTO.getName().isBlank() || produtDTO.getPrice() == null || produtDTO.getMaker() == null){
            return ResponseEntity.badRequest().build();
        }
        productService.save(Product.builder()
                    .name(produtDTO.getName())
                    .price(produtDTO.getPrice())
                    .maker(produtDTO.getMaker())
                    .build());
        return ResponseEntity.created(new URI("/api/product/save")).build();
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMaker(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        
        Optional<Product> productOpt = productService.findById(id);
        
        if(productOpt.isPresent()){
            Product product = productOpt.get();
            product.setName(productDTO.getName());
            product.setMaker(productDTO.getMaker());
            product.setPrice(productDTO.getPrice());
            productService.save(product);
            return ResponseEntity.ok("Registro Actualizado");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        if(id!=null){
            productService.deleteById(id);
            return ResponseEntity.ok("Registro Eliminado");
        }
        return ResponseEntity.badRequest().build();
    }
        
}