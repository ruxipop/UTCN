package presentationLayer;


import businessLayer.DeliveryService;

public interface Observer {
    void update(DeliveryService rest);

}
