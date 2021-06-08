package com.mercadolibre.demo_bootcamp_spring.services;

import com.mercadolibre.demo_bootcamp_spring.repository.OrdersRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.ProductsRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CompraService  {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    //Cache cache = new Cache<List<Turno>>(5);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDate date = LocalDate.now();

 /*   {
        "id_producto":1,
            "nombre": "Bananos",
            "fechaCaducidad":"22/10/2003",
            "infoAdicional":"Bananos de china",
            "idWarehouse":1
    }*/

/*

    public String saveCompra(List<Product> products) throws IOException {
        var res = products.stream().map(x-> productoRepository.findById(x.getNo_lote()).orElseThrow()).collect(Collectors.toList());
        Orders orders = new Orders(null);
        for(var item:res){
            var warehouse = warehouseRepository.findById(1).orElseThrow();
            if(warehouse.getProducts().remove(item))
                warehouseRepository.save(warehouse);
                orders.getProducts().add(item);
        }
        compraRepository.save(orders);
        return "ok";
    }

    public void saveBatch(List<Product> products, Integer id){
        var warehouse = warehouseRepository.findById(id).orElseThrow();
        //var res = productos.stream().map(x-> productoRepository.findById(x.getNo_lote()).orElseThrow()).collect(Collectors.toList());
        var res = products.stream()
                .map(x-> productoRepository.findById(x.getNo_lote())
                        .orElseGet(()-> {
                            var newProduct = new Product(null, x.getNombre(),x.getFechaCaducidad(),x.getInfoAdicional());
    return newProduct;})).collect(Collectors.toList());


        res.stream().forEach(x-> warehouse.getProducts().add(x));
        warehouseRepository.save(warehouse);
    }
*/

}
