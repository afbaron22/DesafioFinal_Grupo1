package com.example.demo_bootcamp_spring.services.Batch;
import com.example.demo_bootcamp_spring.dtos.*;
import com.example.demo_bootcamp_spring.exceptions.*;
import com.example.demo_bootcamp_spring.models.Batch;
import com.example.demo_bootcamp_spring.models.InboundOrder;
import com.example.demo_bootcamp_spring.models.Section;
import com.example.demo_bootcamp_spring.repository.BatchRepository;
import com.example.demo_bootcamp_spring.repository.InboundOrderRepository;
import com.example.demo_bootcamp_spring.repository.ProductsRepository;
import com.example.demo_bootcamp_spring.repository.SectionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BatchService implements IBatchService {

    InboundOrderRepository inboundOrderRepository;
    SectionRepository sectionRepository;
    BatchRepository batchRepository;
    ProductsRepository productsRepository;
    private LocalDate currentDate = LocalDate.now();
    /**CONSTRUCTOR
     * Contructor encargado de inyectar las dependencias de los repositorios.
     * @param inboundOrderRepository
     * @param sectionRepository
     * @param batchRepository
     * @param productsRepository
     */
    public BatchService(InboundOrderRepository inboundOrderRepository, SectionRepository sectionRepository, BatchRepository batchRepository, ProductsRepository productsRepository) {
        this.inboundOrderRepository = inboundOrderRepository;
        this.sectionRepository      = sectionRepository;
        this.batchRepository        = batchRepository;
        this.productsRepository     = productsRepository;
    }
    /**MÉTODO SAVEBATCH
     *Este método se encarga de guardar en persistencia un InboundOrderTransaction, este es un objecto que el
     * usuario envia, posteriormente el metodo extrae el contenido de este inbound y extraé los parametros
     * requeridos para guardar todos los datos en las tablas , estos atributos son el tamaño del bache, la sección
     * y finalmente se crea un nuevo inboundorder.Cabe aclarar que tambien se deben guardar los baches para que
     * la relación funcione y queden los datos almacenados.
     * @param inboundOrder
     * @return
     */
    //------------------------------------------MÉTODO SAVEBATCH--------------------------------------------------
    public BatchStock saveBatch(InboundOrderTransaction inboundOrder){
        var inboundOrderDTO = inboundOrder.getInboundOrder();
        if(existInboundOrder(inboundOrderDTO))
            throw new ExistingInboundOrderId();
        var sizeBatch = getBatchSize(inboundOrderDTO);
        var section   = saveSection(inboundOrderDTO,sizeBatch);
        var newInboundOrder   = new InboundOrder(inboundOrderDTO.getOrderNumber(),inboundOrderDTO.getOrderDate(),section);
        var batches = inboundOrderDTO.getBatchStock().stream().map(x-> returnBatch(x,newInboundOrder)).collect(Collectors.toList());
        inboundOrderRepository.save(newInboundOrder);
        batches.forEach(x->batchRepository.save(x));
        return getBatchResponse(inboundOrderDTO);
    }

    /**MÉTODO PUTBATCH
     *Este método se encarga de actualizar un batch existente en caso de alguna ligera modificación como las
     * fechas de caducidad, estado del batch , y las cantidades iniciales excepto las que ya se han vendido.
     * Mediante un stream se recorre los baches asociados a ese inboundorder.
     * @param inboundOrder
     * @return
     */
    //------------------------------------------MÉTODO PUTBATCH--------------------------------------------------
    public BatchStock putBatch(InboundOrderTransaction inboundOrder){
        var inboundOrderDTO = inboundOrder.getInboundOrder();
        var sizeBatch = getBatchSize(inboundOrderDTO);
        var searchedInbound =inboundOrderRepository.findById(inboundOrderDTO.getOrderNumber())
                .orElseThrow(()-> new NotFoundInboundOrderId());
        updateSection(inboundOrderDTO,searchedInbound,sizeBatch);
        updateOrderDate(inboundOrderDTO,searchedInbound);

        var arrayBatch = inboundOrderDTO.getBatchStock().toArray();
        BatchDTO[] arrayBatchDto = new BatchDTO[arrayBatch.length];
        var arrayB = batchRepository.findByInboundOrder(inboundOrder.getInboundOrder().getOrderNumber()).orElseThrow(()->new NotExistingBatch()).toArray();
        Batch[] array = new Batch[arrayB.length];

        for(var i = 0; i < arrayBatchDto.length;i++){
            array[i] = (Batch) arrayB[i];
            arrayBatchDto[i] = (BatchDTO) arrayBatch[i];
            compareBatch(array[i],arrayBatchDto[i]);
        }
        return getBatchResponse(inboundOrderDTO);
    }
    //http://localhost:8080/api/v1/fresh-products/list?querytype=2

    /**MÉTODO GETPRODUCTFROMBATCHES
     *Este método se encarga de retornar un BatchStockProductSearch(Tipo de respuesta requerida a la hora de buscar productos
     * asociados a los baches), este método busca en la lista de baches presente la existencia del id de un producto y trae la
     * lista asociada a este. Posteriormente se inyecta en el método processList encargada de los respectivos ordenamientos,
     * finalmente se regresa el objeto.
     * @param idProducto
     * @return
     */
    //---------------------------------------MÉTODO GETPRODUCTFROMBATCHES--------------------------------------------------
    public BatchStockProductSearch getProductFromBatches(String idProducto, String ordBy){
        if(ordBy==null) ordBy="Default";
       var list = batchRepository.findByProductId(idProducto).orElseThrow(()->new NotExistingBatch());
       var listFound = processList(list,ordBy);
       var sectionDto = getSearchedSection(list);
      return new BatchStockProductSearch(sectionDto,idProducto,listFound);
    }

    /**
     *
     * @param idProducto
     * @return
     */
    //---------------------------------------MÉTODO GETPRODUCTFROMWAREHOUSES--------------------------------------------------
    public SearchedWarehouseProducts getProductFromWarehouses(String idProducto){
        Map<String,String> warehouses = new HashMap<>();
        var res =batchRepository.findWarehousesWithProduct(idProducto).orElseThrow(()-> new NoRelatedWarehousesToProduct());
        var warehouseList =res.stream().map(x->{
            warehouses.put("warehousecode",x[0].toString());
            warehouses.put("totalQuantity",x[1].toString());
            return warehouses; }).collect(Collectors.toList());
        return new SearchedWarehouseProducts(idProducto,warehouseList);
    }

    @Override
    public BatchStock getBatchesInWarehouseByDueDate(Integer idWarehouse, LocalDate days) {
        BatchStock batchStock = new BatchStock();
        Optional<List<Batch>> batcheslist = batchRepository.findBatchesInWarehouseByDueDate(idWarehouse, days);
        return batchStock;
    }

    /**MÉTODO PROCESSLIST
     * Este método se encarga de procesar la lista de baches asociadas a un producto , mediante un stream, se filtran
     * los productos que no esten vencidos. Este método recibe dos parametros , la lista a procesar y el parametro de
     * orden. Si este parametro ordBy es nulo , se devuelve la lista sin filtros, pero si se pasa alguno de los tipos
     * de ordenamiento disponibles , se procede al ordenamiento.
     * @param list
     * @param ordBy
     * @return
     */
    //---------------------------------------MÉTODO PROCESSLIST--------------------------------------------------
    private List<BatchStockProduct> processList(List<Batch> list, String ordBy){
        var listFound = list.stream().map(x-> { if(!x.getDueDate().isBefore(currentDate) && x.getDueDate().isAfter(currentDate.plusWeeks(3))) return new BatchStockProduct(x.getBatchNumber(),x.getCurrentQuantity(),x.getDueDate());
            return null;}).collect(Collectors.toList());
        while (listFound.remove(null)) {}
        if(ordBy.equals("L"))
            Collections.sort(listFound,Comparator.comparingInt(a -> Integer.parseInt(a.getBatchnumber())));
        else if(ordBy.equals("C"))
            Collections.sort(listFound,Comparator.comparingInt(BatchStockProduct::getCurrentQuantity));
        else if(ordBy.equals("F"))
            Collections.sort(listFound,Comparator.comparing(BatchStockProduct::getDueDate));
        return listFound;
    }

    /**MÉTODO GETSEARCHEDSECTION
     *Este método es invocado desde el método padre de getProductFromBatches, se encarga de buscar la sección asociada
     * a un producto y devolver un sectionDto para su posterior presentación.
     * @param list
     * @return
     */
    //---------------------------------------MÉTODO GETSEARCHEDSECTION--------------------------------------------------
    private SectionDTO getSearchedSection(List<Batch> list){
        var idSection = list.get(0).getInboundOrder().getSection().getId();
        var section = sectionRepository.findById(idSection).orElseThrow(()->new InvalidSectionId());
        return new SectionDTO(section.getState(),section.getWarehouseCode());
    }

    /**MÉTODO EXISTORDER
     *Este método es invocado como una validación a la hora de guardar un inboundOrder, si por alguna razón el numero
     * de orden ya existe retorna falso.
     * @param inboundOrderDTO
     * @return
     */
    //------------------------------------------MÉTODO EXISTORDER--------------------------------------------------
    private Boolean existInboundOrder(InboundOrderDTO inboundOrderDTO){
        return inboundOrderRepository.findById(inboundOrderDTO.getOrderNumber()).isPresent();
    }
    /**MÉTODO COMPAREBATCH
     * Este método se encarga de comparar un batch que esta guardado en persistencia y lo compara con un bachtDto
     * correspondiente al batch que se desea actualizar , va campo a campo y actualiza la información.
     * @param batch
     * @param batchDTO
     */
    //------------------------------------------MÉTODO COMPAREBATCH--------------------------------------------------

    private void compareBatch(Batch batch,BatchDTO batchDTO){
        batch.setCurrentTemperature(batchDTO.getCurrentTemperature());
        batch.setMinimumTemperature(batchDTO.getMinimumTemperature());
        batch.setDueDate(batchDTO.getDueDate());
        batch.setManufacturingDate(batchDTO.getManufacturingDate());
        batch.setManufacturingTime(batchDTO.getManufacturingTime());
        batch.setCurrentQuantity(batchDTO.getInitialQuantity()-(batch.getInitialQuantity()-batch.getCurrentQuantity()));
        batch.setInitialQuantity(batchDTO.getInitialQuantity());
        batchRepository.save(batch);
    }

    /**MÉTODO UPDATE
     *Este método hace un update de la fecha de un inboundOrder y guarda en el repositorio.
     * @param inboundOrderDTO
     * @param inboundOrder
     */
    //------------------------------------------MÉTODO UPDATE ORDERDATE--------------------------------------------------
    private void updateOrderDate(InboundOrderDTO inboundOrderDTO,InboundOrder inboundOrder){
        inboundOrder.setOrderDate(inboundOrderDTO.getOrderDate());
        inboundOrderRepository.save(inboundOrder);
    }

    /**MÉTODO UPDATE UPDATESECTION
     * Este método se encarga de actualizar la sección con la que llega de un DTO, a su vez llega el tamaño de toda
     * la lista de baches.Actualiza los respectivos campos que componen la sección y guarda en persistencia.
     * @param inboundOrderDTO
     * @param inboundOrder
     * @param sizeBatch
     */
    //---------------------------------------MÉTODO UPDATE UPDATESECTION--------------------------------------------------
    private void updateSection(InboundOrderDTO inboundOrderDTO,InboundOrder inboundOrder,Integer sizeBatch){
        var section =inboundOrder.getSection();
        section.setBatchQuantity(sizeBatch);
        section.setState(inboundOrderDTO.getSection().getSectionCode());
        section.setWarehouseCode(inboundOrderDTO.getSection().getWarehouseCode());
        sectionRepository.save(section);
    }


    /**MÉTODO GETBATCHSTOCK
     * Este método se encarga de ajustar la respuesta con los campos requeridos para presentar al cliente,
     * este método es invocado desde el método saveBatch.El funcionamiento consiste en un stream que extraé
     * los datos del inboutOrderDto que llega por parametro y transforma las fechas a string para evitar los
     * campos adicionales que ofrece el localdate.
     * @param inboundOrderDTO
     * @return
     */
    //------------------------------------------MÉTODO GETBATCHSTOCK--------------------------------------------------
    private BatchStock getBatchResponse(InboundOrderDTO inboundOrderDTO){
        return new BatchStock(inboundOrderDTO.getBatchStock().stream().map(x->{
                    return new BatchResponse(x.getBatchNumber(),x.getProductId(),x.getCurrentTemperature(),x.getMinimumTemperature()
                    ,x.getInitialQuantity(),x.getCurrentQuantity(),x.getManufacturingDate().toString(),x.getManufacturingTime().toString(),x.getDueDate().toString());
                    }).collect(Collectors.toList()));
    }

    /**MÉTODO GETBATCHSIZE
     * método encargado de calcular ir por cada batch enviado por el usuario y sumar el tamaño de cada uno,
     * este calculo es necesario para posteriormente guardar una sección.El método consiste en un stream que mapea
     * el contenido de cada bache y con un getter extraé la cantidad.Finalmente con reduce se realiza la suma.
     * @param inboundOrderDTO
     * @return
     */
    //------------------------------------------MÉTODO GETBATCHSIZE--------------------------------------------------
    public Integer getBatchSize(InboundOrderDTO inboundOrderDTO){
        return  inboundOrderDTO
                .getBatchStock().stream()
                .map(x-> {
                    return x.getInitialQuantity();
                }).collect(Collectors.toList()).stream().reduce(0,(a,b)->a+b);
    }

    /**MÉTODO SAVESECTION
     * Este método se encarga de crear una sección con los datos de un dto que llega por parametro y el respectivo
     * tamaño de todos los baches enviados por el usuario.
     * @param inboundOrderDTO
     * @param sizeBatch
     * @return
     */
    //------------------------------------------MÉTODO SAVESECTION--------------------------------------------------
    private Section saveSection(InboundOrderDTO inboundOrderDTO, Integer sizeBatch){
        var section = new Section(null,inboundOrderDTO.getSection().getSectionCode(),inboundOrderDTO.getSection().getWarehouseCode(),0,0,sizeBatch);
        sectionRepository.save(section);
        return section;
    }

    /**MÉTODO RETURNBATCH
     * método encargado de regresar un bache con sus respectivos campos requeridos, este método es principalmente
     * invocado desde saveBatch.
     * @param batchDTO
     * @param inboundOrder
     * @return
     */
    //------------------------------------------MÉTODO RETURNBATCH--------------------------------------------------
    private Batch returnBatch(BatchDTO batchDTO,InboundOrder inboundOrder){
        var productTest =productsRepository.findById(batchDTO.getProductId()).orElseThrow(()-> new NonExistentProductException());
        var newBatch = new Batch(null,productTest,batchDTO.getCurrentTemperature(),batchDTO.getMinimumTemperature(),batchDTO.getDueDate(),batchDTO.getManufacturingDate(),batchDTO.getManufacturingTime(),batchDTO.getInitialQuantity(),batchDTO.getCurrentQuantity(),inboundOrder);
        return newBatch;
    }


}
