package com.mercadolibre.demo_bootcamp_spring.services.Batch;
import com.mercadolibre.demo_bootcamp_spring.dtos.*;
import com.mercadolibre.demo_bootcamp_spring.exceptions.ExistingInboundOrderId;
import com.mercadolibre.demo_bootcamp_spring.exceptions.NonExistentProductException;
import com.mercadolibre.demo_bootcamp_spring.models.Batch;
import com.mercadolibre.demo_bootcamp_spring.models.InboundOrder;
import com.mercadolibre.demo_bootcamp_spring.models.Section;
import com.mercadolibre.demo_bootcamp_spring.repository.BatchRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.InboundOrderRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.ProductsRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.SectionRepository;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class BatchService implements IBatchService {

    InboundOrderRepository inboundOrderRepository;
    SectionRepository      sectionRepository;
    BatchRepository        batchRepository;
    ProductsRepository     productsRepository;

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
        var searchedInbound =inboundOrderRepository.findById(inboundOrderDTO.getOrderNumber()).orElseThrow();
        updateSection(inboundOrderDTO,searchedInbound,sizeBatch);
        updateOrderDate(inboundOrderDTO,searchedInbound);

        var arrayBatch = inboundOrderDTO.getBatchStock().toArray();
        BatchDTO[] arrayBatchDto = new BatchDTO[arrayBatch.length];
        var arrayB = batchRepository.findByInboundOrder(inboundOrder.getInboundOrder().getOrderNumber()).toArray();
        Batch[] array = new Batch[arrayB.length];

        for(var i = 0; i < arrayBatchDto.length;i++){
            array[i] = (Batch) arrayB[i];
            arrayBatchDto[i] = (BatchDTO) arrayBatch[i];
            compareBatch(array[i],arrayBatchDto[i]);
        }
        return getBatchResponse(inboundOrderDTO);
    }
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
    private Section saveSection(InboundOrderDTO inboundOrderDTO,Integer sizeBatch){
        var section = new Section(null,inboundOrderDTO.getSection().getSectionCode(),"1",0,0,sizeBatch);
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
