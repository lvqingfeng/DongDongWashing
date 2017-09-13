package dongdongwashing.com.entity.cameraPosition;

import java.io.Serializable;

import dongdongwashing.com.entity.cameraPosition.AddressComponent.AddressComponent;

/**
 * Created by 沈 on 2017/4/17.
 */

public class CamearPositionResult implements Serializable {

    private String formatted_address;
    private AddressComponent addressComponent;

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public AddressComponent getAddressComponent() {
        return addressComponent;
    }

    public void setAddressComponent(AddressComponent addressComponent) {
        this.addressComponent = addressComponent;
    }

    @Override
    public String toString() {
        return "CamearPositionResult{" +
                "formatted_address='" + formatted_address + '\'' +
                ", addressComponent=" + addressComponent +
                '}';
    }
}
