package cn.itcast.hotel.pojo;

import lombok.Data;

/**
 * ClassName:  RequestParams
 * Description:
 *
 * @author Jay
 * @version v1.0
 */
@Data
public class RequestParams {
    private String key;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String city;
    private String brand;
    private String starName;
    private String minPrice;
    private String maxPrice;
    private String location;
}
