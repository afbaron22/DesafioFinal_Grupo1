package com.mercadolibre.demo_bootcamp_spring.services;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {



   /* @Autowired
    ProductsWarehouseRepository productsWarehouseRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    public void saveBatch(List<Producto> lista){
        var warehouse = getWarehouse(1);
        for(var item: lista){
            var product = productsWarehouseRepository.findById(item.getNo_lote()).orElseGet();
        }
        lista.stream().map(x->{
            var product = productsWarehouseRepository.findById(x.getNo_lote()).orElseThrow();
            if(product!=null){
                var cantidad = product.getCantidad() +1;
                productsWarehouseRepository.save(product);
                return x;
            }else{
                var newProductWarehouse = new ProductsWarehouse(null,warehouse,x);
            }
            return x;
        });

    }
    private ProductsWarehouse validarProducto(Integer idProducto, Integer idWareHouse){
        var cuenta = productsWarehouseRepository.findByNoLote(idProducto,idWareHouse).orElseThrow();

        return cuenta;
    }
    private Warehouse getWarehouse(Integer idWarehouse){
        return warehouseRepository.findById(idWarehouse).orElseThrow();
    }*/



}
